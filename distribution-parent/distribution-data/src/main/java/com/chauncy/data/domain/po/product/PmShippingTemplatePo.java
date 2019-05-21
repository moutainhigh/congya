package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 运费模版说明表。平台运费模版+商家运费模版
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_shipping_template")
@ApiModel(value = "PmShippingTemplatePo对象", description = "运费模版说明表。平台运费模版+商家运费模版")
public class PmShippingTemplatePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "运费模版ID")
    private Long id;

    @ApiModelProperty(value = "运费模版名称")
    private String name;

    @ApiModelProperty(value = "是否包邮")
    private Integer isFreePostage;

    @ApiModelProperty(value = "商品地址")
    private String productAddress;

    @ApiModelProperty(value = "计算方式: 按金额。按件数")
    private Integer calculateWay;

    @ApiModelProperty(value = "运费模版类型 1--平台运费模版。2--商家运费模版")
    private Integer type;


}
