package com.soundgroup.battery.conf;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
public class BeanConfig {


    @Bean(name = {"comboPooledDataSource"})
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.jdbc.Driver" );
    	cpds.setJdbcUrl("jdbc:mysql://192.168.109.26:3306/sharing_battery?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull" );
    	cpds.setUser("battery");
    	cpds.setPassword("@sd#battery");
    	cpds.setMinPoolSize(5);
    	cpds.setAcquireIncrement(5);
    	cpds.setMaxPoolSize(20);
        return cpds;
    }


    @Bean(name = {"serverBootstrap"})
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ServerBootstrap serverBootstrap() {
        return new ServerBootstrap();
    }

    @Bean(name = {"boss"})
    public EventLoopGroup boss() {
        return new NioEventLoopGroup(4);
    }

    @Bean(name = {"cubeTcpWorker"})
    public EventLoopGroup cubeTcpWorker() {
        return new NioEventLoopGroup(4);
    }

    @Bean(name = {"cubeHttpWorker"})
    public EventLoopGroup cubeHttpWorker() {
        return new NioEventLoopGroup(4);
    }

    @Bean(name = {"cubeWebSocketWorker"})
    public EventLoopGroup cubeWebSocketWorker() {
        return new NioEventLoopGroup(4);
    }

}
