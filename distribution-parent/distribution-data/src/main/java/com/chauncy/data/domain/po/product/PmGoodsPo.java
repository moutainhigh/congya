package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@Accessors(chain = true)
@TableName("pm_goods")
@ApiModel(value = "PmGoodsPo对象", description = "商品信息")
public class PmGoodsPo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "分类ID")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "商品缩略图")
    private String icon;

    @ApiModelProperty(value = "轮播图")
    private String carouselImage;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "运费模版ID")
    private Long shippingTemplateId;

    @ApiModelProperty(value = "发货地")
    private String location;

    @ApiModelProperty(value = "限购数量")
    private Integer purchaseLimit;

    @ApiModelProperty(value = "是否含税")
    private Boolean isTax;

    @ApiModelProperty(value = "活动成本比例")
    private BigDecimal activityCostRate;

    @ApiModelProperty(value = "让利成本比例")
    private BigDecimal profitsRate;

    @ApiModelProperty(value = "推广成本比例")
    private BigDecimal generalizeCostRate;

    @ApiModelProperty(value = "税率类型 1--平台税率 2--自定义税率 3—无税率")
    private Integer taxRateType;

    @ApiModelProperty(value = "自定义税率")
    private BigDecimal customTaxRate;

    @ApiModelProperty(value = "商品排序数字")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否包邮 默认为0不包邮")
    private Boolean isFreePostage;

    @ApiModelProperty(value = "商品库存")
    private Integer stock;

    @ApiModelProperty(value = "库存预警")
    private Integer lowStock;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Boolean publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    private Boolean newStatus;

    @ApiModelProperty(value = "是否是店铺推荐；0->不推荐；1->推荐")
    private Boolean recommandStatus;

    @ApiModelProperty(value = "是否是明星单品；0->否；1->是")
    private Boolean starStatus;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    private Boolean verifyStatus;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "商品类型")
    private String goodsType;

    @ApiModelProperty(value = "spu编码")
    private String spu;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 默认0 0->未删除；1->已删除")
    private Boolean delFlag;

}
