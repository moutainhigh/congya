package com.chauncy.data.vo.app.activity.spell;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/6 23:15
 */
@Data
@ApiModel(description = "我的拼团")
public class MySpellGroupVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团id")
    private Long mainId;

    @ApiModelProperty(value = "拼团id am_spell_group_member主键")
    private Long relId;

    @ApiModelProperty(value = "团员头像")
    private List<String> headPortrait;

    @ApiModelProperty(value = "已拼团人数")
    private Integer payedNum;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty("划线价")
    private BigDecimal linePrice;

    @ApiModelProperty(value = "商品缩略图")
    private String icon;

    @ApiModelProperty(value = "拼团状态 1-发起未支付 2-拼团中 3-拼团成功 4-拼团失败   \n")
    private Integer mainStatus;

    @ApiModelProperty(value = "拼团成功展示：关联订单id")
    private Long orderId;

    @ApiModelProperty(value = "拼团成功展示：关联订单状态")
    private OrderStatusEnum orderStatus;

}