package com.chauncy.activity.coupon;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.activity.coupon.AmCouponPo;
import com.chauncy.data.dto.app.order.coupon.CanUseCouponListDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponDto;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponRelationDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchCouponListDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchDetailAssociationsDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchReceiveRecordDto;
import com.chauncy.data.dto.manage.common.FindGoodsBaseByConditionDto;
import com.chauncy.data.vo.app.activity.coupon.SelectCouponVo;
import com.chauncy.data.vo.manage.activity.coupon.*;
import com.chauncy.data.vo.manage.common.goods.GoodsBaseVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
public interface IAmCouponService extends Service<AmCouponPo> {

    /**
     * 保存优惠券--添加或者修改
     *
     * @param saveCouponDto
     * @return
     */
    SaveCouponResultVo saveCoupon(SaveCouponDto saveCouponDto);

    /**
     * 条件分页查询优惠券列表
     *
     * @param searchCouponListDto
     * @return
     */
    PageInfo<SearchCouponListVo> searchCouponList(SearchCouponListDto searchCouponListDto);

    /**
     * 批量删除优惠券
     * @param ids
     * @return
     */
    void delByIds(Long[] ids);

    /**
     * 根据优惠券查找领取记录
     * @param searchReceiveRecordDto
     * @return
     */
    PageInfo<SearchReceiveRecordVo> searchReceiveRecord(SearchReceiveRecordDto searchReceiveRecordDto);

    /**
     * 根据ID查询优惠券详情除关联商品外的信息
     * @param id
     * @return
     */
    FindCouponDetailByIdVo findCouponDetailById(Long id);

    /***
     * 条件分页获取优惠券详情下指定的商品信息
     *
     * @param searchDetailAssociationsDto
     * @return
     */
    PageInfo<SearchDetailAssociationsVo> searchDetailAssociations(SearchDetailAssociationsDto searchDetailAssociationsDto);

    /**
     * 条件获取商品的基础信息，作为给需要选择的功能的展示
     *
     * @param findGoodsBaseByConditionDto
     * @return
     */
    PageInfo<GoodsBaseVo> findGoodsBaseByCondition(FindGoodsBaseByConditionDto findGoodsBaseByConditionDto);

    /**
     * 批量删除指定商品
     *
     * @param relIds
     * @return
     */
    void delByAssociationsId(Long[] relIds);

    /**
     * @Author zhangrt
     * @Date 2019/10/9 21:31
     * @Description 这个订单当前用户能用什么优惠券
     *
     * @Update
     *
     * @Param [userId, skuIds]
     * @return com.chauncy.data.vo.app.activity.coupon.SelectCouponVo
     **/
    List<SelectCouponVo> getSelectCouPonVo(List<CanUseCouponListDto> canUseCouponListDtos);

    SaveCouponResultVo saveCouponGoods(SaveCouponDto saveCouponDto);
}
