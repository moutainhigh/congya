package com.chauncy.data.vo.manage.product;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/6/27 15:48
 **/

@Data
@ApiModel(value = "分类关联的所有商品属性", description = "商品属性参数 ")
public class CategoryRelAttributeVo {

    @ApiModelProperty(value = "规格")
    private List<AttributeIdNameTypeVo> specificationList;

    @ApiModelProperty(value = "商品参数")
    private List<AttributeIdNameTypeVo> AttributeList;

    @ApiModelProperty(value = "服务说明")
    private List<AttributeIdNameTypeVo> serviceList;

    @ApiModelProperty(value = "购买须知说明")
    private List<AttributeIdNameTypeVo> purchaseList;



}
