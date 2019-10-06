package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.dto.app.advice.brand.select.SearchBrandAndSkuBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseListDto;
import com.chauncy.data.dto.app.product.SearchActivityGoodsListDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.AdviceTabVo;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupTabVo;
import com.chauncy.data.vo.app.advice.activity.HomePageActivityGoodsVo;
import com.chauncy.data.vo.app.advice.goods.SearchBrandAndSkuBaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryDetailVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryInfoVo;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import com.chauncy.data.vo.app.user.PersonalCenterPictureVo;
import com.chauncy.data.vo.manage.message.advice.FindBaiHuoAdviceVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     * @Author yeJH
     * @Date 2019/9/24 16:38
     * @Description 点击积分专区，满减专区获取活动分组信息
     *
     * @Update yeJH
     *
     * @param  location  广告位置  REDUCED_ACTIVITY 满减  INTEGRALS_ACTIVITY  积分
     * @param  groupType 活动分组类型 1：满减  2：积分
     * @return java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo>
     **/
    List<ActivityGroupListVo> findActivityGroup(@Param("location") String location, @Param("groupType")Integer groupType);

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据活动分组获取积分选项卡信息
     *
     * @Update yeJH
     *
     * @param  relId  广告与活动分组关联表id
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupTabVo>>
     **/
    List<ActivityGroupTabVo> findIntegralsGroupTab(@Param("relId") Long relId);

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据活动分组获取满减选项卡信息
     *
     * @Update yeJH
     *
     * @param  relId  广告与活动分组关联表id
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupTabVo>>
     **/
    List<ActivityGroupTabVo> findReducedGroupTab(@Param("relId") Long relId);

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据积分活动分组获取选项卡信息 积分：精选商品
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto
     *   tabId  活动分组选项卡id
     *   isPaging  是否分页  活动分组选项卡详情中商品只展示3个
     * @return java.util.List<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    List<ActivityGoodsVo> findIntegralsGroupTabDetail(SearchActivityGoodsListDto searchActivityGoodsListDto);

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据满减活动分组选项卡信息获取满减：热销精选
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto
     *   tabId  活动分组选项卡id
     *   isPaging  是否分页  活动分组选项卡详情中商品只展示3个
     * @return java.util.List<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    List<ActivityGoodsVo> findReducedGroupTabDetail(SearchActivityGoodsListDto searchActivityGoodsListDto);

     /**
     * @Author yeJH
     * @Date 2019/9/25 13:18
     * @Description  分组活动关联的轮播图
     *
     * @Update yeJH
     *
     * @param  relId
     * @return java.util.List<com.chauncy.data.vo.app.advice.home.ShufflingVo>
     **/
    List<ShufflingVo> getShufflingByRelId(Long relId);

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据满减活动分组id获取满减商品列表
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto
     * @return java.util.List<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    List<ActivityGoodsVo> findReducedGroupGoods(SearchActivityGoodsListDto searchActivityGoodsListDto);

    /**
     * @Author yeJH
     * @Date 2019/9/25 22:46
     * @Description 根据积分活动分组id获取积分商品列表
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto
     * @return void
     **/
    List<ActivityGoodsVo> findIntegralsGroupGoods(SearchActivityGoodsListDto searchActivityGoodsListDto);

    /**
     * @Author yeJH
     * @Date 2019/9/26 11:24
     * @Description 满减活动根据活动分组获取商品列表下对应的商品一级分类
     *
     * @Update yeJH
     *
     * @param  groupId  活动分组id
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    List<BaseVo> findReducedGoodsCategory(Long groupId);

    /**
     * @Author yeJH
     * @Date 2019/9/26 11:24
     * @Description 积分活动根据活动分组获取商品列表下对应的商品一级分类
     *
     * @Update yeJH
     *
     * @param  groupId 活动分组id
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    List<BaseVo> findIntegralsGoodsCategory(Long groupId);

    /**
     * @Author yeJH
     * @Date 2019/9/26 21:18
     * @Description 获取APP首页限时秒杀，积分抵现，囤货鸭，拼团鸭
     *
     * @Update yeJH
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.app.advice.activity.HomePageActivityGoodsVo>
     **/
    List<HomePageActivityGoodsVo> findHomePageActivity();

    /**
     * 条件分页查询广告基本信息
     *
     * @param searchAdvicesDto
     * @return
     */
    List<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto);

    /**
     * 获取有店下的店铺分类
     * @return
     */
    List<StoreCategoryInfoVo> findStoreCategory(String location);

    /**
     * 获取有店下的店铺分类选项卡内容
     * @param relId
     * @return
     */
    List<AdviceTabVo> findStoreCategoryTab(Long relId);

    /**
     * 获取资讯动态下推荐的分类
     * @param name
     * @return
     */
    List<BaseVo> findInfoCategory(String name);

    /**
     * 根据选项卡id获取有店下的店铺分类详情
     * @param tabId
     * @return
     */
    List<StoreCategoryDetailVo> findStoreCategoryDetail(@Param("tabId") Long tabId,@Param("userId") Long userId);

    /**
     * 首页跳转内容-有店（所有店铺列表）
     * @param userId
     */
    List<StoreCategoryDetailVo> searchAllStoreDetail(@Param("userId")Long userId);

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
     * 分页条件查询店铺下的商品列表
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchStoreGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

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

    /**
     * 分页条件查询优惠券关联的所有商品
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchCouponGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * 分页条件查询首页下面的商品列表/品牌id/选项卡id/商品分类id/葱鸭百货关联/优惠券关联下的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchHomeGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * @Author chauncy
     * @Date 2019-09-19 13:11
     * @Description //分页条件查询葱鸭百货关联二级分类的所有商品
     *
     * @Update chauncy
     *
     * @Param [searchGoodsBaseListDto]
     * @return java.util.List<com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo>
     **/
    List<SearchGoodsBaseListVo> searchSecondCategoryGoodsBaseList(SearchGoodsBaseListDto searchGoodsBaseListDto);

    /**
     * @Author chauncy
     * @Date 2019-09-22 18:06
     * @Description //猜你喜欢
     *
     * @Update chauncy
     *
     * @Param [name]
     * @return java.util.List<com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo>
     **/
    List<SearchGoodsBaseListVo> guessYourLike(String name);

    /**
     * @Author chauncy
     * @Date 2019-10-05 21:39
     * @Description //获取个人中心顶部背景图
     *
     * @Update chauncy
     *
     * @param
     * @return com.chauncy.data.vo.app.user.PersonalCenterPictureVo
     **/
    @Select("select a.id as advice_id,a.name as advice_name,a.picture as advice_picture,a.location " +
            "from mm_advice a " +
            "where a.location = 'PERSONAL_CENTER' and a.del_flag = 0 and a.enabled = 1")
    PersonalCenterPictureVo getTopPicture();

    /**
     * @Author chauncy
     * @Date 2019-10-05 21:39
     * @Description //获取充值入口图片
     *
     * @Update chauncy
     *
     * @param
     * @return com.chauncy.data.vo.app.user.PersonalCenterPictureVo
     **/
    @Select("select a.id as advice_id,a.name as advice_name,a.picture as advice_picture,a.location " +
            "from mm_advice a " +
            "where a.location = 'TOP_UP_ENTRY' and a.del_flag = 0 and a.enabled = 1")
    PersonalCenterPictureVo getTopUpPicture();
}