package com.chauncy.common.enums.goods;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/17 17:08
 */
public enum StoreGoodsListTypeEnum  implements BaseEnum {


    ALL_LIST(1,"全部商品列表"),
    HOME_PAGE_LIST(2,"首页商品列表"),
    NEW_LIST(3,"新品列表"),
    RECOMMEND_LIST(4,"推荐列表"),
    ACTIVITY_LIST(5,"活动列表"),
    CATEGORY_LIST(6,"分类商品列表"),
    SEARCH_LIST(7,"搜索商品列表"),
    ;

    private Integer id;
    private String name;
    StoreGoodsListTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString(){
        return this.name();
    }

    public static String value(String name){
        return name;
    }

    //通过枚举名称来获取结果
    public static StoreGoodsListTypeEnum getById(Integer id) {
        for (StoreGoodsListTypeEnum type : StoreGoodsListTypeEnum.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
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

        return Objects.nonNull(getById(Integer.parseInt(String.valueOf(field))));
    }

}
