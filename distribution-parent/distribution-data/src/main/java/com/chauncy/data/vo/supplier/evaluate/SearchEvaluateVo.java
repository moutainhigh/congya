package com.chauncy.data.vo.supplier.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-02 13:18
 *
 * 查找评价信息
 */
@ApiModel(description = "查找评价信息Vo")
@Data
public class SearchEvaluateVo {

    @ApiModelProperty(value = "评价id")
    private Long evaluateId;

    @ApiModelProperty(value = "评价时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "skuId")
    private Long sku_id;

    @ApiModelProperty(value = "phone")
    private String phone;

    @ApiModelProperty(value = "商品名称")
    private  String goodsName;

    @ApiModelProperty(value = "宝贝描述星级")
    private Integer descriptionStartLevel;

    @ApiModelProperty(value = "评价详情")
    private EvaluateVo evaluateVo;

}
