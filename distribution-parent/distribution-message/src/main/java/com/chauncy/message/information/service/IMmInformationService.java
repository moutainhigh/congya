package com.chauncy.message.information.service;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;

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
}
