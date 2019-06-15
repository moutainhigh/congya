package com.chauncy.data.dto.supplier.good.add;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-15 16:08
 */
@Data
@ApiModel(description = "添加商品额外的属性值")
public class AddExtraValueDto {

    private Long GoodsAttributeId;


}
