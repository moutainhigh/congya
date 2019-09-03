package com.chauncy.data.dto.manage.message.advice.tab.tab.add.shuffling;

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
 *
 * @author huangwancheng
 * @since 2019-08-14
 *
 * 添加不同品牌不同广告轮播图信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "添加不同品牌不同广告轮播图信息")
public class BrandShufflingDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,新增时前端传0")
    private Long shufflingId;

    @ApiModelProperty(value = "开始时间")
    @Future(message = "开始时间需要在当前时间之后")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @Future(message = "结束时间需要在当前时间之后")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "广告类型：1-'图文详情',2-'资讯',3-'店铺',4-'商品'")
    private Integer adviceType;

    @ApiModelProperty(value = "广告类型为图文详情时的信息")
    private String htmlDetail;

    @ApiModelProperty(value = "商品ID/店铺ID/资讯ID")
    private Long detailId;

    @ApiModelProperty(value = "封面图片")
    private String coverPhoto;

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
