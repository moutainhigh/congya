package com.chauncy.data.dto.app.car;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.vo.app.car.GoodsTypeOrderVo;
import com.chauncy.data.vo.app.car.StoreOrderVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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


    @ApiModelProperty("拆单后每个订单的订单商品详情")
    @NotEmpty
    private List<GoodsTypeOrderDto> goodsTypeOrderDtos;


    @ApiModelProperty(value = "用户实名认证id,若无须实名认证，该字段为空")
    @NeedExistConstraint(tableName = "om_real_user")
    private Long realUserId;


    @ApiModelProperty(value = "同一支付单所有订单总额，包括商品、税费、运费",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "购物券抵扣了多少钱",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalShopTicketMoney;

    @ApiModelProperty(value = "红包抵扣了多少钱",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalRedEnvelopsMoney;

    @ApiModelProperty(value = "（第二版本）积分抵扣了多少钱",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalIntegralMoney=BigDecimal.ZERO;

    @ApiModelProperty(value = "总订单应付总额",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalRealPayMoney;


    @ApiModelProperty(value = "总订单运费",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalShipMoney;

    @ApiModelProperty(value = "总订单税费",hidden = true)
    @JSONField(deserialize = false)
    private BigDecimal totalTaxMoney;
}
