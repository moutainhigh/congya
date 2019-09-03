package com.chauncy.order.service;

import com.chauncy.data.domain.po.order.OmEvaluateQuartzPo;
import com.chauncy.data.core.Service;

/**
 * <p>
 * 定时任务刷新店铺相关评分信息 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-03
 */
public interface IOmEvaluateQuartzService extends Service<OmEvaluateQuartzPo> {

    /**
     *
     * 定时任务更新店铺相关评分
     *
     */
    void updateEvaluate();
}
