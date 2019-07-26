package com.chauncy.common.enums.log;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/20 18:04
 */
public enum AccountLogMatterEnum implements BaseEnum {


    /**
     * 账目流水事由
     * 1.订单支付  用户支出
     * 2.提现  用户收入
     * 3.审核不通过返款  用户支出
     * 4.充值  用户收入
     * 5.平台赠送  用户收入
     * 6.返利  用户收入
     * 7.订单收入  平台收入
     * 8.售后退款  平台支出
     * 9.商品售出  平台收入
     * 10.订单取消  平台支出
     * 11.用户提现  平台支出
     * 12.商家货款提现  平台支出
     * 13.商家利润提现  平台支出
     * 14.货款收入  店铺收入
     * 15.利润收入  店铺收入
     */
    ORDER_PAYMENT(1, "订单支付"),
    WITHDRAWAL(2, "提现"),
    AUDIT_NOT_C(3, "审核不通过返款"),
    RECHARGE(4, "充值"),
    PLATFORM_GIVE(5, "平台赠送"),
    ORDER_REBATES(6, "返利"),
    ORDER_INCOME(7, "订单收入"),
    ORDER_REFUND(8, "售后退款"),
    GOODS_SALES(9, "商品售出"),
    ORDER_CANCEL(10, "订单取消"),
    USER_WITHDRAWAL(11, "用户提现"),
    PAYMENT_WITHDRAWAL(12, "商家货款提现"),
    PROFIT_WITHDRAWAL(13, "商家利润提现"),
    PAYMENT_INCOME(14, "货款收入"),
    PROFIT_INCOME(15, "利润收入"),
    ;

    private Integer id;
    private String name;
    AccountLogMatterEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + ":"  + this.name;
    }

    //通过名称来获取结果
    public static AccountLogMatterEnum getById(Integer id) {
        for (AccountLogMatterEnum type : AccountLogMatterEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isExist(Object field) {

        return Objects.nonNull(getById(Integer.parseInt(field.toString())));
    }



}
