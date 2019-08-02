package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/16 11:10
 */
public enum GoodsCategoryLevelEnum implements BaseEnum {


    /**
     * 商品分类
     * 1->  一级分类
     * 2->  二级分类
     * 3->  三级分类
     */
    FIRST_CATEGORY(1, "一级分类"),
    SECOND_CATEGORY(2, "二级分类"),
    THIRD_CATEGORY(3, "三级分类"),
    ;


    private Integer level;
    private String name;
    GoodsCategoryLevelEnum(Integer level, String name){
        this.level = level;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.level + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static GoodsCategoryLevelEnum getByLevel(Integer level) {
        for (GoodsCategoryLevelEnum type : GoodsCategoryLevelEnum.values()) {
            if (type.getLevel() == level)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static GoodsCategoryLevelEnum fromName(String name) {
        for (GoodsCategoryLevelEnum type : GoodsCategoryLevelEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getByLevel(Integer.parseInt(field.toString())));
    }

}
