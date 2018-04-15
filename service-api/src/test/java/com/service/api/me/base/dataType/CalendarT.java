package com.service.api.me.base.dataType;

import org.junit.Test;

import java.util.Calendar;

public class CalendarT {


    @Test
    public void secondsTime(){
        long timeMill = Calendar.getInstance().getTimeInMillis();
        String str = String.valueOf(timeMill/1000);
        System.out.println( str );

    }
}
