package com.chauncy.web.api.manage.activity.coupon;


import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.dto.manage.common.FindGoodsBaseByConditionDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.common.goods.GoodsBaseVo;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.data.vo.supplier.MemberLevelInfos;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.coupon.ICpCouponService;

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
public class CpCouponApi {

    @Autowired
    private ICpCouponService service;

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    @ApiOperation("获取全部会员ID和名称")
    @GetMapping("/findAllMemberLevel")
    public JsonViewData<List<MemberLevelInfos>> findAllMemberLevel(){

        return new JsonViewData(service.findAllMemberLevel());
    }

    @ApiOperation("条件查询商品ID、名称、所属分类等基础信息")
    @PostMapping("/findGoodsBaseByCondition")
    public JsonViewData<PageInfo<GoodsBaseVo>> findGoodsBaseByCondition(@RequestBody @ApiParam(required = true,name = "findGoodsBaseByConditionDto",value = "条件查询商品ID、名称、所属分类等基础信息")
                                                                            @Validated FindGoodsBaseByConditionDto findGoodsBaseByConditionDto){

        return new JsonViewData(service.findGoodsBaseByCondition(findGoodsBaseByConditionDto));
    }

    @PostMapping("/find_all_category")
    @ApiOperation(value = "联动查询所有分类")
    public JsonViewData findGoodsCategoryTreeVo(){
        List<GoodsCategoryTreeVo> goodsCategoryTreeVo = goodsCategoryService.findGoodsCategoryTreeVo();
        return new JsonViewData(MyBaseTree.build(goodsCategoryTreeVo));
    }



}
