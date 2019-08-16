package com.chauncy.data.dto.app.order.evaluate.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/8/15 23:15
 **/
@Data
@ApiModel(description = "用户评价商品")
@Accessors(chain = true)
public class AddCommentSku {


    @ApiModelProperty(value = "宝贝描述星级")
    private Integer descriptionStartLevel;

    @ApiModelProperty(value = "sku Id")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    private Long skuId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评价图片")
    private String picture;
}
