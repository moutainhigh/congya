package com.chauncy.web.api.manage.cooperation;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.cooperation.SaveCooperationDto;
import com.chauncy.data.dto.app.cooperation.select.SearchOperationDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.cooperation.SearchCooperationVo;
import com.chauncy.store.cooperation.ICooperationInvitationService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 合作邀请表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-30
 */
@RestController
@RequestMapping("manage/cooperation")
@Api(tags = "平台—合作邀请管理")
public class CooperationManageApi extends BaseApi {

    @Autowired
    private ICooperationInvitationService service;

    /**
     * 条件分页查询合作申请列表
     *
     * @param searchOperationDto
     * @return
     */
    @PostMapping("/searchOperation")
    @ApiOperation("条件分页查询合作申请列表")
    public JsonViewData<PageInfo<SearchCooperationVo>> searchOperation(@RequestBody @ApiParam(required = true,name="searchOperationDto",value = "条件分页查询合作申请条件Dto")
                                                                       @Validated SearchOperationDto searchOperationDto){
        return setJsonViewData(service.searchOperation(searchOperationDto));
    }

}
