package com.chauncy.data.dto.manage.activity.coupon.select;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-20 15:17
 *
 * 分页查询优惠券关联商品信息Dto
 */
@Data
@ApiModel(description = "分页查询优惠券关联商品信息Dto")
@Accessors(chain = true)
public class SearchDetailAssociationsDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty("优惠券id")
    @NeedExistConstraint(tableName = "am_coupon")
    @NotNull(message = "优惠券ID不能为空")
    private Long id;

    @ApiModelProperty("搜索id")
    private Long searchId;

    @ApiModelProperty("名称")
    private String name;

}
