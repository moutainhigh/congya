package com.chauncy.common.enums.goods;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/7/17 17:08
 */
public enum StoreGoodsListTypeEnum  implements BaseEnum {


    ALL_LIST(1,"全部"),
    NEW_LIST(2,"新品列表"),
    RECOMMEND_LIST(3,"推荐列表"),
    NEWEST_RECOMMEND_LIST(4,"最新推荐列表"),
    START_LIST(5,"明星单品"),
    ACTIVITY_LIST(6,"活动列表"),
    ;

    private Integer id;
    private String name;
    StoreGoodsListTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString(){
        return this.name() + ":" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过枚举名称来获取结果
    public static StoreGoodsTypeEnum fromName(String name) {
        for (StoreGoodsTypeEnum type : StoreGoodsTypeEnum.values()) {
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

        return Objects.nonNull(fromName(field.toString()));
    }

}
