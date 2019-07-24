package com.chauncy.web.api.manage.activity.reduced;


import com.chauncy.activity.integrals.IAmIntegralsService;
import com.chauncy.activity.reduced.IAmReducedService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.activity.SearchCategoryByActivityIdDto;
import com.chauncy.data.dto.manage.activity.reduced.add.SaveReducedDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.SearchCategoryByActivityIdVo;
import com.chauncy.data.vo.manage.activity.group.FindActivityGroupsVo;
import com.chauncy.data.vo.supplier.MemberLevelInfos;
import com.chauncy.web.base.BaseApi;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 满减活动管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@RestController
@RequestMapping("am-reduced-po")
@Api(tags = "满减活动管理")
public class AmReducedApi extends BaseApi {

    @Autowired
    private IAmReducedService service;

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
    public JsonViewData<SearchCategoryByActivityIdVo> searchCategory(@RequestBody @ApiParam(required = true, name = "baseSearchDto", value = "分类列表查询条件")
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
     * 获取全部可用的分组
     * @return
     */
    @ApiOperation("获取全部可用的分组")
    @GetMapping("/findAllActivityGroup")
    public JsonViewData<List<FindActivityGroupsVo>> findAllActivityGroup(){

        return new JsonViewData(service.FindAllActivityGroup());
    }

    /**
     * 保存满减活动信息
     * @param saveReducedDto
     * @return
     */
    @PostMapping("/saveReduced")
    @ApiOperation("保存满减活动信息")
    public JsonViewData saveReduced(@RequestBody @ApiParam(required = true,name="saveReducedDto",value="保存满减活动信息")
                                    @Validated SaveReducedDto saveReducedDto){

        service.saveReduced(saveReducedDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

}
