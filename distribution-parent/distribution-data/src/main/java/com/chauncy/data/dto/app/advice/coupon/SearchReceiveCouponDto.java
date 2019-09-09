package com.chauncy.data.dto.app.advice.coupon;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-07 21:23
 *
 * 分页查询获取符合该用户的优惠券
 */
@Data
@ApiModel(description = "分页查询获取符合该用户的优惠券")
@Accessors(chain = true)
public class SearchReceiveCouponDto {

    @ApiModelProperty(value = "会员等级",hidden = true)
    @JSONField(serialize = false)
    private Integer level;

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;
}
