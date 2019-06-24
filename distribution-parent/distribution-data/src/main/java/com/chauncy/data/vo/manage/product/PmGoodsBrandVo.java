package com.chauncy.data.vo.manage.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/24 13:54
 */
@Data
@ApiModel(value = "PmGoodsBrandVo对象", description = "商品品牌属性")
public class PmGoodsBrandVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌属性id")
    private Long id;

    @ApiModelProperty(value = "品牌属性名称")
    private String name;

}
