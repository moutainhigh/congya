package com.chauncy.data.vo.app.order.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-05 13:59
 *
 * 购物车显示
 */
@ApiModel("查看购物车返回的信息")
@Data
public class CartVo {

    @ApiModelProperty("店铺ID")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("店铺下的商品")
    private List<StoreGoodsVo> storeGoodsVoList;

}
