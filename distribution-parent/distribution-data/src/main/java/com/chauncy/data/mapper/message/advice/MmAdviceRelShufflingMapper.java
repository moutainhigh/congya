package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelShufflingPo;
import com.chauncy.data.dto.manage.message.advice.shuffling.select.SearchShufflingAssociatedDetailDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.manage.message.advice.shuffling.FindShufflingVo;
import com.chauncy.data.vo.manage.message.advice.shuffling.SearchShufflingAssociatedDetailVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.ActivityGroupShufflingVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandShufflingVo;

import java.util.List;

/**
 * <p>
 * 广告与无关联轮播图关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceRelShufflingMapper extends IBaseMapper<MmAdviceRelShufflingPo> {

    /**
     *
     * 根据某个广告下的某个选项卡与品牌关联的表的ID查找关联的轮播图广告
     * @param relTabBrandId
     * @return
     */
    List<BrandShufflingVo> findShufflingList(Long relTabBrandId);

    /**
     * 条件分页查询轮播图广告需要绑定的资讯
     *
     * @param searchShufflingAssociatedDetailDto
     * @return
     */
    List<SearchShufflingAssociatedDetailVo> searchInformationDetail(SearchShufflingAssociatedDetailDto searchShufflingAssociatedDetailDto);

    /**
     * 条件分页查询轮播图广告需要绑定的店铺
     *
     * @param searchShufflingAssociatedDetailDto
     * @return
     */
    List<SearchShufflingAssociatedDetailVo> searchStoreDetail(SearchShufflingAssociatedDetailDto searchShufflingAssociatedDetailDto);

    /**
     * 条件分页查询轮播图广告需要绑定的商品
     *
     * @param searchShufflingAssociatedDetailDto
     * @return
     */
    List<SearchShufflingAssociatedDetailVo> searchGoodsDetail(SearchShufflingAssociatedDetailDto searchShufflingAssociatedDetailDto);

    /**
     * 根据广告ID获取无关联广告轮播图信息
     *
     * @param adviceId
     * @return
     */
    List<FindShufflingVo> findShuffling(Long adviceId);

    /***
     * 获取选项卡下的品牌下的轮播图广告
     * @param relTabBrandId
     * @return
     */
    List<ShufflingVo> findBrandShuffling(Long relTabBrandId);

    /**
     * @Author chauncy
     * @Date 2019-09-23 15:15
     * @Description //活动分组对应的轮播图
     *
     * @Update chauncy
     *
     * @Param [relAdviceActivityGroupId]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.ActivityGroupShufflingVo>
     **/
    List<ActivityGroupShufflingVo> findActivityGroupShuffling(Long relAdviceActivityGroupId);
}
