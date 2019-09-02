package com.chauncy.data.vo.app.order.my.afterSale;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleLogEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019/8/29 16:23
 **/
@ApiModel(description = "我的售后订单")
@Data
public class MyAfterSaleOrderListVo {

    @ApiModelProperty(value = "售后编号")
    private Long id;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("sku图片")
    private String picture;

    @ApiModelProperty("商品规格")
    private String standardStr;

    @ApiModelProperty("数量")
    private Integer number;

    @ApiModelProperty(name = "status",value = "当前售后状态")
    private AfterSaleLogEnum afterSaleLogEnum;

}
