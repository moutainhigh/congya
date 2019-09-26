package com.chauncy.common.enums.app.sort;

import com.chauncy.common.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-17 13:37
 */
@ApiModel("前端app排序方式")
@Getter
public enum SortWayEnum implements BaseEnum {

    @ApiModelProperty("降序   \n")
    DESC(1,"desc"),
    @ApiModelProperty("升序   \n")
    ASC(2,"asc");

    private Integer id;
    private String name;
    SortWayEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name() + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static SortWayEnum getSortWayEnumById(Integer id) {
        for (SortWayEnum type : SortWayEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static SortWayEnum fromName(String name) {
        for (SortWayEnum type : SortWayEnum.values()) {
            if (type.getName().equals(name))
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
        if (field == null){
            return true;
        }
        return Objects.nonNull(fromName(field.toString()));
    }}
