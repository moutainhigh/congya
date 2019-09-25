package com.chauncy.data.vo.app.advice.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/24 16:54
 */
@Data
@ApiModel(description = "活动分组")
@Accessors(chain = true)
public class ActivityGroupListVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告与活动分组关联表id")
    private Long relId;

    @ApiModelProperty(value = "分组id")
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "分组图片")
    private String groupPicture;
}


