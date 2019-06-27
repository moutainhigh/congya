package com.chauncy.data.dto.manage.user.add;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/6/26 21:39
 **/
@Data
public class AddUserLabelDto {

    private String name;

    private BigDecimal sort;
}
