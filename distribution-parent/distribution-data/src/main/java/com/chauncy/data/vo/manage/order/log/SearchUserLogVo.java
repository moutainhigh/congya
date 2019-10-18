package com.chauncy.data.vo.manage.order.log;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/29 15:11
 */
@Data
@ApiModel(value = "SearchUserLogVo对象", description  = "用户账目流水信息")
public class SearchUserLogVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账目余额")
    @JSONField(ordinal = 1)
    private BigDecimal amount;

    @ApiModelProperty(value = "查询年")
    @JSONField(ordinal = 2)
    private String year;

    @ApiModelProperty(value = "查询月")
    @JSONField(ordinal = 3)
    private String month;

    @ApiModelProperty(value = "收入")
    @JSONField(ordinal = 4)
    private BigDecimal income;

    @ApiModelProperty(value = "支出")
    @JSONField(ordinal = 5)
    private BigDecimal consume;

    @ApiModelProperty(value = "是否展示提现按钮")
    @JSONField(ordinal = 6)
    private Boolean isShow;

    @ApiModelProperty(value = "提现状态   \n 0 可以提现    \n 1 不可以提现，未完成实名认证    \n 2 不可以提现，有提现未完成")
    @JSONField(ordinal = 7)
    private Integer withdrawalCode;

    @ApiModelProperty(value = "对应提现信息")
    @JSONField(ordinal = 8)
    private String withdrawalMsg;

    @ApiModelProperty(value = "流水详情列表")
    @JSONField(ordinal = 9)
    private PageInfo<UserLogDetailVo> userLogDetailVoPageInfo;

}
