package com.chauncy.web.api.manage.activity.seckill;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.seckill.IAmSeckillService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀活动管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@RestController
@RequestMapping("am-seckill-po")
@Api(tags = "秒杀活动管理")
public class AmSeckillApi {

 @Autowired
 private IAmSeckillService service;


}
