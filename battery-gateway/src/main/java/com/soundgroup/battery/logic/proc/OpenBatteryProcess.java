package com.soundgroup.battery.logic.proc;

import com.soundgroup.battery.conf.ResponseEntity;
import com.soundgroup.battery.core.conn.Connection;
import com.soundgroup.battery.core.conn.ConnectionManager;
import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.event.ReplyEvent;
import com.soundgroup.battery.exception.IllegalDataException;
import com.soundgroup.battery.logic.Process;
import com.soundgroup.battery.server.CubeBootstrap;
import com.soundgroup.battery.utils.HexByteToolUtil;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Calendar;

/**
 * @Description  http open battery action
 * @author sushile
 * @date 20180329
 */
public class OpenBatteryProcess
    implements Process {

    public void excute(CubeMsg msg) throws IllegalDataException {
        String doing="error";
        Connection conn = ConnectionManager.getInstance().getConn(new String(msg.getData()));
        if(null!=conn){
            if (conn.getCtx().channel().isWritable()) {
                StringBuilder sb =  new StringBuilder(256);
                sb.append("*HQ,");
                sb.append( new String(msg.getData()) );
                sb.append(",S20,095540,1,0#");
                ByteBuf buff=(ByteBuf) Unpooled.wrappedBuffer(
                        HexByteToolUtil.hexStringToBytes(HexByteToolUtil.encode( sb.toString() )));
                String sn = new String( msg.getData() );
                conn.getCtx().writeAndFlush(buff).addListener( new OpenBatteryProcess.OpenDeviceFutureListener(sn));
                doing="ok";
                msg.getCtx().pipeline().writeAndFlush(doing);
            } else {
                msg.getCtx().pipeline().writeAndFlush(doing);
                conn.getCtx().pipeline().close();
            }
        }else{
            msg.getCtx().pipeline().writeAndFlush(doing);
            msg.getCtx().pipeline().close();
        }

    }


    /**
     * @Description openDeviceListener;
     * @author sushile
     */
    static class OpenDeviceFutureListener implements  GenericFutureListener{

        private String sn = null;

        public OpenDeviceFutureListener( String sn ){
            this.sn = sn;
        }

        public void operationComplete(Future future) throws Exception {
            Gson gson = new Gson();
            if( future.isSuccess() ){
                ReplyEvent replyEvent = CubeBootstrap.processRunnable.getReply(sn);
                replyEvent.setObj(gson.toJson(ResponseEntity.sussRtn("open device success"
                        ,new String())));
            }
            if( future.isCancelled() ){
                ReplyEvent replyEvent = CubeBootstrap.processRunnable.getReply(sn);
                replyEvent.setObj(gson.toJson(ResponseEntity.sussRtn("open device fail"
                        ,new String())));
            }
            }
        }

}
