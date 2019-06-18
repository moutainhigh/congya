package com.chauncy.web.api.manage.sys;

import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huangwancheng
 * @create 2019-05-25 19:17
 */
@Slf4j
@RestController
@Api(description = "Security相关接口")
@RequestMapping("/common")
@Transactional
public class SecurityApi {

    @RequestMapping(value = "/needLogin",method = RequestMethod.GET)
    @ApiOperation(value = "没有登录")
    public Result<Object> needLogin(){

        return new ResultUtil<Object>().setErrorMsg(401, "您还未登录");
    }
}
