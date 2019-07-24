package com.chauncy.web.api.manage.activity.spell;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.spell.IAmSpellGroupService;

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
@RequestMapping("am-spell-group-po")
@Api(tags = "秒杀活动管理")
public class AmSpellGroupApi {

 @Autowired
 private IAmSpellGroupService service;


}
