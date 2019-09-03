package com.chauncy.data.dto.manage.order.bill.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/23 20:58
 */
@Data
@ApiModel(description = "批量审核", value = "BatchAuditDto")
public class BatchAuditDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ids")
    @NotEmpty(message = "ids参数不能为空")
    private Long[] ids;

    @ApiModelProperty("true  审核通过 false 审核驳回")
    @NotNull(message = "enabled参数不能为空")
    private Boolean enabled;

    @ApiModelProperty("驳回原因")
    private String rejectReason;
}
