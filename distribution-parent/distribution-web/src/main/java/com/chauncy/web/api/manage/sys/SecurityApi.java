package com.chauncy.web.api.manage.sys;

import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.RedisUtil;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.Result;
import com.chauncy.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huangwancheng
 * @create 2019-05-25 19:17
 */
@Slf4j
@RestController
@Api(tags = "Security相关接口")
@RequestMapping("/common")
@Transactional
public class SecurityApi {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/needLogin",method = RequestMethod.GET)
    @ApiOperation(value = "没有登录")
    public JsonViewData needLogin(){

        log.error("您还未登录");

        return new JsonViewData (ResultCode.NO_LOGIN,"您还未登录！");
    }

    @GetMapping("/logout")
    @ApiOperation ("退出登录")
    public JsonViewData logout(){
        SysUserPo userPo = securityUtil.getCurrUser ();
        String key1 = SecurityConstant.USER_TOKEN+userPo.getUsername ();
//        Object token = redisUtil.get (key1);
        Object token = redisTemplate.opsForValue().get(key1);
        String key2 = SecurityConstant.TOKEN_PRE+token;
        String key3 = "permission::userMenuList:" + userPo.getId();
        redisUtil.del (key1,key2,key3);
        return new JsonViewData (ResultCode.SUCCESS);
    }
}
