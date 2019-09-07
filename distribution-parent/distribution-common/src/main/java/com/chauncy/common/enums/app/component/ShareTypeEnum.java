package com.chauncy.common.enums.app.component;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-09-04 21:28
 *
 * 分享类型：商品、店铺、、、
 */
@Getter
public enum ShareTypeEnum implements BaseEnum {


    GOODS(1,"商品"),

    INFORMATION(2,"资讯");

    @EnumValue  //这个注解放在数据库存储的字段上
    private Integer id;

    private String name;

    ShareTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //重写toString方法，返回值为显示的值
    @Override
    public String toString() {
        return this.name()+ "_"+this.name;
    }

    //通过ID获取结果
    public static ShareTypeEnum fromId(Integer id) {
        for (ShareTypeEnum type : ShareTypeEnum.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static ShareTypeEnum fromName(String name) {
        for (ShareTypeEnum type : ShareTypeEnum.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    //通过enumName获取结果
    public static ShareTypeEnum fromEnumName(String name) {
        for (ShareTypeEnum type : ShareTypeEnum.values()) {
            if (type.name().equals(name))
                return type;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        //通过ID判断
        return Objects.nonNull(fromId(Integer.parseInt(field.toString())));

        //通过enumName判断
//        return Objects.nonNull(fromEnumName(field.toString()));
    }
}
