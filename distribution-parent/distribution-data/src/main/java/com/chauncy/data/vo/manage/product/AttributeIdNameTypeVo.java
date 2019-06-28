package com.chauncy.data.vo.manage.product;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019/6/27 16:19
 **/
@Data
@ApiModel(value = "分类关联的所有商品属性", description = "商品属性参数 ")
public class AttributeIdNameTypeVo extends BaseVo {

    @ApiModelProperty(value = "规格")
    private Integer type;
}
