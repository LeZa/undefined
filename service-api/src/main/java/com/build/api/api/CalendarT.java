package com.build.api.api;

import java.util.Calendar;
import java.util.Date;

public class CalendarT {

    /**
     * @param args
     */
    public static void main(String[] args) {
    }

    //指定日期的后一天
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
    
	//指定日期的日期开始时间
	public static long dayStartTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	//指定日期的日期结束时间
	public static long dayEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTimeInMillis();
	}
	
	//指定时间的月开始时间
	public static long firstDayOfMonth( Date date){
		  Calendar   cal=Calendar.getInstance();
		  cal.setTime(date);
		  cal.add(Calendar.MONTH, 0);
		  cal.set(Calendar.DAY_OF_MONTH,1);
		  long val = dayStartTime(cal.getTime());
		  return val/1000;
	}
	
	//指定时间的月结束时间
	public static long lastDayOfMonth( Date date ){
		  Calendar   cal=Calendar.getInstance();
		  cal.setTime(date);
		  cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		  long val = dayEndTime(cal.getTime());
		  return val/1000;
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