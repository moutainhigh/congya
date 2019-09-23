package com.chauncy.data.vo.manage.message.advice.tab.association.acticity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-23 12:22
 *
 * 商品基本信息Vo,只包含ID和name
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "商品基本信息Vo,只包含ID和name")
public class SellHotRelGoodsVo {

    @ApiModelProperty("商品ID")
    @JSONField(ordinal = 12)
    private Long goodsId;

    @ApiModelProperty("商品名称")
    @JSONField(ordinal = 13)
    private String goodsName;
}