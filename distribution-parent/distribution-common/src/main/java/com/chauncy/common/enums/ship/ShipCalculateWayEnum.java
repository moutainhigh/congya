package com.chauncy.common.enums.ship;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-06-25 12:06
 * <p>
 * 运费计算方式 1--按金额 2--按件数
 */
public enum ShipCalculateWayEnum implements BaseEnum {

    AMOUNT(1, "按金额"),
    NUMBER(2, "按件数");

    private Integer id;
    private String name;

    ShipCalculateWayEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + "_" + this.name;
    }

    public static String value(String name) {
        return name;
    }

    //通过Id获取结果
    public static ShipCalculateWayEnum getWayById(Integer id) {
        for (ShipCalculateWayEnum way : ShipCalculateWayEnum.values()) {
            if (way.getId() == id)
                return way;
        }
        return null;
    }

    //通过名称来获取结果
    public static ShipCalculateWayEnum fromName(String name) {
        for (ShipCalculateWayEnum way : ShipCalculateWayEnum.values()) {
            if (way.getName().equals(name))
                return way;
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
        return Objects.nonNull(getWayById(Integer.parseInt(field.toString())));
    }
}
        
