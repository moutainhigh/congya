package com.chauncy.data.vo.manage.cooperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-30 22:38
 *
 * 条件分页查询合作申请条件Dto
 */
@Data
@ApiModel(description = "条件分页查询合作申请条件Dto")
@Accessors(chain = true)
public class SearchCooperationVo {

    @ApiModelProperty("App用户ID")
    private Long userId;

    @ApiModelProperty("App用户名称")
    private String userName;

    @ApiModelProperty("App用户手机号")
    private String userPhone;

    @ApiModelProperty("申请者姓名")
    private String applicant;

    @ApiModelProperty("申请者手机号")
    private String applicantPhone;

    @ApiModelProperty("申请时间")
    private LocalDateTime createTime;

    @ApiModelProperty("图片")
    private String picture;

    @ApiModelProperty("备注")
    private String note;

}
