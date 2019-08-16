package com.chauncy.message.advice;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabAssociationPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveStoreClassificationDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchClassificationStoreDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchStoreClassificationDto;
import com.chauncy.data.vo.BaseVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface IMmAdviceRelTabAssociationService extends Service<MmAdviceRelTabAssociationPo> {

    /**
     * 首页有店+店铺分类详情
     *
     * 保存广告位置为首页有店+店铺分类详情的信息
     *
     * @param saveStoreClassificationDto
     * @return
     */
    void saveStoreClassification(SaveStoreClassificationDto saveStoreClassificationDto);

    /**
     * 分页查询店铺分类
     *
     * @param searchStoreClassificationDto
     * @return
     */
    PageInfo<BaseVo> searchStoreClassification(SearchStoreClassificationDto searchStoreClassificationDto);

    /**
     * 分页查询店铺分类下店铺信息
     *
     * @param searchClassificationStoreDto
     * @return
     */
    PageInfo<BaseVo> searchClassificationStore(SearchClassificationStoreDto searchClassificationStoreDto);
}
