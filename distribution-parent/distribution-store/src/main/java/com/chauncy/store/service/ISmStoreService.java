package com.chauncy.store.service;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.app.store.FindStoreCategoryDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchByConditionDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.dto.supplier.store.update.StoreBusinessLicenseDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.store.StoreDetailVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.store.*;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
public interface ISmStoreService extends Service<SmStorePo> {

    /**
     * 保存店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    JsonViewData saveStore(StoreBaseInfoDto storeBaseInfoDto);

    /**
     * 编辑店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    JsonViewData editStore(StoreBaseInfoDto storeBaseInfoDto);

    /**
     * 保存店铺账户信息
     * @param storeAccountInfoDto
     * @return
     */
    JsonViewData saveStore(StoreAccountInfoDto storeAccountInfoDto);
    /**
     * 编辑店铺账户信息
     * @param storeAccountInfoDto
     * @return
     */
    JsonViewData editStore(StoreAccountInfoDto storeAccountInfoDto);

    /**
     * 修改店铺经营状态
     * @param baseUpdateStatusDto
     * ids 店铺ID
     * enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    JsonViewData editStoreStatus(BaseUpdateStatusDto baseUpdateStatusDto);



    /**
     * 条件查询
     * @param storeSearchDto
     * @return
     */
    PageInfo<SmStoreBaseVo> searchBaseInfo(StoreSearchDto storeSearchDto);

    /**
     * 查询店铺基本信息
     * @param id
     * @return
     */
    StoreBaseInfoVo findBaseById(Long id);


    /**
     * 查询店铺账户信息
     * @param id
     * @return
     */
    StoreAccountInfoVo findAccountById(Long id);

    /**
     * 查询店铺运营信息
     * @param id
     * @return
     */
    StoreOperationalInfoVo findOperationalById(Long id);

    /**
     * 根据账号获取店铺id
     * @param userName
     * @return
     */
    Long findStoreIdByName(String userName);


    /**
     * 商家上传经营资质
     * @param storeBusinessLicenseDto
     * @return
     */
    void uploadBusinessLicense( StoreBusinessLicenseDto storeBusinessLicenseDto);


    /**
     * 条件查询可关联店铺
     * @param storeSearchByConditionDto
     * @return
     */
    PageInfo<RelStoreInfoVo> searchRelStoreInfo(StoreSearchByConditionDto storeSearchByConditionDto);

    /**
     * 用户关注店铺
     * @param storeId  店铺id
     * @param userId  用户id
     * @return
     */
    void userFocusStore(Long storeId, Long userId);

    /**
     * 店铺解除绑定
     * @return
     */
    void storeUnbound(Long id);

    /**
     * 获取当前店铺的下级店铺(分店)（模糊搜索）
     *
     * @param storeName
     * @return
     */
    List<BranchInfoVo> searchBranchByName(String storeName);

    /**
     * app查询店铺列表
     * @return
     */
    PageInfo<StorePagingVo> searchPaging(SearchStoreDto searchStoreDto);

    /**
     * app获取店铺信息
     * @param storeId
     * @return
     */
    StorePagingVo findById(Long storeId);

    /**
     * 获取店铺下商品分类信息
     * @return
     */
    List<SearchCategoryVo> findGoodsCategory(FindStoreCategoryDto findStoreCategoryDto);

    /**
     * app获取店铺详情
     * @return
     */
    StoreDetailVo findDetailById(Long storeId);
}
