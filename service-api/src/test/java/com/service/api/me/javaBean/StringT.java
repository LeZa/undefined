package com.service.api.me.javaBean;

public class StringT {

    public static void main(String sck[]){
        String str = "aa,bb";
        String[] strArr = str.trim().split("\\,");
        for(String s : strArr ){
            System.out.println(s);
        }
    }
}
