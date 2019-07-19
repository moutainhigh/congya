package com.chauncy.activity.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.chauncy.activity.coupon.IAmCouponService;
import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.AmCouponPo;
import com.chauncy.data.domain.po.activity.AmCouponRelCouponGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.dto.manage.activity.coupon.SaveCouponDto;
import com.chauncy.data.mapper.activity.AmCouponMapper;
import com.chauncy.data.mapper.activity.AmCouponRelCouponGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.security.util.SecurityUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

/**
 * <p>
 * 优惠券 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmCouponServiceImpl extends AbstractService<AmCouponMapper, AmCouponPo> implements IAmCouponService {

    @Autowired
    private AmCouponMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AmCouponRelCouponGoodsMapper relCouponGoodsMapper;

    @Autowired
    private PmGoodsSkuMapper skuMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    /**
     * 保存优惠券--添加或者修改
     *
     * @param saveCouponDto
     * @return
     */
    @Override
    public void saveCoupon(SaveCouponDto saveCouponDto) {

        if (saveCouponDto.getId() == 0) {
            AmCouponPo couponPo = new AmCouponPo();
            BeanUtils.copyProperties(saveCouponDto, couponPo);
            couponPo.setCreateBy(securityUtil.getCurrUser().getUsername());
            CouponFormEnum couponFormEnum = CouponFormEnum.getCouponFormEnumById(saveCouponDto.getType());
            mapper.insert(couponPo);
            /**满减活动条件限制：4、活动成本/售价>=满减活动比例
             *
             * 1、固定成本(sku)=供货价(sku)+运营比例(sku)*销售价+利润比例(sku)*销售价
             * 2、活动成本=（商品售价(sku)-固定成本）*商品活动成本百分比(商品运营信息Tab)
             * 3、满减活动比例 = 减/满
             */
            switch (couponFormEnum) {
                //满减
                case WITH_PREFERENTIAL_REDUCTION:
                    //保存优惠券与商品到关联表
                    saveCouponDto.getGoodsIds().forEach(a-> {
                        //平台设置商品的活动成本
                        BigDecimal activityCostRate = goodsMapper.selectById(a).getActivityCostRate();
                        //查找商品对应的sku信息
                        List<PmGoodsSkuPo> skuPoList = skuMapper.selectList(new QueryWrapper<PmGoodsSkuPo>().eq("goods_id", a));
                        //保存失败的商品
                        List<BaseVo> goodsFails = Lists.newArrayList();
                        boolean flag = true;
                        for (PmGoodsSkuPo b : skuPoList) {
                            //售价
                            BigDecimal sellPrice = b.getSellPrice();
                            //获取供货价、运营成本、利润
                            BigDecimal supplierPrice = b.getSupplierPrice();
                            BigDecimal operationPrice = BigDecimalUtil.safeMultiply(b.getSupplierPrice(), sellPrice);
                            BigDecimal profitRate = BigDecimalUtil.safeMultiply(b.getProfitRate(), sellPrice);
                            //固定成本
                            BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
                            //活动成本
                            BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
                            //满金额条件
                            BigDecimal reductionFullMoney = saveCouponDto.getReductionFullMoney();
                            //减金额
                            BigDecimal reductionPostMoney = saveCouponDto.getReductionPostMoney();
                            //满减比例
                            BigDecimal rate = BigDecimalUtil.safeDivide(reductionPostMoney, reductionFullMoney, new BigDecimal(-1));
                            //活动成本/售价
                            BigDecimal activitySaleRate = BigDecimalUtil.safeDivide(activityCost, sellPrice, new BigDecimal(-1));
                            //不满足条件
                            if (activitySaleRate.compareTo(rate) < 0) {
                                BaseVo goodsFail = new BaseVo();
                                goodsFail.setId(a);
                                goodsFail.setName(goodsMapper.selectById(a).getName());
                                goodsFails.add(goodsFail);
                                flag =false;
                                break;
                            }
                        }
                        //满足条件,则保存
                        if (flag) {
                            AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                            amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                            amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                            amCouponRelCouponGoodsPo.setGoodsId(a);
                            relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                        }
                    });
                    break;
                case FIXED_DISCOUNT:
                    break;
                case PACKAGE_MAIL:
                    break;
            }
    }
    //修改操作
        else

    {
        AmCouponPo couponPo = mapper.selectById(saveCouponDto.getId());
        BeanUtils.copyProperties(saveCouponDto, couponPo);
        mapper.updateById(couponPo);
        //
    }

}
}
