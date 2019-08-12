package com.chauncy.data.vo.manage.order.bill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/23 0:03
 */
@Data
@ApiModel(value = "货款/利润账单基本信息")
public class BillBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    private Long id;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "期数")
    private String monthDay;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "总货款/总利润")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "状态 1.待提现 2.待审核 3.处理中 4.结算完成 5.结算失败")
    private Integer billStatus;

    @ApiModelProperty(value = "状态 1.待提现 2.待审核 3.处理中 4.结算完成 5.结算失败")
    private String billStatusName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "提现时间")
    private LocalDateTime withdrawalTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
