package com.chauncy.data.vo.app.order.my;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.data.vo.supplier.order.SmSendGoodsTempVo;
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
@ApiModel(description = "用户_我的订单")
@Accessors(chain = true)
public class AppSearchOrderVo {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty("商品数量")
    private Integer totalNumber;

    @ApiModelProperty("实际支付金额")
    private BigDecimal realMoney;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("订单商品信息")
    private List<SmSendGoodsTempVo> smSendGoodsTempVos;



}
