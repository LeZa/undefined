package com.soundgroup.battery.logic.proc;

import com.soundgroup.battery.core.conn.Connection;
import com.soundgroup.battery.core.conn.ConnectionManager;
import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.event.ReplyEvent;
import com.soundgroup.battery.exception.IllegalDataException;
import com.soundgroup.battery.logic.Process;
import com.soundgroup.battery.server.CubeBootstrap;
import com.soundgroup.battery.utils.CommUtils;
import com.soundgroup.battery.utils.HexByteToolUtil;
import com.soundgroup.battery.utils.newStringUtils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 * @Description Receive  battery info
 * @author sushile
 * @date 20180329
 */
public class ReceiveBatteryProcess
        implements Process {

    public void excute(CubeMsg msg) throws IllegalDataException {

        String mac = new String(msg.getData());
        Connection conn = null;
        conn = CommUtils.getConn(msg.getCtx());
        conn.setMac(mac);
        ConnectionManager.getInstance().addToConns(conn.getMac(), conn);

        String backString = Utils.wristwatchToPlatform(msg.getDataString()).get("responseMessage")
                + "";
        if (null != backString && !"".equals(backString)) {
            ByteBuf buff = (ByteBuf) Unpooled.wrappedBuffer(HexByteToolUtil.hexStringToBytes(HexByteToolUtil.encode(backString)));
            conn.getCtx().writeAndFlush(buff);
        } else {
            msg.getCtx().pipeline().close();
        }

        ReplyEvent replyEvent = CubeBootstrap.processRunnable.getReply(mac);
        if (replyEvent == null) {
            return;
        }

        replyEvent.setObj(msg.getDataString());

        synchronized (replyEvent) {
            replyEvent.notifyAll();
        }
    }

}
