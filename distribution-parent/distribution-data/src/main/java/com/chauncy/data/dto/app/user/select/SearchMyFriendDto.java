package com.chauncy.data.dto.app.user.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-09-18 09:43
 *
 * 分页查询我的粉丝条件
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "分页查询我的粉丝条件")
public class SearchMyFriendDto {

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;

}
