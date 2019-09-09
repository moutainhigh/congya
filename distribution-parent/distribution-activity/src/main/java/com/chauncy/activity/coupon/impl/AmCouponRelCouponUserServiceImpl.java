package com.chauncy.activity.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.coupon.CouponUseStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.activity.coupon.AmCouponPo;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.advice.coupon.SearchReceiveCouponDto;
import com.chauncy.data.mapper.activity.coupon.AmCouponMapper;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponUserMapper;
import com.chauncy.activity.coupon.IAmCouponRelCouponUserService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.app.advice.coupon.SearchReceiveCouponVo;
import com.chauncy.data.vo.manage.message.content.BootPageVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        //判断用户是否领取过该优惠券
        AmCouponRelCouponUserPo relCouponUserPo = relCouponUserMapper.selectOne(new QueryWrapper<AmCouponRelCouponUserPo>().lambda()
                .eq(AmCouponRelCouponUserPo::getUserId,userPo.getId()).eq(AmCouponRelCouponUserPo::getCouponId,couponId));

        if (couponPo.getStock() == 0){
            throw new ServiceException(ResultCode.FAIL,String.format("优惠券:[%s]已被抢光了!",couponPo.getName()));
        }
        else {
            //是否已达到限购数量
            if (relCouponUserPo == null){
                //更新优惠券库存信息
                couponMapper.receiveForUpdate(couponId);
                //保存到用户与优惠券关联表
                relCouponUserPo = new AmCouponRelCouponUserPo();

                relCouponUserPo.setCreateBy(userPo.getTrueName()).setId(null).setCouponId(couponId).setReceiveNum(1)
                        .setUserId(userPo.getId()).setUseStatus(CouponUseStatusEnum.NOT_USED.getId());
                relCouponUserMapper.insert(relCouponUserPo);

            }else if (relCouponUserPo.getReceiveNum() < couponPo.getEveryLimitNum()){
                //更新优惠券库存信息
                couponMapper.receiveForUpdate(couponId);
                //更新用户与优惠券关联表
                relCouponUserMapper.receiveForUpdate(couponId,userPo.getId());
            }else {

                throw new ServiceException(ResultCode.FAIL,String.format("领取的优惠券:【%s】以达到上限:[%s]！",couponPo.getName(),couponPo.getEveryLimitNum()));
            }
        }

    }
}
