package com.chauncy.data.core;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 *  基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public abstract class AbstractService<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements Service<T> {


    protected static int defaultPageSize = 10;

    protected static int defaultPageNo = 1;

    protected static String defaultSoft="sort desc";

    @Autowired
    private IBaseMapper<T> IBaseMapper;

    @Override
    public Map<String, Object> findByUserUame(String username) {
        return IBaseMapper.findByUserName(username);
    }

    @Override
    public List<Long> findChildIds(@Param("parentId") Long parentId, @Param("tableName") String tableName){
        return IBaseMapper.loadChildIds(parentId,tableName);
    }


    @Override
    public List<Long> findParentIds(@Param("id") Long id, @Param("tableName") String tableName){
        return IBaseMapper.loadParentIds(id,tableName);
    }


    /**
     *根据name和数据库名称查询对应的名字
     * @param names
     * @param tableName
     * @return
     */
    @Override
    public List<Long> findIdByNamesInAndTableName(List<String> names, String tableName, String concatWhereSql){
        return IBaseMapper.loadIdByNamesInAndTableName(names,tableName,concatWhereSql);
    }

}
