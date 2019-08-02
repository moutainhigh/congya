package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/11  10:46
 * @Version 1.0
 *
 * 商品详情显示的店铺基本信息
 */
@Data
@ApiModel (description = "商品详情显示的店铺基本信息")
public class StoreVo {

    @ApiModelProperty ("店铺id")
    private Long storeId;

    @ApiModelProperty ("店铺名称")
    private String storeName;

    @ApiModelProperty ("店铺缩略图")
    private String storeIcon;

    @ApiModelProperty ("宝贝描述")
    private BigDecimal babyDescription;

    @ApiModelProperty ("卖家服务")
    private BigDecimal sellerService;

    @ApiModelProperty ("物流服务")
    private BigDecimal logisticsServices;

}
