package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储产品参数信息的表
 * <p>
 * 规格值
 * 参数值
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_attribute_value")
@ApiModel(value = "PmGoodsAttributeValuePo对象", description = "存储产品参数信息的表 规格值 参数值")
public class PmGoodsAttributeValuePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性id")
    private Long productAttributeId;

    @ApiModelProperty(value = "属性值或材质")
    private String value;

    @ApiModelProperty(value = "上市时间")
    private LocalDateTime listingDate;


}
