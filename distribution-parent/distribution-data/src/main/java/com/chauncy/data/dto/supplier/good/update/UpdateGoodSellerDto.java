package com.chauncy.data.dto.supplier.good.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-16 13:50
 *
 * 添加或更新销售信息
 */
@Data
@ApiModel(description = "UpdateGoodSellerDto",value = "UpdateGoodSellerDto")
public class UpdateGoodSellerDto {

    @ApiModelProperty(value = "商品ID")
    @NotNull(message = "商品不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty(value = "发货地")
    @NotNull(message = "发货地不能为空")
    private String location;

    @ApiModelProperty(value = "限购数量")
    @NotNull(message = "限购数量不能为空")
    private Integer purchaseLimit;


}
