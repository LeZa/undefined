package com.soundgroup.battery.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {


    public static  Map<String,Object> sussRtn(String msg,Object data){

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("msg",msg);
        resultMap.put("code",0);
        resultMap.put("data",data);
        return  resultMap;
    }

    public static  Map<String,Object> failRtn( String msg){

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("msg",msg);
        resultMap.put("code",-1);
        resultMap.put("data",new ArrayList());
        return resultMap;
    }
}
