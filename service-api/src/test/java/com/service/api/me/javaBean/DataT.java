package com.service.api.me.javaBean;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataT {

   static  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {
//	        printWeekdays();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        System.out.println(cal.getTimeInMillis());
        String currentTimeStr = String.valueOf( cal.getTimeInMillis() );
     System.out.println( currentTimeStr.substring(4, 10) );
        /*    Calendar cal = Calendar.getInstance(Locale.CHINA);
	        cal.setFirstDayOfWeek(Calendar.MONDAY);
	        System.out.println("锟斤拷锟斤拷锟斤拷锟斤拷锟� " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));

	        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
	        
	        cal.add(Calendar.DATE, - day_of_week);
	        System.out.println("锟斤拷锟杰碉拷一锟斤拷: " + cal.getTimeInMillis());

	        cal.add(Calendar.DATE, 6);
	        System.out.println("锟斤拷锟斤拷末: " +  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
	        
	        try {
				java.util.Date date =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2016-03-20 23:59:59");
				cal.setTime(date);
				System.out.println(cal.getTimeInMillis());
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        System.out.println(Calendar.MONDAY);*/


        String day = "2016-03-08 06:04:00";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);

            cal.setTime( new Date() );
            cal.add(cal.DATE,-7);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
            System.out.println(dayStartTime(cal.getTime()));
            System.out.println( dayStartTime( cal.getTime()));
            cal.setTimeInMillis(dayStartTime(cal.getTime()));
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
//			long valFirst = firstDayOfMonth(date);
//			long valLast = lastDayOfMonth(date);
//			System.out.println("===============dateToLong:" + valFirst);
//			System.out.println("===============dateToLong:" + valLast);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//		String[] strArrMon = getMonth(2016,6);
//		for(String str:strArrMon){
//			System.out.println(str);
//		}
//		Calendar cal = Calendar.getInstance();
//		System.out.println(cal.get(Calendar.MONTH));
//		System.out.println(cal.get(Calendar.YEAR));


    }

    private static final int FIRST_DAY = Calendar.MONDAY;

    private static void printWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        for (int i = 0; i < 7; i++) {
            printDay(calendar);
            calendar.add(Calendar.DATE, 1);
        }
    }

    private static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    private static void printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd EE");
        System.out.println(dateFormat.format(calendar.getTime()));
    }



    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    // 指定日期的日期开始时间
    public static long dayStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    // 指定日期的日期结束时间
    public static long dayEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    // 指定时间的月开始时间
    public static long firstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        long val = dayStartTime(cal.getTime());
        return val / 1000;
    }

    // 指定时间的月结束时间
    public static long lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        long val = dayEndTime(cal.getTime());
        return val / 1000;
    }

    //当前时间的月开始时间
    @Test
    public void firstDayOfMonth(){
        //获取前月的第一天
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String  firstDay = format.format(cal_1.getTime());
        System.out.println("-----1------firstDay:"+firstDay);
    }
    //当前时间的月结束时间
    @Test
    public void lastDayOfMonth(){
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        String lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);
    }

    public static boolean isLeap(int year) {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    public static String[] getMonth(int year, int month) {
        int monCount = getDays(year, month);
        String[] strArrMon = new String[monCount];
        for (int i = 1; i <= monCount; i++) {
            String str = String.valueOf(year)+"-";
            if (month < 10) {
                str = str + "0" + month;
            } else {
                str = str + String.valueOf(month);
            }
            str = str+"-";
            if(i<10){
                str = str+"0"+i;
            }else{
                str = str+String.valueOf(i);
            }
            strArrMon[i - 1] = str;
        }
        if (strArrMon[10] != null && !strArrMon[10].equals("")) {
            return strArrMon;
        } else {
            return null;
        }
    }

}
