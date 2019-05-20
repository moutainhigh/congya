package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-19 23:59
 *
 * 运费模版
 *
 * ->平台运费模版
 * ->商家运费模版
 *
 */
@Data
@TableName(value = "pm_shipping_template")
public class ShippingTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @ApiModelProperty(value = "运费模版名称")
    private String name;

    @ApiModelProperty(value = "商品地址")
    private String productAddress;

    @ApiModelProperty(value = "计算方式 1->按金额 2->按件数")
    private int calculate;

    @ApiModelProperty(value = "运费模版类型 1->平台运费模版。2->商家运费模版")
    private int type;

}
