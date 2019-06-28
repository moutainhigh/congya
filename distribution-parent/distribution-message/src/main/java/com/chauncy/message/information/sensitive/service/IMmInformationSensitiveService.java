package com.chauncy.message.information.sensitive.service;

import com.chauncy.data.domain.po.message.information.sensitive.MmInformationSensitivePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationSensitiveDto;
import com.chauncy.data.vo.manage.message.information.sensitive.InformationSensitiveVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface IMmInformationSensitiveService extends Service<MmInformationSensitivePo> {

    /**
     * 保存店铺资讯敏感词信息
     * @param informationSensitiveDto
     */
    void saveInformationSensitive(InformationSensitiveDto informationSensitiveDto);
    
    /**
     * 编辑店铺资讯敏感词信息
     * @param informationSensitiveDto
     */
    void editInformationSensitive(InformationSensitiveDto informationSensitiveDto);

    /**
     * 根据ID查找店铺敏感词
     *
     * @param id
     * @return
     */
    InformationSensitiveVo findById(Long id);
    /**
     * 根据标签ID、标签名称查询
     *
     * @return
     */
    PageInfo<InformationSensitiveVo> searchPaging(BaseSearchDto baseSearchDto);

    /**
     * 批量禁用启用
     * @param baseUpdateStatusDto
     */
    void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto);
    /**
     * 批量删除敏感词
     * @param ids
     */
    void delInformationSensitiveByIds(Long[] ids);
}
