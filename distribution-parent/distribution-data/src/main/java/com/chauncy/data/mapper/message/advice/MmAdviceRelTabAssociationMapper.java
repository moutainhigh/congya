package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabAssociationPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.tab.association.TabInfosVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.ActivitySellHotTabInfosVo;

import java.util.List;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceRelTabAssociationMapper extends IBaseMapper<MmAdviceRelTabAssociationPo> {

    //获取该广告的该店铺下的选项卡
    List<TabInfosVo> findTabInfos(Long adviceAssociationId);

    /**
     * @Author chauncy
     * @Date 2019-09-23 13:39
     * @Description //获取热销广告选项卡信息
     *
     * @Update chauncy
     *
     * @Param [relAdviceActivityGroupId]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.ActivitySellHotTabInfosVo>
     **/
    List<ActivitySellHotTabInfosVo> findActivitySellHotTabInfos(Long relAdviceActivityGroupId);
}
