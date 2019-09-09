package com.chauncy.data.vo.app.advice.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/9/5 11:37
 */
@Data
@ApiModel(description = "店铺分类信息")
@Accessors(chain = true)
public class StoreCategoryInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告与店铺分类关联表id")
    private Long relId;

    @ApiModelProperty(value = "店铺分类id")
    private Long storeCategoryId;

    @ApiModelProperty(value = "店铺分类名称")
    private String storeCategoryName;

    @ApiModelProperty(value = "分类缩略图")
    private String storeCategoryIcon;
}

