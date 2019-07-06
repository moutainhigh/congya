package com.chauncy.web.api.manage.message.interact;


import com.chauncy.message.interact.service.IMmFeedBackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * app用户反馈列表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-06
 */
@RestController
@RequestMapping("mm-feed-back-po")
@Api(tags = "app用户反馈列表")
public class MmFeedBackApi {

 @Autowired
 private IMmFeedBackService service;


}
