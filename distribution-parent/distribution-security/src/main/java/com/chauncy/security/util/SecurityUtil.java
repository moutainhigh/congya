package com.chauncy.security.util;

import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.system.service.ISysRoleUserService;
import com.chauncy.system.service.ISysUserRoleCatchService;
import com.chauncy.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-24 15:16
 *
 * 安全认证工具类
 *
 * 获取用户、角色、权限相关信息
 *
 */
@Component
@Slf4j
public class SecurityUtil {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleUserService iUserRoleService;

    @Autowired
    private ISysUserRoleCatchService userRoleCatchService;

    /**
     * 获取当前登录用户
     * @return
     */
    public SysUserPo getCurrUser(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(user.getUsername());
    }

    /**
     * 获取当前用户数据权限 null代表具有所有权限
     */
    public List<String> getDeparmentIds(){

        List<String> deparmentIds = new ArrayList<>();
        SysUserPo u = getCurrUser();
        // 用户角色
        List<SysRolePo> userRoleList = userRoleCatchService.findByUserId(u.getId());
        // 判断有无全部数据的角色
        Boolean flagAll = false;
        for(SysRolePo r : userRoleList){
            if(r.getDataType()==null||r.getDataType().equals(SecurityConstant.DATA_TYPE_ALL)){
                flagAll = true;
                break;
            }
        }
        if(flagAll){
            return null;
        }
        // 查找自定义
        return userRoleCatchService.findDepIdsByUserId(u.getId());
    }

    /**
     * 通过用户名获取用户拥有权限
     * @param username
     */
    public List<GrantedAuthority> getCurrUserPerms(String username){

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(SysPermissionPo p : userService.findByUsername(username).getPermissions()){
            authorities.add(new SimpleGrantedAuthority(p.getTitle()));
        }
        return authorities;
    }
}
