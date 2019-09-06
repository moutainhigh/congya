package com.chauncy.data.vo.app.order.my.afterSale;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleLogEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/8/29 11:42
 **/
@ApiModel(description = "我的售后订单详情")
@Data
public class AfterSaleDetailVo {

    @ApiModelProperty(value = "售后方式：ONLY_REFUND-仅退款；RETURN_GOODS-退货退款")
    private AfterSaleTypeEnum afterSaleTypeEnum;

    @ApiModelProperty(value = "售后状态：NEED_STORE_DO-待商家处理；NEED_BUYER_DO-待买家处理；NEED_BUYER_RETURN-待买家退货" +
            "NEED_STORE_REFUND-待商家退款；CLOSE-退款关闭；SUCCESS-退款成功")
    private AfterSaleStatusEnum afterSaleStatusEnum;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operatingTime;

    @ApiModelProperty("剩余分钟数")
    private Long remainMinute;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private AfterSaleLogEnum node;

    @ApiModelProperty("售后内容提示")
    private String contentTips;

    @ApiModelProperty("售后内容说明")
    private String contentExplain;

    @ApiModelProperty("商品名称")
    private String goodsName;


    @ApiModelProperty("sku图片")
    private String picture;


    @ApiModelProperty("商品规格")
    private String standardStr;


    @ApiModelProperty("退款原因")
    private String reason;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "申请件数")
    private BigDecimal number;

    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty(value = "退款编号")
    private Long afterSaleOrderId;



}
