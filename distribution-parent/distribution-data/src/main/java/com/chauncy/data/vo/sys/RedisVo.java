package com.chauncy.data.vo.sys;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author huangwancheng
 * @create 2019-05-27 14:32
 */
@Data
@AllArgsConstructor
public class RedisVo {

    private String key;

    private String value;
}
