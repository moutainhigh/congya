package com.chauncy.data.bo.supplier.activity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-27 16:20
 *
 * 商家参与的活动 包括活动ID 和 活动类型
 */
@Data
@Accessors(chain = true)
public class StoreActivityBo {

    private Long activityId;

    private Integer activityType;

    private Long goodsId;
}
