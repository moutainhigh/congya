package com.chauncy.message.information.label.service;

import com.chauncy.data.domain.po.message.information.label.MmInformationLabelPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationLabelDto;
import com.chauncy.data.dto.manage.message.information.select.InformationLabelSearchDto;
import com.chauncy.data.vo.manage.message.information.label.InformationLabelVo;
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
public interface IMmInformationLabelService extends Service<MmInformationLabelPo> {


    /**
     * 保存店铺资讯标签信息
     * @param informationLabelDto
     */
    void saveInformationLabel(InformationLabelDto informationLabelDto);

    /**
     * 编辑店铺资讯标签信息
     * @param informationLabelDto
     */
    void editInformationLabel(InformationLabelDto informationLabelDto);

    /**
     * 根据ID查找店铺标签
     *
     * @param id
     * @return
     */
    InformationLabelVo findById(Long id);

    /**
     * 根据标签ID、标签名称查询
     * @return
     */
    PageInfo<InformationLabelVo> searchPaging(InformationLabelSearchDto informationLabelSearchDto);

    /**
     * 查询店铺资讯所有标签
     * @return
     */
    List<InformationLabelVo> selectAll();

    /**
     * 批量禁用启用
     *
     * @param baseUpdateStatusDto
     */
    void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 批量删除标签
     * @param ids
     */
    void delInformationLabelByIds(Long[] ids);
}
