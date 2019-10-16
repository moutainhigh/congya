package com.chauncy.data.vo.manage.order.list;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/25 14:33
 **/

@Data
@ApiModel(description = "订单详情")
@Accessors(chain = true)
public class OrderDetailVo {

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty("运费")
    private BigDecimal shipMoney;

    @ApiModelProperty("订单金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("优惠金额")
    // TODO: 2019/7/25 没有活动优惠金额都是0
    private BigDecimal discountMoney = BigDecimal.ZERO;

    @ApiModelProperty("应付金额")
    private BigDecimal needPayMoney;

    @ApiModelProperty("实收金额")
    private BigDecimal realMoney;

    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "收货时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty("支付流水号")
    private String payOrderNo;

    @ApiModelProperty("支付方式")
    private String payTypeCode;

    @ApiModelProperty(value = "支付金额，精确到分")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "app用户ID")
    private Long umUserId;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "收货地区")
    private String areaName;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(hidden = true,value = "用户实名认证id")
    @JSONField(serialize = false)
    private Long realUserId;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "真实姓名")
    private String trueName;

    @ApiModelProperty(value = "身份证正面照片")
    private String frontPhoto;

    @ApiModelProperty(value = "身份证反面照片")
    private String backPhoto;

    @ApiModelProperty(value = "商品信息列表")
    private List<GoodsTempVo> goodsTempVos;





}
