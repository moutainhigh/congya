package com.chauncy.data.dto.supplier.activity.select;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-26 11:01
 *
 * 查询需要被选参与活动的商品的条件
 */
@Data
@ApiModel(description = "查询需要被选参与活动的商品的条件")
@Accessors(chain = true)
public class  SearchAssociatedGoodsDto {

    @ApiModelProperty("商品ID")
//    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty("活动ID")
    @NotNull(message = "活动ID不能为空")
    private Long activityId;

    @ApiModelProperty("活动类型:满减、积分、秒杀、拼团")
    @EnumConstraint(target = ActivityTypeEnum.class)
    @NotNull(message = "活动类型不能为空")
    private String activityType;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
