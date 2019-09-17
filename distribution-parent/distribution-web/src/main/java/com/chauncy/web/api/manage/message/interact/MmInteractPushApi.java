package com.chauncy.web.api.manage.message.interact;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.interact.add.AddPushMessageDto;
import com.chauncy.data.dto.manage.message.interact.add.AddSmsMessageDto;
import com.chauncy.data.dto.manage.message.interact.select.SearchPushDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.interact.push.InteractPushVo;
import com.chauncy.data.vo.manage.message.interact.push.UmUsersVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.chauncy.message.sms.impl.IMmSMSMessageService;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.interact.service.IMmInteractPushService;

import java.util.List;

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
    private IMmSMSMessageService smsMessageService;

    /**
     * 用户列表
     *
     * @param searchUserListDto
     * @return
     */
    @PostMapping("/list/searchUsers")
    @ApiOperation("用户列表")
    public JsonViewData<PageInfo<UmUsersVo>> searchUsers(@Validated @RequestBody SearchUserListDto searchUserListDto){

        PageInfo<UmUsersVo> umUserListVoPageInfo = service.searchUserList(searchUserListDto);
        return new JsonViewData(umUserListVoPageInfo);

    }

    /**
     * 查找所有会员等级id和名称
     *
     * @return
     */
    @GetMapping("/searchMemberLevel")
    @ApiOperation("查找所有会员等级")
    public JsonViewData<List<BaseVo>> searchMemberLevel(){
        return new JsonViewData(service.searchMemberLevel());
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

    /**
     * 条件查询推送信息
     *
     * @param searchPushDto
     * @return
     */
    @PostMapping("/searchPush")
    @ApiOperation("条件查询推送信息")
    public JsonViewData<PageInfo<InteractPushVo>> search(@RequestBody @ApiParam(required = true,name = "searchPushDto",value = "条件分页查找平台的推送信息")
                                                         @Validated SearchPushDto searchPushDto){
        return new JsonViewData(service.search(searchPushDto));
    }

    /**
     * 根据推送信息ID批量删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delPushByIds/{ids}")
    @ApiOperation("批量删除推送信息")
    public JsonViewData delPushByIds(@PathVariable Long[] ids){

        service.delPushByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * @param addSmsMessageDto
     * @return
     */
    @PostMapping("/addPushSms")
    @ApiOperation("添加推送短信")
    public JsonViewData addPushSms(@RequestBody  @Validated AddSmsMessageDto addSmsMessageDto){
        smsMessageService.saveSmsMessage(addSmsMessageDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }
}
