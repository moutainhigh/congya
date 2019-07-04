package com.chauncy.common.enums.user;

import com.chauncy.common.enums.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author zhangrt
 * @Date 2019/7/1 16:47
 **/
@ApiModel("验证码类型枚举类")
@Getter
public enum ValidCodeEnum implements BaseEnum {

    @ApiModelProperty("登录验证码")
    LOGIN_CODE(1,"SMS_144205069","redis:app:user:login:phone:%s"),
    @ApiModelProperty("注册验证码")
    REGISTER_CODE(2,"SMS_144205067","redis:app:user:register:phone:%s"),
    @ApiModelProperty("修改密码")
    RESET_PASSWORD_CODE(3,"SMS_144205066","redis:app:user:reset:phone:%s"),
    @ApiModelProperty("绑定手机")
    BIND_PHONE_CODE(3,"SMS_169637862","redis:app:user:bind:phone:%s");



    private Integer id;
    /**
     *手机验证码模板
     */
    private String templateCode;

    /**
     *redisKey
     */
    private String redisKey;

    ValidCodeEnum(Integer id, String templateCode,String redisKey) {
        this.id=id;
        this.templateCode=templateCode;
        this.redisKey=redisKey;
    }

    //通过name获取结果
    public static ValidCodeEnum getValidCodeEnum(String name) {
        for (ValidCodeEnum validCodeEnum : ValidCodeEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }


    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getValidCodeEnum(field.toString()));
    }
}
