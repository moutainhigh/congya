package com.chauncy.data.dto.manage.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author cheng
 * @create 2019-06-13 23:12
 */
@ApiModel(value = "PmGoodsAttributeValueDto对象", description = "存储产品参数信息 规格值 参数值")
@Data
public class GoodAttributeValueDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "value,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "属性id")
    @NotNull(message = "属性ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_attribute_value",message = "该属性ID不存在！",field = "product_attribute_id")
    private Long productAttributeId;

    @ApiModelProperty(value = "属性值")
    private String value;

}
