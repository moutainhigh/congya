package com.chauncy.data.dto.app.order.evaluate.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-30 17:50
 *
 * 用户评价商品
 */
@Data
@ApiModel(description = "用户评价商品")
@Accessors(chain = true)
public class AddValuateDto {


    @ApiModelProperty(value = "订单编号")
    @NeedExistConstraint(tableName = "om_order")
    private Long orderId;

    @ApiModelProperty(value = "每个商品的评价信息")
    List<AddCommentSku> addCommentSkus;


    @ApiModelProperty(value = "物流服务星级")
    private Integer shipStartLevel;

    @ApiModelProperty(value = "服务态度星级")
    private Integer attitudeStartLevel;



}
