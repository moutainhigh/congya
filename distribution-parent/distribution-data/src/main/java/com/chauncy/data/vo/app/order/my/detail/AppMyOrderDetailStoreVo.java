package com.chauncy.data.vo.app.order.my.detail;

import com.chauncy.data.vo.app.order.cart.StoreGoodsVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author zhangrt
 * @create 2019-07-05 13:59
 *
 *
 */
@ApiModel("订单的店铺商品信息")
@Data
public class AppMyOrderDetailStoreVo {

    @ApiModelProperty("店铺ID")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("订单下的商品")
    private List<AppMyOrderDetailGoodsVo> appMyOrderDetailGoodsVos;

}
