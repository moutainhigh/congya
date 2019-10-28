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

    NSUFFICIENT_INVENTORY(10,"库存不足！"),

    THIRD_FAIL(11,"第三方调用出错！！"),


    LOGISTICS_ERROR1(600,"您不是合法的订阅者（即授权Key出错)"),
    LOGISTICS_ERROR2(601,"POLL:KEY已过期"),
    LOGISTICS_ERROR3(500," 服务器错误"),
    LOGISTICS_ERROR4(501,"重复订阅"),
    LOGISTICS_ERROR5(700,"订阅方的订阅数据存在错误（如不支持的快递公司、单号为空、单号超长等）或错误的回调地址"),
    LOGISTICS_ERROR6(702,"POLL:识别不到该单号对应的快递公司"),
    LOGISTICS_ERROR7(701,"拒绝订阅的快递公司 ");

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
