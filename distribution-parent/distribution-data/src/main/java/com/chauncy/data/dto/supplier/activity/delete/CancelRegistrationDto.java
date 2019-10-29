package com.chauncy.data.dto.supplier.activity.delete;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-29 17:41
 *
 * 商家端取消活动报名
 */
@Data
@ApiModel(description = "商家端取消活动报名")
@Accessors(chain = true)
public class CancelRegistrationDto {

    @ApiModelProperty("记录ID")
    private Long activityGoodsRelId;

    @ApiModelProperty(value = "活动sku记录ID",hidden = true)
    private List<Long> goodsSkuRelIds;

    @ApiModelProperty(value = "商家ID",hidden = true)
    private Long storeId;

    @ApiModelProperty(value = "是否取消报名,ture-是，false-否，重新报名")
    private Boolean isCancel;

    @ApiModelProperty("活动类型:满减、积分、秒杀、拼团")
    @EnumConstraint(target = ActivityTypeEnum.class)
    @NotNull(message = "活动类型不能为空")
    private String activityType;

    @ApiModelProperty("商品ID")
    @NeedExistConstraint(tableName = "pm_goods")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty("活动ID")
    @NotNull(message = "活动ID不能为空")
    private Long activityId;

}
