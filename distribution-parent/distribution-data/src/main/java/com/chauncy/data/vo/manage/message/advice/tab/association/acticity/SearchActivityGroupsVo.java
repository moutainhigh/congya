package com.chauncy.data.vo.manage.message.advice.tab.association.acticity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-07 14:25
 *
 * 条件分页查询活动分组信息
 */
@Data
@ApiModel(description = "条件分页查询活动分组信息")
@Accessors(chain = true)
public class SearchActivityGroupsVo {

    @ApiModelProperty(value = "活动分组ID")
    private Long id;

    @ApiModelProperty(value = "活动分组名称")
    private String name;

    @ApiModelProperty(value = "活动分组图片")
    private String picture;
}
