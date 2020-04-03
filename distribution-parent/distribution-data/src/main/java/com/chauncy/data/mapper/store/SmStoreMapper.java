package com.chauncy.data.mapper.store;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.app.store.FindStoreParamDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchByConditionDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryDetailVo;
import com.chauncy.data.vo.app.advice.store.StoreHomePageVo;
import com.chauncy.data.vo.app.component.ScreenStoreParamVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.store.StoreDetailVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.manage.store.*;
import com.chauncy.data.vo.manage.store.rel.SmRelStoreVo;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
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
     * @Author yeJH
     * @Date 2019/10/15 20:22
     * @Description 团队合作
     *              1.获取被绑定的店铺的关系链的层数  不能超过5层
     *              2.当前店铺是否绑定过其他店铺
     *              3.被绑定的店铺是否被其他店铺绑定过
     *
     * @Update yeJH
     *
     * @param  storeId   当前店铺
     * @param  parentId  被绑定的店铺
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getTeamWorkCondition(@Param("storeId") Long storeId, @Param("parentId") Long parentId);

    /**
     * @Author yeJH
     * @Date 2019/10/16 13:48
     * @Description 被绑定的店铺不允许存在在当前店铺的子店铺中（避免形成闭环）
     *              true 表示被绑定的店铺在当前店铺的子店铺中 循环绑定  形成闭环
     *              false 表示被绑定的店铺不在当前店铺的子店铺中  满足绑定条件
     *
     * @Update yeJH
     *
     * @param  storeId
     * @param  parentId
     * @return java.lang.Boolean
     **/
    Boolean getProductAgentCondition(@Param("storeId") Long storeId, @Param("parentId") Long parentId);

    /**
     * APP 查询店铺  启用中
     * @param id
     * @return
     */
    SmStorePo getEnabledStoreById(@Param("id") Long id);

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
     * @Author yeJH
     * @Date 2019/9/20 18:01
     * @Description 搜索界面-搜索店铺列表
     *
     * @Update yeJH
     *
     * @Param [searchStoreDto]
     * @return void
     **/
    List<StoreCategoryDetailVo> searchStoreBaseList(SearchStoreDto searchStoreDto);

    /**
     * @Author yeJH
     * @Date 2019/10/17 14:41
     * @Description 获取筛选店铺的参数  店铺分类  店铺标签
     *
     * @Update yeJH
     *
     * @param  findStoreParamDto
     * @return com.chauncy.data.vo.app.component.ScreenStoreParamVo
     **/
    ScreenStoreParamVo findScreenStoreParam(FindStoreParamDto findStoreParamDto);

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
    StoreDetailVo findDetailById(@Param("storeId") Long storeId, @Param("userId") Long userId);

    /**
     * 分页查询广告为首页有店+店铺分类详情：除选项卡关联的店铺外的店铺
     *
     * @param name
     * @param associatedIds
     * @return
     */
    List<BaseVo> searchStores(@Param("classificationId") Long classificationId , @Param("name") String name, @Param("associatedIds") List<Long> associatedIds);

    /**
     * 获取店铺首页-店铺详情信息
     * @param storeId
     * @param userId
     * @return
     */
    StoreHomePageVo getStoreHomePage(@Param("storeId") Long storeId, @Param("userId") Long userId);
    /**
     * 不用updateById  update a=a+1
     *
     * @param favoritesId
     */
    @Update("update sm_store set collection_num = collection_num+1 where id = #{favoritesId}")
    void addFavorites(Long favoritesId);

    /**
     * 不用updateById  update a=a-1
     *
     * @param favoritesId
     */
    @Update("update sm_store set collection_num = collection_num-1 where id = #{favoritesId} and collection_num > 0")
    void delFavorites(Long favoritesId);

    /**
     * @Author yeJH
     * @Date 2020/4/4 0:19
     * @Description 新增商品所属店铺商品总数+1
     *
     * @Update yeJH
     *
     * @param  smStoreId
     * @return void
     **/
    void addGoodsNum(@Param("smStoreId") Long smStoreId);
    
    /**
     * @Author yeJH
     * @Date 2020/4/4 0:18
     * @Description 删除商品所属店铺商品总数-1
     *
     * @Update yeJH
     *
     * @param  goodsId
     * @return void
     **/
    void reduceGoodsNum(@Param("goodsId") Long goodsId);

    /**
     * @Author yeJH
     * @Date 2020/4/4 0:43
     * @Description 下单添加店铺营业额
     *
     * @Update yeJH
     *
     * @param  goodsId
     * @param  realPayMoney
     * @return void
     **/
    void addStoreTurnover(@Param("goodsId") Long goodsId, @Param("realPayMoney") BigDecimal realPayMoney);
}
