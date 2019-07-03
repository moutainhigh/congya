package com.chauncy.common.enums.system;

/**
 * @Author zhangrt
 * @Date 2019/7/2 16:11
 **/
public enum LoginType {

    MANAGE("总后台登录"),
    SUPPLIER("商家端登录"),
    APP_PASSWORD("App密码登录"),
    APP_CODE("App手机验证码登录");

    private String description;

    LoginType(String description){
        this.description=description;
    }

}
