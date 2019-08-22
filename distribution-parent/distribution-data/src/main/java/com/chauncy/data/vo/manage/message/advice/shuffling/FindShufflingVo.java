package com.chauncy.data.vo.manage.message.advice.shuffling;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-08-20 14:48
 *
 * 查找广告对应的轮播图广告
 */
@Data
@ApiModel("查找广告对应的轮播图广告")
@Accessors(chain = true)
public class FindShufflingVo {

    @ApiModelProperty(value = "轮播图广告ID")
    @JSONField(ordinal = 4)
    private Long shufflingId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 5)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 6)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "广告类型：1-'图文详情',2-'资讯',3-'店铺',4-'商品'")
    @JSONField(ordinal = 7)
    private AdviceTypeEnum adviceType;

    @ApiModelProperty(value = "广告类型为图文详情时的信息")
    @JSONField(ordinal = 8)
    private String htmlDetail;

    @ApiModelProperty(value = "商品ID/店铺ID/资讯ID")
    @JSONField(ordinal = 9)
    private Long detailId;

    @ApiModelProperty(value = "商品名称/店铺名称/资讯名称")
    @JSONField(ordinal = 10)
    private String detailName;

    @ApiModelProperty(value = "封面图片")
    @JSONField(ordinal = 11)
    private String coverPhoto;
}
