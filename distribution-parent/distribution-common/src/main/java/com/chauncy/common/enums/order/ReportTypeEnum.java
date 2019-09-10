package com.chauncy.common.enums.order;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @author yeJH
 * @since 2019/8/12 15:09
 */
public enum ReportTypeEnum  implements BaseEnum {


    /**
     * 报表类型
     * 1-总后台展示商品销售报表
     * 2-商家后台展示商品销售报表
     * 3-商家后台展示分店商品销售报表
     */
    PLATFORM_REPORT(1, "总后台展示商品销售报表"),
    STORE_REPORT(2, "商家后台展示商品销售报表"),
    BRANCH_REPORT(3, "商家后台展示分店商品销售报表"),
    ;

    private Integer id;
    private String name;
    ReportTypeEnum(Integer id, String name){
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

    //通过Id获取结果
    public static ReportTypeEnum getById(Integer id) {
        for (ReportTypeEnum type : ReportTypeEnum.values()) {
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
