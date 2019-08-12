package com.chauncy.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    /**
     * LocalDate 转 Date
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * Date 转 LocalDate
     * @param date
     * @return
     */
    public static LocalDate datetoLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * 根据时间获取时间对应为第几周
     * @param localDate
     * @return
     */
    public static int getWeekOfYear(LocalDate localDate){
        Calendar cal = Calendar.getInstance();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        cal.setTime(Date.from(zdt.toInstant()));
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前时间所在年的周数
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前时间所在年的最大周数
      * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    /**
     * 获取某年的第几周的开始日期
      * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年的第几周的结束日期
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 获取当前时间所在周的开始日期
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 获取当前时间所在周的结束日期
      * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 获取当前时间的年份
      * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
      * @param date
     * @return
     */
    public static int getMonth (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }
}

