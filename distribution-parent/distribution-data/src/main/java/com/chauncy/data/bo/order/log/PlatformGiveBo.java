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
@ApiModel(value = "PlatformGiveBo", description  = "系统赠送积分/购物券/红包")
public class PlatformGiveBo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "赠送积分数")
    private BigDecimal marginIntegral;

    @ApiModelProperty(value = "赠送红包数")
    private BigDecimal marginRedEnvelops;

    @ApiModelProperty(value = "赠送购物券数")
    private BigDecimal marginShopTicket;

    @ApiModelProperty(value = "保存流水参数")
    private AddAccountLogBo addAccountLogBo;

    @ApiModelProperty(value = "系统赠送用户")
    private Long umUserId;

}
