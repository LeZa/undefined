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

import java.util.*;

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
        mongoDatabase.createCollection("tempHeart");
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
 /*       MongoCollection<Document> collection = mongoDatabase.getCollection("temp");
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while( mongoCursor.hasNext() ){
                System.out.println ( mongoCursor.next() );
        }*/
        System.out.println("--------------------------------------------------------------------------------------------");
        MongoCollection<Document> collection1 = mongoDatabase.getCollection("heart");
        FindIterable<Document> findIterable1 = collection1.find();
        MongoCursor<Document> mongoCursor1 = findIterable1.iterator();
        while( mongoCursor1.hasNext() ){
            Document document =  mongoCursor1.next();
            Set<Map.Entry<String, Object>> setVal = document.entrySet();
            for( Map.Entry<String,Object> map : setVal ){
                String heartVal = String.valueOf(  map.getValue() ) ;
                if( heartVal.indexOf(",") > -1 ){
                    String[] getLenArr = heartVal.split(",");
                    System.out.print("SN..."+getLenArr[1]+getLenArr[2]+getLenArr[3]+getLenArr[4]+getLenArr[5]);//SN
                    System.out.print("    A..."+getLenArr[12]+getLenArr[13]+getLenArr[14]+getLenArr[15]);
                    System.out.print( "   battery..."+getLenArr[16]);//0-10
                    System.out.print( "   N..."+getLenArr[17]+getLenArr[18]+getLenArr[19]+getLenArr[20]);
                    System.out.print("  Status..."+getLenArr[25]+getLenArr[26]+getLenArr[27]+getLenArr[28]);
                    System.out.println("  TimeinMillis..."+getLenArr[getLenArr.length-1]);
                }
            }

        }
        /*MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            Document document = mongoCursor.next();
            System.out.println( document.get("201700541") );
        }*/
    }
}
