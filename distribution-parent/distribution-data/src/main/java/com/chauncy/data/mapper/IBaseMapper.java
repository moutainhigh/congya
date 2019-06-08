package com.chauncy.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 定制版MyBatis IBaseMapper
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public interface IBaseMapper<T> extends BaseMapper<T> {


    Map<String, Object> findByUserName(@Param("username") String username);

    /**
     * 根据数据库表名和parentId获取所有子级，当parentId=null时获取所有数据
     * 表中必须存在parentId和id字段
     * @param parentId
     * @param tableName
     * @return
     */
    List<Long> getChildIds(@Param("parentId") Long parentId, @Param("tableName") String tableName);

    /**
     *判断id是否存在
     * @param value 值
     * @param tableName 表名称
     * @param field 数据要过滤的字段  如 id=#{value}
     * @return
     */
    int countById(@Param("value") Object value,@Param("tableName") String tableName,@Param("field")String field);

}
