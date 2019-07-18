package com.chauncy.data.vo.app.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/9 23:52
 **/

@Data
@ApiModel(description = "根据店铺拆单")
@Accessors(chain = true)
public class StoreOrderVo {

    @ApiModelProperty("根据商品类型拆单列表")
    private List<GoodsTypeOrderVo> goodsTypeOrderVos;

    @ApiModelProperty("供应商")
    private String storeName;


}
