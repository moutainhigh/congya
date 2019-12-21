package com.chauncy.data.vo.app.order.my.detail;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.data.bo.app.logistics.LogisticsDataBo;
import com.chauncy.data.vo.manage.order.list.GoodsTempVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * app我的订单详情
 * @Author zhangrt
 * @Date 2019/7/25 14:33
 **/

@Data
@ApiModel(description = "app我的订单详情")
@Accessors(chain = true)
public class AppMyOrderDetailVo {

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "收货时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "取消时间")
    private LocalDateTime closeTime;

    @ApiModelProperty("支付流水号")
    private String payOrderNo;

    @ApiModelProperty("订单金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("预计奖励购物券")
    private BigDecimal rewardShopTicket;

    @ApiModelProperty("预计返积分")
    private BigDecimal rewardIntegral;

    @ApiModelProperty("预计返经验值")
    private BigDecimal rewardExperience;

    /*@ApiModelProperty(value = "支付金额，精确到分")
    private BigDecimal payAmount;*/
    @ApiModelProperty(value = "红包抵扣金额")
    private BigDecimal shopTicketMoney;

    @ApiModelProperty(value = "购物券抵扣金额")
    private BigDecimal redEnvelopsMoney;

    @ApiModelProperty(value = "优惠券抵扣金额")
    private BigDecimal couponMoney;

    @ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal integralMoney;

    @ApiModelProperty("运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "税费")
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "实际总付")
    private BigDecimal realMoney;


    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "收货地区")
    private String areaName;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "店铺和商品信息")
    private AppMyOrderDetailStoreVo appMyOrderDetailStoreVo;

    @ApiModelProperty("物流节点信息")
    private List<LogisticsDataBo> logisticsData;

    @ApiModelProperty("二维码")
    private String qRCode;

    @ApiModelProperty(value = "商家地址")
    private String companyAddr;

    @ApiModelProperty(value = "商家手机号码")
    private String ownerMobile;

    @ApiModelProperty(value = "商家名称")
    private String storeName;

    @ApiModelProperty(value = "商家id")
    private Long storeId;

    @ApiModelProperty(value = "是否拼团订单")
    private Boolean isGroup;

    @ApiModelProperty(value = "拼团id,查看评团详情时要传的id")
    private Long relId;

    @ApiModelProperty(value = "团员头像 第一个是团长")
    private List<String> headPortrait;

    @ApiModelProperty("客服im账号")
    private String imAccount;


}
