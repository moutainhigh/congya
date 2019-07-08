package com.chauncy.web.api.manage.user.list;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.dto.manage.user.update.UpdateUserDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户标签 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Api(tags = "平台_App用户管理_用户列表")
@RestController
@RequestMapping("/manage/user")
public class UmUserListApi extends BaseApi {

    @Autowired
    private IUmUserService service;


    @PostMapping("/list/search")
    @ApiOperation("用户列表")
    public JsonViewData<PageInfo<UmUserListVo>> search(@Validated @RequestBody SearchUserListDto searchUserListDto){

        PageInfo<UmUserListVo> umUserListVoPageInfo = service.searchUserList(searchUserListDto);
        return setJsonViewData(umUserListVoPageInfo);

    }

    @PostMapping("/list/updateStatus")
    @ApiOperation(value = "批量修改用户状态")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editStatus(@Valid @RequestBody BaseUpdateStatusDto baseUpdateStatusDto) {

        service.editEnabledBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改用户状态成功");
    }

    @PostMapping("/view/{id}")
    @ApiOperation(value = "查询用户信息详情")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData view(@PathVariable Long id) {

        /*service.editEnabledBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改用户状态成功");*/
        return setJsonViewData(service.getUserDetailVo(id));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData update(@Validated @RequestBody UpdateUserDto updateUserDto) {

        return setJsonViewData(service.updateUmUser(updateUserDto,getUser().getUsername()));
    }

    @PostMapping("/getRelUser/{id}")
    @ApiOperation(value = "查询关联用户")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData getRelUser(@PathVariable Long id) {

        return setJsonViewData(service.getRelUsers(id));
    }




}
