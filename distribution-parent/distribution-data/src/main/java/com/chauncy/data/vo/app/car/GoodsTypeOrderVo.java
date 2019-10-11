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

    @ApiModelProperty(value = "该订单运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "该订单税费")
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "该订单金额，包括商品金额，运费，税费")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "该订单商品数量")
    private Integer totalNumber;

    @ApiModelProperty(value = "满减活动满足多少钱才能减")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减活动减多少钱")
    private BigDecimal reductionPostMoney;

    @ApiModelProperty(value = "评团优惠多少钱")
    private BigDecimal groupDiscountMoney;

    @ApiModelProperty(value = "积分优惠多少钱")
    private BigDecimal integralDiscountMoney;

}
