package com.chauncy.data.dto.app.order.cart.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-10 23:23
 *
 * 购物车更改sku
 */
@Data
@ApiModel(description = "购物车更改sku")
@Accessors(chain = true)
public class UpdateCartSkuDto {

    @ApiModelProperty("购物车列表ID")
    @NotNull(message = "购物车id不能为空")
    @NeedExistConstraint(tableName = "om_shopping_cart")
    private Long id;

    @ApiModelProperty("需要更改的SkuId")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    @NotNull(message = "需要更改的SkuId不能为空")
    private Long needChangeId;

    @ApiModelProperty("选择的SkuId,当不改变sku时该值传0")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    @NotNull(message = "选择的SkuId不能为空")
    private Long selectId;

    @ApiModelProperty("商品数量")
    private Integer num;
}
