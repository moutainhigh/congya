package com.chauncy.web.api.manage.activity.coupon;


import com.chauncy.activity.coupon.IAmCouponService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.dto.manage.activity.EditEnableDto;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponDto;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponRelationDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchCouponListDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchDetailAssociationsDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchReceiveRecordDto;
import com.chauncy.data.dto.manage.common.FindGoodsBaseByConditionDto;
import com.chauncy.data.dto.manage.good.select.SearchGoodCategoryDto;
import com.chauncy.data.dto.manage.good.select.SearchThirdCategoryDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.activity.coupon.*;
import com.chauncy.data.vo.manage.common.goods.GoodsBaseVo;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.product.SearchThirdCategoryVo;
import com.chauncy.data.vo.supplier.MemberLevelInfos;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 优惠券 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
@RestController
@RequestMapping("manage/activity/coupon")
@Api(tags = "平台—活动管理-优惠券管理")
public class AmCouponApi extends BaseApi {

    @Autowired
    private IAmCouponService service;

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    /**
     * 获取全部会员ID和名称
     * @return
     */
    @ApiOperation("获取全部会员ID和名称")
    @GetMapping("/findAllMemberLevel")
    public JsonViewData<List<MemberLevelInfos>> findAllMemberLevel(){

        return new JsonViewData(service.findAllMemberLevel());
    }

    /**
     * 条件查询商品ID、名称、所属分类等基础信
     * @param findGoodsBaseByConditionDto
     * @return
     */
    @ApiOperation("条件查询商品ID、名称、所属分类等基础信息")
    @PostMapping("/findGoodsBaseByCondition")
    public JsonViewData<PageInfo<GoodsBaseVo>> findGoodsBaseByCondition(@RequestBody @ApiParam(required = true,name = "findGoodsBaseByConditionDto",value = "条件查询商品ID、名称、所属分类等基础信息")
                                                                            @Validated FindGoodsBaseByConditionDto findGoodsBaseByConditionDto){

        return new JsonViewData(service.findGoodsBaseByCondition(findGoodsBaseByConditionDto));
    }

    /**
     * 联动查询所有二级分类
     * @return
     */
    @PostMapping("/find_all_second_category")
    @ApiOperation(value = "联动查询所有二级分类")
    public JsonViewData findGoodsCategoryTreeVo(){
        List<GoodsCategoryTreeVo> goodsCategoryTreeVo = goodsCategoryService.findGoodsCategoryTreeVo();
        return new JsonViewData(MyBaseTree.build(goodsCategoryTreeVo));
    }

    /**
     * @Author chauncy
     * @Date 2019-10-07 15:50
     * @Description //条件分页查询所有第三级分类信息
     *
     * @Update chauncy
     *
     * @param  searchThirdCategoryDto
     * @return com.chauncy.data.vo.JsonViewData<SearchThirdCategoryVo>
     **/
    @PostMapping("/searchThirdCategory")
    @ApiOperation(value = "条件分页查询所有第三级分类信息")
    public JsonViewData<PageInfo<SearchThirdCategoryVo>> searchThirdCategory(@RequestBody @ApiParam(required = true,name="",value = "")
                                                                   @Validated SearchThirdCategoryDto searchThirdCategoryDto){
        return setJsonViewData(goodsCategoryService.searchThirdCategory(searchThirdCategoryDto));
    }

    @PostMapping("/searchCategory")
    @ApiOperation(value = "查看商品分类列表")
    public JsonViewData<SearchCategoryVo> searchCategory(@RequestBody @Valid @ApiParam(required = true, name = "baseSearchDto", value = "分类列表查询条件")
                                                         SearchGoodCategoryDto categoryDto) {
        Integer pageNo=categoryDto.getPageNo()==null?defaultPageNo:categoryDto.getPageNo();
        Integer pageSize=categoryDto.getPageSize()==null?defaultPageSize:categoryDto.getPageSize();
        return setJsonViewData(goodsCategoryService.searchList(categoryDto,pageNo,pageSize));
    }

    /**
     * 保存优惠券--添加或者修改
     * 当选择优惠形式为满减优惠或者固定折扣时，只能选择指定商品
     *
     * @param saveCouponDto
     * @return
     */
    @PostMapping("/saveCoupon")
    @ApiOperation("保存优惠券--添加或者修改,当ID为0时为添加，不为0时为修改")
    public JsonViewData<SaveCouponResultVo> saveCoupon(@RequestBody @ApiParam(required = true,name = "saveCouponDto",value = "保存优惠券信息")
                                   @Validated SaveCouponDto saveCouponDto){


        return new JsonViewData(service.saveCoupon(saveCouponDto));
    }

    /**
     * 条件分页查询优惠券列表
     *
     * @param searchCouponListDto
     * @return
     */
    @PostMapping("/searchCouponList")
    @ApiOperation("条件分页查询优惠券列表")
    public JsonViewData<PageInfo<SearchCouponListVo>> searchCouponList(@RequestBody @ApiParam(required = true,name = "searchCouponListDto",value = "条件查询优惠券列表")
                                     @Validated SearchCouponListDto searchCouponListDto){

        return new JsonViewData(service.searchCouponList(searchCouponListDto));
    }

    /**
     * 批量启用或禁用
     * @param baseUpdateStatusDto
     * @return
     */
    @PostMapping("/editEnable")
    @ApiOperation("批量启用或禁用")
    public JsonViewData editEnable(@Validated @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "资讯id、修改的状态值")
                                               EditEnableDto baseUpdateStatusDto){
        service.editEnable(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 批量删除优惠券
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除优惠券", notes = "根据id批量删除")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                 @PathVariable Long[] ids) {
        service.delByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    /**
     * 批量删除指定商品
     *
     * @param relIds
     * @return
     */
    @ApiOperation(value = "批量删除指定商品/分类关联表", notes = "根据relId批量删除")
    @GetMapping("/delByAssociationsId/{relIds}")
    public JsonViewData delByAssociationsId(@ApiParam(required = true, name = "relIds", value = "指定商品/分类ID")
                                 @PathVariable Long[] relIds) {
        service.delByAssociationsId(relIds);
        return new JsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    /**
     * 根据优惠券查找领取记录
     * @param searchReceiveRecordDto
     * @return
     */
    @ApiOperation("根据优惠券id查找领取记录")
    @PostMapping("/searchReceiveRecord")
    public JsonViewData<PageInfo<SearchReceiveRecordVo>> searchReceiveRecord(@RequestBody @ApiParam(required = true, name = "searchReceiveRecordDto", value = "根据优惠券id查找领取记录")
                                                                       @Validated SearchReceiveRecordDto searchReceiveRecordDto){
        return new JsonViewData(service.searchReceiveRecord(searchReceiveRecordDto));
    }

    //TODO 查找优惠券详情,分开两个接口获取，findCouponDetailById()获取除关联商品外的信息，另一个接口查询关联商品，需要不同范围的条件分页查询

    /**
     * 根据ID查询优惠券详情除关联商品外的信息
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询优惠券详情除关联商品外的信息")
    @GetMapping("/findCouponDetailById/{id}")
    public JsonViewData<FindCouponDetailByIdVo> findCouponDetailById(@ApiParam(required = true,name="id",value="优惠券ID")
                                                                     @PathVariable Long id){
        return new JsonViewData<FindCouponDetailByIdVo>(service.findCouponDetailById(id));
    }

    /***
     * 条件分页获取优惠券详情下指定的商品信息
     *
     * @param searchDetailAssociationsDto
     * @return
     */
    @ApiOperation("条件分页获取优惠券详情下指定的商品信息")
    @PostMapping("/searchDetailAssociations")
    public JsonViewData<PageInfo<SearchDetailAssociationsVo>> searchDetailAssociations(@RequestBody @ApiParam(required = true, name = "分页查询优惠券关联商品信息Dto", value = "searchDetailAssociationsDto")
                                                       @Validated SearchDetailAssociationsDto searchDetailAssociationsDto){

        return new JsonViewData<PageInfo<SearchDetailAssociationsVo>>(service.searchDetailAssociations(searchDetailAssociationsDto));
    }

    /**
     * @Author chauncy
     * @Date 2020-03-01 13:48
     * @Description //保存优惠券关联信息
     *
     * @Update chauncy
     *
     * @param  saveCouponGoodsDto
     * @return com.chauncy.data.vo.JsonViewData<com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponRelationDto>
     **/
    @ApiOperation("保存优惠券关联信息")
    @PostMapping("/saveCouponGoods")
    public JsonViewData<SaveCouponRelationDto> saveCouponGoods(@RequestBody @ApiParam(required = true,name = "保存优惠券关联商品",value = "saveCouponGoodsDto")
                                                            @Validated SaveCouponRelationDto saveCouponGoodsDto){
        service.saveCouponGoods(saveCouponGoodsDto);

        return new JsonViewData(ResultCode.SUCCESS,"保存成功");
    }



}
