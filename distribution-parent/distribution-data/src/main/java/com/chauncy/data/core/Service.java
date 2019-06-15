package com.chauncy.data.core;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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
}
