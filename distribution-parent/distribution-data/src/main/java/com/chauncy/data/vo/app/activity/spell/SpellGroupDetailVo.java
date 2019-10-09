package com.chauncy.data.vo.app.activity.spell;

import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailGoodsVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/10/7 20:40
 */
@Data
@ApiModel(description = "我的拼团详情")
public class SpellGroupDetailVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "拼团id")
    private Long relId;

    @JsonIgnore
    @ApiModelProperty(value = "团员头像")
    private String headPortraits;

    @ApiModelProperty(value = "团员头像 第一个是团长")
    private List<String> headPortrait;

    @ApiModelProperty(value = "拼团状态 1-发起未支付 2-拼团中 3-拼团成功 4-拼团失败   \n")
    private Integer mainStatus;

    @ApiModelProperty(value = "关联订单id")
    private Long orderId;

    @ApiModelProperty(value = "下单商品详情")
    private AppMyOrderDetailGoodsVo goodsDetailVo;

    @ApiModelProperty(value = "收货地址")
    private SpellGroupAreaVo spellGroupAreaVo;

}