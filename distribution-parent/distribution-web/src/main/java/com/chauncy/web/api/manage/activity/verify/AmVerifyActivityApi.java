package com.chauncy.web.api.manage.activity.verify;

import com.chauncy.activity.registration.IAmActivityRelActivityGoodsService;
import com.chauncy.activity.view.IActivityViewService;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.supplier.activity.select.FindActivitySkuDto;
import com.chauncy.data.dto.supplier.activity.select.UpdateVerifyStatusDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.activity.GetActivitySkuInfoVo;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-07-29 20:59
 *
 * 平台审核商家报名活动
 */
@RestController
@RequestMapping("/manage/activity/verify")
@Api(tags = "平台—活动管理-活动报名审核管理")
public class AmVerifyActivityApi extends BaseApi {

    @Autowired
    private IActivityViewService activityViewService;

    @Autowired
    private IAmActivityRelActivityGoodsService service;

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
     * 修改报名的活动的状态
     *
     * @param updateVerifyStatusDto
     * @return
     */
    @PostMapping("/updateVerifyStatus")
    @ApiOperation("修改报名的活动的状态")
    public JsonViewData updateVerifyStatus(@RequestBody @ApiParam(required = true,name = "updateVerifyStatusDto",value = "修改报名的活动的状态")
                                           @Validated UpdateVerifyStatusDto updateVerifyStatusDto){
        service.updateVerifyStatus(updateVerifyStatusDto);
        return setJsonViewData(ResultCode.SUCCESS, String.format("%s成功", VerifyStatusEnum.getVerifyStatusById(updateVerifyStatusDto.getVerifyStatus()).getName()));
    }
}
