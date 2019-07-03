package com.chauncy.security.config;

import cn.hutool.core.util.StrUtil;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author huangwancheng
 * @create 2019-05-24 22:26
 *
 * 代表了Spring Security的用户实体类，带有用户名、密码、权限特性等性质
 * 实现该接口来定义自己认证用户的获取方式
 *
 * 证成功后会将 UserDetails 赋给 Authentication 的 principal 属性，然后再把 Authentication
 * 保存到 SecurityContext 中，之后需要实用用户信息时通过 SecurityContextHolder 获取存放在
 * SecurityContext 中的 Authentication 的 principal
 *
 */
@Slf4j
@NoArgsConstructor
public class SecurityUserDetails extends SysUserPo implements UserDetails {

    private static final long serialVersionUID = 1L;

    public SecurityUserDetails(SysUserPo user) {

        if(user!=null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.setRoles(user.getRoles());
            this.setPermissions(user.getPermissions());
        }
    }

    /**
     * 添加用户拥有的权限和角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<SysPermissionPo> permissions = this.getPermissions();
        // 添加请求权限
        if(permissions!=null&&permissions.size()>0){
            for (SysPermissionPo permission : permissions) {
                if(SecurityConstant.PERMISSION_OPERATION.equals(permission.getType())
                        && StrUtil.isNotBlank(permission.getTitle())
                        &&StrUtil.isNotBlank(permission.getPath())) {

                    authorityList.add(new SimpleGrantedAuthority(permission.getTitle()));
                }
            }
        }
        // 添加角色
        List<SysRolePo> roles = this.getRoles();
        if(roles!=null&&roles.size()>0){
            // lambda表达式
            roles.forEach(item -> {
                if(StrUtil.isNotBlank(item.getName())){
                    authorityList.add(new SimpleGrantedAuthority(item.getName()));
                }
            });
        }
        return authorityList;
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * 是否禁用
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {

        return SecurityConstant.USER_STATUS_LOCK.equals(this.getStatus()) ? false : true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {

        return SecurityConstant.USER_STATUS_NORMAL.equals(this.getStatus()) ? true : false;
    }
}
