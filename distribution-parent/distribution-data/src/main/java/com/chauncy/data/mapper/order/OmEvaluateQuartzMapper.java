package com.chauncy.data.mapper.order;

import com.chauncy.data.bo.app.order.evaluate.StoreEvaluateBo;
import com.chauncy.data.domain.po.order.OmEvaluateQuartzPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 定时任务刷新店铺相关评分信息 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-03
 */
public interface OmEvaluateQuartzMapper extends IBaseMapper<OmEvaluateQuartzPo> {

    /**
     * 获取每个店铺对应的所有的评分
     *
     * @return
     */
    List<OmEvaluateQuartzPo> calculateEvaluate();

    /**
     * 清空om_valuate_quartz表
     */
    @Select("truncate table om_evaluate_quartz")
    void truncate();
}
