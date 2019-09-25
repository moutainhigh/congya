package com.chauncy.web.api.app.activity.integral;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupDetailVo;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupTabVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryInfoVo;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/24 16:12
 */
@Api(tags = "APP_活动_积分")
@RestController
@RequestMapping("/app/activity/integral")
@Slf4j
public class AmIntegralMainApi extends BaseApi {


    @Autowired
    private IMmAdviceService adviceService;

    /**
     * @Author yeJH
     * @Date 2019/9/24 16:27
     * @Description 点击积分专区，满减专区获取活动分组信息
     *
     * @Update yeJH
     *
     * @param  groupType  活动分组类型 1：满减  2：积分
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo>>
     **/
    @GetMapping("/findActivityGroup/{groupType}")
    @ApiOperation(value = "点击积分专区，满减专区获取活动分组信息")
    public JsonViewData<List<ActivityGroupListVo>> findActivityGroup(@ApiParam(required = true,
            value = "活动分组类型 1：满减  2：积分", name = "groupType") @PathVariable Integer groupType){

        List<ActivityGroupListVo> activityGroupListVoList = adviceService.findActivityGroup(groupType);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功", activityGroupListVoList);
    }

    /**
     * @Author yeJH
     * @Date 2019/9/24 18:01
     * @Description 根据活动分组关联表id获取活动分组详情
     *              获取选项卡信息（满减：热销精选；积分：精选商品）
     *              获取轮播图信息
     *
     * @Update yeJH
     *
     * @param  relId  广告与活动分组关联表id
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupDetailVo>>
     **/
    @GetMapping("/findActivityGroupTab/{relId}")
    @ApiOperation(value = "根据活动分组获取选项卡信息（满减：热销精选；积分：精选商品）")
    public JsonViewData<ActivityGroupDetailVo> findActivityGroupTab(@ApiParam(required = true,
            value = "广告与活动分组关联表id", name = "relId") @PathVariable Long relId){

        ActivityGroupDetailVo activityGroupDetailVo = adviceService.findActivityGroupDetail(relId);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功", activityGroupDetailVo);
    }



}
