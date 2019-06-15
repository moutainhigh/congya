package com.chauncy.common.enums.system;

/**
 * @Author: xiaoye
 * @Date: 2019/6/6 14:53
 */
public enum SysRoleEnum {


    /**
     * 系统角色类型
     *
     * ROLE_ADMIN
     * ROLE_TEST
     * ROLE_STORE
     */
    ROLE_ADMIN(1,"ROLE_ADMIN"),
    ROLE_TEST(2,"ROLE_TEST"),
    ROLE_STORE(3,"ROLE_STORE"),

    ;


    private Integer id;
    private String name;
    SysRoleEnum(Integer id, String name){
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

    /**
     *  通过Id获取结果
     */
    public static SysRoleEnum getGoodsTypeById(Integer id) {
        for (SysRoleEnum type : SysRoleEnum.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
    /**
     * 通过名称来获取结果
     * @param name
     * @return
     */
    public static SysRoleEnum fromName(String name) {
        for (SysRoleEnum type : SysRoleEnum.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
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

}
