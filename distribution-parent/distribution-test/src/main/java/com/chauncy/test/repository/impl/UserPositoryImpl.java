package com.chauncy.test.repository.impl;

import com.chauncy.test.domain.DO.GlobalDO;
import com.chauncy.test.repository.UserPository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 18:06
 * @Version 1.0
 */
@Repository
public class UserPositoryImpl implements UserPository {

    @Autowired
    private MongoTemplate template;

    @Override
    public List<GlobalDO> findAll() {
        return template.findAll(GlobalDO.class);
    }

    @Override
    public void insert(GlobalDO userDO) {
        template.insert(userDO);
    }
}
