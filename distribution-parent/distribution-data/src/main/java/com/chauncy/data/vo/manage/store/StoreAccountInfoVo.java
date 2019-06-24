package com.chauncy.data.vo.manage.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 店铺账户信息
 * @author yeJH
 * @since 2019/6/19 22:44
 */
@Data
@ApiModel(value = "店铺账户信息")
public class StoreAccountInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;


    @ApiModelProperty(value = "公司地址")
    private String companyAddr;

    @ApiModelProperty(value = "联系电话")
    private String companyMobile;

    @ApiModelProperty(value = "邮箱")
    private String companyEmail;

    @ApiModelProperty(value = "法人")
    private String legalPerson;

    @ApiModelProperty(value = "税号")
    private String taxNumber;

    @ApiModelProperty(value = "持卡人姓名")
    private String cardholder;

    @ApiModelProperty(value = "开户行")
    private String openingBank;

    @ApiModelProperty(value = "收款账户")
    private String account;

    @ApiModelProperty(value = "银行预留电话")
    private String bankReserveMobile;

    @ApiModelProperty(value = "货款账单结算周期")
    private Integer paymentBillSettlementCycle;

    @ApiModelProperty(value = "收入账单结算周期")
    private Integer incomeBillSettlementCycle;

    @ApiModelProperty(value = "收入配置比例")
    private BigDecimal incomeRate;

    @ApiModelProperty(value = "经营资质，营业执照")
    private String[] businessLicenses;

    @ApiModelProperty(value = "经营资质，营业执照")
    @JSONField(serialize=false)
    private String businessLicense;
}
