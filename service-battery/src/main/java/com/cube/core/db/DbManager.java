package com.cube.core.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description DbManager
 * @author niulu
 * @version 0.1
 */
@Component
public class DbManager {

    @Autowired
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }
    

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public  void close(Connection conn) throws SQLException{
        conn.close();
    }


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
    

}
