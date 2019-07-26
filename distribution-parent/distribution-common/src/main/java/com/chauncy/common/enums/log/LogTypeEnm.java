package com.chauncy.common.enums.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/26 14:04
 */
public enum LogTypeEnm  implements BaseEnum {


    /**
     * 流水类型
     * 1.支出
     * 2.收入
     */
    EXPENDITURE (1, "支出"),
    INCOME(2, "收入"),
    ;


    @EnumValue
    private Integer id;
    private String name;
    LogTypeEnm(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + ":"  + this.name;
    }

    //通过名称来获取结果
    public static LogTypeEnm getById(Integer id) {
        for (LogTypeEnm type : LogTypeEnm.values()) {
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
