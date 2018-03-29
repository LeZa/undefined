package com.cube.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.cube.core.common.SysConst;
import com.cube.core.conn.Connection;
import com.cube.core.conn.ConnectionManager;
import com.cube.event.CubeMsg;
import com.cube.event.EventEnum;
import com.cube.server.CubeBootstrap;
import com.cube.utils.ByteBufUtils;
import com.cube.utils.CommUtils;

@Sharable
public class CubeInboundHandler extends ChannelInboundHandlerAdapter {
	private static final Logger LOG = Logger.getLogger(CubeInboundHandler.class);

    /**
     * @description DelayClose
     * @author niulu
     * @version 0.1
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
        Connection conn = ConnectionManager.getInstance().getNewConnection(ctx);
        Attribute<Connection> connAttr = ctx.channel().attr(SysConst.CONN_KEY);
        connAttr.set(conn);
        String key = "HeartBeat";
        Attribute<String> secAttr = ctx.channel().attr(SysConst.SECURE_KEY);
        secAttr.set(key);
        ByteBuf buf = ByteBufUtils.str2Buf(key);
        ByteBuf frame = ByteBufUtils.toFrameBuf(EventEnum.ONE.getVal(), buf);
        ReferenceCountUtil.release(buf);
        ctx.channel().writeAndFlush(frame);
        LOG.info((new StringBuilder()).append("channelRegistered==conn.listAllConn=:").append(ConnectionManager.getInstance().listAllConn().size()).toString());
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


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

             CubeMsg aa=new CubeMsg();
             aa.setType(EventEnum.ONE);
             aa.setData("esc".getBytes());//SN
             aa.setCtx(ctx);
             aa.setDataString("123");
             CubeBootstrap.processRunnable.pushUpMsg(aa);
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
