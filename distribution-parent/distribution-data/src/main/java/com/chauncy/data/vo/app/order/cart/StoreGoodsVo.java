package com.chauncy.data.vo.app.order.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-05 16:48
 *
 * 店铺下的商品
 */
@ApiModel(description = "店铺下的商品")
@Data
public class StoreGoodsVo {

    @ApiModelProperty("购物车Id")
    private Long cartId;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品副标题")
    private String subtitle;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("sku图片")
    private String picture;

    @ApiModelProperty("sku销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty("sku销划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty("sku属性值:蓝色,35码")
    private String value;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("sku总量")
    private Integer sum;

    @ApiModelProperty("是否已经下架")
    private Boolean isObtained;

    @ApiModelProperty("是否售罄")
    private Boolean isSoldOut;

    @ApiModelProperty("返券值")
    private BigDecimal rewardShopTicke;

//    @ApiModelProperty("满减活动信息")
//    @ApiModelProperty("积分活动信息")
//    @ApiModelProperty("秒杀活动信息")


}
