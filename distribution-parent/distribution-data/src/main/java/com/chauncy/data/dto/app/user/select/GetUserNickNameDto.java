package com.chauncy.data.dto.app.user.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yeJH
 * @Description 根据IM账号获取用户信息
 * @since 2019/12/18 20:40
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "根据IM账号获取用户信息")
public class GetUserNickNameDto {

    /*@NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型  1->用户IM  2->客服IM")
    private Integer type;*/

    @NotEmpty(message = "IM账号不能为空")
    @ApiModelProperty(value = "im账号")
    private String ImAccount;

}
