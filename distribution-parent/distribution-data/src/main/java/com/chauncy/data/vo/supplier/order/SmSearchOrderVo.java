package com.chauncy.data.vo.supplier.order;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/24 12:17
 **/

@Data
@ApiModel(description = "商家端订单列表")
@Accessors(chain = true)
public class SmSearchOrderVo {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty("商品数量")
    private Integer totalNumber;

    @ApiModelProperty("订单金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("收货人姓名")
    private String shipName;

    @ApiModelProperty("收货人地址")
    private String detailedAddress;

    @ApiModelProperty("收货人手机")
    private String mobile;

    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;
}
