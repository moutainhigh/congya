package com.chauncy.data.vo.manage.order.bill;

import com.chauncy.data.vo.manage.store.RelStoreInfoVo;
import com.chauncy.data.vo.manage.store.rel.StoreBankCardVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/23 11:44
 */
@Data
@ApiModel(value = "货款/利润账单详情信息")
public class BillDetailVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前登录用户是否为店铺用户 true 是 false 否")
    private Boolean isStoreUser;

    @ApiModelProperty(value = "店铺信息")
    private RelStoreInfoVo relStoreInfoVo;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "期数")
    private String monthDay;

    @ApiModelProperty(value = "账单id")
    private Long id;

    @ApiModelProperty(value = "提现时间")
    private LocalDateTime withdrawalTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "总货款/总利润")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实发金额")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "扣除金额/修改")
    private BigDecimal deductedAmount;

    @ApiModelProperty(value = "扣除金额备注")
    private String deductedRemark;

    @ApiModelProperty(value = "状态 1.待提现 2.待审核 3.处理中 4.结算完成 5.结算失败")
    private Integer billStatus;

    @ApiModelProperty(value = "状态 1.待提现 2.待审核 3.处理中 4.结算完成 5.结算失败")
    private String billStatusName;

    @ApiModelProperty(value = "店铺银行卡信息")
    private StoreBankCardVo storeBankCardVo;

    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;

    @ApiModelProperty(value = "结算进度")
    private List<BillSettlementVo> billSettlementVoList;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private LocalDateTime createTime;

    @ApiModelProperty(value = "处理时间")
    @JsonIgnore
    private LocalDateTime processingTime;

    @ApiModelProperty(value = "结算时间")
    @JsonIgnore
    private LocalDateTime settlementTime;

    /*@ApiModelProperty(value = "商品信息")
    private List<BillRelGoodsTempVo> billRelGoodsTempVoList;*/

}
