package com.chauncy.data.dto.manage.store.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/23 18:33
 */
@Data
@ApiModel(value = "SaveStoreBankCardDto对象", description = "保存银行卡信息")
public class SaveStoreBankCardDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "银行卡id")
    private Long id;

    @ApiModelProperty(value = "开户行")
    @NotNull(message = "开户行不能为空")
    private String openingBank;

    @ApiModelProperty(value = "银行卡号")
    @NotNull(message = "银行卡号不能为空")
    private String account;

    @ApiModelProperty(value = "持卡人姓名")
    @NotNull(message = "持卡人姓名不能为空")
    private String cardholder;

}
