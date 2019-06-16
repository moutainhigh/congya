package com.chauncy.store.category.service;

import com.chauncy.data.domain.po.store.category.SmStoreCategoryPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.store.add.StoreCategoryDto;
import com.chauncy.data.vo.JsonViewData;

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
    JsonViewData saveStoreCategory(StoreCategoryDto storeCategoryDto);

    /**
     * 编辑店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    JsonViewData editStoreCategory(StoreCategoryDto storeCategoryDto);

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    JsonViewData findById(Long id);
}
