package com.chauncy.data.vo.supplier.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/24 12:17
 **/

@Data
@ApiModel(description = "商家端订单列表")
@Accessors(chain = true)
public class SmSendOrderVo {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("商品数量")
    private Integer totalNumber;

    @ApiModelProperty("商品金额")
    private Integer totalMoney;

    @ApiModelProperty("运费")
    private BigDecimal shipMoney;

    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty("发货时间")
    private LocalDateTime sendTime;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String trueName;

    @ApiModelProperty("收货人姓名")
    private String shipName;

    @ApiModelProperty("收货人地区")
    private String areaName;

    @ApiModelProperty("收货人地址")
    private String shipAddress;

    @ApiModelProperty("收货人手机")
    private String mobile;

    @ApiModelProperty("商品信息")
    private List<SmSendGoodsTempVo> smSendGoodsTempVos;


}