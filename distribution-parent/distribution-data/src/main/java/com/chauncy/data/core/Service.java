package com.chauncy.data.core;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public interface Service<T> extends IService<T>{

    Map<String, Object> findByUserUame(String username);
}
