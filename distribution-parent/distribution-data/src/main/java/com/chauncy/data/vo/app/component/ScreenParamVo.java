package com.chauncy.data.vo.app.component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/19 17:31
 */
@Data
@ApiModel(value = "ScreenParamVo", description =  "app筛选参数")
public class ScreenParamVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品筛选参数")
    private ScreenGoodsParamVo screenGoodsParamVo;

    @ApiModelProperty(value = "店铺筛选参数")
    private ScreenStoreParamVo screenStoreParamVo;

    @ApiModelProperty(value = "资讯筛选参数")
    private ScreenInfoParamVo screenInfoParamVo;

}
