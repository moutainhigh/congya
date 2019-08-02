package com.chauncy.data.vo.manage.order.bill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/23 12:00
 */
@Data
@ApiModel(value = "货款/利润账单结算进度信息")
public class BillSettlementVo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "进度")
    private Integer id;

    @ApiModelProperty(value = "进度")
    private String name;

    @ApiModelProperty(value = "时间")
    private LocalDateTime dateTime;

}
