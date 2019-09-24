package com.chauncy.message.advice;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabAssociationPo;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveActivityGroupAdviceDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveStoreClassificationDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchActivityGroupDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchClassificationStoreDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchStoreClassificationDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchStoresDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.StoreVo;
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

    /**
     * 条件分页查询已经关联的店铺
     *
     * @param searchStoresDto
     * @return
     */
    PageInfo<StoreVo> searchStores(SearchStoresDto searchStoresDto);

    /**
     * @Author chauncy
     * @Date 2019-09-20 09:29
     * @Description //条件分页查询活动分组信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGroupDto]
     * @return java.lang.Object
     **/
    PageInfo<BaseVo> searchActivityGroup(SearchActivityGroupDto searchActivityGroupDto);

    /**
     * @Author chauncy
     * @Date 2019-09-22 22:13
     * @Description //保存积分、满减活动广告
     *
     * @Update chauncy
     *
     * @Param [saveActivityGroupAdviceDto]
     * @return void
     **/
    void saveActivityGroupAdvice(SaveActivityGroupAdviceDto saveActivityGroupAdviceDto);
}
