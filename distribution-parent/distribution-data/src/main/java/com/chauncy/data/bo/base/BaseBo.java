package com.chauncy.data.bo.base;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-19 12:59
 *
 * 基础业务对象Bo,只包含ID和name
 */
@Data
@Accessors(chain = true)
public class BaseBo {

    private Long id;

    //名称或值
    private String name;

}
