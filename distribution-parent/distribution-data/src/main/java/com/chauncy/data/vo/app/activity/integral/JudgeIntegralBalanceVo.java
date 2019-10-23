package com.chauncy.data.vo.app.activity.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019/10/12 11:15
 **/

@Data
@ApiModel(description = "判断积分余额")
public class JudgeIntegralBalanceVo {

    @ApiModelProperty(value = "积分余额是否足够：true-足够；false-不够")
    Boolean isEnough;

}

