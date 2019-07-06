package com.chauncy.data.vo.manage.message.interact.feedBack;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-06 17:47
 *
 * 条件查询意见反馈Vo
 */
@Data
@ApiModel(description = "条件查询意见反馈Vo")
public class SearchFeedBackVo {

    @ApiModelProperty("app用户ID")
    private Long userId;

    @ApiModelProperty("app用户昵称")
    private String nickName;

    @ApiModelProperty("反馈时间")
    private LocalDateTime feedTime;

    @ApiModelProperty("反馈内容")
    private String content;

}
