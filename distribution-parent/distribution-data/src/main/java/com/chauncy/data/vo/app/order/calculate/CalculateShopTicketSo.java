package com.chauncy.data.vo.app.order.calculate;

import com.chauncy.common.util.BigDecimalUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**J计算奖励购物券需要用到的字段
 * @Author zhangrt
 * @Date 2019/7/11 19:15
 **/
@Data
@Accessors(chain = true)
public class CalculateShopTicketSo {

    //销售价
    private BigDecimal sellPrice;

    //商品活动百分比
    private BigDecimal activityCostRate;

    //让利成本
    private BigDecimal profitsRate;
    //利润比例
    private BigDecimal profitRate;
    //供货价
    private BigDecimal supplierPrice;
    //运营成本
    private BigDecimal operationCost;
    //会员等级比例
    private BigDecimal purchasePresent;
    //购物券比例
    private BigDecimal moneyToShopTicket;

    /**
     * 根据公式算出返回购物券
     * 没有活动和优惠券  付现价=销售价
     * @return
     */
    public BigDecimal getShopTicket(){
        //固定成本=供货价+运营成本（%）+利润比例（%）（都是乘以销售价）
        BigDecimal fixedCost= BigDecimalUtil.safeAdd(supplierPrice,BigDecimalUtil.safeMultiply(operationCost,sellPrice)
        ,BigDecimalUtil.safeMultiply(profitRate,sellPrice)
        );
        //活动成本=（售价-固定成本）*商品活动百分比（%）
        BigDecimal activityCost=BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(sellPrice,fixedCost),activityCostRate);

        //购物券=（付现价-固定成本-活动成本）X让利成本（%）(商品详情页面)X该用户所属的会员等级反购物券比例X购物券比例设置(参数设置)

        //（付现价-固定成本-活动成本）
        BigDecimal a=BigDecimalUtil.safeSubtract(true,sellPrice,fixedCost,activityCost);

        //让利成本（%）(商品详情页面)X该用户所属的会员等级反购物券比例X购物券比例设置(参数设置)
        BigDecimal b=BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(profitsRate,purchasePresent),moneyToShopTicket);

        //购物券
        return BigDecimalUtil.safeMultiply(a,b);

    }

}
