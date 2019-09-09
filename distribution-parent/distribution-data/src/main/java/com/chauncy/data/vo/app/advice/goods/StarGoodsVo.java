package com.chauncy.data.vo.app.advice.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/6 16:49
 */
@Data
@ApiModel(description = "明星单品")
@Accessors(chain = true)
public class StarGoodsVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品缩略图")
    private String goodsIcon;

}