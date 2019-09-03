package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelAssociaitonPo;
import com.chauncy.data.dto.manage.message.advice.select.SearchAssociatedClassificationDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.ClassificationVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreTabsVo;

import java.util.List;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表,其中当广告位置为店铺分类详情关联的店铺分类是点击不同店铺分类就有不同的选项卡+推荐的店铺(多关联选项卡)；当广告位置为葱鸭百货时关联的就是该表的商品三级分类；当广告位为有品详情或葱鸭百货二级分类时关联的是点击不同的品牌或一级分类时就有不同的轮播图(有关联的轮播图)。 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceRelAssociaitonMapper extends IBaseMapper<MmAdviceRelAssociaitonPo> {

    //获取该广告分类下的店铺分类信息以及该广告与店铺分类关联的ID
    List<StoreTabsVo> findStoreClassificationList(Long adviceId);

    /**
     * 根据广告ID分页获取广告位置为葱鸭百货分类推荐/资讯分类推荐关联的分类信息
     *
     * @param adviceId
     * @return
     */
    List<ClassificationVo> findClassification(Long adviceId);

    /**
     * 根据广告ID条件分页获取广告位置为葱鸭百货分类推荐/资讯分类推荐关联的分类信息
     *
     * @param searchAssociatedClassificationDto
     * @return
     */
    List<ClassificationVo> searchAssociatedClassification(SearchAssociatedClassificationDto searchAssociatedClassificationDto);
}
