package com.chauncy.data.vo.app.advice.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/8 19:35
 */
@Data
@ApiModel(description = "店铺详情-商品三级分类列表")
@Accessors(chain = true)
public class GoodsThirdCategoryListVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "三级分类名称")
    private String categoryName;

    @ApiModelProperty(value = "三级分类id")
    private Long categoryId;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsSum;


}
