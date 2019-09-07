package com.chauncy.common.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-09-02 13:58
 *
 * guava常用操作谷类
 */

public class GuavaUtil {

    /**
     * list转字符串
     *
     * @param list
     * @param splitter
     * @param <T> 泛型T
     * @return
     */
    public static <T> String ListToString(List<T> list,String splitter){

        String result = Joiner.on(splitter).join(list);

        return result;
    }

    /**
     * list转字符串，跳过null
     *
     * @param list
     * @param splitter
     * @param <T> 泛型T
     * @return
     */
    public static <T> String ListToStringAndSkipNull(List<T> list,String splitter){

        String result = Joiner.on(splitter).skipNulls().join(list);

        return result;
    }

    /**
     * list转字符串，将null变成其他值
     *
     * @param list
     * @param nameless 将null变成其他值
     * @param splitter
     * @param <T> 泛型T
     * @return
     */
    public static <T> String ListToStringAndSkipNull(List<T> list,String nameless,String splitter){

        String result = Joiner.on(splitter).useForNull(nameless).join(list);

        return result;
    }

    /**
     *
     * map转String
     *
     * @param map
     * @param splitter1，分隔符1
     * @param splitter2，分隔符1
     * @param <T1>，泛型T1
     * @param <T2>，泛型T2
     * @return
     *
     * 使用:
     *  Map<String, Integer> salary = Maps.newHashMap();
     * 	    salary.put("John", 1000);
     * 	    salary.put("Jane", 1500);
     * 	    GuavaUtil.MapToString(salary," , "," =" );
     *
     */

    public static <T1,T2> String MapToString(Map<T1,T2> map,String splitter1,String splitter2){

        String result = Joiner.on(splitter1).withKeyValueSeparator(splitter2).join(map);

        return result;
    }

    /**
     * 字符串转list
     *
     * @param target 需要转换的字符串
     * @param objectClass 指定T的具体类型
     * @param splitter 分隔符
     * @param <T> 泛型
     * @return
     */
    public static <T> List<T> StringToList(String target,Class<T> objectClass,String splitter){

        List<T> result =(List<T>) Splitter.on(splitter).trimResults().splitToList(target);

        return result;
    }

    /**
     * 多个字符进行分割
     *
     * @param target 需要转换的字符串
     * @param objectClass 指定T的具体类型
     * @param pattern 分隔符正则表达
     * @param <T> 泛型
     * @return
     */
    public static <T> List<T> StringToListOnMultipleSeparator(String target,Class<T> objectClass,String pattern){

        List<T> result =(List<T>) Splitter.onPattern(pattern).omitEmptyStrings().splitToList(target);

        return result;
    }

    /**
     * 每隔多少字符进行分割
     *
     * @param target 需要转换的字符串
     * @param objectClass 指定T的具体类型
     * @param num 每隔几个字符串进行分割
     * @param <T> 泛型
     * @return
     */
    public static <T> List<T> StringToListOnSpecificLength(String target,Class<T> objectClass,Integer num){

        List<T> result =(List<T>) Splitter.fixedLength(num).splitToList(target);

        return result;
    }

    /**
     * 限制分割多少字后停止
     *
     * @param target 需要转换的字符串
     * @param objectClass 指定T的具体类型
     * @param num 限制分割多少字后停止
     * @param splitter 分隔符
     * @param <T> 泛型
     * @return
     */
    public static <T> List<T> StringToListLimitSplitting(String target,Class<T> objectClass,String splitter,Integer num){

        List<T> result =(List<T>) Splitter.on(splitter).limit(num).splitToList(target);

        return result;
    }



    /**
     * map转list
     *
     * @param target 需要转换的字符串
     * @param objectClass1 指定T的具体类型1
     * @param objectClass2 指定T的具体类型2
     * @param splitter1 分隔符1
     * @param splitter2 分隔符2
     * @param <T1> 泛型1
     * @param <T2> 泛型2
     * @return
     */
    public static <T1,T2> Map<T1,T2> MapToList(String target,Class<T1> objectClass1,Class<T2> objectClass2
    ,String splitter1,String splitter2){

        Map<T1,T2> result = (Map<T1,T2>)Splitter.on(splitter1).withKeyValueSeparator(splitter2).split(target);

        return result;
    }

}
