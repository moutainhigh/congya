package com.chauncy.data.vo.manage.message.interact.push;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-09 12:41
 *
 * 推送信息列表
 */
@ApiModel(description = "推送信息列表")
@Data
@Accessors(chain = true)
public class InteractPushVo {


    @ApiModelProperty(value = "推送的信息ID")
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "推送时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "推送方式 1、通知栏推送 2、APP内消息中心推送")
    private String pushType;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "图文详情")
    private String detailHtml;

    @ApiModelProperty(value = "列表显示推送对象类型 1、全部用户 2、指定用户 3、指定会员等级",hidden = true)
    @JSONField(serialize = false)
    private String objectType;

    @ApiModelProperty(value = "指定用户列表")
    private List<UmUsersVo> userList;

    @ApiModelProperty(value = "指定会员等级ID")
    private Long memberLevelId;

    @ApiModelProperty(value = "指定会员等级名称")
    private String memberLevelName;

    @ApiModelProperty(value = "显示具体的推送对象 1、全部用户 2、指定的具体用户 3、指定的具体会员等级")
    private String specifiedObject;
}
