package com.chauncy.web.api.manage.message.advice;


import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceService;

import com.chauncy.web.base.BaseApi;

import java.util.List;

/**
 * <p>
 * 配置广告信息 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/manage/message/advice")
@Api(tags = "平台_广告运营管理")
public class MmAdviceApi extends BaseApi {

    @Autowired
    private IMmAdviceService service;

    /**
     * 获取广告位置
     *
     * @return
     */
    @ApiOperation("获取广告位置")
    @GetMapping("/findAdviceLocation")
    public JsonViewData<AdviceLocationEnum> findAdviceLocation(){

        return setJsonViewData(service.findAdviceLocation());
    }

    /**
     * 条件分页获取广告信息及其对应的详情
     *
     * @param searchAdvicesDto
     * @return
     */
    @PostMapping("/searchAdvices")
    @ApiOperation("条件分页查询广告信息")
    public JsonViewData<PageInfo<SearchAdvicesVo>> searchAdvices(@RequestBody @ApiParam(required = true,name="searchAdvicesDto",value = "分页条件查询广告信息条件")
                                        @Validated SearchAdvicesDto searchAdvicesDto){

        return setJsonViewData(service.searchAdvices(searchAdvicesDto));
    }

    /**
     * 批量删除广告
     *
     * @param idList
     * @return
     */
    @GetMapping("/deleteAdvices/{idList}")
    @ApiOperation("批量删除广告")
    public JsonViewData deleteAdvices(@ApiParam(required = true,name = "idList",value = "广告ID集合")
                                      @PathVariable List<Long> idList){
        service.deleteAdvices(idList);
        return setJsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    /**
     * 批量启用或禁用
     * @param baseUpdateStatusDto
     * @return
     */
    @PostMapping("/editEnable")
    @ApiOperation("批量启用或禁用")
    public JsonViewData editEnable(@Validated @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "启用禁用广告")
                                           BaseUpdateStatusDto baseUpdateStatusDto){
        service.editEnabledBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 保存充值入口/拼团鸭广告
     *
     * @param saveOtherAdviceDto
     * @return
     */
    @PostMapping("/saveOtherAdvice")
    @ApiOperation("保存充值入口/拼团鸭广告")
    public JsonViewData saveOtherAdvice(@RequestBody @ApiParam(required = true,name = "saveOtherAdviceDto",value = "保存充值入口/拼团鸭广告")
                                        @Validated SaveOtherAdviceDto saveOtherAdviceDto){
        service.saveOtherAdvice(saveOtherAdviceDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功！");
    }
}
