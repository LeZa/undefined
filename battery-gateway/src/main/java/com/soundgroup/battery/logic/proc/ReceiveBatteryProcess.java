package com.soundgroup.battery.logic.proc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.conn.Connection;
import com.soundgroup.battery.core.conn.ConnectionManager;
import com.soundgroup.battery.core.db.DbManager;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author sushile
 * @Description Receive  battery info
 * @date 20180329
 */

public class ReceiveBatteryProcess
        implements Process {

    public void excute(CubeMsg msg) {
        try {
            String mac = new String(msg.getData());
            Connection conn = null;
            conn = CommUtils.getConn(msg.getCtx());
            conn.setMac(mac);
            ConnectionManager.getInstance().addToConns(conn.getMac(), conn);

            AnnotationConfigApplicationContext
                    applicationContext = CubeRun.getApplicationContext();
            ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource) applicationContext.getBean("comboPooledDataSource");
            java.sql.Connection connection = comboPooledDataSource.getConnection();
            String sql = "select count(1) from Battery ery where ery.battery_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, new String(msg.getData()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int len = resultSet.getInt(1);
                String msgString = msg.getDataString();
                String[] msgArr = msgString.split(",");
                String sn = new String(msg.getData());
                if (len > 0) {
                    String  updateSql = "update Battery set point_x=?,point_y=?,battery_power=?" +
                            "where battery_number=?";
                    preparedStatement = connection.prepareStatement(updateSql);
                    String point_x = msgArr[12] + msgArr[13] + msgArr[14] + msgArr[15];
                    preparedStatement.setDouble(1,Double.parseDouble( point_x ));
                    String point_y = msgArr[17] + msgArr[18] + msgArr[19] + msgArr[20];
                    preparedStatement.setDouble(2,Double.parseDouble(point_y));
                    String battery = msgArr[16];//0-10
                    preparedStatement.setDouble(3,Double.parseDouble(battery));
                    preparedStatement.setString(4,sn);
                    preparedStatement.execute();
                    //update
                } else if (len == 0) {
                    //insert
                    String insertSql = "insert into Battery(battery_number,point_x,point_y,battery_power) values(?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.setString(1, sn);
                    String point_x = msgArr[12] + msgArr[13] + msgArr[14] + msgArr[15];
                    preparedStatement.setDouble(2, Double.parseDouble(point_x));
                    String point_y = msgArr[17] + msgArr[18] + msgArr[19] + msgArr[20];
                    preparedStatement.setDouble(3, Double.parseDouble(point_y));
                    String battery = msgArr[16];//0-10
                    preparedStatement.setDouble(4, Double.parseDouble(battery));
                    preparedStatement.execute();
                }
            }

          /*  String backString = Utils.wristwatchToPlatform(msg.getDataString()).get("responseMessage")
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
            }*/
        } catch (Exception exp) {
           exp.printStackTrace();
        }
    }

}
