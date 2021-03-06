package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.dto.app.brand.SearchGoodsDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.brand.BrandGoodsListVo;
import com.chauncy.data.vo.app.brand.BrandInfoVo;
import com.chauncy.data.vo.app.brand.BrandListVo;
import com.chauncy.data.vo.app.brand.GoodsVo;
import com.chauncy.data.vo.manage.product.AttributeIdNameTypeVo;
import com.chauncy.data.vo.manage.product.PmGoodsAttributeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 商品属性参数表
 * <p>
 * 类目下:
 * 规格管理(平台)
 * 商品参数管理(平台)
 * 服务说明管理(平台、商家)
 * 购买须知管理(平台)
 * 活动说明管理(平台)
 * <p>
 * 商品下：
 * 类目
 * 标签管理(平台)
 * 规格管理(平台)
 * 品牌管理
 * 参数值
 * <p>
 * 服务说明管理(平台、商家)
 * 活动说明管理(平台)
 * 标签管理(平台）
 * 购买须知管理(平台)
 * 规格管理(平台)
 * 商品参数管理(平台)
 * 品牌管理(平台) IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsAttributeMapper extends IBaseMapper<PmGoodsAttributePo> {

    /**
     * 根据不同类型对应的名称查找
     *
     * @param type
     * @param name
     * @return
     */
    PmGoodsAttributePo findByTypeAndName(@Param("type") Integer type, @Param("name") String name, @Param("storeId") Long storeId);

    /**
     * 按条件查询
     *
     * @param type
     * @param name
     * @param enabled
     * @return
     */
    List<PmGoodsAttributeVo> findByCondition(@Param("type") Integer type, @Param("name") String name, @Param("enabled") Boolean enabled);

    /**
     * 根据分类id下的的商品找出哪些属性被商品引用了
     * @param categoryId
     * @return
     */
    List<PmGoodsAttributePo> loadByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据分类ID和属性类型获取商品属性
     *
     * @param categoryId
     * @param type
     * @return
     */
    List<BaseVo> findAttByTypeAndCat(@Param("categoryId") Long categoryId,@Param("type") Integer type);

    /**
     * 根据分类ID查找对应的默认的规格属性信息
     *
     * @param categoryId
     * @return
     */
    List<BaseBo> findStandardName(@Param("categoryId") Long categoryId);

    /**
     * 根据type list找出商品属性集合
     * @param types
     * @return
     */
    List<AttributeIdNameTypeVo> loadAttributeIdNameTypeVos(@Param("types") List<Integer> types);

    /**
     * 通过类型查找属性
     *
     * @param type
     * @param storeId  商家服务说明  不同店铺只能看到自己的商家服务说明
     * @return
     */
    List<BaseVo> findAttByType(@Param("type")Integer type, @Param("storeId")Long storeId);

    /**
     * 查找分类下的品牌信息
     *
     * @return
     */
    List<BrandInfoVo> getBrandList (@Param ("categoryId") Long categoryId);

    /**
     * 获取三级分类信息
     * @param categoryId
     */
    List<BrandListVo> getThirdCategory (Long categoryId);

    /**
     * 获取品牌信息
     * @param brandId
     * @return
     */
    @Select ("select id as brandId ,name as brandName, subtitle as brandSubTitle,collection_num from pm_goods_attribute where id = #{brandId}")
    BrandGoodsListVo getBrandById (Long brandId);

    /**
     * 查询类目下的分类下的商品
     *
     * @param searchGoodsDto
     */
    List<GoodsVo> getBrandGoodsList (SearchGoodsDto searchGoodsDto);

    /**
     * 条件分页查询广告选项卡需要被关联的品牌
     *
     * @param finalAssociatedBrandIdList1
     * @return
     */
    List<BaseVo> searchBrands(@Param("name") String name, @Param("idList") List<Long> finalAssociatedBrandIdList1);

    /**
     * 不用updateById  update a=a+1
     *
     * @param favoritesId
     */
    @Update("update pm_goods_attribute set collection_num = collection_num+1 where id = #{favoritesId}")
    void addFavorites(Long favoritesId);

    /**
     * 不用updateById  update a=a-1
     *
     * @param favoritesId
     */
    @Update("update pm_goods_attribute set collection_num = collection_num-1 where id = #{favoritesId} and collection_num > 0")
    void delFavorites(Long favoritesId);

    List<BaseVo> findAttByTypeAndStore(@Param("type") Integer type, @Param("storeId") Long storeId);
}
