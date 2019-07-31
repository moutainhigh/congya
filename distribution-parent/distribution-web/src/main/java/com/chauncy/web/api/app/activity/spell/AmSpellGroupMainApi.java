package com.chauncy.web.api.app.activity.spell;


import com.chauncy.activity.spell.IAmSpellGroupMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.spell.IAmSpellGroupMainService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 拼团单号表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
@Api (tags = "APP_活动_拼团")
@RestController
@RequestMapping ("/app/activity/spell")
@Slf4j
public class AmSpellGroupMainApi extends BaseApi {

    @Autowired
    private IAmSpellGroupMainService service;

    @Autowired
    private IAmSpellGroupMemberService spellGroupMemberService;


}
