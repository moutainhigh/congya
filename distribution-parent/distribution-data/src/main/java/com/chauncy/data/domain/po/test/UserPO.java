package com.chauncy.data.domain.po.test;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:30
 * @Version 1.0
 */
@TableName("tb_user")
@Data
@Alias("usersPO")
public class UserPO {

    private Long id;
    private String name;
    private Integer age;
    private Double salary;

}
