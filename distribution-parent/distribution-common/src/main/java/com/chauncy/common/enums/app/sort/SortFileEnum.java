package com.chauncy.common.enums.app.sort;

import com.chauncy.common.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-17 13:14
 * <p>
 * 前端app排序字段
 */
@ApiModel("前端app排序字段")
@Getter
public enum SortFileEnum implements BaseEnum {

    @ApiModelProperty("综合排序")
    COMPREHENSIVE_SORT(1, "综合排序"),
    @ApiModelProperty("销量排序")
    SALES_SORT(2, "销量排序"),
    @ApiModelProperty("价格排序")
    PRICE_SORT(3, "价格排序"),
    ;

    private Integer id;

    private String name;

    SortFileEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name + "_" + this.name();
    }

    //通过name获取结果
    public static SortFileEnum getSortFileEnum(String name) {
        for (SortFileEnum sortFileEnum : SortFileEnum.values()) {
            if (sortFileEnum.name().equals(name))
                return sortFileEnum;
        }
        return null;
    }


    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getSortFileEnum(field.toString()));
    }
}

