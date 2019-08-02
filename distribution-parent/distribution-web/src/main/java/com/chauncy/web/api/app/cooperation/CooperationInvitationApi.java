package com.chauncy.web.api.app.cooperation;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.cooperation.SaveCooperationDto;
import com.chauncy.data.vo.JsonViewData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.store.cooperation.ICooperationInvitationService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 合作邀请表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-30
 */
@RestController
@RequestMapping("app/cooperation")
@Api(tags = "APP_合作邀请")
public class CooperationInvitationApi extends BaseApi {

    @Autowired
    private ICooperationInvitationService service;

    /**
     * 保存用户合作邀请信息
     *
     * @param saveCooperationDto
     * @return
     */
    @ApiOperation("保存用户合作邀请信息")
    @PostMapping("/saveCooperation")
    public JsonViewData saveCooperation(@RequestBody @ApiParam(required = true,name = "saveCooperationDto",value = "保存用户合作邀请信息")
                                        @Validated SaveCooperationDto saveCooperationDto){
        service.saveCooperation(saveCooperationDto);
        return setJsonViewData(ResultCode.SUCCESS,"提交成功!");
    }

}
