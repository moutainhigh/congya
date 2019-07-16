package com.chauncy.product.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.dto.app.brand.SearchGoodsDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.good.add.AddOrUpdateAttValueDto;
import com.chauncy.data.dto.manage.good.add.GoodAttributeDto;
import com.chauncy.data.dto.manage.good.select.FindAttributeInfoByConditionDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.brand.BrandGoodsListVo;
import com.chauncy.data.vo.app.brand.BrandListVo;
import com.chauncy.data.vo.manage.product.AttributeIdNameTypeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

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
 * 品牌管理(平台) 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsAttributeService extends Service<PmGoodsAttributePo> {


    /**
     * 保存商品属性
     * @param goodsAttributeDto
     * @return
     */
    JsonViewData saveAttribute (GoodAttributeDto goodsAttributeDto);

    /**
     * 批量删除属性以及关联的值
     * @param ids
     * @return
     */
    JsonViewData deleteAttributeByIds(Long[] ids);

    /**
     * 更新属性基本数据(不包括属性值)
     *
     * @param goodAttributeDto
     * @return
     */
    JsonViewData edit(GoodAttributeDto goodAttributeDto);

    /**
     * 根据ID查找属性以及关联的属性值
     * @param id
     * @return
     */
    JsonViewData findById(Long id);


    /**
     * 条件查询属性以及关联属性值
     *
     * @param findAttributeInfoByConditionDto
     * @return
     */
    JsonViewData findByCondition(FindAttributeInfoByConditionDto findAttributeInfoByConditionDto);

    /**
     * 启用或禁用
     *
     * @param baseUpdateStatusDto
     * @return
     */
    JsonViewData updateStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 查询分类下的商品用到了哪一些属性id
     * @param categoryId
     * @return
     */
    List<PmGoodsAttributePo> findByCategoryId(Long categoryId);

    /**
     * 根据属性ID添加或根据属性值ID修改属性值
     *
     * @param addOrUpdateAttValueDto
     * @return
     */
    void addOrUpdateAttInfo(AddOrUpdateAttValueDto addOrUpdateAttValueDto);

    /**
     * 根据type list找出商品属性集合
     * @param types
     * @return
     */
    List<AttributeIdNameTypeVo> findAttributeIdNameTypeVos(List<Integer> types);

    /**
     * 获取品牌列表
     * @return
     */
    PageInfo<BrandListVo> getBrandList (BaseSearchDto baseSearchDto);

    /**
     * 获取一级分类列表
     * @return
     */
    List<BaseVo> getFirstCategory ();

    /**
     * 条件分页获取品牌下的商品
     *
     * @param searchGoodsDto
     * @return
     */
    BrandGoodsListVo getBrandGoodsList (SearchGoodsDto searchGoodsDto);
}
