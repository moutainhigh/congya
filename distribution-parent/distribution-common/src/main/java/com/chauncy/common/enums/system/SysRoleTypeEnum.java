package com.chauncy.common.enums.system;

import com.chauncy.common.enums.BaseEnum;

import java.util.Objects;

/**
 * 系统角色类型枚举类
 * @author yeJH
 * @since 2019/6/21 13:16
 */
public enum  SysRoleTypeEnum  implements BaseEnum {


    /**
     * 店铺店主
     */
    ROLE_STORE(1,"ROLE_STORE"),
    ;

    private Integer id;
    private String name;
    SysRoleTypeEnum(Integer id, String name){
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
    public static SysRoleTypeEnum getSysRoleTypeById(Integer id) {
        for (SysRoleTypeEnum attribute : SysRoleTypeEnum.values()) {
            if (attribute.getId() == id)
                return attribute;
        }
        return null;
    }
    //通过名称来获取结果
    public static SysRoleTypeEnum fromName(String name) {
        for (SysRoleTypeEnum attribute : SysRoleTypeEnum.values()) {
            if (attribute.getName() == name)
                return attribute;
        }
        throw new IllegalArgumentException(name);
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
    public  boolean isExist(Object field) {
        return Objects.nonNull(getSysRoleTypeById(Integer.parseInt(field.toString())));
    }

}
