package com.chauncy.common.enums.common;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-06-21 10:17
 *
 *  校验状态 1-待提交 2-待审核 3-审核通过 4-不通过/驳回
 */
public enum VerifyStatusEnum implements BaseEnum {

    UNCHECKED(1,"待提交"),
    WAIT_CONFIRM(2,"待审核"),
    CHECKED(3,"审核通过"),
    NOT_APPROVED(4,"不通过/驳回"),
    MODIFY(5,"返回修改");

    private Integer id;
    private String name;
    VerifyStatusEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static VerifyStatusEnum getVerifyStatusById(Integer id) {
        for (VerifyStatusEnum type : VerifyStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }
    //通过名称来获取结果
    public static VerifyStatusEnum fromName(String name) {
        for (VerifyStatusEnum type : VerifyStatusEnum.values()) {
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
        return Objects.nonNull(getVerifyStatusById(Integer.parseInt(field.toString())));
    }}

