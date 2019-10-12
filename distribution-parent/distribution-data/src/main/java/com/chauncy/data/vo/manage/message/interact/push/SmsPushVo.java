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
@ApiModel(description = "推送短息信息列表")
@Data
@Accessors(chain = true)
public class SmsPushVo {


    @ApiModelProperty(value = "推送的信息ID")
    private Long id;

    @ApiModelProperty(value = "推送时间")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "短信内容")
    private String content;

    @ApiModelProperty(value = "阿里云短信模板")
    private String templateCode;

    @ApiModelProperty(value = "列表显示推送对象类型 1、全部用户 2、指定用户 3、指定会员等级")
    private Integer objectType;


}
