package com.service.api.me.base.dataType;

import org.junit.Test;

public class StringT {

    @Test
    public void void16(){

        String getLen = "24,21,70,05,52,36,04,41,37,04,04,18,22,36,12,20,05,11,34,99,04,1E,00,00,00,FF,FF,FF,FF,01,0F,05,00,00,00,00,00,01,CC,00,25,EF,11,4F,C1";
        System.out.println( getLen.length() );
        String[] getLenArr = getLen.split(",");
        System.out.println("SN..."+getLenArr[1]+getLenArr[2]+getLenArr[3]+getLenArr[4]+getLenArr[5]);//SN
        System.out.println("A..."+getLenArr[12]+getLenArr[13]+getLenArr[14]+getLenArr[15]);
        System.out.println( "battery..."+getLenArr[16]);//0-10
        System.out.println( "N..."+getLenArr[17]+getLenArr[18]+getLenArr[19]+getLenArr[20]);

        int xe = 24;
        System.out.println(    String.format("%x",xe));
        System.out.println(String.format("'x':将参数格式化为十六进制整数。%x", 17));
    }
}
