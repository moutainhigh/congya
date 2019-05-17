package com.chauncy.test.repository;

import com.chauncy.test.domain.DO.GlobalDO;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 18:05
 * @Version 1.0
 */
public interface UserPository {

    List<GlobalDO> findAll();

    void insert(GlobalDO userDO);
}
