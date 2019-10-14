package com.chauncy.data.vo.app.order.cart;

import com.chauncy.data.vo.app.advice.activity.*;
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

    @ApiModelProperty(value = "商品参与的活动类型:0-不参与活动、1-满减、2-积分、5-秒杀进行中、6-秒杀待开始(距离当前时间一天)")
    private Integer activityType;

    @ApiModelProperty(value = "满减信息")
    private CartReducedVo cartReducedVo;

    @ApiModelProperty(value = "积分信息")
    private CartIntegralsVo cartIntegralsVo;

    @ApiModelProperty(value = "秒杀信息")
    private CartSeckillVo cartSeckillVo;


}
