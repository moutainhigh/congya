package com.chauncy.data.dto.supplier.activity.delete;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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

    @ApiModelProperty("活动sku记录ID")
    private List<Long> goodsSkuRelIds;

    @ApiModelProperty(value = "商家ID",hidden = true)
    private Long storeId;

}
