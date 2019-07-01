package com.chauncy.data.dto.app.order.evaluate.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    @NeedExistConstraint(tableName = "om_order")
    private Long orderId;

    @ApiModelProperty(value = "sku Id")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    private Long skuId;

    @ApiModelProperty(value = "评价父ID")
    private Long parentId;

    @ApiModelProperty(value = "宝贝描述星级")
    private Integer descriptionStartLevel;

    @ApiModelProperty(value = "物流服务星级")
    private Integer shipStartLevel;

    @ApiModelProperty(value = "服务态度星级")
    private Integer attitudeStartLevel;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评价图片")
    private String picture;

}
