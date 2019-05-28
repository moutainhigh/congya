package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.mapper.sys.SysRoleUserMapper;
import com.chauncy.system.service.ISysUserRoleCatchService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-27 16:45
 */
@Service
public class ISysUserRoleCatchServiceImpl extends AbstractService<SysRoleUserMapper, SysRoleUserPo> implements ISysUserRoleCatchService {

    @Autowired
    private SysRoleUserMapper mapper;

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    public List<SysRolePo> findByUserId(@Param("userId") String userId){
        return  mapper.findByUserId(userId);
    }

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    public List<String> findDepIdsByUserId(String userId){
        return  mapper.findDepIdsByUserId(userId);
    }

}
