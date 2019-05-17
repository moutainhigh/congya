package com.chauncy.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/29 15:31
 * @Version 1.0
 */
public class DateFormatUtil {
    /**
     * 字符串转为日期格式
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date stringFormatDate(String dateString) throws ParseException {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = bartDateFormat.parse(dateString);
        return date;
    }
    /**
     * 字符串转为日期格式
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date stringFormatDateTime(String dateString) throws ParseException{
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = bartDateFormat.parse(dateString);
        return date;
    }

    /**
     * 字符串转为日期格式
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date stringFormatDateTime2(String dateString) throws ParseException{
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = bartDateFormat.parse(dateString);
        return date;
    }

    /**
     * 将时间格式化为含时分秒的字符串
     * @param date
     * @return
     * @throws ParseException
     */
    public static String dateTimeFormatString(Date date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 将时间格式化为不含时分秒的字符串
     * @param date
     * @return
     * @throws ParseException
     */
    public static String dateFormatString(Date date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public final static Date[] strToDateArray(String dateStr) {
        java.sql.Date date_temp = java.sql.Date.valueOf(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date_temp);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.MINUTE, 0);
        calendar.add(Calendar.SECOND, 0);
        Date beginDate = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 通过实践搜索,字符串转日期
     * @param beginDateStr
     *             开始时间
     * @param endDateStr
     *             结束时间
     * @return
     */
    public static Date[] strToDateArray(String beginDateStr,String endDateStr){
        try {
            if ("".equals(endDateStr) || endDateStr.length()==0) {
                endDateStr = beginDateStr;
            }
            Date beginDate = stringFormatDate(beginDateStr);
            Date endDate = stringFormatDate(endDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            beginDate = calendar.getTime();
            calendar.setTime(endDate);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            endDate = calendar.getTime();
            return new Date[]{beginDate,endDate};
        } catch (Exception e) {
            throw new RuntimeException("时间格式化错误");
        }
    }

    public static Date getTheDayOfEndTime(String dateString ) throws ParseException {
        Date date = null;
        if(dateString.contains(" ")){
            date = stringFormatDateTime(dateString);
        }else{
            date = stringFormatDate(dateString);
        }
        return getTheDayOfEndTime(date);
    }

    public static Date getTheDayOfEndTime(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date getTheDayOfStartTime(String dateString ) throws ParseException {
        Date date = null;
        if(dateString.contains(" ")){
            date = stringFormatDateTime(dateString);
        }else{
            date = stringFormatDate(dateString);
        }
        return getTheDayOfStartTime(date);
    }

    public static Date getTheDayOfStartTime(Date date) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
}

