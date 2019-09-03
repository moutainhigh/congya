package com.chauncy.data.vo.app.order.my.afterSale;

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

    @ApiModelProperty(value = "当前售后状态")
    private String currentStatus;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operatingTime;

    @ApiModelProperty("剩余分钟数")
    private Long remainMinute;

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
    private BigDecimal applyTime;

    @ApiModelProperty(value = "退款编号")
    private Long afterSaleOrderId;

    @ApiModelProperty("退款方式")
    private String refundWay;


}
