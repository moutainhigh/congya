package com.chauncy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.mapper.sys.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登陆身份认证
 * @author: zhangrt
 * createAt: 2018/9/14
 */
@Component(value="customUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserPo loadUserByUsername(String name) throws UsernameNotFoundException {
        SysUserPo conditionSysUserPo = new SysUserPo();
        conditionSysUserPo.setUsername(name);
        QueryWrapper<SysUserPo> queryWrapper=new QueryWrapper<>(conditionSysUserPo);
        SysUserPo sysUserPo;
        try {
             sysUserPo = sysUserMapper.selectOne(queryWrapper);
        }catch (Exception e){
            LoggerUtil.error(e);
            throw e;
        }
        if (sysUserPo == null) {
            throw new UsernameNotFoundException(String.format("账号不存在 '%s'.", name));
        }
       /* Role role = authMapper.findRoleByUserId(userDetail.getId());
        userDetail.setRole(role);*/
        return sysUserPo;
    }
}
