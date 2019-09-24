package com.chauncy.data.dto.manage.order.report.select;

import com.chauncy.common.enums.order.ReportTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/8/11 18:04
 */
@Data
@ApiModel(value = "SearchReportDto", description = "查询商品报表参数")
public class SearchReportDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品销售报表类型")
    @NotNull(message = "reportType参数不能为空")
    private Integer reportType;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "直属商家名称")
    private String storeName;

    @ApiModelProperty(value = "分配商家名称")
    private String branchName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate startTime;

    @JsonFormat( pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate endTime;

    @JsonIgnore
    @ApiModelProperty(value = "筛选店铺报表")
    private Long storeId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
