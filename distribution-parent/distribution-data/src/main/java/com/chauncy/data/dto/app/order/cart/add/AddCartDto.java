package com.chauncy.data.dto.app.order.cart.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-05 11:33
 */
@Data
@ApiModel(description = "加入购物车需要的数据")
@Accessors(chain = true)
public class AddCartDto {

    @ApiModelProperty("购物车列表ID")
    @NotNull(message = "购物车id不能为空",groups = IUpdateGroup.class)
    @NeedExistConstraint(tableName = "om_shopping_cart")
    private Long id;

    @ApiModelProperty("商品skuID")
    @NotNull(message = "商品skuID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    private Long skuId;

    @ApiModelProperty("商品数量")
    @NotNull(message = "商品数量不能为空")
    private Integer num;
}
