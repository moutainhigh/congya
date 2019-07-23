package com.chauncy.security.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.security.config.SecurityUserDetails;
import com.chauncy.security.config.UserDetailsServiceImpl;
import com.chauncy.security.details.MyAuthenticationDetails;
import com.chauncy.security.exception.LoginFailLimitException;
import com.chauncy.user.service.IUmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        switch (details.getLoginType()) {
            case MANAGE:
            case SUPPLIER:
                String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
                String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
                // 这里构建来判断用户是否存在和密码是否正确
                SecurityUserDetails userInfo = (SecurityUserDetails) userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
                if (userInfo.getUsername() == null) {
                    throw new DisabledException("用户名不存在");
                }
                if (!bCryptPasswordEncoder.matches(password, userInfo.getPassword())) {
                    throw new BadCredentialsException("密码不正确");
                }
                if (userInfo.getStatus()==-1){
                    throw new LockedException("账户被禁用，请联系管理员");
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
                appAfterLogin(phone);
                return new UsernamePasswordAuthenticationToken(securityUserDetails, queryUser.getPassword());
            case APP_PASSWORD:
                String appPhone = details.getPhone();
                String appPassword = (String) authentication.getCredentials();// 这个是表单中输入的密码；
                UmUserPo appCondition = new UmUserPo();
                appCondition.setPhone(appPhone);
                UmUserPo appQueryUser = umUserService.getOne(new QueryWrapper<>(appCondition));
                if (appQueryUser==null){
                    throw new DisabledException("该手机号码未注册！");
                }
                if (!appQueryUser.getEnabled()){
                    throw new LockedException("该账户已被禁用！");
                }
                if (!bCryptPasswordEncoder.matches(appPassword, appQueryUser.getPassword())) {
                    throw new BadCredentialsException("密码不正确");
                }
                SecurityUserDetails appSecurityUser=new SecurityUserDetails();
                appSecurityUser.setUsername(appPhone);
                appSecurityUser.setPassword(appQueryUser.getPassword());
                appAfterLogin(appPhone);
                return new UsernamePasswordAuthenticationToken(appSecurityUser, appQueryUser.getPassword());
            case THIRD_WECHAT:
                String unionId=details.getUnionId();
                UmUserPo thirdCondition = new UmUserPo();
                thirdCondition.setUnionId(unionId);
                UmUserPo thirdQueryUser = umUserService.getOne(new QueryWrapper<>(thirdCondition));
                if (thirdQueryUser==null){
                    throw new LoginFailLimitException("该用户尚未注册，请先完成手机验证注册！");
                }
                if (!thirdQueryUser.getEnabled()){
                    throw new LockedException("该账户已被禁用！");
                }
                SecurityUserDetails thirdSecurityUser=new SecurityUserDetails();
                thirdSecurityUser.setUsername(thirdQueryUser.getPhone());
                thirdSecurityUser.setPassword(thirdQueryUser.getPassword());
                appAfterLogin(thirdQueryUser.getPhone());
                return new UsernamePasswordAuthenticationToken(thirdSecurityUser, thirdQueryUser.getPassword());
        }
        return null;
    }

    /**
     * 登录之后更新一些统计信息
     */
    private void appAfterLogin(String phone){
        umUserService.updateLogin(phone);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
