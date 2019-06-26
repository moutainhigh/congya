package com.chauncy.message.information.service.impl;

import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.mapper.message.information.InformationMapper;
import com.chauncy.message.information.service.IInformationService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@Service
public class InformationServiceImpl extends AbstractService<InformationMapper, MmInformationPo> implements IInformationService {

 @Autowired
 private InformationMapper mapper;

}
