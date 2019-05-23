package com.chauncy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.jwt.JwtUtils;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserPo> implements ISysUserService {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public String login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final SysUserPo userDetail = (SysUserPo) authentication.getPrincipal();
        final String token = jwtUtils.generateAccessToken(userDetail);
        //存储token
        jwtUtils.putToken(username, token);
        return token;
    }

    @Override
    public SysUserPo register(SysUserPo sysUserPo) {
        final String username = sysUserPo.getUsername();
        SysUserPo conditionSysUserPo = new SysUserPo();
        conditionSysUserPo.setUsername(username);
        QueryWrapper<SysUserPo> queryWrapper=new QueryWrapper<>(conditionSysUserPo);
        SysUserPo querySysUserPo = sysUserMapper.selectOne(queryWrapper);
        if(querySysUserPo!=null) {
            throw new ServiceException(ResultCode.DUPLICATION,"用户已存在！");
        }
        final String rawPassword = sysUserPo.getPassword();
        sysUserPo.setPassword(passwordEncoder.encode(rawPassword));
        sysUserMapper.insert(sysUserPo);
        return sysUserPo;
    }

    @Override
    public void logout(String token) {
        token = token.substring(tokenHead.length());
        String userName = jwtUtils.getUsernameFromToken(token);
        jwtUtils.deleteToken(userName);
    }

    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new ServiceException(ResultCode.FAIL,"登录失败！用户被禁用或账号密码错误！");
        }
    }
}
