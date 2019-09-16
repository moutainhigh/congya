package com.chauncy.activity.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.coupon.CouponBeLongTypeEnum;
import com.chauncy.common.enums.app.coupon.CouponUseStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.activity.coupon.AmCouponPo;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.advice.coupon.SearchMyCouponDto;
import com.chauncy.data.dto.app.advice.coupon.SearchReceiveCouponDto;
import com.chauncy.data.mapper.activity.coupon.AmCouponMapper;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponUserMapper;
import com.chauncy.activity.coupon.IAmCouponRelCouponUserService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.app.advice.coupon.SearchMyCouponVo;
import com.chauncy.data.vo.app.advice.coupon.SearchReceiveCouponVo;
import com.chauncy.data.vo.app.advice.gift.SearchTopUpGiftVo;
import com.chauncy.data.vo.manage.message.content.BootPageVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券和用户关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmCouponRelCouponUserServiceImpl extends AbstractService<AmCouponRelCouponUserMapper, AmCouponRelCouponUserPo> implements IAmCouponRelCouponUserService {

    @Autowired
    private AmCouponRelCouponUserMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private AmCouponMapper couponMapper;

    @Autowired
    private AmCouponRelCouponUserMapper relCouponUserMapper;

    @ApiModelProperty


    /**
     * 用户领券中心
     *
     * @param searchReceiveCouponDto
     * @return
     */
    @Override
    public PageInfo<SearchReceiveCouponVo> receiveCouponCenter(SearchReceiveCouponDto searchReceiveCouponDto) {

        //获取该用户信息
        UmUserPo userPo = securityUtil.getAppCurrUser();
        //用户等级
        Integer level  = memberLevelMapper.selectById(userPo.getMemberLevelId()).getLevel();
        searchReceiveCouponDto.setLevel(level);
        //获取该会员等级下所有的优惠券
        int pageNo = searchReceiveCouponDto.getPageNo() == null ? defaultPageNo : searchReceiveCouponDto.getPageNo();
        int pageSize = searchReceiveCouponDto.getPageSize() == null ? defaultPageSize : searchReceiveCouponDto.getPageSize();

        PageInfo<SearchReceiveCouponVo> searchReceiveCouponVoPageInfo = new PageInfo<>();
        searchReceiveCouponVoPageInfo = PageHelper.startPage(pageNo,pageSize).doSelectPageInfo(
                ()->mapper.receiveCouponCenter(searchReceiveCouponDto));
        //用户已经领取的优惠券
        List<Long> receivedIds = relCouponUserMapper.selectList(new QueryWrapper<AmCouponRelCouponUserPo>().lambda()
                .eq(AmCouponRelCouponUserPo::getUserId,userPo.getId())).stream().map(a->a.getCouponId()).collect(Collectors.toList());


        searchReceiveCouponVoPageInfo.getList().forEach(a->{
            //是否已领取
            if (receivedIds.contains(a.getCouponId())){
                a.setIsReceive(true);
            }
            //是否已抢光
            if (a.getStock() == 0){
                a.setIsSnatchedOut(true);
            }

        });

        return searchReceiveCouponVoPageInfo;
    }

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    @Override
    public void receiveCoupon(Long couponId) {

        UmUserPo userPo = securityUtil.getAppCurrUser();
        //首先先判断优惠券库存是否足够
        AmCouponPo couponPo = couponMapper.getCoupon(couponId);
        if (couponPo.getStock() == 0){
            throw new ServiceException(ResultCode.FAIL,String.format("优惠券:[%s]已被抢光了!",couponPo.getName()));
        }

        //获取用户领取(type为1)该优惠券的数量
        List<AmCouponRelCouponUserPo> relCouponUserList = relCouponUserMapper.selectList(new QueryWrapper<AmCouponRelCouponUserPo>().lambda()
                .eq(AmCouponRelCouponUserPo::getUserId,userPo.getId()).eq(AmCouponRelCouponUserPo::getCouponId,couponId)
                .eq(AmCouponRelCouponUserPo::getType,CouponBeLongTypeEnum.RECEIVE.getId()));

        AmCouponRelCouponUserPo relCouponUserPo = new AmCouponRelCouponUserPo();
        /*if (ListUtil.isListNullAndEmpty(relCouponUserList)){
            //更新优惠券库存信息
            couponMapper.receiveForUpdate(couponId);
            //保存到用户与优惠券关联表
            relCouponUserPo.setCreateBy(userPo.getTrueName()).setId(null).setCouponId(couponId).setReceiveNum(1)
                    .setUserId(userPo.getId()).setUseStatus(CouponUseStatusEnum.NOT_USED.getId()).setType(CouponBeLongTypeEnum.RECEIVE.getId());
            relCouponUserMapper.insert(relCouponUserPo);
        }else if (relCouponUserList.size() < couponPo.getEveryLimitNum()){
            //更新优惠券库存信息
            couponMapper.receiveForUpdate(couponId);
            //保存到用户与优惠券关联表
            relCouponUserPo.setCreateBy(userPo.getTrueName()).setId(null).setCouponId(couponId).setReceiveNum(1)
                    .setUserId(userPo.getId()).setUseStatus(CouponUseStatusEnum.NOT_USED.getId()).setType(CouponBeLongTypeEnum.RECEIVE.getId());
            relCouponUserMapper.insert(relCouponUserPo);
        }else {
            throw new ServiceException(ResultCode.FAIL,String.format("领取的优惠券:【%s】以达到上限:[%s]！",couponPo.getName(),couponPo.getEveryLimitNum()));
        }*/

        if (!ListUtil.isListNullAndEmpty(relCouponUserList) && relCouponUserList.size() >= couponPo.getEveryLimitNum()){
            throw new ServiceException(ResultCode.FAIL,String.format("领取的优惠券:【%s】以达到上限:[%s]！",couponPo.getName(),couponPo.getEveryLimitNum()));
        }else{
            //更新优惠券库存信息
            couponMapper.receiveForUpdate(couponId);
            LocalDateTime deadLine = LocalDateTime.now().plusDays(10L);
            //保存到用户与优惠券关联表
            relCouponUserPo.setCreateBy(userPo.getTrueName()).setId(null).setCouponId(couponId).setReceiveNum(1)
                    .setUserId(userPo.getId()).setUseStatus(CouponUseStatusEnum.NOT_USED.getId())
                    .setType(CouponBeLongTypeEnum.RECEIVE.getId()).setDeadLine(deadLine);
            relCouponUserMapper.insert(relCouponUserPo);
        }

    }

    /**
     * 我的优惠券
     *
     * @return
     */
    @Override
    public PageInfo<SearchMyCouponVo> searchMyCoupon(SearchMyCouponDto searchMyCouponDto) {

        UmUserPo user = securityUtil.getAppCurrUser();

        int pageNo = searchMyCouponDto.getPageNo() == null ? defaultPageNo : searchMyCouponDto.getPageNo();
        int pageSize = searchMyCouponDto.getPageSize() == null ? defaultPageSize : searchMyCouponDto.getPageSize();

        PageInfo<SearchMyCouponVo> searchMyCouponVoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->mapper.searchMyCoupon(user.getId(),searchMyCouponDto.getIsAvailable()));

        if (searchMyCouponDto.getIsAvailable() == false) {
            searchMyCouponVoPageInfo.getList().forEach(a -> {
                if (a.getDeadLine().compareTo(LocalDate.now()) < 0 && a.getUseStatus() == CouponUseStatusEnum.NOT_USED.getId()){
                    a.setUseStatus(CouponUseStatusEnum.FAILURE.getId());
                }
            });
        }

        return searchMyCouponVoPageInfo;
    }
}
