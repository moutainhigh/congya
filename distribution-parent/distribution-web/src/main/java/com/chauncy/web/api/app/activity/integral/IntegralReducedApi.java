package com.chauncy.web.api.app.activity.integral;

import com.chauncy.activity.reduced.IAmReducedService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.product.FindActivityGoodsCategoryDto;
import com.chauncy.data.dto.app.product.FindTabGoodsListDto;
import com.chauncy.data.dto.app.product.SearchActivityGoodsListDto;
import com.chauncy.data.dto.app.product.SearchReducedGoodsDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupDetailVo;
import com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo;
import com.chauncy.data.vo.app.advice.activity.HomePageActivityVo;
import com.chauncy.data.vo.app.goods.ActivityGoodsVo;
import com.chauncy.message.advice.IMmAdviceService;
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
 * @since 2019/9/24 16:12
 */
@Api(tags = "APP_活动_积分/满减")
@RestController
@RequestMapping("/app/activity/integral")
@Slf4j
public class IntegralReducedApi extends BaseApi {


    @Autowired
    private IMmAdviceService adviceService;

    @Autowired
    private IAmReducedService amReducedService;

    /**
     * @Author yeJH
     * @Date 2019/9/24 16:27
     * @Description 获取APP首页限时秒杀，积分抵现，囤货鸭，拼团鸭
     *
     * @Update yeJH
     *
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.activity.ActivityGroupListVo>>
     **/
    @GetMapping("/findHomePageActivity")
    @ApiOperation(value = "获取APP首页限时秒杀，积分抵现，囤货鸭，拼团鸭")
    public JsonViewData<HomePageActivityVo> findHomePageActivity(){

        HomePageActivityVo homePageActivityVo = adviceService.findHomePageActivity();
        return new JsonViewData(ResultCode.SUCCESS,"查找成功", homePageActivityVo);
    }

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
    @GetMapping("/findActivityGroupDetail/{relId}")
    @ApiOperation(value = "根据活动分组获取活动分组详情")
    public JsonViewData<ActivityGroupDetailVo> findActivityGroupDetail(@Valid @ApiParam(required = true,
            value = "广告与活动分组关联表id", name = "relId") @PathVariable Long relId){

        ActivityGroupDetailVo activityGroupDetailVo = adviceService.findActivityGroupDetail(relId);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功", activityGroupDetailVo);
    }

    /**
     * @Author yeJH
     * @Date 2019/9/25 16:14
     * @Description 点击选项卡获取3个热销/推荐商品
     *
     * @Update yeJH
     *
     * @param  findTabGoodsListDto
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.ActivityGoodsVo>>
     **/
    @PostMapping("/findTabGoodsList")
    @ApiOperation(value = "点击选项卡获取3个热销/推荐商品")
    public JsonViewData<List<ActivityGoodsVo>> findTabGoodsList(@Valid @RequestBody @ApiParam(required = true,
            name = "searchActivityGoodsListDto", value = "查询积分/满减活动商品列表参数")
                                                                        FindTabGoodsListDto findTabGoodsListDto){

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                adviceService.findTabGoodsList(findTabGoodsListDto));
    }

    /**
     * @Author yeJH
     * @Date 2019/9/25 16:14
     * @Description 获取积分/满减活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchActivityGoodsListDto  查询积分/满减活动商品列表参数
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.ActivityGoodsVo>>
     **/
    @PostMapping("/searchActivityGoodsList")
    @ApiOperation(value = "获取积分/满减活动商品列表")
    public JsonViewData<PageInfo<ActivityGoodsVo>> searchActivityGoodsList(@Valid @RequestBody @ApiParam(required = true,
            name = "searchActivityGoodsListDto", value = "查询积分/满减活动商品列表参数")
            SearchActivityGoodsListDto searchActivityGoodsListDto){

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                adviceService.searchActivityGoodsList(searchActivityGoodsListDto));
    }

    /**
     * @Author yeJH
     * @Date 2019/9/25 16:14
     * @Description 获取 活动分组下 或者 满减去凑单 的活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  findActivityGoodsCategoryDto
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.BaseVo>>
     **/
    @PostMapping("/findGoodsCategory")
    @ApiOperation(value = "获取 活动分组下 或者 满减去凑单 的活动商品一级分类")
    public JsonViewData<List<BaseVo>> findGoodsCategory(@Valid @RequestBody @ApiParam(required = true,
            name = "searchActivityGoodsListDto", value = "查询积分/满减活动商品列表参数")
            FindActivityGoodsCategoryDto findActivityGoodsCategoryDto) {

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                adviceService.findGoodsCategory(findActivityGoodsCategoryDto));
    }

    /**
     * @Author yeJH
     * @Date 2019/9/25 16:14
     * @Description 商品详情页，购物车页面点击去凑单获取满减商品列表
     *
     * @Update yeJH
     *
     * @param  searchReducedGoodsDto
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.BaseVo>>
     **/
    @PostMapping("/searchReducedGoods")
    @ApiOperation(value = "点击去凑单获取满减商品列表")
    public JsonViewData<PageInfo<ActivityGoodsVo>> searchReducedGoods(@Valid @RequestBody @ApiParam(required = true,
            name = "searchActivityGoodsListDto", value = "查询积分/满减活动商品列表参数")
            SearchReducedGoodsDto searchReducedGoodsDto) {

        return new JsonViewData(ResultCode.SUCCESS,"查找成功",
                amReducedService.searchReducedGoods(searchReducedGoodsDto));
    }

}
