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
 * 今日必拼广告绑定参加拼团活动商品关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_advice_rel_spell_goods")
@ApiModel(value = "MmAdviceRelSpellGoodsPo对象", description = "今日必拼广告绑定参加拼团活动商品关联表")
public class MmAdviceRelSpellGoodsPo implements Serializable {

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
//    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "一级分类id")
    private Long firstCategoryId;

    @ApiModelProperty(value = "二级分类id")
    private Long secondCategoryId;

    @ApiModelProperty(value = "三级分类id")
    private Long thirdCategoryId;

    @ApiModelProperty(value = "广告id")
    private Long adviceId;


}
