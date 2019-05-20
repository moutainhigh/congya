package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 12:59
 *
 * 商品信息
 *
 */
@Data
@TableName(value = "pm_goods")
public class PmGoodsPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "分类ID")
    private int goodsCategoryId;

    @ApiModelProperty(value = "运费模版ID")
    private int shippingTemplateId;

    @ApiModelProperty(value = "会员ID")
    private int memberId;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "商品缩略图")
    private String icon;

    @ApiModelProperty(value = "轮播图")
    private String carouselImage;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "发货地")
    private String location;

    @ApiModelProperty(value = "限购数量")
    private int purchaseLimit;

    @ApiModelProperty(value = "是否含税")
    private boolean isTax;

    @ApiModelProperty(value = "活动成本比例")
    private double activityCostRate;

    @ApiModelProperty(value = "让利成本比例")
    private double profitsRate;

    @ApiModelProperty(value = "推广成本比例")
    private double generalizeCostRate;

    @ApiModelProperty(value = "税率类型 1--平台税率 2--自定义税率 0—无税率")
    private int taxRateType;

    @ApiModelProperty(value = "自定义税率")
    private double customTaxRate;

    @ApiModelProperty(value = "商品排序数字")
    private int goods_sort;

    @ApiModelProperty(value = "商品库存")
    private int stock;

    @ApiModelProperty(value = "库存预警")
    private int lowStock;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private int deleteStatus;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private int publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    private int newStatus;

    @ApiModelProperty(value = "是否是店长推荐；0->不推荐；1->推荐")
    private int recommandStatus;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    private int verifyStatus;

}
