package com.service.api.me.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

public class DbTest {

    @Test
    public void connectMongodb(){
        //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("192.168.109.37",27018);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("test", "test", "123".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs,credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        mongoDatabase.createCollection("heart");

        System.out.println("Connect to database successfully");
    }

    @Test
    public void putData(){
        ServerAddress serverAddress = new ServerAddress("192.168.109.37",27018);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("test", "test", "123".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs,credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = mongoDatabase.getCollection("sushile");
        Document document = new Document();
        document.append("201700541","24,21,70,05,52,36,07,35,29,17,04,18,22,36,11,39,05,11,34,99,11,3E,00,01,98,FF,FF,FF,FF,01,0F,00,00,00,00,00,00,01,CC,00,25,EF,11,4F,BD,1523954287629");

        collection.insertOne(document);
    }

    @Test
    public void getData(){
        ServerAddress serverAddress = new ServerAddress("192.168.109.37",27018);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha1Credential("test", "test", "123".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs,credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = mongoDatabase.getCollection("sushile");
        FindIterable<Document> findIterable = collection.find(Filters.exists("201700641"));
        if( findIterable.iterator().hasNext() ){
            System.out.println( "has Next");
        }else{
            print("does't have");
        }
        /*MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            Document document = mongoCursor.next();
            System.out.println( document.get("201700541") );
        }*/
    }
}
