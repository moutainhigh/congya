package com.chauncy.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.annotation.SystemLog;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.system.LogType;
import com.chauncy.common.enums.system.LoginType;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.third.easemob.RegistIM;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.common.util.huanxin.HuanXinUtil;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.sys.TokenUser;
import com.chauncy.data.vo.sys.UserInfoVo;
import com.chauncy.security.details.MyAuthenticationDetails;
import com.chauncy.security.util.IpInfoUtil;
import com.chauncy.security.util.ResponseUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author huangwancheng
 * @create 2019-05-25 00:02
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${distribution.token.redis}")
    private Boolean tokenRedis;

    @Value("${distribution.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Value("${distribution.saveLoginTime}")
    private Integer saveLoginTime;

    @Value("${distribution.token.storePerms}")
    private Boolean storePerms;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IBaseMapper<UmUserPo> umUserPoIBaseMapper;

    @Autowired
    private IBaseMapper<SysUserPo> sysUserPoIBaseMapper;

    @Override
    @SystemLog(description = "登录系统", type = LogType.LOGIN)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        MyAuthenticationDetails details = (MyAuthenticationDetails) authentication.getDetails();

        //用户选择保存登录状态几天
        String saveLogin = request.getParameter(SecurityConstant.SAVE_LOGIN);
        Boolean saved = false;
        if(StrUtil.isNotBlank(saveLogin) && Boolean.valueOf(saveLogin)){
            saved = true;
            if(!tokenRedis){
                tokenExpireTime = saveLoginTime * 60 * 24;
            }
        }
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        List<String> list = new ArrayList<>();
        for(GrantedAuthority g : authorities){
            list.add(g.getAuthority());
        }
//        ipInfoUtil.getUrl(request);
        // 登陆成功生成token
        String token;
        if(tokenRedis){
            // redis
            token = UUID.randomUUID().toString().replace("-", "");
            TokenUser user = new TokenUser(username, list, saved);
            // 不缓存权限
            if(!storePerms){
                user.setPermissions(null);
            }
            // 单点登录 之前的token失效
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);
            if(StrUtil.isNotBlank(oldToken)){
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
            if(saved){
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, saveLoginTime, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), saveLoginTime, TimeUnit.DAYS);
            }else{
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenExpireTime, TimeUnit.MINUTES);
            }
        }else{
            // 不缓存权限
            if(!storePerms){
                list = null;
            }
            // jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(username)
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }
       /* String loginType=request.getParameter("loginType");
        if (loginType.equals("MANAGE")||loginType.equals("SUPPLIER")){
            ResponseUtil.out(response, ResponseUtil.resultMap(true,200,"登录成功", token));
        }*/
        UserInfoVo userInfoVo = new UserInfoVo();
        //登录成功之后的操作--检查该用户是否注册环信账号，没有则注册
       if (details.getLoginType().equals(LoginType.MANAGE) || details.getLoginType().equals(LoginType.SUPPLIER)) {
           SysUserPo sysUserPo = sysUserPoIBaseMapper.selectOne(new QueryWrapper<SysUserPo>().lambda()
                   .eq(SysUserPo::getUsername,username));
           //是否注册环信账号
//           isRegistry(sysUserPo.getId(), sysUserPo.getNickName());
           new HuanXinUtil().createUser(sysUserPo.getId(), Constants.PASSWORD,sysUserPo.getNickName());
           ResponseUtil.out(response, new JsonViewData<String>(token));
       }else if (details.getLoginType().equals(LoginType.THIRD_WECHAT)){
           UmUserPo userPo =umUserPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                   .eq(UmUserPo::getUnionId,details.getUnionId()));
           //是否注册环信账号
//           isRegistry(userPo.getId().toString(), userPo.getName());
           new HuanXinUtil().createUser(userPo.getId().toString(), Constants.PASSWORD,userPo.getName());
           userInfoVo.setToken(token);
           userInfoVo.setIM(String.valueOf(userPo.getId()));
           userInfoVo.setJPush(String.valueOf(userPo.getId()));
           userInfoVo.setNickName(userPo.getName() == null ? userPo.getName() : SecurityConstant.USER_DEFAULT_NICKNAME);
           userInfoVo.setAvatar(userPo.getPhoto() == null ? userPo.getPhoto() : SecurityConstant.USER_DEFAULT_AVATAR);
           ResponseUtil.out(response, new JsonViewData<UserInfoVo>(userInfoVo));

       }else {
           UmUserPo userPo =umUserPoIBaseMapper.selectOne(new QueryWrapper<UmUserPo>().lambda()
                   .eq(UmUserPo::getPhone,details.getPhone()));
           //是否注册环信账号
//           isRegistry(userPo.getId().toString(), userPo.getName());
           new HuanXinUtil().createUser(userPo.getId().toString(), Constants.PASSWORD,userPo.getName());
           userInfoVo.setToken(token);
           userInfoVo.setIM(String.valueOf(userPo.getId()));
           userInfoVo.setJPush(String.valueOf(userPo.getId()));
           userInfoVo.setNickName(userPo.getName() == null ? userPo.getName() : SecurityConstant.USER_DEFAULT_NICKNAME);
           ResponseUtil.out(response, new JsonViewData<UserInfoVo>(userInfoVo));

       }
    }

    private void isRegistry(String s, String name) {
        if (RegistIM.getUser(s) == null) {
            RegUserBo regUserBo = new RegUserBo();
            regUserBo.setPassword(Constants.PASSWORD);
            regUserBo.setUsername(s);
            regUserBo.setNickname(name == null ? name : SecurityConstant.USER_DEFAULT_NICKNAME);
            RegistIM.reg(regUserBo);
        }
    }
}
