package com.chauncy.data.mapper.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019-06-05 18:04
 **/
public interface CommonMapper  {

    /**
     * 根据数据库表名和parentId获取所有子级，当parentId=null时获取所有数据
     * 表中必须存在parentId和id字段
     * @param parentId
     * @param tableName
     * @return
     */
    List<Long> getChildIds(@Param("parentId") Long parentId, @Param("tableName") String tableName);
}
