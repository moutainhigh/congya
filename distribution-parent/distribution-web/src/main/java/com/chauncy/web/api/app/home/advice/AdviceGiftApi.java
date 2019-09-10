package com.chauncy.web.api.app.home.advice;

import com.chauncy.activity.gift.IAmGiftOrderService;
import com.chauncy.activity.gift.IAmGiftService;
import com.chauncy.data.dto.app.advice.goods.select.SearchTopUpGiftDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.gift.SearchTopUpGiftVo;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cheng
 * @create 2019-09-09 22:31
 */
@Api(tags = "APP_首页_经验包")
@RestController
@RequestMapping("/app/home/gift")
@Slf4j
public class AdviceGiftApi extends BaseApi {

    @Autowired
    private IAmGiftService giftService;

    @Autowired
    private IAmGiftOrderService giftOrderService;

    /**
     * 分页查询购买经验包信息
     *
     * @return
     */
    @PostMapping("/searchTopUpGift")
    @ApiOperation("分页查询购买经验包信息")
    public JsonViewData<PageInfo<SearchTopUpGiftVo>> searchTopUPGift(@RequestBody @ApiParam(required = true,name = "",value = "分页查询购买经验包信息")
                                                                     @Validated SearchTopUpGiftDto searchTopUpGiftDto){

        return setJsonViewData(giftService.searchTopUPGift(searchTopUpGiftDto));
    }

    /**
     * 购买礼包
     *
     * @param giftId
     * @return
     */
    @GetMapping("/buyGift/{giftId}")
    @ApiOperation("购买礼包,返回购买礼包的订单ID")
    public JsonViewData<Long> buyGift(@PathVariable Long giftId){

        return setJsonViewData(giftOrderService.buyGift(giftId));
    }
}
