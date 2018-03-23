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
     * 如果5秒没有验证通过，关闭连接
     * @description DelayClose
     * @author niulu
     * @version 0.1
     * @date 2014年8月16日
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
                LOG.info("{}检查5秒内有没有通过验证，连接已经被unRegistered"+ctx.channel().toString());
                return;
            }
            String mac = CommUtils.getMacFromAttr(ctx);
            if (StringUtils.isBlank(mac)) {
                // 没有验证通过，关闭连接
                LOG.info("{} at 5秒内没有通过验证，关闭连接"+ctx.channel().toString());
                ctx.pipeline().close();
            }
        }

    }

    /**
     * 连接时
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // 新的Connection
        Connection conn = ConnectionManager.getInstance().getNewConnection(ctx);
        Attribute<Connection> connAttr = ctx.channel().attr(SysConst.CONN_KEY);
        connAttr.set(conn);
        // 生成随机的key，用来验证连接合法性
        String key = "HeartBeat..........";
        Attribute<String> secAttr = ctx.channel().attr(SysConst.SECURE_KEY);
        secAttr.set(key);
        // 生成待发送的key
        ByteBuf buf = ByteBufUtils.str2Buf(key);
        ByteBuf frame = ByteBufUtils.toFrameBuf(EventEnum.ONE.getVal(), buf);
        ReferenceCountUtil.release(buf);
        // 需要在当前线程马上发送
        ctx.channel().writeAndFlush(frame);
        LOG.info((new StringBuilder()).append("channelRegistered==conn.listAllConn=:").append(ConnectionManager.getInstance().listAllConn().size()).toString());
    }

    /**
     * 断开时
     */
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
     * 接收时
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String info=(String)msg;
 			String KeyName="";
 				//判断设备号
 				String tmpInfo = info.substring(1, info.length()-1);
// 				if(tmpInfo.contains("*")){
 					String[] targetMessageArray = tmpInfo.split("\\$");
 					//app调用的
 				/*	if("app".equals(targetMessageArray[0])){
 						String snStrings = targetMessageArray[1].substring(1, targetMessageArray[1].length()-1);
 						//LOG.debug("==========targetMessageArray[1]==============="+targetMessageArray[1]);
 						String[] commandMessageArray = snStrings.split("\\,");
 						String[] commandMessageArray22 = commandMessageArray[0].split("\\*");
 						StringBuffer tempKye = new StringBuffer();
 						tempKye
 						.append(commandMessageArray22[0])
 									   .append("*")
 									   .append(commandMessageArray22[1])
 									   .append("*");
 					 
 						KeyName=tempKye.toString();
 						LOG.debug(new Date()+"KeyName=:"+KeyName);
 						 CubeMsg aa=new CubeMsg();
						 aa.setType(EventEnum.TWO);
			        	 aa.setData(((String)KeyName).getBytes());//把主键放进去
			             aa.setCtx(ctx);
			             aa.setDataString(targetMessageArray[1]);//设置发送的指令信息
			             LOG.info((new StringBuilder()).append("app发送数据，服务器接收到=:").append(info).toString());
			             CubeBootstrap.processRunnable.pushUpMsg(aa);
 						
 					}else{*/
 		                //手表调用
 						//LOG.debug("==========targetMessageArray[0]==============="+targetMessageArray[0]);
 						/*String[] commandMessageArray = targetMessageArray[0].split("\\*");
 						StringBuffer tempKye = new StringBuffer();
 						tempKye.append(commandMessageArray[0])
 									   .append("*")
 									   .append(commandMessageArray[1])
 									   .append("*");
 						KeyName=tempKye.toString();*/
 						
 						CubeMsg aa=new CubeMsg();
 						 aa.setType(EventEnum.ONE);
 			        	 aa.setData(((String)"esc").getBytes());//把主键放进去
 			             aa.setCtx(ctx);
 			             aa.setDataString("123");
 			             LOG.info((new StringBuilder()).append("手表发送数据，服务器接收到=:").append(info).toString());
 			             CubeBootstrap.processRunnable.pushUpMsg(aa);
// 					}
// 				}else{
// 					LOG.error((new StringBuilder()).append("服务器接收数据格式错误=:").append(msg.toString()).toString());
//
// 				}
             
       /* } catch (Exception ex) {
        	LOG.error((new StringBuilder()).append("接收数据，字段截取异常=:").append(ConnectionManager.getInstance().listAllConn().size()).toString(),ex);
        	ctx.pipeline().close();*/
			
        } finally {
            // 如果是CubeMsg，不是ReferenceCounted，将不会release
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    /**
     * 空闲时
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                // 空闲时间过久，关闭连接
//                LOG.info("空闲超过心跳事件，断开连接.mac:{}"+CommUtils.getMacFromAttr(ctx));
//                ctx.pipeline().close();
            }
        }
    }
    
    /**
     * 异常时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //LOG.info((new StringBuilder()).append("exceptionCaught close channel异常关闭").toString());
        ctx.pipeline().close();
    }
  

}