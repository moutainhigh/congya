package com.chauncy.message.information.service.impl;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.dto.manage.message.information.add.InformationDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Service
public class MmInformationServiceImpl extends AbstractService<MmInformationMapper, MmInformationPo> implements IMmInformationService {

    @Autowired
    private MmInformationMapper mmInformationMapper;


    /**
     * 保存资讯
     *
     * @param informationDto
     */
    @Override
    public void saveInformation(InformationDto informationDto) {

    }
}
