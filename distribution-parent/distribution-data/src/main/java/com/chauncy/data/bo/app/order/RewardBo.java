package com.chauncy.data.bo.app.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/9/8 19:53
 **/
@Data
public class RewardBo {

    private BigDecimal rewardShopTicket;
    private BigDecimal rewardExperience;
    private BigDecimal rewardIntegrate;
}
