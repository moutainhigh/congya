package com.chauncy.order.service.impl;

import com.chauncy.data.bo.app.order.evaluate.StoreEvaluateBo;
import com.chauncy.data.domain.po.order.OmEvaluateQuartzPo;
import com.chauncy.data.mapper.order.OmEvaluateQuartzMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.service.IOmEvaluateQuartzService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 定时任务刷新店铺相关评分信息 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmEvaluateQuartzServiceImpl extends AbstractService<OmEvaluateQuartzMapper, OmEvaluateQuartzPo> implements IOmEvaluateQuartzService {

    @Autowired
    private OmEvaluateQuartzMapper mapper;

    /**
     *
     * 定时任务更新店铺相关评分
     *
     */
    @Override
    public void updateEvaluate() {

        //获取每个店铺对应的所有的评分
        List<OmEvaluateQuartzPo> evaluateQuartzPoList = mapper.calculateEvaluate();

        //清空om_valuate_quartz表
        mapper.truncate();

        this.saveOrUpdateBatch(evaluateQuartzPoList);
    }
}
