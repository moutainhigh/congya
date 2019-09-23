package com.chauncy.data.dto.manage.order.log.select;

import com.chauncy.common.enums.log.PlatformLogMatterEnum;
import com.chauncy.common.enums.log.StoreLogMatterEnum;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/8/21 12:46
 */
@Data
@ApiModel(value = "SearchStoreLogDto", description = "查找店铺交易流水参数")
public class SearchStoreLogDto extends BaseSearchPagingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @JsonIgnore
    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "关联账单id")
    private Long omRelId;

    @ApiModelProperty(value = "流水id")
    private Long logId;

    @ApiModelProperty(value = "流水事由")
    private Integer logMatter;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate startTime;

    @JsonFormat( pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate endTime;

    @ApiModelProperty(value = "最小发生额")
    private BigDecimal minAmount;

    @ApiModelProperty(value = "最大发生额")
    private BigDecimal maxAmount;

}
