package com.chauncy.data.domain.po.afterSale;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售后订单表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_after_sale_order")
@ApiModel(value = "OmAfterSaleOrderPo对象", description = "售后订单表")
public class OmAfterSaleOrderPo implements Serializable {

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

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "数量")
    private Integer number;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "售后类型：ONLY_REFUND-仅退款 RETURN_GOODS-退款退货")
    private AfterSaleTypeEnum afterSaleType;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "售后状态：NEED_STORE_DO-待商家处理 NEED_BUYER_DO-待买家处理 NEED_BUYER_RETURN-待买家退货 " +
            "NEED_STORE_REFUND-待商家退款 CLOSE-退款关闭 SUCCESS-退款成功")
    private AfterSaleStatusEnum status;

    @ApiModelProperty(value = "订单类型")
    private String goodsType;

    @ApiModelProperty(value = "图片，用逗号隔开")
    private String pictures;

    @ApiModelProperty(value = "商品快照id")
    private Long goodsTempId;



}
