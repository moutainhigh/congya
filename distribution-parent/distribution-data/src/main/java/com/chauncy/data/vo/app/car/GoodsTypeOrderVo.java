package com.chauncy.data.vo.app.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/9 23:52
 **/

@Data
@ApiModel(description = "商品类型拆单")
@Accessors(chain = true)
public class GoodsTypeOrderVo {


    @ApiModelProperty("购物车商品详情")
    private List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos;

    @ApiModelProperty("商品类型")
    private String goodsType;


}
