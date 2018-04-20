package com.service.api.me.curator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class CommonUtil {


    private CommonUtil(){};
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

    public static String bytesToHexString(byte[] src) {
        StringBuffer sb = new StringBuffer("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
            if (i != src.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (bytes);
    }

}
