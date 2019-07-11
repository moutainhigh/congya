package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.system.service.IBasicSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 平台基本设置 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasicSettingServiceImpl extends AbstractService<BasicSettingMapper, BasicSettingPo> implements IBasicSettingService {

    @Autowired
    private BasicSettingMapper mapper;

}
