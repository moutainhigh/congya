package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告基本信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-po")
@Api(tags = "广告基本信息表")
public class MmAdviceApi extends BaseApi {

    @Autowired
    private IMmAdviceService service;


}
