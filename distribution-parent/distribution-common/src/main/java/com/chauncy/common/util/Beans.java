package com.chauncy.common.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Bean工具
 *
 * @author Liangzhongqiu
 * @date 2017/6/4 004
 * @time 13:52
 */
public class Beans {

    private static final String IGNORE_PROPERTY = "class";

    private Beans() {
    }

    /**
     * 将Map转为Bean
     *
     * @param orgMap   待转换的map
     * @param destBean 目标对象
     * @param <T>      泛型
     * @throws IntrospectionException       course by an exception happens during Introspection.
     * @throws ReflectiveOperationException course by an invoked method or an application tries to reflectively create an instance
     */
    public static <T> void transformMapToBean(Map<String, Object> orgMap, T destBean) throws IntrospectionException, ReflectiveOperationException {
        transformMapToBean(orgMap, destBean, true, null);
    }

    /**
     * 将Map转为Bean
     *
     * @param orgMap           待转换的map
     * @param destBean         目标对象
     * @param include          是否拷贝 orgBeanFieldSet的属性，如果为false将不拷贝orgBeanFieldSet的属性
     * @param destBeanFieldSet 指定需要拷贝的属性
     *                         如果 orgBeanFieldSet == null || orgBeanFieldSet.isEmpty(),则将拷贝所有的属性
     * @param <T>              泛型
     * @throws IntrospectionException       course by an exception happens during Introspection.
     * @throws ReflectiveOperationException course by an invoked method or an application tries to reflectively create an instance
     */
    public static <T> void transformMapToBean(Map<String, Object> orgMap, T destBean, boolean include, Set<String> destBeanFieldSet) throws IntrospectionException, ReflectiveOperationException {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(destBean.getClass()).getPropertyDescriptors();
        boolean execute = destBeanFieldSet == null || destBeanFieldSet.isEmpty();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            if ((execute) || (include && destBeanFieldSet.contains(propertyName)) || (!include && !destBeanFieldSet.contains(propertyName))) {
                propertyDescriptor.getWriteMethod().invoke(destBean, orgMap.get(propertyName));
            }
        }
    }

    /**
     * 将Bean 拷贝到 Map
     *
     * @param orgBean 带拷贝的Been
     * @param destMap 目标map
     * @param <T>     泛型
     * @throws IntrospectionException       course by an exception happens during Introspection.
     * @throws ReflectiveOperationException course by an invoked method or an application tries to reflectively create an instance
     */
    public static <T> void transformBeanToMap(T orgBean, Map<String, Object> destMap) throws IntrospectionException, ReflectiveOperationException {
        transformBeanToMap(orgBean, destMap, true, null);
    }

    /**
     * 将Bean 拷贝到 Map
     *
     * @param orgBean         待拷贝的Bean
     * @param destMap         目标map，存放Bean的属性以及其值
     * @param include         是否拷贝 orgBeanFieldSet的属性，如果为false将不拷贝orgBeanFieldSet的属性
     * @param orgBeanFieldSet 指定需要拷贝的属性
     *                        如果 orgBeanFieldSet == null || orgBeanFieldSet.isEmpty(),则将拷贝所有的属性
     * @param <T>             泛型
     * @throws IntrospectionException       course by an exception happens during Introspection.
     * @throws ReflectiveOperationException course by an invoked method or an application tries to reflectively create an instance
     */
    public static <T> void transformBeanToMap(T orgBean, Map<String, Object> destMap, boolean include, Set<String> orgBeanFieldSet) throws IntrospectionException, ReflectiveOperationException {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(orgBean.getClass()).getPropertyDescriptors();
        boolean execute = orgBeanFieldSet == null || orgBeanFieldSet.isEmpty();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            if (IGNORE_PROPERTY.equals(propertyName)) {
                continue;
            }
            if ((execute) || (include && orgBeanFieldSet.contains(propertyName)) || (!include && !orgBeanFieldSet.contains(propertyName))) {
                Object result = propertyDescriptor.getReadMethod().invoke(orgBean);
                destMap.put(propertyName, result);
            }
        }
    }

    /**
     * 拷贝；将源Bean(org)属性值拷贝至目标Bean(dest)属性中
     *
     * @param org      源Bean
     * @param dest     目标Bean
     * @param copyNull 是否将属性值为null也拷贝过去
     * @param <T>      源Bean泛型
     * @param <K>      目标Bean泛型
     * @throws IntrospectionException       course by an exception happens during Introspection.
     * @throws ReflectiveOperationException course by an invoked method or an application tries to reflectively create an instance
     */
    public static <T, K> void copyProperties(T org, K dest, boolean copyNull) throws IntrospectionException, ReflectiveOperationException {
        PropertyDescriptor[] orgPropertyDescriptors = Introspector.getBeanInfo(org.getClass()).getPropertyDescriptors();
        //同Bean拷贝
        if (org.getClass().equals(dest.getClass())) {
            for (PropertyDescriptor propertyDescriptor : orgPropertyDescriptors) {
                if (!IGNORE_PROPERTY.equals(propertyDescriptor.getName())) {
                    Object value = propertyDescriptor.getReadMethod().invoke(org);
                    if (!copyNull && (Objects.isNull(value))) {
                        continue;
                    }
                    propertyDescriptor.getWriteMethod().invoke(dest, value);
                }
            }
            return;
        }
        PropertyDescriptor[] destPropertyDescriptors = Introspector.getBeanInfo(dest.getClass()).getPropertyDescriptors();
        for (PropertyDescriptor orgPropertyDescriptor : orgPropertyDescriptors) {
            if (!IGNORE_PROPERTY.equals(orgPropertyDescriptor.getName())) {
                for (PropertyDescriptor destPropertyDescriptor : destPropertyDescriptors) {
                    if (orgPropertyDescriptor.getName().equals(destPropertyDescriptor.getName()) && orgPropertyDescriptor.getPropertyType().equals(destPropertyDescriptor.getPropertyType())) {
                        Object value = orgPropertyDescriptor.getReadMethod().invoke(org);
                        if (!copyNull && Objects.isNull(value)) {
                            continue;
                        }
                        destPropertyDescriptor.getWriteMethod().invoke(dest, value);
                        break;
                    }
                }
            }
        }
    }

}
