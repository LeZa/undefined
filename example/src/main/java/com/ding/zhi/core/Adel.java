package com.ding.zhi.core;

import com.ding.zhi.service.AdelService;
import com.ding.zhi.service.AdelServiceImpl;
import org.junit.jupiter.api.Test;

public class Adel {

    public static void main( String sck[] ){
        AdelService as = new AdelServiceImpl();
        as.print();
    }

    @Test
    public void testJunit(){
        System.out.println("sfds");
    }
}
