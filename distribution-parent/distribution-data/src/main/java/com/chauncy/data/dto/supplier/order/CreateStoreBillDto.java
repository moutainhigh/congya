package com.chauncy.data.dto.supplier.order;

import com.chauncy.common.enums.order.BillTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yeJH
 * @since 2019/8/7 19:07
 */
@Data
@ApiModel(value = "CreateStoreBillDto", description = "创建账单参数")
@Accessors(chain = true)
public class CreateStoreBillDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("需要创建账单的那一周,任何一天都可以")
    @NotNull(message = "endDate参数不能为空")
    private LocalDate endDate;

    @ApiModelProperty("账单类型  1 货款账单  2 利润账单")
    @EnumConstraint(target = BillTypeEnum.class)
    private Integer billType;

}
