package com.chauncy.data.vo.app.message.information.interact;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/14 16:26
 */
@Data
@ApiModel(value = "UnreadNoticeNumVo", description =  "未读消息数目")
public class UnreadNoticeNumVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快递物流消息未读数目")
    private Integer expressNum;

    @ApiModelProperty(value = "系统通知消息未读数目")
    private Integer systemNoticeNum;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "系统指定用户通知消息未读数目")
    private Integer systemNoticeNum1;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "系统全部用户通知消息未读数目")
    private Integer systemNoticeNum2;

    @ApiModelProperty(value = "任务奖励消息未读数目")
    private String taskRewardNum;


}