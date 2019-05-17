package com.chauncy.test.service.impl;

import com.chauncy.data.domain.po.test.UserPO;
import com.chauncy.data.mapper.test.UserMapper;
import com.chauncy.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:38
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> findByUserUame(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public Map<String, Object> findByUserUamee(String username) {
        return userMapper.findByUserNamee(username);
    }

    @Override
    public int insert(UserPO userPO) {
        return userMapper.insert(userPO);
    }

    @Override
    public List<UserPO> findByAll() {
        return userMapper.selectList(null);
    }

    @Override
    public List<Object> findByAlls() {
        return userMapper.findByAlls();
    }

    @Override
    public List<Map<String, Object>> find() {
        return userMapper.find();
    }

    @Override
    public List<List<Object>> findAll() {
        return userMapper.findAll();
    }

}
