package com.chauncy.data.bo.app.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @description: 新增用户APP内消息
 * @since 2019/10/22 16:56
 */
@Data
@ApiModel(value = "SaveUserNoticeBo", description = "新增用户APP内消息")
public class SaveUserNoticeBo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "消息对应的订单id，如订单签收，发货的消息")
    private Long orderId;

    @ApiModelProperty(value = "消息对应的售后订单id")
    private Long afterSaleOrderId;

    @ApiModelProperty(value = "消息对应的用户提现id")
    private List<Long> withdrawalIdList;

}
