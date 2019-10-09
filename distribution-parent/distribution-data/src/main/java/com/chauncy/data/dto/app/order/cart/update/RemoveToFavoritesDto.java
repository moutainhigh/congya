package com.chauncy.data.dto.app.order.cart.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-09 10:34
 *
 * 移动购物车商品至收藏夹
 */
@Data
@ApiModel(description = "移动购物车商品至收藏夹")
@Accessors(chain = true)
public class RemoveToFavoritesDto {

    @ApiModelProperty(value = "商品ID集合")
    private Long[] goodsIds;

    @ApiModelProperty(value = "购物车ID集合")
    private Long[] cartIds;
}
