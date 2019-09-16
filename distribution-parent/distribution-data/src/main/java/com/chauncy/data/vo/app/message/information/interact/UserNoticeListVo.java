package com.chauncy.data.vo.app.message.information.interact;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/9/14 18:24
 */
@Data
@ApiModel(value = "UserNoticeListVo", description =  "用户消息列表")
public class UserNoticeListVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息id")
    private Long id;

    @ApiModelProperty(value = "消息类型 NoticeTypeEnum 1.快递物流 2.系统通知  3.任务奖励")
    private Integer noticeType;

    @ApiModelProperty(value = "消息是否已读 1-是 0-否 默认为0")
    private Boolean isRead;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;



}

