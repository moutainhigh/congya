package com.chauncy.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.sys.TokenUser;
import com.chauncy.security.util.ResponseUtil;
import com.chauncy.security.util.SecurityUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author huangwancheng
 * @create 2019-05-24 15:03
 *
 * token过滤器来验证token有效性
 *
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private Boolean tokenRedis;

    private Integer tokenExpireTime;

    private Boolean storePerms;

    private StringRedisTemplate redisTemplate;

    private SecurityUtil securityUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Boolean tokenRedis, Integer tokenExpireTime,
                                   Boolean storePerms, StringRedisTemplate redisTemplate, SecurityUtil securityUtil) {
        super(authenticationManager);
        this.tokenRedis = tokenRedis;
        this.tokenExpireTime = tokenExpireTime;
        this.storePerms = storePerms;
        this.redisTemplate = redisTemplate;
        this.securityUtil = securityUtil;
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("%%%%%%%%%验证token有效性开始%%%%%%%%%");

        //日志跟踪
        boolean bInsertMDC = insertMDC();

        String header = request.getHeader(SecurityConstant.HEADER);
        if(StrUtil.isBlank(header)){
            header = request.getParameter(SecurityConstant.HEADER);
        }
        Boolean notValid = StrUtil.isBlank(header) || (!tokenRedis && !header.startsWith(SecurityConstant.TOKEN_SPLIT));
        if (notValid) {
            try {
                chain.doFilter(request, response);
            } finally {
                if (bInsertMDC) {
                    MDC.remove(Constants.UNIQUE_ID);
                }
            }
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            LoggerUtil.error(e);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            if (bInsertMDC) {
                MDC.remove(Constants.UNIQUE_ID);
                MDC.remove(Constants.USER_NAME);
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

        // 用户名
        String username = null;
        // 权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(tokenRedis){
            // redis
            String v = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_PRE + header);
            if(StrUtil.isBlank(v)){
                LoggerUtil.info("redis不存在导致登录失败！");
                ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.NO_LOGIN,"未登陆或登陆已超时！"));
                return null;
            }
            TokenUser user = new Gson().fromJson(v, TokenUser.class);
            username = user.getUsername();
            MDC.put(Constants.USER_NAME,username);
            if(storePerms){
                // 缓存了权限
                for(String ga : user.getPermissions()){
                    authorities.add(new SimpleGrantedAuthority(ga));
                }
            }else{
                // 未缓存 读取权限数据
                authorities = securityUtil.getCurrUserPerms(username);
            }
            if(!user.getSaveLogin()){
                // 若未保存登录状态重新设置失效时间
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, header, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + header, v, tokenExpireTime, TimeUnit.MINUTES);
            }
        }else{
            // JWT
            try {
                // 解析token
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
                        .getBody();

                // 获取用户名
                username = claims.getSubject();
                // 获取权限
                if(storePerms) {
                    // 缓存了权限
                    String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
                    if(StrUtil.isNotBlank(authority)){
                        List<String> list = new Gson().fromJson(authority, new TypeToken<List<String>>(){}.getType());
                        for(String ga : list){
                            authorities.add(new SimpleGrantedAuthority(ga));
                        }
                    }
                }else{
                    // 未缓存 读取权限数据
                    authorities = securityUtil.getCurrUserPerms(username);
                }
            } catch (ExpiredJwtException e) {
                LoggerUtil.error(e);
                ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.NO_LOGIN,"jwt过期，未登陆或登陆已超时！"));
            } catch (Exception e){
                log.error(e.toString());
                ResponseUtil.out(response, new JsonViewData<Object>(ResultCode.FAIL,"解析token错误"));
            }
        }

        if(StrUtil.isNotBlank(username)) {
            //此处password不能为null
            User principal = new User(username, "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        }
        return null;
    }

    /**
     * @Author chauncy
     * @Date 2019-11-19 20:31
     * @Description //使用UUID作为日志跟踪ID
     *
     * @Update chauncy
     *
     * @param
     * @return boolean
     **/
    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(Constants.UNIQUE_ID, uniqueId);
        return true;
    }
}
