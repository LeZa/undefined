package com.cube.logic.proc;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.apache.log4j.Logger;

import com.cube.core.conn.Connection;
import com.cube.core.conn.ConnectionManager;
import com.cube.event.CubeMsg;
import com.cube.event.ReplyEvent;
import com.cube.exception.IllegalDataException;
import com.cube.logic.Process;
import com.cube.server.CubeBootstrap;
import com.cube.utils.CommUtils;
import com.cube.utils.HexByteToolUtil;
import com.cube.utils.newStringUtils.Utils;


/**
 * @description HandShake
 * @author niulu
 * @version 0.1
 */
public class ReceiveWatchMessages implements Process
{
    private static final Logger LOG = Logger.getLogger(ReceiveWatchMessages.class);

    public void excute(CubeMsg msg)
        throws IllegalDataException
    {
        String mac = new String(msg.getData());
        Connection conn = null;
        conn = CommUtils.getConn(msg.getCtx());
        conn.setMac(mac);
        ConnectionManager.getInstance().addToConns(conn.getMac(), conn);

        String backString = Utils.wristwatchToPlatform(msg.getDataString()).get("responseMessage")
                + "";
        if (null != backString && !"".equals(backString)) {
            ByteBuf buff = (ByteBuf)Unpooled.wrappedBuffer(HexByteToolUtil.hexStringToBytes(HexByteToolUtil.encode(backString)));
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
