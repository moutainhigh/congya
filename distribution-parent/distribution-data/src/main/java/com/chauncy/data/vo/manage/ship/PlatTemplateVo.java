package com.chauncy.data.vo.manage.ship;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-24 17:57
 *
 * 返回平台运费模版信息给前端
 */
@Data
@ApiModel(description = "返回平台运费模版信息给前端")
public class PlatTemplateVo {

    @ApiModelProperty("运费id")
    private Long templateId;

    @ApiModelProperty("运费名称")
    private String templateName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("发货地址")
    private  String dispatch;

    @ApiModelProperty(value = "商品地址")
    private Long productAddressId;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("运费计算方式 1->按金额。2->按件数")
    private Integer calculateWay;

    @ApiModelProperty(value = "默认(基础)运费")
    private BigDecimal defaultFreight;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    private BigDecimal defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费")
    private BigDecimal defaultPostMoney;

    @ApiModelProperty("指定地区按金额的运费计算列表")
    private List<AmountVo> amountCalculateList;

    @ApiModelProperty("指定地区按件数的运费计算列表")
    private List<NumberVo> numberCalculateList;

    //商家端需要的数据
    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("提交时间")
    private LocalDateTime submitTime;

    @ApiModelProperty("审核时间")
    private LocalDateTime verifyTime;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("审核者")
    private String auditor;



}
