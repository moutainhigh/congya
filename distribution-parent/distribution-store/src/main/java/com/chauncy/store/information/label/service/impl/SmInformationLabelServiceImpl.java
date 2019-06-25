package com.chauncy.store.information.label.service.impl;

import com.chauncy.data.domain.po.store.information.label.SmInformationLabelPo;
import com.chauncy.data.mapper.store.information.label.SmInformationLabelMapper;
import com.chauncy.store.information.label.service.ISmInformationLabelService;
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
public class SmInformationLabelServiceImpl extends AbstractService<SmInformationLabelMapper,SmInformationLabelPo> implements ISmInformationLabelService {

 @Autowired
 private SmInformationLabelMapper mapper;

}
