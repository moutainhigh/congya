package com.chauncy.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * 定制版MyBatis Mapper
 *
 * @author huangwancheng
 * @since 2019-05-22
 */
public interface Mapper<T> extends BaseMapper<T> {

    Map<String, Object> findByUserName(@Param("username") String username);

}
