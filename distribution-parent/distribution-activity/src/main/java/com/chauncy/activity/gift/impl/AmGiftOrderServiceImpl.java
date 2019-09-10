package com.chauncy.activity.gift.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.app.coupon.CouponBeLongTypeEnum;
import com.chauncy.common.enums.app.coupon.CouponUseStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftRelGiftCouponPo;
import com.chauncy.data.domain.po.test.UserPO;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponUserMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftOrderMapper;
import com.chauncy.activity.gift.IAmGiftOrderService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.activity.gift.AmGiftRelGiftCouponMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IUmUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 礼包订单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmGiftOrderServiceImpl extends AbstractService<AmGiftOrderMapper, AmGiftOrderPo> implements IAmGiftOrderService {

    @Autowired
    private AmGiftOrderMapper mapper;

    @Autowired
    private AmGiftMapper giftMapper;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private IUmUserService userService;

    @Autowired
    private AmGiftRelGiftCouponMapper relGiftCouponMapper;

    @Autowired
    private AmCouponRelCouponUserMapper relCouponUserMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 购买礼包
     *
     * @param giftId
     * @return
     */
    @Override
    public Long buyGift(Long giftId) {

        UmUserPo userPo = securityUtil.getAppCurrUser();

        //获取礼包相关信息
        AmGiftPo giftPo = giftMapper.selectById(giftId);
        AmGiftOrderPo giftOrderPo = new AmGiftOrderPo();

        BeanUtils.copyProperties(giftPo,giftOrderPo);
        giftOrderPo.setGiftId(giftPo.getId()).setGiftName(giftPo.getName()).setUserId(userPo.getId()).setId(null)
                .setPayStatus(PayOrderStatusEnum.NEED_PAY.getId()).setCreateBy(userPo.getTrueName()).setUpdateBy(null);

        mapper.insert(giftOrderPo);

        return giftOrderPo.getId();
    }

    //TODO junhao支付充值礼包成功后调用
    @Override
    public void payPost(Long orderId){

        //获取订单详情
        AmGiftOrderPo giftOrderPo = mapper.selectById(orderId);
        //获取礼包ID
        Long giftId = giftOrderPo.getGiftId();
        //获取礼包详情
        AmGiftPo giftPo = giftMapper.selectById(giftId);
        //获取用户ID
        Long userId = giftOrderPo.getUserId();
        //获取用户详情
        UmUserPo userPo = userMapper.selectById(userId);

        //1、获取经验值并更新 2、获取购物券并更新 3、获取积分并更新
        userPo.setCurrentExperience(BigDecimalUtil.safeAdd(userPo.getCurrentExperience(),giftPo.getExperience()))
                .setCurrentShopTicket(BigDecimalUtil.safeAdd(userPo.getCurrentShopTicket(),giftPo.getVouchers()))
                .setCurrentIntegral(BigDecimalUtil.safeAdd(userPo.getCurrentIntegral(),giftPo.getIntegrals()));
        userMapper.updateById(userPo);

        //经验值升级
        userService.updateLevel(userPo.getId());

        //4、获取优惠券并更新
        List<Long> couponIds = relGiftCouponMapper.selectList(new QueryWrapper<AmGiftRelGiftCouponPo>().lambda()
                .eq(AmGiftRelGiftCouponPo::getGiftId,giftId)).stream().map(a->a.getCouponId()).collect(Collectors.toList());

        if (!ListUtil.isListNullAndEmpty(couponIds)){
            couponIds.forEach(a->{
                //保存到用户和优惠券关联表中
                AmCouponRelCouponUserPo relCouponUserPo = new AmCouponRelCouponUserPo();
                relCouponUserPo.setId(null).setCreateBy(userPo.getTrueName()).setUseStatus(CouponUseStatusEnum.NOT_USED.getId())
                        .setReceiveNum(1).setType(CouponBeLongTypeEnum.RECEIVE.getId()).setUserId(userPo.getId()).setCouponId(a);
                relCouponUserMapper.insert(relCouponUserPo);
            });
        }
        //TODO junhao补充经验值、购物券、积分、优惠券等流水


    }
}
