package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.common.enums.goods.StoreGoodsTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/9 12:43
 */
@Data
@ApiModel(value = "StockTemplateBaseDto对象", description = "新增编辑库存模板基本信息")
public class StockTemplateBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "库存模板名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "商品类型 1->自有商品  2->分配商品(编辑时不可变)")
    @EnumConstraint(target = StoreGoodsTypeEnum.class)
    private Integer type;

    @ApiModelProperty(value = "商品id集合")
    @NotEmpty
    @NeedExistConstraint(tableName = "pm_goods")
    private Long[] goodsIds;

}
