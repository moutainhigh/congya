package com.chauncy.web.api.supplier.activity;

import com.chauncy.activity.view.IActivityViewService;
import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import com.chauncy.data.dto.supplier.activity.add.SearchAllActivitiesDto;
import com.chauncy.data.dto.supplier.activity.select.FindByIdAndTypeDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.supplier.activity.ActivityVo;
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
 * @Author cheng
 * @create 2019-07-24 15:41
 *
 * 商家报名活动管理
 */
@RestController
@RequestMapping("/suplier/activity")
@Api(tags = "商家端—报名活动管理-报名活动列表")
public class RegistrationActivityApi extends BaseApi {

    @Autowired
    private IActivityViewService activityViewService;

    /**
     *
     * 查询全部活动列表信息
     * @param searchAllActivitiesDto
     * @return
     */
    @ApiOperation("查询全部活动列表信息")
    @PostMapping("/searchAllActivities")
    public JsonViewData<PageInfo<ActivityViewPo>> searchAllActivitiesVo(@RequestBody @ApiParam(required = true,name="searchAllActivitiesDto",value="查询全部活动条件")
                                                                               @Validated SearchAllActivitiesDto searchAllActivitiesDto){

        return setJsonViewData(activityViewService.searchAllActivitiesVo(searchAllActivitiesDto));
    }

    /**
     * 查询活动详情
     * @param findByIdAndTypeDto
     * @return
     */
    @PostMapping("/findActivityDetailByIdAndType")
    @ApiOperation("查询活动详情")
    public JsonViewData<SearchActivityListVo> findActivityDetailByIdAndType(@RequestBody @ApiParam(required = true,name = "findByIdAndTypeDto",value = "商家端查询活动详情")
                                                                            @Validated FindByIdAndTypeDto findByIdAndTypeDto){

        return setJsonViewData(activityViewService.findActivityDetailByIdAndType(findByIdAndTypeDto));
    }

}
