package com.chauncy.data.vo.app.order.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/10/5 12:12
 **/
@ApiModel(description = "实名认证vo")
@Data
@Accessors(chain = true)
public class RealUserVo {

    @ApiModelProperty("实名认证的id")
    private Long realUserId;

    @ApiModelProperty("1-认证通过 2-认证失败")
    private Integer status;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "真实姓名")
    private String trueName;

    @ApiModelProperty(value = "身份证正面照片")
    private String frontPhoto;

    @ApiModelProperty(value = "身份证反面照片")
    private String backPhoto;
}
