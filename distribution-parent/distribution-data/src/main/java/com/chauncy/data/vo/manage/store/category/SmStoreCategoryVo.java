package com.chauncy.data.vo.manage.store.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/6/18 14:36
 */
@Data
public class SmStoreCategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺分类id")
    private Long id;

    @ApiModelProperty(value = "店铺分类名称")
    private String name;

    @ApiModelProperty(value = "排序数字")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;


}
