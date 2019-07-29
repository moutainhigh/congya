package com.chauncy.web.api.supplier.activity;

import com.alipay.api.domain.Activity;
import com.chauncy.activity.registration.IAmActivityRelActivityGoodsService;
import com.chauncy.activity.view.IActivityViewService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import com.chauncy.data.dto.supplier.activity.add.SaveRegistrationDto;
import com.chauncy.data.dto.supplier.activity.add.SearchAllActivitiesDto;
import com.chauncy.data.dto.supplier.activity.delete.CancelRegistrationDto;
import com.chauncy.data.dto.supplier.activity.select.FindActivitySkuDto;
import com.chauncy.data.dto.supplier.activity.select.FindByIdAndTypeDto;
import com.chauncy.data.dto.supplier.activity.select.SearchAssociatedGoodsDto;
import com.chauncy.data.dto.supplier.activity.select.SearchSupplierActivityDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.supplier.activity.*;
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

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-24 15:41
 *
 * 商家报名活动管理
 */
@RestController
@RequestMapping("/suplier/activity")
@Api(tags = "商家端_报名活动管理_报名活动列表")
public class RegistrationActivityApi extends BaseApi {

    @Autowired
    private IActivityViewService activityViewService;

    @Autowired
    private IAmActivityRelActivityGoodsService service;

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

    /**
     * 条件查询需要被选参与活动的商品
     *
     * @param searchAssociatedGoodsDto
     * @return
     */
    @ApiOperation("条件查询需要被选参与活动的商品")
    @PostMapping("/searchAssociatedGoods")
    public JsonViewData<PageInfo<SearchAssociatedGoodsVo>> searchAssociatedGoods(@RequestBody @ApiParam(required = true,name = "查询需要被选参与活动的商品的条件",value ="associatedGoodDto")

                                                                     @Validated SearchAssociatedGoodsDto searchAssociatedGoodsDto){
        return setJsonViewData(service.searchAssociatedGoods(searchAssociatedGoodsDto));
    }

    /**
     * 查找对应的商品及其对应的sku信息
     * @param findActivitySkuDto
     * @return
     */
    @ApiOperation("查找对应的商品及其对应的sku信息")
    @PostMapping("/findActivityGoodsAndSku")
    public JsonViewData<GetActivitySkuInfoVo> findActivitySku(@RequestBody @ApiParam(required = true,name="findActivitySkuDto",value = "查找参加活动的商品的sku信息条件")
                                                       @Validated FindActivitySkuDto findActivitySkuDto){

        return setJsonViewData(service.findActivitySku(findActivitySkuDto));
    }

    /**
     * 判断商品是否符合不能参加在同一时间段内的活动、不符合活动要求
     * @param isConformDto
     * @return
     */
    @ApiOperation("判断商品是否符合不能参加在同一时间段内的活动、不符合活动要求")
    @PostMapping("/isConform")
    public JsonViewData isConform(@RequestBody @ApiParam(required = true,name="findActivitySkuDto",value = "查找参加活动的商品的sku信息条件")
                                  @Validated FindActivitySkuDto isConformDto){
        service.isComform(isConformDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 保存商家端的报名活动信息
     *
     * @param saveRegistrationDto
     * @return
     */
    @PostMapping("/saveRegistration")
    @ApiOperation("保存商家端的报名活动信息")
    public JsonViewData saveRegistration(@RequestBody @ApiParam(required = true,name = "saveRegistrationDto",value = "保存商家报名活动的信息")
                                     @Validated SaveRegistrationDto saveRegistrationDto){
        service.saveRegistration(saveRegistrationDto);
        return setJsonViewData(ResultCode.SUCCESS,"操作成功！");
    }

    /**
     * 商家端查找参与的活动
     *
     * @param searchSupplierActivityDto
     * @return
     */
    @ApiOperation("商家端查找参与的活动")
    @PostMapping("/searchSupplierActivity")
    public JsonViewData<PageInfo<SearchSupplierActivityVo>> searchSupplierActivity(@RequestBody @ApiParam(required = true,name="searchSupplierActivity",value="查找商家端参与的活动")
                                               @Validated SearchSupplierActivityDto searchSupplierActivityDto){

        return setJsonViewData(service.searchSupplierActivity(searchSupplierActivityDto));
    }

    /**
     * 商家端取消活动报名
     * @param cancelRegistrationDtos
     * @return
     */
    @PostMapping("/cancelRegistration")
    @ApiOperation("商家端取消活动报名")
    public JsonViewData cancelRegistration(@RequestBody @ApiParam(required = true,name="cancelRegistrationDto",value = "取消活动报名")
                                           @Validated List<CancelRegistrationDto> cancelRegistrationDtos){

        service.cancelRegistration(cancelRegistrationDtos);
        return setJsonViewData(ResultCode.SUCCESS,"取消成功");
    }
}
