package com.chauncy.user.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.user.UmUserLabelPo;
import com.chauncy.data.mapper.user.UmUserLabelMapper;
import com.chauncy.user.service.IUmUserLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户标签 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Service
public class UmUserLabelServiceImpl extends AbstractService<UmUserLabelMapper, UmUserLabelPo> implements IUmUserLabelService {

    @Autowired
    private UmUserLabelMapper mapper;

}
