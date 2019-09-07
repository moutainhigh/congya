package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.SearchAttributeByNamePageDto;
import com.chauncy.data.dto.manage.good.select.SearchGoodCategoryDto;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.manage.product.SearchAttributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsCategoryService extends Service<PmGoodsCategoryPo> {
    /**
     * 查询分类详情
     * @param id
     * @return
     */
    Map<String,Object> findById(Long id);

    /**
     * 根据分类id、type、属性名称集合查找属性id
     * @param names
     * @param type
     * @param cId
     * @return
     */
    List<Long> findAttributeIdsByNamesAndCategoryId(@Param("names")List<String> names,
                                                @Param("type") Integer type,
                                                @Param("cId") Long cId);


    /**
     * 根据分类id、type、属性名称查找属性id
     * @param name
     * @param type
     * @param cId
     * @return
     */
    Long findAttributeIdsByNameAndCategoryId(@Param("name")String name,
                                                    @Param("type") Integer type,
                                                    @Param("cId") Long cId);



    /**
     * 查找分类下的属性是否被勾选
     * @param searchAttributeByNamePageDto
     * @return
     */
    List<SearchAttributeVo> findAttributeVo( SearchAttributeByNamePageDto searchAttributeByNamePageDto);


    /**
     * 查找分类列表
     * @param baseSearchDto
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String,Object> searchList(BaseSearchDto baseSearchDto, Integer pageNo, Integer pageSize);


    /**
     * 联动查询所有分类
     *
     * @return
     */
    List<GoodsCategoryTreeVo> findGoodsCategoryTreeVo();

    /**
     * 联动查询葱鸭百货广告位关联的商品分类
     *
     * @return
     */
    List<GoodsCategoryVo> findGoodsCategory(Long adviceId);
}
