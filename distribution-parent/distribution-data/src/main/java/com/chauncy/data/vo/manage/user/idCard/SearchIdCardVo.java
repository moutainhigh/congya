package com.chauncy.data.vo.manage.user.idCard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/4 16:47
 **/
@Data
@Accessors(chain = true)
@ApiModel(description = "用户实名认证列表与详情一起的")
public class SearchIdCardVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("姓名")
    private String trueName;

    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("身份证正面照")
    private String frontPhoto;

    @ApiModelProperty("身份证反面照")
    private String backPhoto;


}
