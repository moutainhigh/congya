package com.chauncy.web.api.manage.message.interact;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.interact.add.AddPushMessageDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.interact.push.UmUsersVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.interact.service.IMmInteractPushService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 平台信息管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@RestController
@RequestMapping("/manage/message/interact")
@Api(tags = "平台—互动管理-平台信息管理")
public class MmInteractPushApi {

    @Autowired
    private IMmInteractPushService service;

    @Autowired
    private IUmUserService umUserService;

    /**
     * 用户列表
     *
     * @param searchUserListDto
     * @return
     */
    @PostMapping("/list/search")
    @ApiOperation("用户列表")
    public JsonViewData<PageInfo<UmUsersVo>> search(@Validated @RequestBody SearchUserListDto searchUserListDto){

        PageInfo<UmUsersVo> umUserListVoPageInfo = service.searchUserList(searchUserListDto);
        return new JsonViewData(umUserListVoPageInfo);

    }

    /**
     * 添加推送信息
     * @param addPushMessageDto
     * @return
     */
    @PostMapping("/addPushMessage")
    @ApiOperation("添加推送信息")
    public JsonViewData addPushMessage(@RequestBody @ApiParam(required = true,name = "addPushMessageDto",value = "添加推送消息Vo")
                                       @Validated AddPushMessageDto addPushMessageDto){
        service.addPushMessage(addPushMessageDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }
}
