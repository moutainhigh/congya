package com.chauncy.security.provider;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.security.config.SecurityUserDetails;
import com.chauncy.security.config.UserDetailsServiceImpl;
import com.chauncy.security.details.MyAuthenticationDetails;
import com.chauncy.security.exception.LoginFailLimitException;
import com.chauncy.user.service.IUmUserService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author zhangrt
 * @Date 2019/7/2 13:05
 **/
@Component
public class AppProvider implements AuthenticationProvider {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private IUmUserService umUserService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MyAuthenticationDetails details = (MyAuthenticationDetails) authentication.getDetails();
        switch (details.getLoginType()) {
            case MANAGE:
                String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
                String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
                // 这里构建来判断用户是否存在和密码是否正确
                SecurityUserDetails userInfo = (SecurityUserDetails) userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
                if (userInfo.getUsername() == null) {
                    throw new DisabledException("用户名不存在");
                }
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                if (!bCryptPasswordEncoder.matches(password, userInfo.getPassword())) {
                    throw new BadCredentialsException("密码不正确");
                }
                Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
                // 构建返回的用户登录成功的token
                return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
            case APP_CODE:
                String phone = details.getPhone();
                String code = details.getVerifyCode();
                UmUserPo condition = new UmUserPo();
                condition.setPhone(phone);
                UmUserPo queryUser = umUserService.getOne(new QueryWrapper<>(condition));
                if (queryUser==null){
                    throw new DisabledException("该手机号码未注册！");
                }
                if (!queryUser.getEnabled()){
                    throw new LockedException("该账户已被禁用！");
                }
                boolean checkCode = umUserService.validVerifyCode(code, phone, ValidCodeEnum.LOGIN_CODE);
                if (!checkCode){
                    throw new LoginFailLimitException("验证码错误！");
                }
                SecurityUserDetails securityUserDetails=new SecurityUserDetails();
                securityUserDetails.setUsername(phone);
                securityUserDetails.setPassword(queryUser.getPassword());
                return new UsernamePasswordAuthenticationToken(securityUserDetails, queryUser.getPassword());
            case SUPPLIER:
                break;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
