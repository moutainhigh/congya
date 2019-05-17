package com.chauncy.test.service;


import com.chauncy.data.domain.po.test.UserPO;

import java.util.List;
import java.util.Map;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:37
 * @Version 1.0
 */
public interface UserService {

    Map<String, Object> findByUserUame(String username);

    List<UserPO> findByAll();

    List<Object> findByAlls();

    List<Map<String,Object>> find();

    List<List<Object>> findAll();

    Map<String, Object> findByUserUamee(String username);

    int insert(UserPO userPO);
}
