package com.chauncy.common.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/9/14 20:55
 */
public enum NoticeContentEnum   implements BaseEnum {
    /**
     * 奖励通知  消息内容枚举
     */
    UPGRADE(1, "恭喜你的会员等级已经提升到{0}级，点击查看详情"),
    SHOPPING_REWARD(2, "预计入账{0}{1}，具体时间以本次订单完成为准"),
    FRIENDS_ASSIST(3, "预计入账{0}积分，具体时间以本次订单完成为准"),
    GET_INTEGRATE(4, "恭喜您有{0}积分到账了，点击查看使用"),
    GET_SHOP_TICKET(5, "恭喜您有{0}消费券到账了，点击查看使用"),
    GET_RED_ENVELOPS(6, "恭喜您有{0}红包到账了，点击查看使用"),
    /**
     * 交易物流  消息内容枚举
     */
    ALREADY_SIGNED(7, "您购买的{0}订单已签收"),
    SHIPPED(8, "您购买的{0}订单已发货"),
    REFUND_SUCCESSFUL(9, "亲爱的{0}：退款售后编号：{1}，系统已给您退款。请注意查收"),
    RETURN_GOODS(10, "亲爱的{0}：退款售后编号：{1}，卖家已同意退款。请于{2}前将货物寄回指定售后点"),
    WITHDRAWAL_SUCCESS(11, "亲爱的{0}：流水编号{1}，请注意查收"),
    ;

    @EnumValue
    private Integer id;
    private String name;

    NoticeContentEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    //通过名称来获取结果
    public static NoticeContentEnum getById(Integer id) {
        for (NoticeContentEnum type : NoticeContentEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static NoticeContentEnum fromName(String name) {
        for (NoticeContentEnum type : NoticeContentEnum.values()) {
            if (type.name().equals(name))
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
        if (null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }
}
