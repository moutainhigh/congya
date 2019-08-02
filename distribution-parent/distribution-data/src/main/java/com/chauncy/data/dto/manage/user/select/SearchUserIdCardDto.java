package com.chauncy.data.dto.manage.user.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/4 16:31
 **/
@ApiModel(description = "用户实名认证列表")
@Data
@Accessors(chain = true)
public class SearchUserIdCardDto {
    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("用户状态 0-启用 1-禁用")
    private Boolean enabled;

    @ApiModelProperty("最近登陆开始时间")
    private LocalDateTime startRecentLoginTime;

    @ApiModelProperty("最近登陆结束时间")
    private LocalDateTime endRecentLoginTime;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
