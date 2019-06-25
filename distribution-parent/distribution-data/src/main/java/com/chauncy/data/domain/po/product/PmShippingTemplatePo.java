package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 运费模版说明表。平台运费模版+商家运费模版
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_shipping_template")
@ApiModel(value = "PmShippingTemplatePo对象", description = "运费模版说明表。平台运费模版+商家运费模版")
public class PmShippingTemplatePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "运费模版ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "运费模版名称")
    private String name;

    @ApiModelProperty(value = "是否包邮")
    private Boolean isFreePostage;

    @ApiModelProperty(value = "商品地址")
    private Long productAddressId;

    @ApiModelProperty(value = "计算方式: 1--按金额。2--按件数")
    private Integer calculateWay;

    @ApiModelProperty(value = "运费模版类型 1--平台运费模版。2--商家运费模版")
    private Integer type;

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

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "默认(基础)运费")
    private BigDecimal defaultFreight;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    private BigDecimal defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费")
    private BigDecimal defaultPostMoney;

    @ApiModelProperty(value = "默认运费的最大件数")
    private Integer defaultMaxNumber;

    @ApiModelProperty(value = "默认最大件数内的运费")
    private BigDecimal defaultMaxNumberMoney;

    @ApiModelProperty(value = "默认超过最大件数每增加件数")
    private Integer defaultAddtionalNumber;

    @ApiModelProperty(value = "默认每增加件数就增加的运费")
    private BigDecimal defaultAddtionalFreight;

    @ApiModelProperty(value = "模版审核状态")
    private Integer verifyStatus;

    @ApiModelProperty(value = "提交审核时间")
    private LocalDateTime submitTime;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核者")
    private  String auditor;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "运费模版不通过详情")
    private String content;
}
