package com.chauncy.data.dto.app.car;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.util.BigDecimalUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查出订单中的商品信息以及需要计算购物券的字段
 *
 * @Author zhangrt
 * @Date 2019/7/11 20:52
 **/
@Data
@Accessors(chain = true)
public class ShopTicketSoWithCarGoodDto {

    @ApiModelProperty("skuid")
    private Long id;


    @ApiModelProperty("数量")
    private Integer number;

    //销售价
    @ApiModelProperty(value = "销售价", hidden = true)
    @JSONField(serialize = false)
    private BigDecimal sellPrice;

    //商品活动百分比
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal activityCostRate;

    //让利成本比例
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal profitsRate;

    //利润比例
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal profitRate;

    //供货价
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal supplierPrice;
    //运营成本

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal operationCost;

    //自定义税率
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal customTaxRate;


    @ApiModelProperty(value = "商品缩略图", hidden = true)
    @JSONField(serialize = false)
    private String icon;

    @ApiModelProperty(value = "商品名称", hidden = true)
    @JSONField(serialize = false)
    private String name;

    @ApiModelProperty(value = "规格", hidden = true)
    @JSONField(serialize = false)
    private String standardStr;

//    @ApiModelProperty("商品当前参与的活动类型，没有则为空字符串")
//    private String activityType;
//
//    @ApiModelProperty("该支付单所参与的优惠活动")
//    private List<String> activityList;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private String storeName;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Long storeId;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private String goodsType;

    //会员等级比例
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal purchasePresent;

    //购物券比例
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal moneyToShopTicket;

    //运费模板id
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Long shippingTemplateId;

    //是否包邮
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Boolean isFreePostage;

    //第二版本
    //实付价格(还没减去红包和购物券)
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal realPayMoney;

    //该商品使用了多少积分
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal integral;

    //该商品积分抵扣了多少钱
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal integralMoney;

    //该商品优惠券抵扣了多少钱
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private BigDecimal couponMoney;

    //第二版本
    @ApiModelProperty(value = "参与的优惠类型：0-无活动 1-满减 2-积分 3-秒杀 4-拼团 ")
    private Integer activityType = 0;


    /**
     * 根据公式算出返回预计购物券
     * 没有活动和优惠券  付现价=销售价
     *
     * @return
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    public BigDecimal getRewardShopTicket() {
        //固定成本=供货价+运营成本（%）+利润比例（%）（都是乘以销售价）
        BigDecimal fixedCost = BigDecimalUtil.safeAdd(supplierPrice, BigDecimalUtil.safeMultiply(transfromDecimal(operationCost), sellPrice)
                , BigDecimalUtil.safeMultiply(transfromDecimal(profitRate), sellPrice)
        );
        //活动成本=（售价-固定成本）*商品活动百分比（%）
        BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(sellPrice, fixedCost), transfromDecimal(activityCostRate));

        //购物券=（付现价-固定成本-活动成本）X让利成本（%）(商品详情页面)X该用户所属的会员等级反购物券比例X购物券比例设置(参数设置)

        //（付现价-固定成本-活动成本）
        BigDecimal a = BigDecimalUtil.safeSubtract(true, realPayMoney, fixedCost, activityCost);

        //让利成本（%）(商品详情页面)X该用户所属的会员等级反购物券比例X购物券比例设置(参数设置)
        BigDecimal b = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(transfromDecimal(profitsRate), transfromDecimal(purchasePresent)), moneyToShopTicket);

        //购物券
        return BigDecimalUtil.safeMultiply(a, b);

    }

    /**
     * @return java.math.BigDecimal
     * @Author zhangrt
     * @Date 2019/10/18 12:18
     * @Description 计算出固定成本
     * @Update
     * @Param []
     **/

    public BigDecimal computeFixedCost() {
        //运营成本
        BigDecimal operation = BigDecimalUtil.safeMultiply(sellPrice, transfromDecimal(operationCost));
        //利润
        BigDecimal profit = BigDecimalUtil.safeMultiply(sellPrice, transfromDecimal(profitRate));
        return BigDecimalUtil.safeAdd(operation, profit, supplierPrice);
    }

    /**
     * 将百分比转换成小数
     *
     * @param bigDecimal
     * @return
     */
    private BigDecimal transfromDecimal(BigDecimal bigDecimal) {
        return BigDecimalUtil.safeDivide(bigDecimal, 100);
    }


}
