package com.chauncy.web.api.app.order.evalute;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.order.evaluate.add.AddValuateDto;
import com.chauncy.data.dto.app.order.evaluate.add.SearchEvaluateDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetPersonalEvaluateDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品评价表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@RestController
@RequestMapping("/app/order/evaluate")
@Api(tags = "app_用户_商品评价")
public class OmEvaluateApi {

    @Autowired
    private IOmEvaluateService service;

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    @PostMapping("/addEvaluate")
    @ApiOperation("用户进行商品评价")
    public JsonViewData addEvaluate(@RequestBody @ApiParam(required = true,name = "addEvaluateDto",value = "用户评价商品")
                                    @Validated AddValuateDto addValuateDto){

        service.addEvaluate(addValuateDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 获取商品对应的所有评价信息
     *
     * @return
     */
    @PostMapping("/getGoodsEvaluate")
    @ApiOperation("获取商品对应的所有评价信息")
    public JsonViewData<PageInfo<GoodsEvaluateVo>> getGoodsEvaluate(@RequestBody @ApiParam(required = true,name = "searchEvaluateDto",value = "获取商品对应的所有评价信息")
                                                                    @Validated SearchEvaluateDto searchEvaluateDto){

        return new JsonViewData(service.getGoodsEvaluate(searchEvaluateDto));
    }

    /**
     * 用户获取已经评价的商品评价信息
     * @param getPersonalEvaluateDto
     * @return
     */
    @PostMapping("/getPersonalEvaluate")
    @ApiOperation("用户获取已经评价的商品评价信息")
    public JsonViewData<PageInfo<GoodsEvaluateVo>> getPersonalEvaluate(@RequestBody @ApiParam(required = true,name = "getPersonalEvaluate",value = "用户获取已经评价的商品评价信息")
                                                              @Validated GetPersonalEvaluateDto getPersonalEvaluateDto){

        return new JsonViewData(service.getPersonalEvaluate(getPersonalEvaluateDto));
    }
}
