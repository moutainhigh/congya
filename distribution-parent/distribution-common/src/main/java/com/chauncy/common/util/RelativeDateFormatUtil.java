package com.chauncy.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author yeJH
 * @since 2019/8/27 14:15
 */
public class RelativeDateFormatUtil {

    private static final long ONE_MINUTE = 60L;
    private static final long ONE_HOUR = 3600L;
    private static final long ONE_DAY = 86400L;
    private static final long ONE_MONTH = 2592000L;
    private static final long ONE_YEAR = 31536000L;

    private static final String ONE_SECOND_AGO = "刚刚";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static String format(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        long delta = now.toEpochSecond(ZoneOffset.of("+8")) - localDateTime.toEpochSecond(ZoneOffset.of("+8"));
        if (delta < 1L * ONE_MINUTE) {
            return ONE_SECOND_AGO;
        }
        if (delta < 59L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (now.toLocalDate().toEpochDay() - localDateTime.toLocalDate().toEpochDay() == 1) {
            return "昨天";
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < ONE_YEAR) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return df.format(localDateTime);
            /*long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;*/
        }
    }

    private static long toSeconds(long date) {
        return date;
    }

    private static long toMinutes(long date) {
        return date / ONE_MINUTE;
    }

    private static long toHours(long date) {
        return date / ONE_HOUR;
    }

    private static long toDays(long date) {
        return date / ONE_DAY;
    }

    private static long toMonths(long date) {
        return date / ONE_MONTH;
    }

    private static long toYears(long date) {
        return date / ONE_YEAR;
    }

}