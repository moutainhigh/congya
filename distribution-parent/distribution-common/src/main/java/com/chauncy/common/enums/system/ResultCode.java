package com.chauncy.common.enums.system;


/**
 * @author zhangrt
 * @date 2017-05-22
 * @time 16:53
 */
public enum ResultCode  {

    FAIL(0, "操作失败！"),

    SUCCESS(1, "操作成功！"),

    NO_LOGIN(2, "未登陆或登陆已超时！"),

    NO_EXISTS(3, "数据不存在！"),

    PARAM_ERROR(4, "参数错误！"),

    DUPLICATION(5, "数据重复！"),

    NO_AUTH(6, "无权限！"),

    SYSTEM_ERROR(7, "系统出错！"),

    OCCUPATION(8, "数据被占用，系统繁忙！"),

    REMOTE_LOGIN(9, "异地登录,被挤下线！"),
    NSUFFICIENT_INVENTORY(10,"库存不足！");

    private int value;
    private String description;

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    ResultCode(int value, String description) {
        this.value = value;
        this.description = description;
    }


}
