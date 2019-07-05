package com.chauncy.data.dto.app.user.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author zhangrt
 * @Date 2019/7/3 23:24
 **/
@Data
@ApiModel(description = "用户实名认证")
@Accessors(chain = true)

public class AddIdCardDto {

    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "姓名不能为空")
    private String trueName;

    @ApiModelProperty(value = "身份证")
    /*@Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)\n",
            message = "身份证格式错误！")*/
    private String idCard;

    @ApiModelProperty(value = "正面照片")
    @NotBlank(message = "正面照片不能为空")
    private String frontPhoto;

    @ApiModelProperty(value = "反面照片")
    @NotBlank(message = "反面照片不能为空")
    private String backPhoto;


}
