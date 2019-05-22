package com.chauncy.data.core;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chauncy.data.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 *  基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public abstract class AbstractService<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements Service<T> {


    @Autowired
    private Mapper<T> mapper;

    @Override
    public Map<String, Object> findByUserUame(String username) {
        return mapper.findByUserName(username);
    }

}
