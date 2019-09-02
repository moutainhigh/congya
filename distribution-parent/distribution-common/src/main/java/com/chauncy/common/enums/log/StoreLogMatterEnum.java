package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/31 0:15
 */
public enum StoreLogMatterEnum  implements BaseEnum {


    /**
     * 11.货款收入  店铺收入
     * 12.利润收入  店铺收入
     */
    PAYMENT_INCOME(11, "货款收入"),
    PROFIT_INCOME(12, "利润收入"),
    ;

    @EnumValue
    private Integer id;
    private String name;
    StoreLogMatterEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    //通过名称来获取结果
    public static StoreLogMatterEnum getById(Integer id) {
        for (StoreLogMatterEnum type : StoreLogMatterEnum.values()) {
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
        if(null == field) {
            return true;
        } else {
            return Objects.nonNull(getById(Integer.parseInt(field.toString())));
        }
    }



}

