package com.chauncy.data.dto.manage.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/23 17:50
 **/

@Data
@ApiModel(description = "搜索订单列表")
@Accessors(chain = true)
public class SearchOrderDto {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("商店id")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("下单开始时间")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("下单结束时间")
    private LocalDateTime endCreateTime;

    @ApiModelProperty("订单状态 0-未支付 1-待发货 2-待收货 3-待评价 4-已完成" +
            "5-待使用 6-已使用 7-已取消")
    private Integer orderStatus;

    @ApiModelProperty("订单最小金额")
    private BigDecimal minMoney;

    @ApiModelProperty("订单最大金额")
    private BigDecimal maxMoney;
}
