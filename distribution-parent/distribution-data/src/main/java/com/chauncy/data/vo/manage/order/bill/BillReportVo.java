package com.chauncy.data.vo.manage.order.bill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/8/20 21:04
 */
@Data
@ApiModel(value = "订单交易报表信息")
public class BillReportVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "总货款/总利润")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "总商品数量")
    private BigDecimal totalNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
