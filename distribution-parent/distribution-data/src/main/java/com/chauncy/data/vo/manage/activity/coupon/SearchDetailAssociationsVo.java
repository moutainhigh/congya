package com.chauncy.data.vo.manage.activity.coupon;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-20 15:22
 *
 * 条件分页获取优惠券详情下指定的商品信息条件Dto
 */
@Data
@ApiModel(description = "条件分页获取优惠券详情下指定的商品信息")
@Accessors(chain = true)
public class SearchDetailAssociationsVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("所属分类")
    private String categoryName;

    @ApiModelProperty(value = "分类级别",hidden = true)
    private Integer level;

    @ApiModelProperty(value = "分类级别",hidden = true)
    private Long goodsCategoryId;
}
