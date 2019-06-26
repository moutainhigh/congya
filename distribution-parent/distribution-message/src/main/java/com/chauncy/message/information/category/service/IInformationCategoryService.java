package com.chauncy.message.information.category.service;

import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.information.add.InformationCategoryDto;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface IInformationCategoryService extends Service<MmInformationCategoryPo> {

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
