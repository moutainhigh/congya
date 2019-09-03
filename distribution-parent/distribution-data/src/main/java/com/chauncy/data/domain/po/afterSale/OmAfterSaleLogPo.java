package com.chauncy.data.domain.po.afterSale;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleLogEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售后详情表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_after_sale_log")
@ApiModel(value = "OmAfterSaleLogPo对象", description = "售后详情表")
public class OmAfterSaleLogPo implements Serializable {

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


    @ApiModelProperty(value = "售后进行到哪个节点,总共有19个，具体看代码解释")
    private AfterSaleLogEnum node;

    @ApiModelProperty(value = "描述，包括用户描述和商家描述")
    private String describe;


   /* @ApiModelProperty(value = "用户id")
    private Long userId;*/

    /*@ApiModelProperty(value = "退款金额")
    private BigDecimal money;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "描述，包括用户描述和商家描述")
    private String distribution;

    @ApiModelProperty(value = "图片，多个用逗号隔开")
    private String picture;

    @ApiModelProperty(value = "退货收货人姓名")
    private String receiveName;

    @ApiModelProperty(value = "电话")
    private String receivePhone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "邮编")
    private String postalCode;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号")
    private String shipNo;*/

    @ApiModelProperty(value = "售后订单id")
    private Long afterSaleOrderId;


}
