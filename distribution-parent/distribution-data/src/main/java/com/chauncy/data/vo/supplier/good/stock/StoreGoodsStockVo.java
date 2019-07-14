package com.chauncy.data.vo.supplier.good.stock;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/12 12:06
 */
@Data
@ApiModel(description = "商品库存模板信息")
public class StoreGoodsStockVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("库存id")
    private Long id;

    @ApiModelProperty(value = "库存名称")
    private String name;

    @ApiModelProperty(value = "库存模板id")
    private Long stockTemplateId;

    @ApiModelProperty(value = "库存模板名称")
    private String stockTemplateName;

    @ApiModelProperty(value = "商品所属店铺id")
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "店铺分配库存总数")
    private Integer distributeStockSum;

    @ApiModelProperty(value = "库存对应的商品规格信息")
    private List<StockTemplateSkuInfoVo> stockTemplateSkuInfoVoList;

}
