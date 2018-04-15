package com.soundgroup.battery.conf;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.soundgroup.battery.core.common.RocksDBHolder;
import com.soundgroup.battery.logic.http.OpenExecAction;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
public class BeanConfig {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driverclass}")
    private String jdbcDriverClass;

    @Value("${jdbc.username}")
    private String jdbcUser;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(name = {"comboPooledDataSource"})
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( jdbcDriverClass );
    	cpds.setJdbcUrl(jdbcUrl);
    	cpds.setUser(jdbcUser);
    	cpds.setPassword(jdbcPassword);
    	cpds.setMinPoolSize(5);
    	cpds.setAcquireIncrement(5);
    	cpds.setMaxPoolSize(20);
        return cpds;
    }

    private String shardId = "1";


    @Bean(name = {"rocksDBHolder"})
    public RocksDBHolder initStorage() throws Exception {
        RocksDBHolder rocksDBHolder = new RocksDBHolder(shardId);
        rocksDBHolder.init();
       return rocksDBHolder;
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
