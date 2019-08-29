package com.chauncy.web.api.app.home.advice;

import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-27 11:14
 *
 * app端获取广告信息
 *
 */
@Api(tags = "APP_首页_广告")
@RestController
@RequestMapping("/app/home/advice")
@Slf4j
public class AdviceInfoApi extends BaseApi {

    @Autowired
    private IMmAdviceService adviceService;

    /**
     * 获取APP首页葱鸭优选、葱鸭百货等广告位信息
     *
     * @return
     */
    @GetMapping("/getAdviceInfo")
    @ApiOperation("获取APP首页葱鸭优选、葱鸭百货等广告位信息")
    public JsonViewData<List<GetAdviceInfoVo>> getAdviceInfo(){

        return setJsonViewData(adviceService.getAdviceInfo());
    }

    /**
     * 获取首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货内部轮播图
     *
     * @return
     */
    @GetMapping("/getShuffling/{location}")
    @ApiOperation(value = "获取首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货内部轮播图",
                  notes = "YOUPIN_INSIDE_SHUFFLING--有品内部 \n" +
                          "YOUDIAN_INSIDE_SHUFFLING--有店内部 \n" +
                          "SALE_INSIDE_SHUFFLING--特卖内部 \n" +
                          "YOUXUAN_INSIDE_SHUFFLING--优选内部 \n" +
                          "BAIHUO_INSIDE_SHUFFLING--葱鸭百货内部轮播图")
    public JsonViewData<List<ShufflingVo>> getShuffling(@ApiParam(required = true,name = "广告位置",value = "location")
                                                  @PathVariable String location){

        return setJsonViewData(adviceService.getShuffling(location));
    }

    /**
     * 根据ID获取特卖、有品、主题、优选等广告选项卡
     *
     * @param adviceId
     * @return
     */
    @ApiOperation(value="根据广告ID获取特卖、有品、主题、优选等广告选项卡",notes = "获取选项卡")
    @GetMapping("/getTab/{adviceId}")
    public JsonViewData<List<BaseVo>> getTab(@PathVariable Long adviceId){

        return setJsonViewData(adviceService.getTab(adviceId));
    }

    /**
     * 根据选项卡分页获取特卖、主题、优选等选项卡关联的商品基本信息
     *
     * @param searchGoodsBaseDto
     * @return
     */
    @ApiOperation(value = "根据选项卡分页获取特卖、主题、优选等选项卡关联的商品基本信息")
    @GetMapping("/getGoodsBaseInfo")
    public JsonViewData<PageInfo<SearchGoodsBaseVo>> searchGoodsBase(@RequestBody @ApiParam(required = true,name = "searchGoodsBaseDto",value = "分页查询商品基本信息")
                                                                         @Validated SearchGoodsBaseDto searchGoodsBaseDto){

        return setJsonViewData(adviceService.searchGoodsBase(searchGoodsBaseDto));
    }



}
