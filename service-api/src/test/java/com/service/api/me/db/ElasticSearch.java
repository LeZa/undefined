package com.service.api.me.db;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticSearch {

    private final static String HOST = "192.168.89.54";

    private final static int PORT = 9200;


    private TransportClient client;

    @Before
    public void getClient() throws UnknownHostException {
            client = new PreBuiltTransportClient( Settings.EMPTY)
                    .addTransportAddress( new TransportAddress( InetAddress.getByName(HOST),PORT));
            client.close();
    }

    @Test
    public void printData(){
        IndexResponse getResponse = client.prepareIndex("maintest","apache_access").get();
        System.out.println( getResponse.toString() );

    }
}
