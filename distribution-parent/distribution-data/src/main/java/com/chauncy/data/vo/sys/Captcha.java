package com.chauncy.data.vo.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 验证码
 *
 * @Author huangwancheng
 * @create 2019-05-29 14:48
 */
@Data
public class Captcha implements Serializable {

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "验证码")
    private String code;
}
