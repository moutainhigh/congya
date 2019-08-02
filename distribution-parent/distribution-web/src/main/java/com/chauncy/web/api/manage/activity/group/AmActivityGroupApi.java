package com.chauncy.web.api.manage.activity.group;


import com.chauncy.activity.group.IAmActivityGroupService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.activity.EditEnableDto;
import com.chauncy.data.dto.manage.activity.group.add.SaveGroupDto;
import com.chauncy.data.dto.manage.activity.group.select.SearchGroupDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.group.SearchActivityGroupVo;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 活动分组管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/manage/activity/group")
@Api(tags = "平台—活动管理-活动分组管理")
public class AmActivityGroupApi extends BaseApi {

    @Autowired
    private IAmActivityGroupService service;

    /**
     * 保存活动分组信息
     *
     * @param saveGroupDto
     * @return
     */
    @PostMapping("/saveGroup")
    @ApiOperation("保存活动分组信息")
    public JsonViewData saveGroup(@RequestBody @ApiParam(required = true,name="saveGroupDto",value = "保存活动分组信息")
                                  @Validated SaveGroupDto saveGroupDto){
        service.saveGroup(saveGroupDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * 条件查询活动分组信息
     * @param searchGroupDto
     * @return
     */
    @ApiOperation("条件查询活动分组信息")
    @PostMapping("/search")
    public JsonViewData<PageInfo<SearchActivityGroupVo>> search(@RequestBody @ApiParam(required = true,name="searchGroupDto",value = "条件查询活动分组信息")
                                                                @Validated SearchGroupDto searchGroupDto){
        return setJsonViewData(service.search(searchGroupDto));
    }

    /**
     * 批量禁用启用
     * @param enableDto
     * @return
     */
    @ApiOperation("批量禁用启用")
    @PostMapping("/editEnable")
    public JsonViewData editEnable(@RequestBody @ApiParam(required = true,name="enableDto",value="批量禁用启用")
                                   @Validated EditEnableDto enableDto){
        service.editEnable(enableDto);
        return setJsonViewData(ResultCode.SUCCESS,"修改成功");
    }

    /**
     * 批量删除活动分组
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除礼包")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true,name="ids",value = "活动分组ID集合")
                                 @PathVariable List<Long> ids){

        service.delByIds(ids);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功");
    }

}
