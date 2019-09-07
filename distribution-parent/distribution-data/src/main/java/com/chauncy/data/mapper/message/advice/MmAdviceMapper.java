package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.dto.app.advice.brand.select.SearchBrandAndSkuBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseListDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchBrandAndSkuBaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.manage.message.advice.FindBaiHuoAdviceVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 广告基本信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceMapper extends IBaseMapper<MmAdvicePo> {

    /**
     * 条件分页查询广告基本信息
     *
     * @param searchAdvicesDto
     * @return
     */
    List<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto);

    /**
     * 查找广告位为葱鸭百货的所有广告
     *
     * @return
     */
    List<FindBaiHuoAdviceVo> findAdvice(String location);

    /**
     * 获取APP首页葱鸭优选、葱鸭百货等广告位信息
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
    List<SearchGoodsBaseVo> searchGoodsBase(SearchGoodsBaseDto searchGoodsBaseDto);

    /**
     * 获取商品的标签
     *
     * @param goodsId
     * @return
     */
    List<String> getLabelNames(Long goodsId);

    /**
     * 获取有品选项卡下的品牌
     *
     * @param searchBrandAndSkuBaseDto
     * @return
     */
    List<SearchBrandAndSkuBaseVo> searchBrandBase(SearchBrandAndSkuBaseDto searchBrandAndSkuBaseDto);

    /**
     * 分页条件查询品牌下的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchBrandGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * 分页条件查询品牌下的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchTabGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * 分页条件查询葱鸭优选推荐的分类的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchCategoryGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * 分页条件查询葱鸭百货关联三级分类的所有商品
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchAssociatedGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);
}
