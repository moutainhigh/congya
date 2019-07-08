package com.chauncy.data.dto.manage.user.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author zhangrt
 * @Date 2019/7/5 23:32
 **/
@ApiModel(description = "用户列表")
@Data
@Accessors(chain = true)
public class SearchUserListDto {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("会员等级id")
    private Long levelId;

    @ApiModelProperty("名称")
    private String trueName;

    @ApiModelProperty("状态 0-禁用 1-启用")
    private Boolean enabled;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("店铺名")
    private String storeName;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;


}

