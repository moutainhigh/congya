package com.chauncy.data.domain.po.product;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
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

/**
 * <p>
 * 按件数计算运费
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_number_shipping")
@ApiModel(value = "PmNumberShippingPo对象", description = "按件数计算运费")
public class PmNumberShippingPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "按件数计算运费ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "指定地区")
    private String destination;

    @ApiModelProperty(value = "默认运费的最大件数")
    private Integer defaultMaxNumber;

    @ApiModelProperty(value = "默认最大件数内的运费")
    private BigDecimal defaultMaxNumberMoney;

    @ApiModelProperty(value = "默认超过最大件数每增加件数")
    private Integer defaultAddtionalNumber;

    @ApiModelProperty(value = "默认每增加件数就增加的运费")
    private BigDecimal defaultAddtionalFreight;

    @ApiModelProperty(value = "指定地区运费的最大件数")
    private Integer destinationMaxNumber;

    @ApiModelProperty(value = "指定地区最大件数内的运费")
    private BigDecimal destinationMaxNumberMoney;

    @ApiModelProperty(value = "指定地区超过最大件数每增加件数")
    private Integer destinationAddtionalNumber;

    @ApiModelProperty(value = "指定地区每增加件数就增加的运费")
    private BigDecimal destinationAddtionalFreight;

    @ApiModelProperty(value = "运费模版ID")
    private Long shippingId;

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


}
