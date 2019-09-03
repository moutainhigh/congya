package com.chauncy.data.domain.po.message.advice;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告与(品牌、一级分类)关联轮播图关联表(不同品牌、不同一级分类有不同广告轮播图)
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_advice_rel_shuffling_association")
@ApiModel(value = "MmAdviceRelShufflingAssociationPo对象", description = "广告与(品牌、一级分类)关联轮播图关联表(不同品牌、不同一级分类有不同广告轮播图)")
public class MmAdviceRelShufflingAssociationPo implements Serializable {

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

    @ApiModelProperty(value = "广告类型：HTML_DETAIL-'图文详情',INFORMATION-'资讯',STROE-'店铺',GOODS-'商品'")
    private String adviceType;

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

    @ApiModelProperty(value = "广告与品牌、店铺分类/商品分类关联表id")
    private Long relId;


}
