package com.chauncy.common.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Auther: zhangrt
 * @DATE 2019-05-17
 * @Description:
 */
public class ListUtil
{

    public static List removeDuplicate(List list)
    {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    public static String listAppendToString(List list)
    {
        if (isListNullAndEmpty(list))
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : list)
        {
            if (object != null)
                stringBuilder.append(object.toString() + ",");
        }
        String str = stringBuilder.toString();
        if (StringUtils.isBlank(str))
            return "";
        return str.substring(0, str.length() - 1);
    }

    public static boolean isListNullAndEmpty(List list)
    {
        return (list == null || list.isEmpty());
    }

    public static List removeDuplicateWithOrder(List list)
    {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); )
        {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    public static List removeDuplicateForeach(List list)
    {
        List listTemp = new ArrayList();
        if (isListNullAndEmpty(list))
            return listTemp;
        for (int i = 0; i < list.size(); i++)
        {
            if (!listTemp.contains(list.get(i)))
            {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }



    private static int getResult(double l)
    {
        int result;
        if (l > 0)
        {
            result = 1;
        }
        else if (l < 0)
        {
            result = -1;
        }
        else
        {
            result = 0;
        }
        return result;
    }

    /***
     * 对集合进行深拷贝 注意需要对泛型类进行序列化(必须实现Serializable)
     *
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T> List<T> depCopy(List<T> srcList) throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(srcList);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(byteIn);
        List<T> destList = (List<T>) inStream.readObject();
        return destList;
}

    /**
     * 使用对象属性来进行去重  使用例子 : list.stream().filter(distinctByKey(b -> b.getName()))
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor)
    {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}