package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-27 14:47
 */
@CacheConfig(cacheNames = "userPermission")
public interface SysPermissionCatchService extends Service<SysPermissionPo> {
    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    @Cacheable(key = "#userId")
    List<SysPermissionPo> findByUserId(String userId);
}
