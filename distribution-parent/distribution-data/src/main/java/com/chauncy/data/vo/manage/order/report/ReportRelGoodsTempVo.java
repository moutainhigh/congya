package com.chauncy.data.vo.manage.order.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/8/12 17:47
 */
@Data
@ApiModel(value = "商品销售报表关联商品信息")
public class ReportRelGoodsTempVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "规格")
    private String standardStr;

    @ApiModelProperty(value = "货号")
    private String articleNumber;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "分配供货价（元）")
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "小计")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "订单创建时间")
    private LocalDateTime createTime;

}
