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
 * 广告选项卡与商品/品牌关联表，广告位置为具体店铺分类、特卖、有品、主题和优选，非多重关联选项卡
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_advice_rel_tab_things")
@ApiModel(value = "MmAdviceRelTabThingsPo对象", description = "广告选项卡与商品/品牌关联表，广告位置为具体店铺分类、特卖、有品、主题和优选，非多重关联选项卡")
public class MmAdviceRelTabThingsPo implements Serializable {

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

    @ApiModelProperty(value = "选项卡ID")
    private Long tabId;

    @ApiModelProperty(value = "关联ID：品牌id、商品ID、店铺分类ID")
    private Long associationId;

    @ApiModelProperty(value = "类型:1->店铺分类,2->品牌,3->商品,4->商品三级分类")
    private Integer type;
}
