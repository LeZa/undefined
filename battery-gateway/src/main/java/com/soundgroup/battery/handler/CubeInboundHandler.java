package com.soundgroup.battery.handler;

import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.RocksDBHolder;
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

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Sharable
public class CubeInboundHandler extends ChannelInboundHandlerAdapter {

    private RocksDBHolder rocksDBHolder;

    public CubeInboundHandler(){
        AnnotationConfigApplicationContext
                applicationContext = CubeRun.getApplicationContext();
        rocksDBHolder = (RocksDBHolder) applicationContext.getBean("rocksDBHolder");
    }

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
            //LOG.info((new StringBuilder()).append("remove a connetion:{}, from connectionmanager=:").append(conn.getMac()).toString());
            ConnectionManager.getInstance().removeConn(conn.getMac());
        }
//        Attribute<ScheduledFuture<?>> futureAttr = ctx.channel().attr(SysConst.DELAY_KEY);
//        if(futureAttr.get() != null){
//            LOG.info("remove future");
//            futureAttr.get().cancel(false);
//            futureAttr.remove();
//        }
        //LOG.info((new StringBuilder()).append("remove a connection==remoteAddress=:").append(ctx.channel().remoteAddress()).toString());
        attr.remove();
        ctx.channel().attr(SysConst.MAC_KEY).remove();
        //LOG.error((new StringBuilder()).append("断开时，异常关闭处理").toString());
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
            String daySN =  sn+currentDay;
            if( rocksDBHolder.getResource().get(daySN.getBytes()) != null){
                byte[] exists  = rocksDBHolder.getResource().get(daySN.getBytes());
                byte[] newVal = heartMsg.getBytes();
                String existsStr =  new String( exists );
                String newStr =  new String( newVal );
                String putStr = existsStr + ","+ newStr;
                rocksDBHolder.getResource().put(daySN.getBytes(),putStr.getBytes());
            }else{
                rocksDBHolder.getResource().put(daySN.getBytes(),heartMsg.getBytes());
            }
            /** DaySN  storage end **/
            long curTimeinMillis = Calendar.getInstance().getTimeInMillis();
            String newMsgStr = heartMsg = msgStr+","+String.valueOf( curTimeinMillis/1000 );
            rocksDBHolder.getResource().put(sn.getBytes(),newMsgStr.getBytes());
            CubeBootstrap.processRunnable.pushUpMsg(cubeMsg);
        } catch (Exception ex) {
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
