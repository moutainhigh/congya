package com.chauncy.web.api.manage.message.advice;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.advice.add.SaveSpellAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchSpellAdviceBindGoodsDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchSpellAdviceGoodsDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo;
import com.chauncy.message.advice.IMmAdviceRelSpellGoodsService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 今日必拼广告绑定参加拼团活动商品关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-09
 */
@RestController
@RequestMapping("/manage/message/advice/spell")
@Api(tags = "平台_今日必拼广告管理")
public class MmAdviceRelSpellGoodsApi extends BaseApi {

    @Autowired
    private IMmAdviceRelSpellGoodsService service;

    /**
     * @Author chauncy
     * @Date 2019-10-09 20:08
     * @Description //条件分页查询今日必拼广告需要绑定的参加拼团的商品信息
     *
     * @Update chauncy
     *
     * @param  searchSpellAdviceGoodsDto
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo>>
     **/
    @ApiOperation("条件分页查询今日必拼广告需要绑定的参加拼团的所有商品信息")
    @PostMapping("/searchSpellAdviceGoods")
    public JsonViewData<PageInfo<SearchAdviceGoodsVo>> searchSpellAdviceGoods(@RequestBody @ApiParam(required = true,name = "searchAdviceGoodsDto",value = "条件分页查询商品")
                                                                              @Validated SearchSpellAdviceGoodsDto searchSpellAdviceGoodsDto) {

        return setJsonViewData(service.searchSpellAdviceGoods(searchSpellAdviceGoodsDto));
    }

    /**
     * @Author chauncy
     * @Date 2019-10-09 21:42
     * @Description //条件分页查询今日必拼广告已经绑定的参加拼团的商品信息
     *
     * @Update chauncy
     *
     * @param  searchSpellAdviceGoodsDto
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo>>
     **/
    @ApiOperation("条件分页查询今日必拼广告已经绑定的参加拼团的商品信息")
    @PostMapping("/searchSpellAdviceBindGoods")
    public JsonViewData<PageInfo<SearchAdviceGoodsVo>> searchSpellAdviceBindGoods(@RequestBody @ApiParam(required = true,name = "searchAdviceGoodsDto",value = "条件分页查询已经被关联的商品")
                                                                                  @Validated SearchSpellAdviceBindGoodsDto searchSpellAdviceGoodsDto) {

        return setJsonViewData(service.searchSpellAdviceBindGoods(searchSpellAdviceGoodsDto));
    }

    /**
     * @Author chauncy
     * @Date 2019-10-09 22:17
     * @Description //保存今日必拼广告信息
     *
     * @Update chauncy
     *
     * @param  saveSpellAdviceDto
     * @return com.chauncy.data.vo.JsonViewData
     **/
    @ApiOperation("保存今日必拼广告信息")
    @PostMapping("/saveSpellAdvice")
    public JsonViewData saveSpellAdvice(@RequestBody @ApiParam(required = true,name = "saveSpellAdviceDto",value = "保存今日必拼广告")
                                        @Validated SaveSpellAdviceDto saveSpellAdviceDto){

        service.saveSpellAdvice(saveSpellAdviceDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }
}
