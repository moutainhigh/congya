package com.chauncy.data.vo.manage.message.advice.tab.tab;

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
 *
 * @author huangwancheng
 * @since 2019-08-14
 *
 * 添加不同品牌不同广告轮播图信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "添加不同品牌不同广告轮播图信息")
public class BrandShufflingVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "轮播图广告ID")
    @JSONField(ordinal = 11)
    private Long shufflingId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 12)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 13)
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "广告类型：1-'图文详情',2-'资讯',3-'店铺',4-'商品'")
    @JSONField(ordinal = 14)
    private Integer adviceType;

    @ApiModelProperty(value = "广告类型为图文详情时的信息")
    @JSONField(ordinal = 15)
    private String htmlDetail;

    @ApiModelProperty(value = "商品ID/店铺ID/资讯ID")
    @JSONField(ordinal = 16)
    private Long detailId;

    @ApiModelProperty(value = "商品名称/店铺名称/资讯名称")
    @JSONField(ordinal = 17)
    private String detailName;

    @ApiModelProperty(value = "封面图片")
    @JSONField(ordinal = 18)
    private String coverPhoto;

    @ApiModelProperty(value = "广告ID",hidden = true)
    @JSONField(serialize = false)
    private Long adviceId;

    @ApiModelProperty(value = "广告和一级类目关联的id",hidden = true)
    @JSONField(serialize = false)
    private Long relCategoryId;

    @ApiModelProperty(value = "商品一级类目ID",hidden = true)
    @JSONField(serialize = false)
    private Long firstCategoryId;

    @ApiModelProperty(value = "广告选项卡关联的品牌的记录的ID",hidden = true)
    @JSONField(serialize = false)
    private Long relTabBrandId;

    @ApiModelProperty(value = "品牌ID",hidden = true)
    @JSONField(serialize = false)
    private Long brandId;


}
