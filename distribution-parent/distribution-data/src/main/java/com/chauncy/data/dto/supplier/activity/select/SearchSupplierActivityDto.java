package com.chauncy.data.dto.supplier.activity.select;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-29 12:39
 *
 * 条件分页查询商家端信息
 */
@Data
@ApiModel(description = "条件分页查询商家端信息")
@Accessors(chain = true)
public class SearchSupplierActivityDto {

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("店铺ID")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("活动类型:满减、积分、秒杀、拼团")
    @EnumConstraint(target = ActivityTypeEnum.class)
    @NotNull(message = "活动类型不能为空")
    private String activityType;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
