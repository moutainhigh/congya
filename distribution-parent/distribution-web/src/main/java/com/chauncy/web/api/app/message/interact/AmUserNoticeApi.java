package com.chauncy.web.api.app.message.interact;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo;
import com.chauncy.data.vo.app.message.information.interact.UserNoticeListVo;
import com.chauncy.message.interact.service.IMmUserNoticeService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/14 14:17
 */
@Api(tags = "APP_消息列表接口")
@RestController
@RequestMapping("/app/user/notice")
@Slf4j
public class AmUserNoticeApi extends BaseApi {

    @Autowired
    private IMmUserNoticeService mmUserNoticeService;

    /**
     * 获取用户消息列表未读消息数目
     *
     * @return
     */
    @GetMapping("/getUnreadNoticeNum")
    @ApiOperation(value = "获取用户消息列表未读消息数目")
    public JsonViewData<UnreadNoticeNumVo> getUnreadNoticeNum(){

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",mmUserNoticeService.getUnreadNoticeNum());
    }

    /**
     * 用户消息列表
     * @param
     * @return
     */
    @ApiOperation(value = "用户消息列表", notes = "获取用户系统通知，任务奖励，快递物流消息列表")
    @PostMapping("/searchPaging/{noticeType}")
    public JsonViewData<PageInfo<UserNoticeListVo>> searchPaging(
            @Valid @RequestBody @ApiParam(required = true,
                    name = "baseSearchPagingDto", value = "查询条件") BaseSearchPagingDto baseSearchPagingDto,
            @ApiParam(required = true, value = "1：快递物流   \n2：系统通知   \n3：任务奖励   \n", name = "noticeType")
            @PathVariable Integer noticeType) {

        PageInfo<UserNoticeListVo> smStoreBaseVoPageInfo = mmUserNoticeService.searchPaging(noticeType, baseSearchPagingDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                smStoreBaseVoPageInfo);
    }

}
