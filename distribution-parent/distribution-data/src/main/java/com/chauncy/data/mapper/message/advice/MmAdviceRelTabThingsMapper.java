package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabThingsPo;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchStoresDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SellHotRelGoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsVo;

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

    /**
     * 条件查询已经关联的店铺
     *
     * @param searchStoresDto
     * @return
     */
    List<StoreVo> searchStores(SearchStoresDto searchStoresDto);

    /**
     * 获取品牌选项卡关联的品牌
     *
     * @param tabId
     */
    List<BrandVo> findBrandList(Long tabId);

    /**
     * 获取品牌选项卡关联的品牌
     *
     * @param tabId
     */
    List<GoodsVo> findGoodsList(Long tabId);

    /**
     * @Author chauncy
     * @Date 2019-09-23 13:52
     * @Description //查找热销选项卡关联的商品列表
     *
     * @Update chauncy
     *
     * @Param [sellHotTabId]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SellHotRelGoodsVo>
     **/
    List<SellHotRelGoodsVo> findSellHotGoodsList(Long sellHotTabId);
}
