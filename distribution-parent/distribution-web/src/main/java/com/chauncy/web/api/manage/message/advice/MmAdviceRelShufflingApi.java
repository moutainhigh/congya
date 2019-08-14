package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelShufflingService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告与无关联轮播图关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-rel-shuffling-po")
@Api(tags = "广告与无关联轮播图关联表")
public class MmAdviceRelShufflingApi extends BaseApi {

    @Autowired
    private IMmAdviceRelShufflingService service;


}
