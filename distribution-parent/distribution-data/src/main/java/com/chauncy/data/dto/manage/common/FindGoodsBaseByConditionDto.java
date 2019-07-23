package com.chauncy.data.dto.manage.common;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-18 15:37
 *
 * 条件获取商品的基础信息，作为给需要选择的功能的展示Dto
 */
@Data
@ApiModel("条件获取商品的基础信息")
public class FindGoodsBaseByConditionDto {

    @ApiModelProperty(value = "优惠券ID")
    @NeedExistConstraint(tableName = "am_coupon")
    private Long couponId;

    @ApiModelProperty(value = "商品ID")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty(value = "模糊查询名称")
    private String goodsName;

    @ApiModelProperty(value = "第三级分类Id")
    @NeedExistConstraint(tableName = "pm_goods_category")
    private Long categoryId;

    @ApiModelProperty(value = "第三级分类名称")
    private String categoryName;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
