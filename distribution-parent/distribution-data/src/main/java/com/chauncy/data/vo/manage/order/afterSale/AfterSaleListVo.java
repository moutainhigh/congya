package com.chauncy.data.vo.manage.order.afterSale;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/23 0:03
 */
@Data
@ApiModel(value = "售后订单列表")
public class AfterSaleListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "售后单号")
    private Long afterSaleOrderId;

    @ApiModelProperty(value = "原订单号")
    private Long orderId;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "售后数量")
    private Integer number;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refund;

    @ApiModelProperty(value = "售后类型：ONLY_REFUND-仅退款 RETURN_GOODS-退款退货")
    private AfterSaleTypeEnum afterSaleType;

    @ApiModelProperty(value = "售后原因")
    private String reason;

    @ApiModelProperty(value = "售后状态：NEED_STORE_DO-待商家处理 NEED_BUYER_DO-待买家处理 NEED_BUYER_RETURN-待买家退货 " +
            "NEED_STORE_REFUND-待商家退款 CLOSE-退款关闭 SUCCESS-退款成功")
    private AfterSaleStatusEnum status;

    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "订单类型")
    private String goodsType;

    //后面的是详情

    @ApiModelProperty(value = "售后图片")
    private List<String> pictures;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "商品id")
    private Long skuId;

    @ApiModelProperty(value = "商品规格")
    private String standardStr;


    @ApiModelProperty(value = "价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "运单号")
    private String billNo;

    @ApiModelProperty(value = "退货说明")
    private String returnPolicy;

    @ApiModelProperty(value = "售后进度")
    List<AfterSaleLogVo> afterSaleLogVos;



}
