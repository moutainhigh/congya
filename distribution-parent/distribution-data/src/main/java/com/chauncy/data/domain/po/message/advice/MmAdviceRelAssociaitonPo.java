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
 * 广告与品牌、店铺分类/商品分类关联表,其中当广告位置为店铺分类详情关联的店铺分类是点击不同店铺分类就有不同的选项卡+推荐的店铺(多关联选项卡)；当广告位置为葱鸭百货时关联的就是该表的商品三级分类；当广告位为有品详情或葱鸭百货二级分类时关联的是点击不同的品牌或一级分类时就有不同的轮播图(有关联的轮播图)。
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_advice_rel_associaiton")
@ApiModel(value = "MmAdviceRelAssociaitonPo对象", description = "广告与品牌、店铺分类/商品分类关联表,其中当广告位置为店铺分类详情关联的店铺分类是点击不同店铺分类就有不同的选项卡+推荐的店铺(多关联选项卡)；当广告位置为葱鸭百货时关联的就是该表的商品三级分类；当广告位为有品详情或葱鸭百货二级分类时关联的是点击不同的品牌或一级分类时就有不同的轮播图(有关联的轮播图)。")
public class MmAdviceRelAssociaitonPo implements Serializable {

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

    @ApiModelProperty(value = "广告ID")
    private Long adviceId;

    @ApiModelProperty(value = "关联的ID：品牌ID/商品三级分类ID/店铺分类ID")
    private Long associationId;

    @ApiModelProperty(value = "关联类型:1->店铺分类,2->品牌,3->商品,4->商品三级分类 ")
    private Integer type;

    @ApiModelProperty(value = "冗余三级分类对应的一级分类")
    private Long firstCategoryId;

    @ApiModelProperty(value = "冗余三级分类对应的二级分类")
    private Long secondCategoryId;


}
