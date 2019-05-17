package com.chauncy.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chauncy.data.domain.po.UserPO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:33
 * @Version 1.0
 */
public interface UserMapper extends BaseMapper<UserPO> {

    Map<String, Object> findByUserName(@Param("username") String username);

    List<Map<String, Object>> find();

    List<Object> findByAlls();

    List<List<Object>> findAll();

    @MapKey("名字")
    Map<String,Object> findByUserNamee(String username);

    int insert(UserPO userPO);
}
