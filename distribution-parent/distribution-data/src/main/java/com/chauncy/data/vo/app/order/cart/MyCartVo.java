package com.chauncy.data.vo.app.order.cart;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-10 17:07
 */
@ApiModel("查看购物车返回的商品件数和具体的店铺和对应的商品信息")
@Data
public class MyCartVo {

    @ApiModelProperty("商品件数")
    private Integer num;

    @ApiModelProperty("店铺ID")
    private PageInfo<CartVo> cartVo;

    @ApiModelProperty("失效商品列表")
    private List<StoreGoodsVo> disableList;

}
