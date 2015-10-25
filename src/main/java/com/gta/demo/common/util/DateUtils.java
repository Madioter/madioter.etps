package com.gta.demo.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yi.wang1 on 2014/12/29.
 */
public class DateUtils {
    public static Date getDate(long time) {
        long days = (time + 8 * 60 * 60) / (60 * 60 * 24 * 1000);
        return new Date(days * 24 * 60 * 60 * 1000);
    }

    public static Date nextDate(Date date) {
        long time = date.getTime() + 60 * 60 * 24 * 1000;
        return new Date(time);
    }

    public static String simpleDateFormat(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取月份的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayFromMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        return calendar.getTime();
    }
    
    public static Date simpleDateFormat(String dateString,String pattern){
    	if(dateString == null || "".equals(dateString)){
    		return null;
    	}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try{
        	return simpleDateFormat.parse(dateString);
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
    }
    
    /**
     * 获取今天的字符串
     * @return “20151201”
     */
    public static String getTodayString(){
    	Calendar calendar = Calendar.getInstance();
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	return df.format(calendar.getTime() );
    }
}
