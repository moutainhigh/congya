package com.chauncy.data.dto.supplier.good.select;

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
 * @since 2019/7/13 13:04
 */
@Data
@ApiModel(value = "SearchStoreGoodsStockDto对象", description = "查找店铺库存的条件")
public class SearchStoreGoodsStockDto  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "库存名称")
    private String stockName;

    @ApiModelProperty(value = "直属店铺名称")
    private String storeName;

    @ApiModelProperty(value = "分配店铺名称")
    private String branchName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate startTime;

    @JsonFormat( pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate endTime;

    @ApiModelProperty(value = "库存最小数量")
    private Integer minStockNum;

    @ApiModelProperty(value = "库存最大数量")
    private Integer maxStockNum;

    @ApiModelProperty(value = "是否启用 1-是 0-否 ")
    private Boolean enabled;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
