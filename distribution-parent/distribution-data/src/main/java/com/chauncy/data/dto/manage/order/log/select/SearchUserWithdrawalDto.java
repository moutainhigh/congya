package com.chauncy.data.dto.manage.order.log.select;

import com.chauncy.common.enums.log.WithdrawalStatusEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/7/30 12:38
 */
@Data
@ApiModel(value = "SearchUserWithdrawalDto对象", description = "查找用户流水参数")
public class SearchUserWithdrawalDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String name;


    @ApiModelProperty(value = "手机号码")
    private String phone;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "申请时间")
    private LocalDate startTime;

    @JsonFormat( pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "申请时间")
    private LocalDate endTime;

    @ApiModelProperty(value = "状态 1.待审核 2.处理中 3.提现成功 4.驳回")
    @EnumConstraint(target = WithdrawalStatusEnum.class)
    private Integer withdrawalStatus;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
