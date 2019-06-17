package com.chauncy.data.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-17 15:27
 *
 * 基础Vo,只包含ID和name
 */
@Data
@Accessors(chain = true)
public class BaseVo {

    private Long id;

    private String name;
}
