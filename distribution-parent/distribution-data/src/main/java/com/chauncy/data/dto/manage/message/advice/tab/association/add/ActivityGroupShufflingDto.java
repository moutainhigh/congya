package com.chauncy.data.dto.manage.message.advice.tab.association.add;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-09-22 22:02
 *
 * 不同广告不同热销广告选项卡下的轮播图广告
 */
@Data
@ApiModel(description = "不同广告不同热销广告选项卡下的轮播图广告")
@Accessors(chain = true)
public class ActivityGroupShufflingDto {

    @ApiModelProperty(value = "id,新增时前端传0")
    @JSONField(ordinal = 0)
    private Long shufflingId;

    @ApiModelProperty(value = "开始时间")
//    @Future(message = "开始时间需要在当前时间之后")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    @JSONField(ordinal = 1)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
//    @Future(message = "结束时间需要在当前时间之后")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    @JSONField(ordinal = 2)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "广告类型：1-'图文详情',2-'资讯',3-'店铺',4-'商品'")
    @JSONField(ordinal = 3)
    private Integer adviceType;

    @ApiModelProperty(value = "广告类型为图文详情时的信息")
    @JSONField(ordinal = 4)
    private String htmlDetail;

    @ApiModelProperty(value = "商品ID/店铺ID/资讯ID")
    @JSONField(ordinal = 5)
    private Long detailId;

    @ApiModelProperty(value = "封面图片")
    @JSONField(ordinal = 6)
    private String coverPhoto;

    @ApiModelProperty(value = "热销广告选项卡ID",hidden = true)
    private Long tabId;

    @ApiModelProperty(value = "广告ID",hidden = true)
    private Long adviceId;

    @ApiModelProperty(value = "广告和一级类目关联的id",hidden = true)
    private Long relCategoryId;

    @ApiModelProperty(value = "商品一级类目ID",hidden = true)
    private Long firstCategoryId;

    @ApiModelProperty(value = "广告选项卡关联的品牌的记录的ID",hidden = true)
    private Long relTabBrandId;

    @ApiModelProperty(value = "品牌ID",hidden = true)
    private Long brandId;


}
