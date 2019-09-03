package com.chauncy.data.dto.app.car;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.vo.app.car.StoreOrderVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/14 18:39
 **/
@Data
@ApiModel(description = "购物车提交订单")
@Accessors(chain = true)
public class SubmitOrderDto {

    @NotNull(message = "收货地址不能为空！")
    @NeedExistConstraint(tableName = "um_area_shipping",message = "收货地址不存在！")
    @ApiModelProperty(value = "我的收货地址id")
    private Long umAreaShipId;

    @NotNull(message = "是否使用葱鸭钱包不能为空！")
    private Boolean isUseWallet;


    @ApiModelProperty(value = "购物券抵扣了多少钱")
    private BigDecimal totalShopTicketMoney;


    @ApiModelProperty(value = "红包抵扣了多少钱")
    private BigDecimal totalRedEnvelopsMoney;

    @ApiModelProperty(value = "积分抵扣了多少钱")
    private BigDecimal totalIntegralMoney;

   /* @ApiModelProperty(value = "可抵扣金额")
    private BigDecimal deductionMoney;*/

    @ApiModelProperty(value = "同一支付单所有订单总额，包括商品、税费、运费")
    private BigDecimal totalMoney;

    /*@ApiModelProperty(value = "预计奖励购物券")
    private BigDecimal rewardShopTicket;*/



    @ApiModelProperty(value = "运费")
    private BigDecimal totalShipMoney;

    @ApiModelProperty(value = "税费")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty("根据店铺与商品类型拆单列表")
    private List<StoreOrderVo> storeOrderVos;

    @ApiModelProperty(value = "总数量")
    private int totalNumber;


    @ApiModelProperty(value = "应付总额")
    private BigDecimal totalRealPayMoney;

    @ApiModelProperty(value = "用户实名认证id,若无须实名认证，该字段为空")
    private Long realUserId;
}
