package com.chauncy.data.vo.manage.order.log;

import com.chauncy.common.enums.log.PlatformLogMatterEnum;
import com.chauncy.common.enums.log.StoreLogMatterEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/8/21 12:37
 */
@Data
@ApiModel(value = "SearchStoreLogVo", description  = "交易流水信息")
public class SearchStoreLogVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水号")
    private Long id;

    @ApiModelProperty(value = "关联账单号")
    private Long omRelId;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @ApiModelProperty(value = "流水事由")
    private StoreLogMatterEnum logMatter;

    @ApiModelProperty(value = "流水金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
