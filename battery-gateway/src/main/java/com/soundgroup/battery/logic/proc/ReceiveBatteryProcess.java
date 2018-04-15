package com.soundgroup.battery.logic.proc;

import com.soundgroup.battery.core.conn.Connection;
import com.soundgroup.battery.core.conn.ConnectionManager;
import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.logic.Process;
import com.soundgroup.battery.utils.CommUtils;


/**
 * @author sushile
 * @Description Receive  battery info
 * @date 20180329
 */
public class ReceiveBatteryProcess
        implements Process {

    public void excute(CubeMsg msg) {
  /*      java.sql.Connection connection = null;
        PreparedStatement preparedStatement = null;*/

        try {
            String mac = new String(msg.getData());
            /*String msgString = msg.getDataString();*/
            Connection conn = null;
            conn = CommUtils.getConn(msg.getCtx());
            conn.setMac(mac);
            ConnectionManager.getInstance().addToConns(conn.getMac(), conn);
         /*   AnnotationConfigApplicationContext
                    applicationContext = CubeRun.getApplicationContext();
            ComboPooledDataSource  comboPooledDataSource  = (ComboPooledDataSource) applicationContext.getBean("comboPooledDataSource");
            connection = comboPooledDataSource.getConnection();
            String sql = "select count(1) from Battery ery where ery.battery_number=?";
            preparedStatement  = connection.prepareStatement(sql);
            preparedStatement.setString(1, new String(msg.getData()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int len = resultSet.getInt(1);
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
            }*/
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
      /*  finally {
            if( preparedStatement != null ){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( connection != null ){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

}
