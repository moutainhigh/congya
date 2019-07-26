package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/26 10:55
 */
public enum AccountTypeEnum  implements BaseEnum {


    /**
     * 用户账目类型
     * 1.线上
     * 2.红包
     * 3.购物券
     * 4.积分
     */
    ONLINE_FUNDS (1, "线上资金"),
    RED_ENVELOPS(2, "红包"),
    SHOP_TICKET(3, "购物券"),
    INTEGRATE(4, "积分"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    AccountTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + ":"  + this.name;
    }

    //通过名称来获取结果
    public static AccountTypeEnum getById(Integer id) {
        for (AccountTypeEnum type : AccountTypeEnum.values()) {
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
