package com.chauncy.web.api.manage.activity.spell;


import com.chauncy.activity.integrals.IAmIntegralsService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.activity.EditEnableDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.SearchCategoryByActivityIdDto;
import com.chauncy.data.dto.manage.activity.spell.SaveSpellDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.manage.activity.SearchCategoryByActivityIdVo;
import com.chauncy.data.vo.supplier.MemberLevelInfos;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.spell.IAmSpellGroupService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 拼团活动管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/manage/activity/spell")
@Api(tags = "平台—活动管理-拼团活动管理")
public class AmSpellGroupApi extends BaseApi {

    @Autowired
    private IAmSpellGroupService service;

    @Autowired
    private IAmIntegralsService integralsService;

    /**
     * 获取分类信息
     *
     * @param searchCategoryByActivityIdDto
     * @return
     */
    @PostMapping("/searchCategory")
    @ApiOperation("获取分类信息")
    public JsonViewData<SearchCategoryByActivityIdVo> searchCategory(@RequestBody @ApiParam(required = true, name = "searchCategoryByActivityIdDto", value = "分类列表查询条件")
                                                                     @Validated SearchCategoryByActivityIdDto searchCategoryByActivityIdDto) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            map = integralsService.searchCategory(searchCategoryByActivityIdDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setJsonViewData(map);

    }

    /**
     * 获取全部会员ID和名称
     * @return
     */
    @ApiOperation("获取全部会员ID和名称")
    @GetMapping("/findAllMemberLevel")
    public JsonViewData<List<MemberLevelInfos>> findAllMemberLevel(){

        return new JsonViewData(service.findAllMemberLevel());
    }

    /**
     * 保存拼团活动信息
     * @param saveSpellDto
     * @return
     */
    @PostMapping("/saveSeckill")
    @ApiOperation("保存拼团活动信息")
    public JsonViewData saveSpell(@RequestBody @ApiParam(required = true,name="saveSpellDto",value="保存拼团活动信息")
                                    @Validated SaveSpellDto saveSpellDto){

        service.saveSpell(saveSpellDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * 条件查询拼团活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    @ApiOperation("条件查询秒杀活动信息")
    @PostMapping("/searchSpellList")
    public JsonViewData<PageInfo<SearchActivityListVo>> searchSpellList(@RequestBody @ApiParam(required = true,name="searchActivityListDto",value = "条件查询拼团活动信息")
                                                                          @Validated SearchActivityListDto searchActivityListDto){

        return setJsonViewData(service.searchSpellList(searchActivityListDto));
    }

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除活动")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true,name="ids",value = "活动ID")
                                 @PathVariable List<Long> ids){
        service.delByIds(ids);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    /**
     * 批量结束
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


}
