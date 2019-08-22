package com.chauncy.data.dto.manage.order.bill.select;

import com.chauncy.data.dto.base.BaseSearchPagingDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/8/20 20:55
 */
@Data
@ApiModel(value = "SearchOrderReportDto", description = "查询订单交易报表参数")
public class SearchOrderReportDto extends BaseSearchPagingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    private Long billId;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "查询金额最小值")
    private BigDecimal minTotalAmount;

    @ApiModelProperty(value = "查询金额最大值")
    private BigDecimal maxTotalAmount;


}
