package com.soundgroup.battery.conf;

import java.util.HashMap;
import java.util.Map;

public final  class CommonUtil {


    private  CommonUtil(){};
    /**
     * @Description  url content to map
     * @param content
     * @return
     */
    public static Map<String,Object> url2Map(String content){
        Map<String,Object>  paramMap = new HashMap<String,Object>();
        if( content.indexOf("&") > -1){
            String[] paramArr = content.split("&");
            for(String keyvalue:paramArr){
                String[] pair = keyvalue.split("=");
                if(pair.length==2){
                    paramMap.put(pair[0], pair[1]);
                }
            }
        }else{
            String[] pair = content.split("=");
            if(pair.length==2){
                paramMap.put(pair[0], pair[1]);
            }
        }
        return paramMap;

    }
}
