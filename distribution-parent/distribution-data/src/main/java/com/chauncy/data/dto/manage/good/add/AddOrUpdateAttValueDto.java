package com.chauncy.data.dto.manage.good.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-23 17:48
 *
 * 添加或修改规格(包括规格值)
 *
 */
@ApiModel(description ="添加或修改规格(包括规格值)")
@Data
@Accessors(chain=true)
public class AddOrUpdateAttValueDto {

    @ApiModelProperty("规格Id")
    private Long attributeId;

    @ApiModelProperty("规格名称")
    private String attributeName;

    @ApiModelProperty("规格值对象：id规格值ID,name规格值")
    protected List<AttributeValueInfoDto> standardValueInfo;

    @ApiModelProperty("规格排序")
    private BigDecimal sort;

    @ApiModelProperty("是否启用")
    private Boolean enable;

    @ApiModelProperty("规格类型")
    private Integer type;

}
