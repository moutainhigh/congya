package com.chauncy.data.core;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public interface Service<T> extends IService<T>{

    Map<String, Object> findByUserUame(String username);

    /**
     * 根据数据库表名和parentId获取所有子级，当parentId=null时获取所有数据
     * 表中必须存在parentId和id字段
     * @param parentId
     * @param tableName
     * @return
     */
    List<Long> findChildIds(@Param("parentId") Long parentId, @Param("tableName") String tableName);


    /**
     * 根据数据库表名和id获取所有父级包括本身
     * 表中必须存在id字段
     * @param id
     * @param tableName
     * @return
     */
    List<Long> findParentIds(@Param("id") Long id, @Param("tableName") String tableName);

    /**
     *根据name和数据库名称查询对应的id
     * @param names
     * @param tableName
     * @param concatWhereSql 拼接wheresql  以and开头
     * @return
     */
    List<Long> findIdByNamesInAndTableName(List<String> names, String tableName, String concatWhereSql);
}
