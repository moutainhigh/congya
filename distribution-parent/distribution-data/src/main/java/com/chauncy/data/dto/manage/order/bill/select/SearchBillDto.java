package com.chauncy.data.dto.manage.order.bill.select;

import com.chauncy.common.enums.order.BillStatusEnum;
import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/7/22 20:25
 */
@Data
@ApiModel(value = "SearchBillDto对象", description = "查询账单参数")
public class SearchBillDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单类型  1.货款账单  2.利润账单")
    @NotNull(message = "账单类型（billType）不能为空")
    @EnumConstraint(target = BillTypeEnum.class)
    private Integer billType;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "期数")
    private String monthDay;

    @ApiModelProperty(value = "状态 1.待提现 2.待审核 3.处理中 4.结算完成 5.审核失败")
    @EnumConstraint(target = BillStatusEnum.class)
    private Integer billStatus;

    @ApiModelProperty(value = "提现时间")
    private LocalDate startTime;

    @ApiModelProperty(value = "提现时间")
    private LocalDate endTime;

    @ApiModelProperty(value = "总货款/总利润最小值")
    private Integer minTotalAmount;

    @ApiModelProperty(value = "总货款/总利润最大值")
    private Integer maxTotalAmount;

    @JsonIgnore
    @ApiModelProperty(value = "筛选店铺账单")
    private Long storeId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
