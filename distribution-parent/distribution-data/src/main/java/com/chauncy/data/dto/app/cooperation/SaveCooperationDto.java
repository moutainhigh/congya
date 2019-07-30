package com.chauncy.data.dto.app.cooperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-30 22:08
 *
 * 保存用户合作邀请信息
 */
@Data
@ApiModel(description = "保存用户合作邀请信息")
@Accessors(chain = true)
public class SaveCooperationDto {

//    @ApiModelProperty(value = "app用户ID")
//    private Long userId;

    @ApiModelProperty(value = "申请者")
    private String applicant;

    @ApiModelProperty(value = "申请者手机号")
    private String applicantPhone;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "图片")
    private String picture;
}
