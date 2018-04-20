package com.soundgroup.battery.logic.http;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.soundgroup.battery.core.CubeRun;
import com.soundgroup.battery.core.common.MongoDBOperator;
import com.soundgroup.battery.logic.DefaultHttpProcess;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.bson.Document;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

@Component("dayBatteryExecAction")
public class DayBatteryExecAction
        extends DefaultHttpProcess {

    private MongoDatabase mongoDatabase;

    @Override
    protected void doExcute(ChannelHandlerContext ctx, FullHttpRequest req,
                            FullHttpResponse resp, Map<String, Object> paramMap, HttpPostRequestDecoder decoder) throws Exception {

        AnnotationConfigApplicationContext applicationContext
                = CubeRun.getApplicationContext();
        mongoDatabase = (MongoDatabase) applicationContext.getBean("mongoDatabase");
        MongoCollection mongoCollection1 = MongoDBOperator.mongoCollection(mongoDatabase,"temp");
        String sn = (String) paramMap.get("SN");
        String day =  (String) paramMap.get("day");
        String key = sn+day;
        FindIterable<Document> findIterable1 = MongoDBOperator.hasNext(mongoCollection1,"temp",key);
        Document document = findIterable1.iterator().next();
        String heartVal = (String) document.get(key);
        resp.content().writeBytes( heartVal.getBytes() );
        resp.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}
