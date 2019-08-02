package com.chauncy.user.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.user.UmRelUserLabelPo;
import com.chauncy.data.mapper.user.UmRelUserLabelMapper;
import com.chauncy.user.service.IUmRelUserLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户与标签关联 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UmRelUserLabelServiceImpl extends AbstractService<UmRelUserLabelMapper, UmRelUserLabelPo> implements IUmRelUserLabelService {

    @Autowired
    private UmRelUserLabelMapper mapper;

}
