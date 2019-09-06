package com.chauncy.data.bo.manage.order.log;

import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/25 11:58
 */
@Data
@ApiModel(value = "AddAccountLogBo", description = "保存流水")
public class AddAccountLogBo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "流水触发的事件类型")
    @EnumConstraint(target = LogTriggerEventEnum.class)
    private LogTriggerEventEnum logTriggerEventEnum;

    @ApiModelProperty(value = "流水对应的id，订单id，提现单id等")
    private Long relId;

    @ApiModelProperty(value = "当前操作用户后台userName，spp用户phone")
    private String operator;

}
