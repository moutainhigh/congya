package com.chauncy.data.vo.app.car;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/7/9 23:54
 **/

@Data
@ApiModel(description = "购物车商品vo")
@Accessors(chain = true)
public class CarGoodsVo {

    @ApiModelProperty("skuid")
    private long id;

    @ApiModelProperty("商品缩略图")
    private String icon;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品规格")
    private String standardStr;

    @ApiModelProperty("销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty("数量")
    private int number;

    @JSONField(serialize = false)
    private String storeName;

    @JSONField(serialize = false)
    private String goodsType;


}
