package com.chauncy.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.advice.brand.select.FindBrandShufflingDto;
import com.chauncy.data.dto.app.advice.brand.select.SearchBrandAndSkuBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseListDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveClassificationAdviceDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAssociatedClassificationDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchInformationCategoryDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.AdviceTabVo;
import com.chauncy.data.vo.app.advice.goods.SearchBrandAndSkuBaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryDetailVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryInfoVo;
import com.chauncy.data.vo.manage.message.advice.ClassificationVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 广告基本信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface IMmAdviceService extends Service<MmAdvicePo> {

    /**
     * 获取广告位置
     * @return
     */
    Object findAdviceLocation();

    /**
     * 获取有店下的店铺分类
     * @return
     */
    List<StoreCategoryInfoVo> findStoreCategory();

    /**
     * 获取资讯动态下推荐的分类
     * @return
     */
    List<BaseVo> findInfoCategory();

    /**
     * 获取有店下的店铺分类选项卡内容
     * @param storeCategoryId
     * @return
     */
    List<AdviceTabVo> findStoreCategoryTab(Long storeCategoryId);

    /**
     * 根据选项卡id获取有店下的店铺分类详情
     * @param tabId
     * @return
     */
    PageInfo<StoreCategoryDetailVo> findStoreCategoryDetail(Long tabId,  BaseSearchPagingDto baseSearchPagingDto);

    /**
     * 条件分页获取广告信息及其对应的详情
     *
     * @param searchAdvicesDto
     * @return
     */
    PageInfo<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto);

    /**
     * 批量删除广告
     *
     * @param idList
     */
    void deleteAdvices(List<Long> idList);

    /**
     * 保存充值入口/拼团鸭广告
     *
     * @param saveOtherAdviceDto
     * @return
     */
    void saveOtherAdvice(SaveOtherAdviceDto saveOtherAdviceDto);

    /**
     * 添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐
     *
     * @param saveClassificationAdviceDto
     * @return
     */
    void saveGoodsCategoryAdvice(SaveClassificationAdviceDto saveClassificationAdviceDto);

    /**
     * 条件分页查询获取广告位置为葱鸭百货分类推荐/资讯分类推荐已经关联的分类信息
     *
     * @param searchAssociatedClassificationDto
     * @return
     */
    PageInfo<ClassificationVo> searchAssociatedClassification(SearchAssociatedClassificationDto searchAssociatedClassificationDto);

    /**
     * 分页查找需要广告位置资讯分类推荐需要关联的资讯分类
     *
     * @return
     * @param searchInformationCategoryDto
     */
    PageInfo<BaseVo> searchInformationCategory(SearchInformationCategoryDto searchInformationCategoryDto);

    /**
     * 批量启用或禁用,同一个广告位只能有一个是启用状态
     *
     * @param baseUpdateStatusDto
     * @return
     */
    void editEnabled(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 查找广告位为葱鸭百货的所有广告
     *
     * @return
     */
//    List<FindBaiHuoAdviceVo> findAdvice();

    /**
     * 获取APP首页葱鸭优选、葱鸭百货等广告位信息
     *
     * @return
     */
    List<GetAdviceInfoVo> getAdviceInfo();

    /**
     * 获取首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货内部轮播图
     *
     * @return
     */
    List<ShufflingVo> getShuffling(String location);

    /**
     * 根据ID获取特卖、有品、主题、优选等广告选项卡
     *
     * @param adviceId
     * @return
     */
    List<BaseVo> getTab(Long adviceId);

    /**
     * 根据选项卡分页获取特卖、主题、优选等选项卡关联的商品基本信息
     *
     * @param searchGoodsBaseDto
     * @return
     */
    PageInfo<SearchGoodsBaseVo> searchGoodsBase(SearchGoodsBaseDto searchGoodsBaseDto);

    /**
     * 根据选项卡分页获取关联的品牌和商品具体的sku基本信息
     *
     * 这里显示的商品是具体的sku信息
     *
     * @param searchBrandAndSkuBaseDto
     * @return
     */
    PageInfo<SearchBrandAndSkuBaseVo> searchBrandAndSkuBase(SearchBrandAndSkuBaseDto searchBrandAndSkuBaseDto);

    /**
     * 分页条件查询首页下面的商品列表/品牌id/选项卡id/商品分类id/葱鸭百货关联/优惠券关联下的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    PageInfo<SearchGoodsBaseListVo> searchGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * 获取选项卡下的品牌下的轮播图广告
     *
     * @param findBrandShufflingDto
     * @return
     */
    List<ShufflingVo> findBrandShuffling(FindBrandShufflingDto findBrandShufflingDto);
}
