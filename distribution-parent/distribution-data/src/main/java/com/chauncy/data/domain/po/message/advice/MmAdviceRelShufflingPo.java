package com.chauncy.data.domain.po.message.advice;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.chauncy.common.enums.app.advice.AdviceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告与无关联轮播图关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_advice_rel_shuffling")
@ApiModel(value = "MmAdviceRelShufflingPo对象", description = "广告与无关联轮播图关联表")
public class MmAdviceRelShufflingPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "广告类型：1-'图文详情',2-'资讯',3-'店铺',4-'商品'")
    private AdviceTypeEnum adviceType;

    @ApiModelProperty(value = "广告类型为图文详情时的信息")
    private String htmlDetail;

    @ApiModelProperty(value = "商品ID/店铺ID/资讯ID")
    private Long detailId;

    @ApiModelProperty(value = "广告类型为资讯时的资讯ID(没用)")
    private Long informationId;

    @ApiModelProperty(value = "广告类型为店铺的店铺ID(没用)")
    private Long storeId;

    @ApiModelProperty(value = "广告类型为商品时的商品ID(没用)")
    private Long goodsId;

    @ApiModelProperty(value = "封面图片")
    private String coverPhoto;

    @ApiModelProperty(value = "广告ID")
    private Long adviceId;

    @ApiModelProperty(value = "广告和一级类目关联的id")
    private Long relCategoryId;

    @ApiModelProperty(value = "商品一级类目ID")
    private Long firstCategoryId;

    @ApiModelProperty(value = "广告选项卡关联的品牌的记录的ID")
    private Long relTabBrandId;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @ApiModelProperty(value = "选项卡ID或者热销广告ID")
    private Long tabId;

    @ApiModelProperty(value = "活动分组与广告关联ID")
    private Long relActivityGroupId;
}
