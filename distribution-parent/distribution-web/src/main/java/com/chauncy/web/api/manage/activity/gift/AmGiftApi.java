package com.chauncy.web.api.manage.activity.gift;


import com.chauncy.activity.gift.IAmGiftService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.activity.EditEnableDto;
import com.chauncy.data.dto.manage.activity.gift.add.SaveGiftDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchBuyGiftRecordDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchCouponDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchGiftDto;
import com.chauncy.data.dto.manage.activity.gift.select.SearchReceiveGiftRecordDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.gift.FindGiftVo;
import com.chauncy.data.vo.manage.activity.gift.SearchBuyGiftRecordVo;
import com.chauncy.data.vo.manage.activity.gift.SearchGiftListVo;
import com.chauncy.data.vo.manage.activity.gift.SearchReceiveGiftRecordVo;
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
 * 礼包表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("manage/activity/gift")
@Api(tags = "平台—活动管理-礼包管理")
public class AmGiftApi extends BaseApi {

    @Autowired
    private IAmGiftService service;


    /**
     * 保存礼包
     * @param saveGiftDto
     * @return
     */
    @PostMapping("/saveGift")
    @ApiOperation("保存礼包")
    public JsonViewData saveGift(@RequestBody @ApiParam(required = true,name="saveGiftDto",value="保存礼包")
                                 @Validated SaveGiftDto saveGiftDto){
        service.saveGift(saveGiftDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * 批量删除礼包关联的优惠券
     *
     * @param relIds
     * @return
     */
    @ApiOperation("批量删除礼包关联的优惠券")
    @GetMapping("/delRelCouponByIds/{relIds}")
    public JsonViewData delRelCouponByIds(@ApiParam(required = true,name="relIds",value = "记录id")
                                          @PathVariable List<Long> relIds){

        service.delRelCouponByIds(relIds);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    /**
     * 根据ID查找礼包信息
     * 不需要了，在多条件分页获取信息处理了
     * @param id
     * @return
     */
    @ApiOperation("根据ID查找礼包信息")
    @GetMapping("/findById/{id}")
    public JsonViewData<FindGiftVo> findById(@ApiParam(required = true,name="id",value = "礼包id")
                                 @PathVariable Long id){
        return setJsonViewData(service.findById(id));
    }

    /**
     * 多条件分页获取礼包信息
     * @param searchGiftDto
     * @return
     */
    @ApiOperation("多条件分页获取礼包信息")
    @PostMapping("/searchGiftList")
    public JsonViewData<PageInfo<SearchGiftListVo>> searchGiftList(@RequestBody @ApiParam(required = true,name = "searchGiftDto",value = "多条件分页查询礼品列表")
                                                                   @Validated SearchGiftDto searchGiftDto){
        return setJsonViewData(service.searchGiftList(searchGiftDto));
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
     * 批量删除礼包
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除礼包")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true,name="ids",value = "礼包id")
                                          @PathVariable List<Long> ids){

        service.delByIds(ids);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    @ApiOperation("判断用户是否领取过新人礼包")
    @GetMapping("/isReceive")
    public JsonViewData isReceive(){
        return setJsonViewData(service.isReceive());
    }

    /**
     * 新人领取礼包
     * @param giftId
     * @return
     */
    @ApiOperation("新人领取礼包")
    @GetMapping("/receiveGift/{giftId}")
    public JsonViewData receiveGift(@ApiParam(required = true,name = "giftId",value = "礼包ID")
                                    @PathVariable Long giftId){
        service.receiveGift(giftId);
        return setJsonViewData(ResultCode.SUCCESS,"恭喜你,领取成功!");
    }

    /**
     * 多条件查询新人礼包领取记录
     *
     * @param searchReceiveRecordDto
     * @return
     */
    @ApiOperation("多条件查询新人礼包领取记录")
    @PostMapping("/searchReceiveRecord")
    public JsonViewData<PageInfo<SearchReceiveGiftRecordVo>> searchReceiveRecord(@RequestBody @ApiParam(required = true,name = "searchReceiveRecordDto",value = "查询条件")
                                                        @Validated SearchReceiveGiftRecordDto searchReceiveRecordDto){

        return setJsonViewData(service.searchReceiveRecord(searchReceiveRecordDto));
    }

    /**
     * 多条件查询购买礼包记录
     *
     * @param searchBuyGiftRecordDto
     * @return
     */
    @PostMapping("/searchBuyGiftRecord")
    @ApiOperation("多条件查询购买礼包记录")
    public JsonViewData<PageInfo<SearchBuyGiftRecordVo>> searchBuyGiftRecord(@RequestBody @ApiParam(required = true,name = "searchBuyGiftRecordDto",value = "查询购买礼包记录")
                                                        @Validated SearchBuyGiftRecordDto searchBuyGiftRecordDto){

        return setJsonViewData(service.searchBuyGiftRecord(searchBuyGiftRecordDto));
    }

    /**
     * 分页查询优惠券
     *
     * @param searchCouponDto
     * @return
     */
    @PostMapping("/searchCoupon")
    @ApiOperation("分页查询优惠券")
    public JsonViewData<PageInfo<BaseVo>> searchCoupon(@RequestBody @ApiParam(required = true,name = "searchCouponDto",value = "查询购买礼包记录")
                                                       @Validated SearchCouponDto searchCouponDto){
        return setJsonViewData(service.searchCoupon(searchCouponDto));
    }

}
