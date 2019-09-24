package com.chauncy.data.domain.po.order;

import java.math.BigDecimal;

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
 * 定时任务刷新店铺相关评分信息
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_evaluate_quartz")
@ApiModel(value = "OmEvaluateQuartzPo对象", description = "定时任务刷新店铺相关评分信息")
public class OmEvaluateQuartzPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "店铺描述平均星级")
    private BigDecimal descriptionStartLevel;

    @ApiModelProperty(value = "物流服务平均星级")
    private BigDecimal shipStartLevel;

    @ApiModelProperty(value = "服务态度平均星级")
    private BigDecimal attitudeStartLevel;

    @ApiModelProperty (value = "商品描述")
    private String babyDescriptionLevel;

    @ApiModelProperty ("卖家服务")
    private String sellerServiceLevel;

    @ApiModelProperty ("发货速度")
    private String logisticsServicesLevel;


}
