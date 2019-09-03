package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabPo;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedGoodsDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandTabInfosVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsTabInfosVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsVo;

import java.util.List;

/**
 * <p>
 * 广告与广告选项卡表关联表，广告位置为除了具体店铺分类的所有有选项卡的广告 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceRelTabMapper extends IBaseMapper<MmAdviceRelTabPo> {

    /**
     * 获取该广告下的所有选项卡信息
     *
     * @param adviceId
     * @return
     */
    List<BrandTabInfosVo> findBrandTabInfosVos(Long adviceId);

    /**
     *  获取该广告下的所有选项卡信息
     *
     * @param adviceId
     * @return
     */
    List<GoodsTabInfosVo> findGoodsTabInfosVos(Long adviceId);

    /**
     * 条件分页查询选项卡已经关联的品牌
     *
     * @param searchTabAssociatedBrandsDto
     * @return
     */
    List<BrandVo> searchTabAssociatedBrands(SearchTabAssociatedBrandsDto searchTabAssociatedBrandsDto);

    /**
     * 条件分页查询选项卡已经关联的商品
     *
     * @param searchTabAssociatedGoodsDto
     * @return
     */
    List<GoodsVo> searchTabAssociatedGoods(SearchTabAssociatedGoodsDto searchTabAssociatedGoodsDto);
}
