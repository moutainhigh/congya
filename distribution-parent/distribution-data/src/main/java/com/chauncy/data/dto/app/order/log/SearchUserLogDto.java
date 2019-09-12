package com.chauncy.data.dto.app.order.log;

import com.chauncy.common.enums.log.AccountTypeEnum;
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
 * @since 2019/7/29 16:59
 */
@Data
@ApiModel(value = "SearchUserLogDto对象", description = "查找用户流水参数")
public class SearchUserLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账目类型 accountType   \n2：红包   \n" +
            "3：购物券   \n4：积分   \n")
    @NotNull(message = "账目类型不能为空")
    private Integer accountType;

    @JsonIgnore
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @JsonIgnore
    @ApiModelProperty(value = "查询时间")
    private String logDate;

    @ApiModelProperty(value = "查询年")
    private String year;

    @ApiModelProperty(value = "查询月")
    private String month;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
