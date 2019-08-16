package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabThingsPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;

import java.util.List;

/**
 * <p>
 * 广告选项卡与商品/品牌关联表，广告位置为具体店铺分类、特卖、有品、主题和优选，非多重关联选项卡 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceRelTabThingsMapper extends IBaseMapper<MmAdviceRelTabThingsPo> {

    //获取该广告的该店铺下的选项卡下的关联店铺
    List<StoreVo> findStoreList(Long tabId);
}
