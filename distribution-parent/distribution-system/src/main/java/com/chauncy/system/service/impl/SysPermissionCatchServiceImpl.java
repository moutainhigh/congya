package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.mapper.sys.SysPermissionMapper;
import com.chauncy.system.service.SysPermissionCatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-27 14:49
 */
@Service
public class SysPermissionCatchServiceImpl extends AbstractService<SysPermissionMapper, SysPermissionPo> implements SysPermissionCatchService {

    @Autowired
    private SysPermissionMapper mapper;

    @Override
    public List<SysPermissionPo> findByUserId(String userId) {
        return mapper.findByUserId(userId);
    }
}
