package com.chauncy.data.mapper.store;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchByConditionDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.store.StoreDetailVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.manage.store.*;
import com.chauncy.data.vo.manage.store.rel.SmRelStoreVo;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
public interface SmStoreMapper extends IBaseMapper<SmStorePo> {

    /**
     * 根据账号获取店铺id
     * @param userName
     * @return
     */
    Long findStoreIdByName(@Param("userName") String userName);

    /**
     * 根据账店铺id获取绑定店铺的列表
     * @param id
     * @return
     */
    List<SmRelStoreVo> findBindingStore(@Param("id") Long id);

    /**
     * 修改店铺经营状态
     * @param baseUpdateStatusDto
     * ids 店铺ID
     * enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    int editStoreStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 条件查询
     *
     * @param storeSearchDto
     * @return
     */
    List<SmStoreBaseVo> searchBaseInfo(StoreSearchDto storeSearchDto);

    /**
     * 查询店铺基本信息
     * @param id
     * @return
     */
    StoreBaseInfoVo findBaseById(@Param("id") Long id);

    /**
     * 查询店铺账户信息
     * @param id
     * @return
     */
    StoreAccountInfoVo findAccountById(@Param("id") Long id);

    /**
     * 查询店铺运营信息
     * @param id
     * @return
     */
    StoreOperationalInfoVo findOperationalById(@Param("id") Long id);

    /**
     * 根据店铺id获取店铺跟哪些品牌关联（品牌下有对应的商品）
     * @param id
     * @return
     */
    List<Long> selectAttributeIdsById(@Param("id") Long id);

    /**
     * 获取店铺关联的品牌id
     * @param id
     * @return
     */
    List<Long> selectRelAttributeIds(@Param("id") Long id);

    /**
     * 根据店铺id获取店铺跟哪些标签关联
     * @param id
     * @return
     */
    List<Long> selectLabelIdsById(@Param("id") Long id);

    /**
     * 条件查询可关联店铺
     *
     * @param storeSearchByConditionDto
     * @return
     */
    List<RelStoreInfoVo> searchRelStoreInfo(StoreSearchByConditionDto storeSearchByConditionDto);

    /**
     * 获取当前店铺的下级店铺(分店)（模糊搜索）
     *
     * @param storeId
     * @param storeName
     * @return
     */
    List<BranchInfoVo> searchBranchByName(@Param("storeId") Long storeId, @Param("storeName")String storeName);

    /**
     * 通过店铺搜索，店铺分类查询店铺列表
     * @param searchStoreDto
     * @return
     */
    List<StorePagingVo> searchPaging(SearchStoreDto searchStoreDto);

    /**
     * app获取店铺信息
     * @param storeId
     * @return
     */
    StorePagingVo findStoreById(Long storeId);

    /**
     * 获取店铺详情
     * @param storeId
     * @return
     */
    StoreDetailVo findDetailById(Long storeId);

    /**
     * 分页查询广告为首页有店+店铺分类详情：除选项卡关联的店铺外的店铺
     *
     * @param name
     * @param associatedIds
     * @return
     */
    List<BaseVo> searchStores(@Param("classificationId") Long classificationId , @Param("name") String name, @Param("associatedIds") List<Long> associatedIds);
}
