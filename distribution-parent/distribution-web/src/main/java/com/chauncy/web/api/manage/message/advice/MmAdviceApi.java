package com.chauncy.web.api.manage.message.advice;


import com.chauncy.common.enums.app.advice.AdviceLocationEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveBaiHuoMiddleAdviceDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveClassificationAdviceDto;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAssociatedClassificationDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchInformationCategoryDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.ClassificationVo;
import com.chauncy.data.vo.manage.message.advice.FindBaiHuoAdviceVo;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.product.service.IPmGoodsCategoryService;
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

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

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
     * 批量启用或禁用,同一个广告位只能有一个是启用状态
     *
     * @param baseUpdateStatusDto
     * @return
     */
    @PostMapping("/editEnable")
    @ApiOperation("启用或禁用,没有批量启用/禁用")
    public JsonViewData editEnable(@Validated @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "启用禁用广告")
                                           BaseUpdateStatusDto baseUpdateStatusDto){
        service.editEnabled(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 保存充值入口/拼团鸭广告
     *
     * @param saveOtherAdviceDto
     * @return
     */
    @PostMapping("/saveOtherAdvice")
    @ApiOperation("查找广告位为保存充值入口、拼团鸭广告、个人中心顶部背景图、邀请有礼")
    public JsonViewData saveOtherAdvice(@RequestBody @ApiParam(required = true,name = "saveOtherAdviceDto",value = "保存充值入口/拼团鸭广告")
                                        @Validated SaveOtherAdviceDto saveOtherAdviceDto){
        service.saveOtherAdvice(saveOtherAdviceDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功！");
    }

    /**
     * 保存商品推荐分类用到
     *
     * @return
     */
    @PostMapping("/findAllCategory")
    @ApiOperation(value = "联动查询所有商品分类")
    public JsonViewData findGoodsCategoryTreeVo(){
        List<GoodsCategoryTreeVo> goodsCategoryTreeVo = goodsCategoryService.findGoodsCategoryTreeVo();
        return setJsonViewData(MyBaseTree.build(goodsCategoryTreeVo));
    }

    /**
     * 分页查找需要广告位置为资讯分类推荐需要关联的资讯分类
     *
     * @return
     */
    @ApiOperation("分页查找需要广告位置为资讯分类推荐需要关联的资讯分类")
    @PostMapping("/searchInformationCategory")
    public JsonViewData<PageInfo<BaseVo>> searchInformationCategory(@RequestBody @ApiParam(required = true,name = "searchInformationCategoryDto",value = "分页查找需要广告位置为资讯分类推荐需要关联的资讯分类")
                                                                        @Validated SearchInformationCategoryDto searchInformationCategoryDto){

        return setJsonViewData(service.searchInformationCategory(searchInformationCategoryDto));
    }

    /**
     * 添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐
     *
     * @param saveClassificationAdviceDto
     * @return
     */
    @ApiOperation("添加推荐的分类:葱鸭百货分类推荐/资讯分类推荐")
    @PostMapping("/saveClassificationAdvice")
    public JsonViewData saveClassificationAdvice(@RequestBody @ApiParam(required = true,name = "saveClassificationAdviceDto",value = "保存推荐的分类")
                                                @Validated SaveClassificationAdviceDto saveClassificationAdviceDto){
        service.saveGoodsCategoryAdvice(saveClassificationAdviceDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
    }

    /**
     * 条件分页查询获取广告位置为葱鸭百货分类推荐/资讯分类推荐已经关联的分类信息
     *
     * @param searchAssociatedClassificationDto
     * @return
     */
    @PostMapping("/searchAssociatedClassification")
    @ApiOperation("条件分页查询获取广告位置为葱鸭百货分类推荐/资讯分类推荐已经关联的分类信息")
    public JsonViewData<PageInfo<ClassificationVo>> searchAssociatedClassification(@RequestBody @ApiParam(required = true,name = "searchAssociatedClassificationDto"
            ,value = "条件分页查询获取广告位置为葱鸭百货分类推荐/资讯分类推荐已经关联的分类信息")
            @Validated SearchAssociatedClassificationDto searchAssociatedClassificationDto){

        return setJsonViewData(service.searchAssociatedClassification(searchAssociatedClassificationDto));
    }

    /**
     * @Author chauncy
     * @Date 2019-10-08 21:41
     * @Description //保存百货中部广告
     *
     * @Update chauncy
     *
     * @param  saveBaiHuoMiddleAdviceDto
     * @return com.chauncy.data.vo.JsonViewData
     **/
//    public JsonViewData saveBaiHuoMiddleAdvice(@RequestBody @ApiParam(required = true,name = "saveBaiHuoMiddleAdviceDto",value = "保存百货中部广告")
//                                               @Validated SaveBaiHuoMiddleAdviceDto saveBaiHuoMiddleAdviceDto){
//
//        service.saveBaiHuoMiddleAdvice(saveBaiHuoMiddleAdviceDto);
//        return setJsonViewData(ResultCode.SUCCESS,"保存成功");
//    }



    /**
     * 查找广告位为葱鸭百货的所有广告
     *
     * @return
     */
//    @PostMapping("/findAdvice")
//    @ApiOperation("查找广告位为葱鸭百货的所有广告")
//    public JsonViewData<List<FindBaiHuoAdviceVo>> findAdvice(){
//
//        return setJsonViewData(service.findAdvice());
//    }
}
