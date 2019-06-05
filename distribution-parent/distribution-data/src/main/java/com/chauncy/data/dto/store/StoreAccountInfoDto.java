package com.chauncy.data.dto.store;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: xiaoye
 * @Date: 2019/6/5 17:47
 */
@Data
@ApiModel(value = "StoreAccountInfoDto对象", description = "店铺账户信息")
public class StoreAccountInfoDto {


    @ApiModelProperty(value = "id")
    @NotNull(message = "店铺ID不能为空")
    private Long id;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;


    @ApiModelProperty(value = "公司地址")
    @NotBlank(message = "公司地址不能为空")
    private String companyAddr;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String companyMobile;

    @ApiModelProperty(value = "邮箱")
    @Email
    private String companyEmail;

    @ApiModelProperty(value = "法人")
    @NotBlank(message = "法人不能为空")
    private String legalPerson;

    @ApiModelProperty(value = "税号")
    @NotBlank(message = "税号不能为空")
    private String taxNumber;

    @ApiModelProperty(value = "持卡人姓名")
    @NotBlank(message = "持卡人姓名不能为空")
    private String cardholder;

    @ApiModelProperty(value = "开户行")
    @NotBlank(message = "开户行不能为空")
    private String openingBank;

    @ApiModelProperty(value = "收款账户")
    @NotBlank(message = "收款账户不能为空")
    private String account;

    @ApiModelProperty(value = "银行预留电话")
    private String bankReserveMobile;

    @ApiModelProperty(value = "货款账单结算周期")
    @Min(value = 0, message = "货款账单结算周期必须大于0")
    private Integer paymentBillSettlementCycle;

    @ApiModelProperty(value = "收入账单结算周期")
    @Min(value = 0, message = "收入账单结算周期必须大于0")
    private Integer incomeBillSettlementCycle;

    @ApiModelProperty(value = "收入配置比例")
    @Min(value = 0, message = "收入配置比例必须大于0")
    private BigDecimal incomeRate;

    @ApiModelProperty(value = "经营资质，营业执照")
    private String businessLicense;
}
