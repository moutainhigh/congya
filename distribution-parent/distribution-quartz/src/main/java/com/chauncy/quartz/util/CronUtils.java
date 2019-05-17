//package com.chauncy.quartz.util;
//
//import org.quartz.CronExpression;
//
//import java.text.ParseException;
//import java.util.Date;
//
///**
// *cron表达式工具类
// *
// * @Author: HUANGWANCHENG
// * @Date: 2019/04/26 17:24
// * @Version 1.0
// */
//public class CronUtils {
//
//    /**
//     * 返回一个布尔值代表一个给定的Cron表达式的有效性
//     *
//     * @param cronExpression Cron表达式
//     * @return boolean 表达式是否有效
//     */
//    public static Boolean isValid(String cronExpression){
//        return CronExpression.isValidExpression(cronExpression);
//    }
//
//    /**
//     * 返回一个字符串值,表示该消息无效Cron表达式给出有效性
//     *
//     * @param cronExpression Cron表达式
//     * @return String 无效时返回表达式错误描述,如果有效返回null
//     */
//    public static String getInvalidMessage(String cronExpression) {
//        try {
//            new CronExpression(cronExpression);
//            return null;
//        } catch (ParseException pe) {
//            return pe.getMessage();
//        }
//    }
//
//    /**
//     * 根据给定的Cron表达式返回下一个执行时间
//     *
//     * @param cronExpression Cron表达式
//     * @return Date 下次Cron表达式执行时间
//     */
//    public static Date getNextExecution(String cronExpression) {
//
//        try {
//            CronExpression cronExpression1 = new CronExpression(cronExpression);
//            return cronExpression1.getNextInvalidTimeAfter(new Date(System.currentTimeMillis()));
//        } catch (ParseException e) {
//            throw new IllegalArgumentException(e.getMessage());
//        }
//    }
//}
