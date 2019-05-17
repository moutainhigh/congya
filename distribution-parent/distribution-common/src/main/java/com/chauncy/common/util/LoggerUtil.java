package com.chauncy.common.util;

import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 日志工具类，使用静态方法打印日志  无需每个类中定义日志对象
 * Logback对每个Logger对象做了缓存，每次调用LoggerFactory.getLogger(String name)时如果已存在则从缓存中获取不会生成新的对象;
 * 同时也不会有对象的创建与销毁造成的性能损失
 *
 * @author zhangrt
 */
public class LoggerUtil
{
    public static void error(Throwable e)
    {
        LoggerFactory.getLogger(getClassName()).error(getExceptionMessage(e));
    }

    public static void error(String msg)
    {
        LoggerFactory.getLogger(getClassName()).error(msg);
    }

    public static void error(String msg, Object... obj)
    {
        LoggerFactory.getLogger(getClassName()).error(msg, obj);
    }

    public static void warn(String msg)
    {
        LoggerFactory.getLogger(getClassName()).error(msg);
    }

    public static void warn(String msg, Object... obj)
    {
        LoggerFactory.getLogger(getClassName()).error(msg, obj);
    }

    public static void info(String msg)
    {
        LoggerFactory.getLogger(getClassName()).info(msg);
    }

    public static void info(String msg, Object... obj)
    {
        LoggerFactory.getLogger(getClassName()).info(msg, obj);
    }

    public static void debug(String msg)
    {
        LoggerFactory.getLogger(getClassName()).debug(msg);
    }

    public static void debug(String msg, Object... obj)
    {
        LoggerFactory.getLogger(getClassName()).debug(msg, obj);
    }

    // 获取调用 error,info,debug静态类的类名
    private static String getClassName()
    {
        return new SecurityManager()
        {
            public String getClassName()
            {
                return getClassContext()[3].getName();
            }
        }.getClassName();
    }

    /**
     * 打印异常栈
     *
     * @param ex
     * @return
     */
    public static String getExceptionMessage(Throwable ex)
    {

        StringBuffer sb = new StringBuffer();
        //exception信息
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null)
        {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        return sb.toString();
    }
}
