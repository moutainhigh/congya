package com.chauncy.data.dto.manage.activity.gift.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-09-02 17:39
 *
 * 条件分页获取积分信息
 */
@Data
@ApiModel(description = "条件分页获取积分信息")
@Accessors(chain = true)
public class SearchCouponDto {

    @ApiModelProperty("优惠券名称")
    private String name;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
