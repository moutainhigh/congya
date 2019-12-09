package com.chauncy.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/22 23:39
 */
public enum BillTypeEnum implements BaseEnum {

    /**
     * 账单类型
     * 1-货款账单  用户在店铺下单 商品所属店铺生成跟供货价关联的货款账单
     * 2-利润账单  用户在店铺下单 用户所在店铺生成跟销售价关联的利润账单
     */
    PAYMENT_BILL(1, "货款账单"),
    PROFIT_BILL(2, "利润账单"),
    GOODS_SALE_REPORT(3, "商品销售报表"),
    ;

    private Integer id;
    private String name;
    BillTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static BillTypeEnum getById(Integer id) {
        for (BillTypeEnum type : BillTypeEnum.values()) {
            if (type.getId() == id)
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
