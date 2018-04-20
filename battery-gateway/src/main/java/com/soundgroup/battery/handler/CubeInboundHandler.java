package com.soundgroup.battery.handler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.MongoDBOperator;
import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.event.EventEnum;
import com.soundgroup.battery.server.CubeBootstrap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.lang.StringUtils;

import com.soundgroup.battery.core.common.SysConst;
import com.soundgroup.battery.core.conn.Connection;
import com.soundgroup.battery.core.conn.ConnectionManager;
import com.soundgroup.battery.utils.ByteBufUtils;
import com.soundgroup.battery.utils.CommUtils;

import org.bson.Document;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Sharable
public class CubeInboundHandler extends ChannelInboundHandlerAdapter {

    private MongoDatabase mongoDatabase;

    /**
     * @description DelayClose
     * @author niulu
     * @version 0.122
     */
    class DelayClose implements Runnable {

        private ChannelHandlerContext ctx;

        public DelayClose(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            Attribute<ScheduledFuture<?>> futureAttr = ctx.channel().attr(SysConst.DELAY_KEY);
            if(futureAttr.get() != null){
                futureAttr.remove();
            }
            if (!ctx.channel().isRegistered()) {
                return;
            }
            String mac = CommUtils.getMacFromAttr(ctx);
            if (StringUtils.isBlank(mac)) {
                ctx.pipeline().close();
            }
        }
    }

    public CubeInboundHandler(){
        AnnotationConfigApplicationContext applicationContext
                 = CubeRun.getApplicationContext();
        mongoDatabase = (MongoDatabase) applicationContext.getBean("mongoDatabase");
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Connection connection = ConnectionManager.getInstance().getNewConnection(ctx);
        Attribute<Connection> attribute = ctx.channel().attr(SysConst.CONN_KEY);
        attribute.set(connection);
        String key = "HeartBeat...";
        Attribute<String> secAttr = ctx.channel().attr(SysConst.SECURE_KEY);
        secAttr.set(key);
        ByteBuf buf = ByteBufUtils.str2Buf(key);
        ByteBuf frame = ByteBufUtils.toFrameBuf(buf);
        ReferenceCountUtil.release(buf);
        ctx.channel().writeAndFlush(frame);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Attribute<Connection> attr = ctx.channel().attr(SysConst.CONN_KEY);
        Connection conn = attr.get();
        if (StringUtils.isNotBlank(conn.getMac())) {
            ConnectionManager.getInstance().removeConn(conn.getMac());
        }
        attr.remove();
        ctx.channel().attr(SysConst.MAC_KEY).remove();
    }


    /**
     * Storage rules.
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

            String currentDay =  new SimpleDateFormat("yyyyMMdd").format( new java.util.Date());
            ByteBuf msgByteBuf = (ByteBuf)msg;
            System.out.println(Calendar.getInstance().getTimeInMillis()+"..."+ msgByteBuf.toString(CharsetUtil.UTF_8));
            String msgStr = msgByteBuf.toString( CharsetUtil.UTF_8);
            String[] msrArr = msgStr.split(",");
            String sn = msrArr[1]+msrArr[2]+msrArr[3]+msrArr[4]+msrArr[5];//SN
            CubeMsg cubeMsg=new CubeMsg();
            cubeMsg.setType(EventEnum.SAVE_BATTERY_INFO);
            cubeMsg.setData( sn.getBytes() );//SN
            cubeMsg.setCtx(ctx);
            cubeMsg.setDataString(msgStr);
            /** DaySN  storage start **/
            String heartMsg = msgStr.replaceAll(",","");
            String daySN = sn+currentDay;
            MongoCollection mongoCollection = MongoDBOperator.mongoCollection(mongoDatabase,"temp");
            MongoCollection mongoCollection1 = MongoDBOperator.mongoCollection(mongoDatabase,"heart");
            FindIterable<Document> findIterable = MongoDBOperator.hasNext(mongoCollection,"temp",daySN);
            if(findIterable.iterator().hasNext() ){
                Document document = findIterable.iterator().next();
                String oldVal = (String) document.get(daySN);
                String existsStr = "";
                String newVal = oldVal + ","+ heartMsg;
                MongoDBOperator.updateOne(mongoCollection,"temp",daySN,existsStr,newVal);
            }else{
                MongoDBOperator.put(mongoCollection,"temp",daySN,heartMsg);
            }
            /** DaySN  storage end **/
            String oldHeartVal = null;
            FindIterable<Document> findIterable1 = MongoDBOperator.hasNext(mongoCollection1,"heart",sn);
            if(findIterable1.iterator().hasNext() ){
                Document document = findIterable1.iterator().next();
                oldHeartVal = (String) document.get(sn);
            }
            MongoDBOperator.updateOne(mongoCollection1,"heart",sn,oldHeartVal,msgStr);
            CubeBootstrap.processRunnable.pushUpMsg(cubeMsg);
        } catch (Exception exp) {
            exp.printStackTrace();
        	ctx.pipeline().close();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.pipeline().close();
    }

}
