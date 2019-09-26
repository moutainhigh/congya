package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.app.component.ShareDto;
import com.chauncy.data.dto.app.product.SearchActivityGoodsListDto;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.AssociationGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchActivityGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchAdviceGoodsDto;
import com.chauncy.data.dto.supplier.activity.select.SearchAssociatedGoodsDto;
import com.chauncy.data.dto.supplier.good.select.SearchExcelDto;
import com.chauncy.data.dto.supplier.good.select.SearchGoodInfosDto;
import com.chauncy.data.dto.supplier.good.select.SearchRecommendGoodsDto;
import com.chauncy.data.dto.supplier.store.update.SelectStockTemplateGoodsDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.store.GoodsSecondCategoryListVo;
import com.chauncy.data.vo.app.component.ScreenGoodsParamVo;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo;
import com.chauncy.data.vo.supplier.activity.SearchAssociatedGoodsVo;
import com.chauncy.data.vo.supplier.good.InformationRelGoodsVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import com.chauncy.data.vo.supplier.good.ExcelGoodVo;
import com.chauncy.data.vo.supplier.good.RecommendGoodsVo;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateGoodsInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 商品信息 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsMapper extends IBaseMapper<PmGoodsPo> {

    /**
     * 查找该商品对应的属性值
     *
     * @param goodsId
     * @return
     */
    List<GoodsValueBo> findGoodsValue(@Param("goodsId") Long goodsId, @Param("attributeId") Long attributeId);

    /**
     * 获取店铺下商品列表
     * @param searchStoreGoodsDto
     * @return
     */
    List<SearchGoodsBaseListVo> searchStoreGoodsBaseList(SearchStoreGoodsDto searchStoreGoodsDto);

    /**
     * 店铺详情-商品分类
     * @param storeId
     * @return
     */
    List<GoodsSecondCategoryListVo> findGoodsCategory(Long storeId);

    /**
     * 查找店铺下的推荐商品列表  前六个
     *
     * @param storeId
     * @return
     */
    List<GoodsBaseInfoVo> findRecommandGoods(@Param("storeId") Long storeId);

    /**
     * 条件查询商品信息
     *
     * @param searchGoodInfosDto
     * @return
     */
    List<PmGoodsVo> searchGoodsInfo(SearchGoodInfosDto searchGoodInfosDto);

    /**
     * 获取店铺推荐商品
     * @param searchRecommendGoodsDto
     */
    List<RecommendGoodsVo> storeRecommendGoods(SearchRecommendGoodsDto searchRecommendGoodsDto);

    /**
     *
     * 获取店铺下商品列表
     * 店铺id
     * 一级分类id
     * 商品列表： 1.店铺全部商品； 2.店铺推荐商品； 3.店铺新品列表； 4.店铺活动商品； 5.明星单品列表（按时间降序）； 6.最新推荐（按排序数值降序）
     * 排序内容;  1.综合排序  2.销量排序  3.价格排序
     * 排序方式   1.降序   2.升序
     *
     * @return
     */
    List<GoodsBaseInfoVo> searchInfoBasePaging(SearchStoreGoodsDto searchStoreGoodsDto);

    /**
     * @Author yeJH
     * @Date 2019/9/19 20:15
     * @Description 根据筛选商品的条件获取商品对应的品牌，分类，类目的参数
     *
     * @Update yeJH
     *
     * @Param [searchStoreGoodsDto]
     * @return com.chauncy.data.vo.app.component.ScreenGoodsParamVo
     **/
    ScreenGoodsParamVo findScreenGoodsParam(SearchStoreGoodsDto searchStoreGoodsDto);

    /**
     * @Author yeJH
     * @Date 2019/9/25 17:35
     * @Description  获取积分/满减活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto  查询积分/满减活动商品列表参数
     * @return java.util.List<com.chauncy.data.vo.app.goods.ActivityGoodsVo>
     **/
    List<ActivityGoodsVo> findActivityGoodsList(SearchActivityGoodsListDto searchActivityGoodsListDto);

    /**
     * 根据资讯id获取关联的商品
     * @param mmInformationId
     * @return
     */
    List<GoodsBaseInfoVo> searchGoodsByInfoId(@Param("mmInformationId") Long mmInformationId);

    /**
     * 查询导入商品信息
     * @param searchExcelDto
     * @return
     */
    List<ExcelGoodVo> searchExcelGoods(SearchExcelDto searchExcelDto);

    /**
     * 根据资讯id获取关联商品信息
     *
     * @param id
     * @return
     */
    List<InformationRelGoodsVo> searchRelGoodsByInfoId(@Param("id") long id);
    /**
     * 库存模板获取分配店铺商品信息
     *
     * @param selectStockTemplateGoodsDto
     * @return
     */
    List<StockTemplateGoodsInfoVo> selectDistributionGoods(SelectStockTemplateGoodsDto selectStockTemplateGoodsDto);
    /**
     * 库存模板获取自有店铺商品信息
     *
     * @param selectStockTemplateGoodsDto
     * @return
     */
    List<BaseBo> selectOwnGoods(SelectStockTemplateGoodsDto selectStockTemplateGoodsDto);
    /**
     * 库存模板id获取询商品信息
     *
     * @param id
     */
    List<StockTemplateGoodsInfoVo> searchGoodsInfoByTemplateId(@Param("id") long id);

    int updateStock(@Param("goodId") long goodId,@Param("number") int number);

    /**
     * 获取商品的id和name
     *
     * @param associationGoodsDto
     */
    List<BaseVo> selectIds (AssociationGoodsDto associationGoodsDto);

    /**
     * 查找某活动下某商家参与的全部商品
     *
     * @param searchAssociatedGoodsDto
     * @param activityId
     * @param storeId
     * @return
     */
    List<SearchAssociatedGoodsVo> searchAssociatedGoods(@Param("t") SearchAssociatedGoodsDto searchAssociatedGoodsDto, @Param("activityId") Long activityId, @Param("storeId")Long storeId,@Param("categoryIds")List<Long> categoryIds);

    /**
     * 条件分页查询需要被关联的商品(考虑数据量大，就不排除已经关联的了，直接查找全部的商品)
     *
     * @param searchAdviceGoodsDto
     * @return
     */
    List<SearchAdviceGoodsVo> searchTabNeedGoods(SearchAdviceGoodsDto searchAdviceGoodsDto);

    //获取商品基本信息：名称、标题、轮播图、发货地等信息
    SpecifiedGoodsVo findGoodsBase(Long goodsId);

    /**
     * 增加销量
     * @param id
     * @param salesVolume
     */
    void addASalesVolume(@Param("id") Long id,@Param("salesVolume") Integer salesVolume);

    /**
     * update a= a+1
     *
     * @param favoritesId
     */
    @Update("update pm_goods set collection_num = collection_num+1 where id = #{favoritesId}")
    void addFavorites(Long favoritesId);

    /**
     * 不用updateById  update a=a-1
     *
     * @param favoritesId
     */
    @Update("update pm_goods set collection_num = collection_num-1 where id = #{favoritesId} and collection_num > 0")
    void delFavorites(Long favoritesId);

    /**
     * 分享商品
     *
     * @param shareDto
     * @return
     */
    void shareGoods(ShareDto shareDto);

    /**
     * @Author chauncy
     * @Date 2019-09-24 10:39
     * @Description //条件分页查询参与对应积分活动分组的商品信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGoodsDto]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo>
     **/
    List<SearchActivityGoodsVo> searchIntegralsGoods(SearchActivityGoodsDto searchActivityGoodsDto);

    /**
     * @Author chauncy
     * @Date 2019-09-24 16:28
     * @Description //查找已经上架的商品
     *
     * @Update chauncy
     *
     * @Param [searchActivityGoodsDto]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo>
     **/
    List<SearchActivityGoodsVo> searchGoods(SearchActivityGoodsDto searchActivityGoodsDto);

    /**
     * @Author chauncy
     * @Date 2019-09-24 17:00
     * @Description //条件分页查询参与对应满减活动分组的商品信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGoodsDto]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo>
     **/
    List<SearchActivityGoodsVo> searchReducedGoods(SearchActivityGoodsDto searchActivityGoodsDto);

    /**
     * @Author chauncy
     * @Date 2019-09-24 17:33
     * @Description //条件分页查询参与对应满减活动分组的商品信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGoodsDto]
     * @return java.util.List<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo>
     **/
    List<SearchActivityGoodsVo> searchSeckillGoods(SearchActivityGoodsDto searchActivityGoodsDto);

    /**
     * @Author chauncy
     * @Date 2019-09-24 18:38
     * @Description //条件分页查询参与对应满减活动分组的商品信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGoodsDto]
     * @return void
     **/
    List<SearchActivityGoodsVo> searchSpellGroupGoods(SearchActivityGoodsDto searchActivityGoodsDto);
}
