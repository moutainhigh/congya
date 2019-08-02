package com.chauncy.data.vo.manage.order;

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
@ApiModel(description = "订单列表")
@Accessors(chain = true)
public class SearchOrderVo {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("商品数量")
    private Integer totalNumber;

    @ApiModelProperty("订单金额")
    private BigDecimal sumMoney;

    @ApiModelProperty("应付金额")
    private BigDecimal needPayMoney;

    @ApiModelProperty("实收金额")
    private BigDecimal realMoney;

    @ApiModelProperty("收货人姓名")
    private String shipName;

    @ApiModelProperty("收货人地址")
    private String detailedAddress;

    @ApiModelProperty("收货人手机")
    private String mobile;

    @ApiModelProperty("活动类型")
    private String activityType;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;





}
