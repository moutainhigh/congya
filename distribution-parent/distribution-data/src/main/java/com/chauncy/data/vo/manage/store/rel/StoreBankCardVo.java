package com.chauncy.data.vo.manage.store.rel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/23 11:50
 */
@Data
@ApiModel(value = "店铺银行卡信息")
public class StoreBankCardVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "银行卡id")
    private Long id;

    @ApiModelProperty(value = "开户行")
    private String openingBank;

    @ApiModelProperty(value = "银行卡号")
    private String account;

    @ApiModelProperty(value = "持卡人姓名")
    private String cardholder;

}
