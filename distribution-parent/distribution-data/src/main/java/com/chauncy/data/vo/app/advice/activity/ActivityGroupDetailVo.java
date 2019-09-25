package com.chauncy.data.vo.app.advice.activity;

import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/25 12:05
 */
@Data
@ApiModel(description = "活动分组详情")
public class ActivityGroupDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "轮播图信息")
    private List<ShufflingVo> shufflingVoList;

    @ApiModelProperty(value = "活动分组选项卡信息")
    private List<ActivityGroupTabVo> activityGroupTabVoList;

}
