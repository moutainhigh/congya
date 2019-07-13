package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/10 11:09
 */
@Data
@ApiModel(value = "StoreGoodsStockBaseDto对象", description = "新增编辑店铺商品库存基本信息")
public class StoreGoodsStockBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺-商品虚拟库存模板关联表")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "分店库存名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "库存模板id")
    @NeedExistConstraint(tableName = "pm_goods_virtual_stock_template")
    private Long stockTemplateId;

    @ApiModelProperty(value = "分配店铺id")
    @NeedExistConstraint(tableName = "sm_store")
    private Long distributeStoreId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "备注")
    private List<StoreRelGoodsStockBaseDto> storeRelGoodsStockBaseDtoList;


}
