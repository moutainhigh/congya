package com.chauncy.message.advice;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.advice.tab.tab.add.SaveRelTabDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchAdviceGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedGoodsDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 广告与广告选项卡表关联表，广告位置为除了具体店铺分类的所有有选项卡的广告 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface IMmAdviceRelTabService extends Service<MmAdviceRelTabPo> {

    /**
     * 条件分页查询需要被关联的品牌
     *
     * @param searchBrandsDto
     * @return
     */
    PageInfo<BaseVo> searchBrands(SearchBrandsDto searchBrandsDto);

    /**
     * 条件分页查询需要被关联的商品
     *
     * @param searchAdviceGoodsDto
     * @return
     */
    PageInfo<BaseVo> searchAdviceGoods(SearchAdviceGoodsDto searchAdviceGoodsDto);

    /**
     * 保存特卖、有品、主题、优选等广告信息
     *
     * @param saveRelTabDto
     * @return
     */
    void saveRelTab(SaveRelTabDto saveRelTabDto);

    /**
     * 条件分页查询选项卡已经关联的商品
     *
     * @param searchTabAssociatedGoodsDto
     * @return
     */
    PageInfo<GoodsVo> searchTabAssociatedGoods(SearchTabAssociatedGoodsDto searchTabAssociatedGoodsDto);

    /**
     * 条件分页查询选项卡已经关联的品牌
     *
     * @param searchTabAssociatedBrandsDto
     * @return
     */
    PageInfo<BrandVo> searchTabAssociatedBrands(SearchTabAssociatedBrandsDto searchTabAssociatedBrandsDto);


}
