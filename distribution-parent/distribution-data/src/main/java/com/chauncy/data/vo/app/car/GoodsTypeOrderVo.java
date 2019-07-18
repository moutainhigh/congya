package com.chauncy.data.vo.app.car;

import com.chauncy.common.util.BigDecimalUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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

    @ApiModelProperty("订单备注")
    private String remark;

    @ApiModelProperty(value = "单个订单运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "单个订单税费")
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "单个订单商品金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "单个订单商品数量")
    private Integer totalNumber;

    @ApiModelProperty(value = "单个订单使用购物券")
    private BigDecimal shopTicket;

    @ApiModelProperty(value = "单个订单使用红包")
    private BigDecimal redEnvelops;

    @ApiModelProperty(value = "单个订单使用优惠券")
    private BigDecimal coupon;

    @ApiModelProperty(value = "单个订单使用总优惠")
    private BigDecimal totalDiscount;

    @ApiModelProperty(value = "单个订单实际付款")
    private BigDecimal realPayMoney;

    @ApiModelProperty(value = "单个订单积分抵扣")
    private BigDecimal integralMoney;

    @ApiModelProperty(value = "单个订单可抵扣金额")
    private BigDecimal deductionMoney;

    @ApiModelProperty(value = "单个订单预计奖励购物券")
    private BigDecimal rewardShopTicket;

    //计算出应付金额=商品总额+运费+税费-总优惠
    public BigDecimal calculationRealPayMoney(){
        BigDecimal a = BigDecimalUtil.safeAdd(totalMoney, shipMoney, taxMoney);
        return BigDecimalUtil.safeSubtract(a,totalDiscount);
    }


}
