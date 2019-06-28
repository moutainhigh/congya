package com.chauncy.store.category.service;

import com.chauncy.data.domain.po.store.category.SmStoreCategoryPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreCategoryDto;
import com.chauncy.data.dto.manage.store.select.StoreCategorySearchDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.category.SmStoreCategoryVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-16
 */
public interface ISmStoreCategoryService extends Service<SmStoreCategoryPo> {

    /**
     * 保存店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    SmStoreCategoryPo saveStoreCategory(StoreCategoryDto storeCategoryDto);

    /**
     * 编辑店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    SmStoreCategoryPo editStoreCategory(StoreCategoryDto storeCategoryDto);

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    Map<String, Object> findById(Long id);

    /**
     * 修改店铺分类启用状态
     * @param baseUpdateStatusDto
     * ids 店铺分类ID
     * enabled 店铺分类启用状态修改 true 启用 false 禁用
     * @return
     */
    void editCategoryStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 条件查询
     * @param storeCategorySearchDto
     * @return
     */
    PageInfo<SmStoreCategoryVo> searchPaging(StoreCategorySearchDto storeCategorySearchDto);

    /**
     * 查询店铺所有分类
     * @return
     */
    List<SmStoreCategoryVo> selectAll();

    /**
     * 批量删除分类
     * @param ids
     */
    void delStoreCategoryByIds(Long[] ids);
    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto);
}
