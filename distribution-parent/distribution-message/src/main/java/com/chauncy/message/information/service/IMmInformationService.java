package com.chauncy.message.information.service;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.vo.manage.message.information.InformationVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
public interface IMmInformationService extends Service<MmInformationPo> {

    /**
     * 保存资讯
     * @param informationDto
     */
    void saveInformation(InformationDto informationDto);
    /**
     * 编辑资讯
     * @param informationDto
     */
    void editInformation(InformationDto informationDto);

    /**
     * 批量删除资讯
     * @param ids
     */
    void delInformationByIds(Long[] ids);

    /**
     * 根据ID查找店铺资讯
     * @param id 资讯id
     * @return
     */
    InformationVo findById(Long id);
}
