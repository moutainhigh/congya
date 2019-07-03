package com.chauncy.data.dto.app.user.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/3 23:24
 **/
@Data
@ApiModel(description = "用户新增收货地址")
@Accessors(chain = true)

public class AddIdCard {

    @ApiModelProperty(value = "收货地址Id")
    private String trueName;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "正面照片")
    private String frontPhoto;

    @ApiModelProperty(value = "反面照片")
    private String backPhoto;


}
