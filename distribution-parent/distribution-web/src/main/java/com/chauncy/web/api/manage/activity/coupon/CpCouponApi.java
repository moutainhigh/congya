package com.chauncy.web.api.manage.activity.coupon;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.dto.manage.activity.coupon.SaveCouponDto;
import com.chauncy.data.dto.manage.common.FindGoodsBaseByConditionDto;
import com.chauncy.data.valid.group.IUpdateGroup;
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
     * 联动查询所有分类
     * @return
     */
    @PostMapping("/find_all_category")
    @ApiOperation(value = "联动查询所有分类")
    public JsonViewData findGoodsCategoryTreeVo(){
        List<GoodsCategoryTreeVo> goodsCategoryTreeVo = goodsCategoryService.findGoodsCategoryTreeVo();
        return new JsonViewData(MyBaseTree.build(goodsCategoryTreeVo));
    }

    /**
     * 保存优惠券--添加或者修改
     * 当选择优惠形式为满减优惠或者固定折扣时，只能选择指定商品
     *
     * @param saveCouponDto
     * @return
     */
    @PostMapping("/saveCoupon")
    @ApiOperation("保存优惠券--添加或者修改")
    public JsonViewData saveCoupon(@RequestBody @ApiParam(required = true,name = "saveCouponDto",value = "保存优惠券信息")
                                   @Validated(IUpdateGroup.class) SaveCouponDto saveCouponDto){

        service.saveCoupon(saveCouponDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

}
