package com.chauncy.data.dto.manage.good.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/12  10:45
 * @Version 1.0
 */
@Data
@ApiModel (value = "列表查询")
public class AssociationGoodsDto {

    @ApiModelProperty (value = "搜索ID")
    private Long id;

    @ApiModelProperty (value = "idList")
    private List<Long> idList;

    @ApiModelProperty (value = "当前编辑的商品id")
    @NotNull(message = "当前编辑的商品id不能为空")
    private Long goodsId;

    @ApiModelProperty (value = "模糊查询名称")
    private String name;

    @Min (1)
    @ApiModelProperty (value = "页码")
    private Integer pageNo;

    @Min (1)
    @ApiModelProperty (value = "分页大小")
    private Integer pageSize;
}
