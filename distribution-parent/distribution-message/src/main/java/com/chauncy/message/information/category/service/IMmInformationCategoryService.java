package com.chauncy.message.information.category.service;

import com.chauncy.data.domain.po.message.information.category.MmInformationCategoryPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationCategoryDto;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface IMmInformationCategoryService extends Service<MmInformationCategoryPo> {

    /**
     * 保存店铺资讯分类信息
     * @param informationCategoryDto
     */
    void saveInformationCategory(InformationCategoryDto informationCategoryDto);


    /**
     * 编辑店铺资讯分类信息
     * @param informationCategoryDto
     */
    void editInformationCategory(InformationCategoryDto informationCategoryDto);

    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    InformationCategoryVo findById(Long id);
    /**
     * 根据标签ID、标签名称查询
     *
     * @return
     */
    PageInfo<InformationCategoryVo> searchPaging(BaseSearchDto baseSearchDto);

    /**
     * 查询店铺资讯所有分类
     *
     * @return
     */
    List<InformationCategoryVo> selectAll();
    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 批量删除分类
     * @param ids
     */
    void delInformationCategoryByIds(Long[] ids);
}
