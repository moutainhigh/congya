package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-06-20 11:07
 *
 * 显示给前端属性信息
 */
@ApiModel(description = "财务需要的信息")
@Data
public class FinanceStandardInfo {

    @ApiModelProperty("规格ID")
    private Long attributeId;

    @ApiModelProperty("规格名称")
    private String attributeName;

    @ApiModelProperty("规格名称")
    private List<FindSkuFinanceVo> findSkuFinanceVos;
}
