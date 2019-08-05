package com.chauncy.data.bo.message;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-08-03 23:34
 *
 * 注册环信IM账号
 */
@Data
@ApiModel(description = "注册环信IM账号")
@Accessors(chain = true)
public class RegUserBo {

    private String username;

    private String password;

    private String nickname;
}
