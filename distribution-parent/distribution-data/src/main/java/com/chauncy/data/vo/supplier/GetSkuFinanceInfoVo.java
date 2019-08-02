package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-06-18 23:39
 * <p>
 * 商品属性页面
 * <p>
 * 获取前端销售需要的商品sku信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "获取前端销售需要的商品sku信息")
public class GetSkuFinanceInfoVo {

    @ApiModelProperty("商品规格信息")
    @Valid
    @NotNull(message = "商品规格信息不能为空")
    private List<GoodsStandardVo> goodsStandardVo;

    @ApiModelProperty("具体sku列表信息")
    private List<Map<String,Object>> mapList;

}
