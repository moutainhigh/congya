package com.chauncy.store.information.service.impl;

import com.chauncy.data.domain.po.store.information.SmInformationPo;
import com.chauncy.data.mapper.store.information.SmInformationMapper;
import com.chauncy.store.information.service.ISmInformationService;
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
public class SmInformationServiceImpl extends AbstractService<SmInformationMapper,SmInformationPo> implements ISmInformationService {

 @Autowired
 private SmInformationMapper mapper;

}
