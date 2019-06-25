package com.chauncy.store.information.category.service;

import com.chauncy.data.domain.po.store.information.category.SmInformationCategoryPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.store.add.InformationCategoryDto;
import com.chauncy.data.vo.manage.store.information.category.InformationCategoryVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface ISmInformationCategoryService extends Service<SmInformationCategoryPo> {

    /**
     * 保存店铺资讯分类信息
     * @param informationCategoryDto
     */
    void saveInformationCategory(InformationCategoryDto informationCategoryDto);


    /**
     * 编辑店铺资讯分类信息
     * @param informationCategoryDto
     */
    void editInformationLabel(InformationCategoryDto informationCategoryDto);

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    InformationCategoryVo findById(Long id);

}
