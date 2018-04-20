package com.soundgroup.battery.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {


    /**
     * @Description  Definition suss msg and object
     * @param msg
     * @param data
     * @return
     */
    public static  Map<String,Object> sussRtn(String msg,Object data){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("msg",msg);
        resultMap.put("code",0);
        resultMap.put("data",data);
        return  resultMap;
    }

    /**
     * @Description Definition fail msg
     * @param msg
     * @return
     */
    public static  Map<String,Object> failRtn( String msg){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("msg",msg);
        resultMap.put("code",-1);
        resultMap.put("data",new ArrayList());
        return resultMap;
    }

    /**
     * @Description Definition fail msg and object
     * @param msg
     * @param data
     * @return
     */
    public static  Map<String,Object> failRtn( String msg,String code,Object data){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("msg",msg);
        resultMap.put("code",-1);
        resultMap.put("data",new ArrayList());
        return resultMap;
    }
}
