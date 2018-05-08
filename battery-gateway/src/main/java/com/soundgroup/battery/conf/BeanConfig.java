package com.soundgroup.battery.conf;

import com.build.db.rmi.RocksdbService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

    @Value("${mongodb.host}")
    private String mongoDBHost;

    @Value("${mongodb.port}")
    private int mongoDBPort;

    @Value("${mongodb.user}")
    private String mongoDBUser;

    @Value("${mongodb.instance}")
    private String mongoDBInstance;

    @Value("${mongodb.password}")
    private String mongoDBPassword;


    @Value("${rmi.url}")
    private String rmiUrl;

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

    /**
     * @Description rocksdbService
     * @return
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    @Bean( name={"RocksdbService"})
    public RocksdbService getRocksdbService()
            throws RemoteException, NotBoundException, MalformedURLException {
            RocksdbService rocksdbService = (RocksdbService) Naming.lookup(rmiUrl);
            return rocksdbService;
    }

    /**
     * @Description  mongodbService
     * @return
     */
    @Bean(name={"mongoDatabase"})
    public MongoDatabase mongoDatabase(){
        ServerAddress serverAddress = new ServerAddress(mongoDBHost,mongoDBPort);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential(mongoDBUser,
                mongoDBInstance, mongoDBPassword.toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs,credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoDBInstance);
        return mongoDatabase;
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
