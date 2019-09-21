package com.chauncy.data.bo.order.log;

import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.user.UmUserPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/9/13 15:46
 */
@Data
@ApiModel(value = "AccountLogBo",
        description  = "账目流水，针对有些操作例如系统赠送，退款没有没有记录对应的账目数据（直接操作用户数据）")
public class AccountLogBo implements Serializable {

    private static final long serialVersionUID = 1L;


    /*@ApiModelProperty(value = "保存流水参数")
    private AddAccountLogBo addAccountLogBo;*/

}
