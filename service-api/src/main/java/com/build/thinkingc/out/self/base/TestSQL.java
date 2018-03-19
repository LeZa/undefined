package com.build.thinkingc.out.self.base;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TestSQL {

	public static final String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String passwordSql = "123456";
	public Connection conn = null;
	public PreparedStatement pst = null;
	public ResultSet rs = null;

	private void GetConn() throws SQLException, ClassNotFoundException {
		try{
		String sqlI = "insert into user(id,userId,userName,cellPhone,token,createDate,lastLDate,"
				+ "fontSize,deptNo,deptName,smartLunid,mail,mail2,telephoneNumber) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		 Class.forName(driver);// 指定连接类型
		conn = DriverManager.getConnection(url, user, passwordSql);// 获取连接
		pst = conn.prepareStatement(sqlI);
		String id = UUID.randomUUID().toString();
		pst.setString(1, id);
		pst.setString(2, "25000");
		pst.setString(3, "曹玮�?13");
		pst.setString(4, "138110120130");
		pst.setString(5, "1");
		pst.setString(6, "1");
		pst.setString(7, "1");
		pst.setString(8, "1");
		pst.setString(9, "1");
		pst.setString(10, "IT服务中心");
		pst.setString(11, "1");
		pst.setString(12, "bjhuaxia2008@163.com");
		pst.setString(13, "1");
		pst.setString(14, "1");
		
		
		pst.execute();
		}finally{
			if( conn != null){
				conn.close();
			}
		}
		
	}
	
	public static void getMaxConnectionNum() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Driver driver = (Driver) (Class.forName(TestSQL.driver).newInstance());
		DriverManager.registerDriver(driver); // 注册 JDBC 驱动程序
		Connection conn = DriverManager.getConnection(url, user, passwordSql);
		// 假如这是第一次创建数据库连接，即检查数据库，获得此数据库答应支持的
		// 最大客户连接数目
		// connections.size()==0 表示目前没有连接己被创建
			DatabaseMetaData metaData = conn.getMetaData();
			int driverMaxConnections = metaData.getMaxConnections();
			System.out.println( driverMaxConnections );
			// 数据库返回的 driverMaxConnections 若为 0 ，表示此数据库没有最大
			// 连接限制，或数据库的最大连接限制不知道
			// driverMaxConnections 为返回的一个整数，表示此数据库答应客户连接的数目
			// 假如连接池中设置的最大连接数量大于数据库答应的连接数目 , 则置连接池的最大
			// 连接数目为数据库答应的最大数目
	}
	
	public static void main(String sck[]){
		try {
			new TestSQL().getMaxConnectionNum();
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}
}
