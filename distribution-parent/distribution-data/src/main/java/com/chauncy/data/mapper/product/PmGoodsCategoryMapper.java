package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelSpellGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo;
import com.chauncy.data.dto.app.store.FindStoreCategoryDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.activity.SearchCategoryByActivityIdDto;
import com.chauncy.data.dto.manage.good.select.SearchAttributeByNamePageDto;
import com.chauncy.data.dto.manage.good.select.SearchThirdCategoryDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.activity.SearchCategoryByActivityIdVo;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.manage.product.SearchAttributeVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.product.SearchThirdCategoryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsCategoryMapper extends IBaseMapper<PmGoodsCategoryPo> {

    /**
     * @Author yeJH
     * @Date 2019/10/17 12:43
     * @Description 获取商品一级分类 根据sort 排序
     *
     * @Update yeJH
     *
     * @param
     * @return java.util.List<com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo>
     **/
    List<GoodsCategoryVo> findFirstCategory();

    /**
     * @Author yeJH
     * @Date 2019/10/17 12:49
     * @Description 根据商品一级分类查找二三级分类
     *
     * @Update yeJH
     *
     * @param  categoryId
     * @return java.util.List<com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo>
     **/
    List<GoodsCategoryVo> findChildrenCategory(Long categoryId);

    /**
     * 查询分类详情
     * @param id
     * @return
     */
    Map<String,Object> loadById(Long id);

    /**
     * 根据分类id、type、属性名称集合查找属性id
     * @param names
     * @param type
     * @param cId
     * @return
     */
    List<Long> getAttributeIdsByNamesAndCategoryId(@Param("names")List<String> names,
                                                   @Param("type") Integer type,
                                                   @Param("cId") Long cId);



    /**
     * 根据分类id、type、属性名称集合查找属性id
     * @param name
     * @param type
     * @param cId
     * @return
     */
    Long getAttributeIdsByNameAndCategoryId(@Param("name")String name,
                                                   @Param("type") Integer type,
                                                   @Param("cId") Long cId);

    /**
     * 查找分类下的属性是否被勾选
     * @param searchAttributeByNamePageDto
     * @return
     */
    List<SearchAttributeVo> loadAttributeVo(@Param("t") SearchAttributeByNamePageDto searchAttributeByNamePageDto);

    /**
     * 根据条件查询一级到三级的所有分类
     * @param baseSearchDto
     * @param pageSize
     * @param offset 偏移量
     * @return
     */
    List<SearchCategoryVo> loadSearchCategoryVoList(@Param("t") BaseSearchDto baseSearchDto,
                                                    @Param("pageSize") Integer pageSize,
                                                    @Param("offset") Integer offset
                                    );

    /**
     * 联动查询所有分类
     *
     * @return
     */
    List<GoodsCategoryTreeVo> loadGoodsCategoryTreeVo();


    /**
     * 总条数
     * @param baseSearchDto
     * @return
     */
    Integer loadCount(@Param("t")BaseSearchDto baseSearchDto);


    /**
     * 根据id获取所有父级包括本身分类名称
     * @param id
     * @return
     */
    List<String> loadParentName(@Param("id") Long id);

    /**
     * 获取店铺下商品一级分类信息
     *
     * @return
     */
    List<SearchCategoryVo> findFirstCategoryByStoreId(FindStoreCategoryDto findStoreCategoryDto);

    /**
     * 获取店铺下商品一级分类信息
     *
     * @return
     */
    List<SearchCategoryVo> findSecondCategoryByStoreId(FindStoreCategoryDto findStoreCategoryDto);

    /**
     * 获取店铺下商品一级分类信息
     *
     * @return
     */
    List<SearchCategoryVo> findThirdCategoryByStoreId(FindStoreCategoryDto findStoreCategoryDto);

    /**
     * 获取一级分类列表
     * @return
     */
    @Select("select id,name\n" +
            "from pm_goods_category\n" +
            "where `level`=1")
    List<BaseVo> getFirstCategory ();

    /**
     * 总条数
     * @param searchCategoryByActivityIdDto
     * @return
     */
    Integer count(@Param("p") SearchCategoryByActivityIdDto searchCategoryByActivityIdDto);

    /**
     * 根据条件查询一级到三级的所有分类
     * @param searchCategoryByActivityIdDto
     * @param pageSize
     * @param offset 偏移量
     * @return
     */
    List<SearchCategoryByActivityIdVo> searchCategoryByActivityId(@Param("t") SearchCategoryByActivityIdDto searchCategoryByActivityIdDto, @Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    /**
     * 联动查询葱鸭百货广告位关联的商品分类
     *
     * @param adviceId
     * @return
     */
    List<GoodsCategoryVo> findGoodsCategoryTree(Long adviceId);

    /**
     * @Author chauncy
     * @Date 2019-10-07 15:46
     * @Description //联动查询所有二级以上的分类
     *
     * @Update chauncy
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo>
     **/
    List<GoodsCategoryTreeVo> FindAllSecondCategory();

    /**
     * @Author chauncy
     * @Date 2019-10-07 16:50
     * @Description //条件分页查询所有第三级分类信息
     *
     * @Update chauncy
     *
     * @param  searchThirdCategoryDto
     * @return java.util.List<com.chauncy.data.vo.manage.product.SearchThirdCategoryVo>
     **/
    List<SearchThirdCategoryVo> searchThirdCategory(SearchThirdCategoryDto searchThirdCategoryDto);

    /**
     * @Author chauncy
     * @Date 2019-10-10 10:44
     * @Description //获取商品对应的一二三级类目ID
     *
     * @Update chauncy
     *
     * @param  a
     * @return com.chauncy.data.domain.po.message.advice.MmAdviceRelSpellGoodsPo
     **/
    @Select("select a.id as third_category_id,a.parent_id as second_category_id,b.parent_id as first_category_id \n" +
            "from pm_goods_category a,pm_goods_category b,pm_goods c \n" +
            "where c.del_flag = 0 and c.publish_status = 1 and c.id = #{a} and c.goods_category_id = a.id and " +
            "a.del_flag = 0 and a.enabled =1 and b.del_flag = 0 and b.enabled =1 and a.parent_id = b.id")
    MmAdviceRelSpellGoodsPo getCategoryByGoodsId(Long a);
}
