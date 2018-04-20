package com.soundgroup.battery.core.common;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

/**
 * @Description MongoDB Operator
 * @author sushile
 * @date  20180419
 */
public class MongoDBOperator {

    private  MongoDBOperator(){}

    /**
     * @Description Get Collection
     * @param mongoDatabase
     * @param colle
     * @return
     */
    public static MongoCollection mongoCollection( MongoDatabase mongoDatabase,String colle ){
        MongoCollection<Document> collection = mongoDatabase.getCollection(colle);
        return  collection;
    }

    /**
     * @Description hasnext
     * @param colle
     * @param key
     * @return
     */
    public static FindIterable<Document> hasNext(MongoCollection mongoCollection,String colle,String key){
        FindIterable<Document> findIterable = mongoCollection.find(Filters.exists(key));
        return  findIterable;
    }

    /**
     * @Description update Operation
     * @param mongoCollection
     * @param colle
     * @param key
     * @param val
     * @param newVal
     */
    public static void updateOne( MongoCollection mongoCollection,String colle,String key,String val,String newVal){
        mongoCollection.updateOne(Filters.eq(key,val),new Document("$set",new Document( key,newVal)));
    }

    /**
     * @Description  Put Operation
     * @param key
     * @param val
     */
    public static void put( MongoCollection mongoCollectione,String colle,String key,String val ){
        Document document = new Document(key, val);
        mongoCollectione.insertOne( document);
    }
}
