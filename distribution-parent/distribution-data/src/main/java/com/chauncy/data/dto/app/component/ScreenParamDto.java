package com.chauncy.data.dto.app.component;

import com.chauncy.data.dto.app.advice.goods.select.FindRelGoodsParamDto;
import com.chauncy.data.dto.app.message.information.select.FindInfoParamDto;
import com.chauncy.data.dto.app.product.FindStoreGoodsParamDto;
import com.chauncy.data.dto.app.store.FindStoreParamDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/19 17:13
 */
@Data
@ApiModel(value = "ScreenParamDto对象", description = "查询店铺/商品/资讯参数")
public class ScreenParamDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索类型   \n1.商品   \n2.店铺   \n3.资讯     \n4.各种关联下的商品")
    @NotNull(message = "搜索类型不能为空")
    private Integer keyWordType;

    @ApiModelProperty(value = "keyWordType = 1 时传参")
    private FindStoreGoodsParamDto findStoreGoodsParamDto;

    @ApiModelProperty(value = "keyWordType = 2 时传参")
    private FindStoreParamDto findStoreParamDto;

    @ApiModelProperty(value = "keyWordType = 3 时传参")
    private FindInfoParamDto findInfoParamDto;

    @ApiModelProperty(value = "keyWordType = 4 时传参")
    private FindRelGoodsParamDto findRelGoodsParamDto;
}
