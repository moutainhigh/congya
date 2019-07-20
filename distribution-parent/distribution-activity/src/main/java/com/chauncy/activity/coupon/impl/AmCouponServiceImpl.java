package com.chauncy.activity.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.coupon.IAmCouponService;
import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.common.enums.app.coupon.CouponScopeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.AmCouponPo;
import com.chauncy.data.domain.po.activity.AmCouponRelCouponGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchCouponListDto;
import com.chauncy.data.mapper.activity.AmCouponMapper;
import com.chauncy.data.mapper.activity.AmCouponRelCouponGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.activity.coupon.SaveCouponResultVo;
import com.chauncy.data.vo.manage.activity.coupon.SearchCouponListVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
@Slf4j
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

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    /**
     * 保存优惠券--添加或者修改
     *
     * @param saveCouponDto
     * @return
     */
    @Override
    public SaveCouponResultVo saveCoupon(SaveCouponDto saveCouponDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        AmCouponPo couponPo = new AmCouponPo();
        //添加操作
        if (saveCouponDto.getId() == 0) {
            BeanUtils.copyProperties(saveCouponDto, couponPo);
            couponPo.setCreateBy(userPo.getUsername());
            couponPo.setId(null);
        }
        //修改操作
        else {
            couponPo = mapper.selectById(saveCouponDto.getId());
            if (couponPo==null){
                throw new ServiceException(ResultCode.FAIL,"该优惠券不存在,请检查！");
            }
            BeanUtils.copyProperties(saveCouponDto, couponPo);
            couponPo.setUpdateBy(userPo.getUsername());
        }
            CouponFormEnum couponFormEnum = CouponFormEnum.getCouponFormEnumById(saveCouponDto.getType());
            CouponScopeEnum couponScopeEnum = CouponScopeEnum.getCouponScopeEnumById(saveCouponDto.getScope());
            SaveCouponResultVo saveCouponResultVo = new SaveCouponResultVo();

            switch (couponFormEnum) {

                //满减
                case WITH_PREFERENTIAL_REDUCTION:
                    /**满减活动条件限制：4、活动成本/售价>=满减活动比例
                     *
                     * 1、固定成本(sku)=供货价(sku)+运营比例(sku)*销售价+利润比例(sku)*销售价
                     * 2、活动成本=（商品售价(sku)-固定成本）*商品活动成本百分比(商品运营信息Tab)
                     * 3、满减活动比例 = 减/满
                     */
                    switch (couponScopeEnum) {
                        //添加商品范围为全部商品
                        case ALL_GOODS:
                            break;
                        //添加商品范围为指定分类
                        case SPECIFIED_CATEGORY:
                            break;
                        //添加商品范围为指定商品
                        case SPECIFIED_GOODS:
                            //记录成功条数
                            Integer success = 0;
                            //失败记录
                            List<BaseVo> failList = Lists.newArrayList();
                            ////筛选符合条件的商品
                            for (Long a : saveCouponDto.getIdList()) {
                                //平台设置商品的活动成本比例(%)
                                BigDecimal activityCostRate = BigDecimalUtil.safeDivide(goodsMapper.selectById(a).getActivityCostRate(), new BigDecimal(100), new BigDecimal(-1));
                                //查找商品对应的sku信息
                                List<PmGoodsSkuPo> skuPoList = skuMapper.selectList(new QueryWrapper<PmGoodsSkuPo>().eq("goods_id", a));
                                boolean flag = true;
                                for (PmGoodsSkuPo b : skuPoList) {
                                    //售价
                                    BigDecimal sellPrice = b.getSellPrice();
                                    //获取供货价、运营成本比例(%)、利润比例(%)
                                    BigDecimal supplierPrice = b.getSupplierPrice();
                                    BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
                                    BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
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
                                        failList.add(goodsFail);
                                        flag = false;
                                        break;
                                    }
                                }
                                //满足条件操作
                                if (flag) {
                                    //添加操作
                                    if (saveCouponDto.getId() == 0) {
                                        mapper.insert(couponPo);
                                        AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                        amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                        amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                                        amCouponRelCouponGoodsPo.setAssociationId(a);
                                        relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                                        success++;
                                    }
                                    //修改操作
                                    else {
                                       mapper.updateById(couponPo);
                                       //删除关联表
                                        relCouponGoodsMapper.delete(new QueryWrapper<AmCouponRelCouponGoodsPo>().eq("coupon_id",couponPo.getId()));
                                        //重新添加关联表
                                        AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                        amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                        amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                                        amCouponRelCouponGoodsPo.setAssociationId(a);
                                        relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                                        success++;
                                    }
                                }
                            }
                            log.error("成功条数: {}", success);
                            log.error("失败条数: {},失败记录: {}", failList.size(), failList);
                            saveCouponResultVo.setSuccessCount(success);
                            saveCouponResultVo.setFailCount(failList.size());
                            saveCouponResultVo.setFailList(failList);
                            return saveCouponResultVo;
                    }
                    break;
                //固定折扣
                case FIXED_DISCOUNT:
                    /**折扣优惠券条件限制：4、活动成本-优惠金额>=0
                     *
                     * 1、固定成本(sku)=供货价(sku)+运营比例(sku)*销售价+利润比例(sku)*销售价
                     * 2、活动成本=（商品售价(sku)-固定成本）*商品活动成本百分比(商品运营信息Tab)
                     * 3、优惠金额 = (1-优惠券面额)*销售价
                     */

                    //指定添加商品的范围
                    switch (couponScopeEnum) {
                        //添加商品范围为全部商品
                        case ALL_GOODS:
                            break;
                        //添加商品范围为指定分类
                        case SPECIFIED_CATEGORY:
                            break;
                        //添加商品范围为指定商品
                        case SPECIFIED_GOODS:
                            //记录成功条数
                            Integer success = 0;
                            //失败记录
                            List<BaseVo> failList = Lists.newArrayList();
                            //筛选符合条件的商品
                            for (Long a : saveCouponDto.getIdList()) {
                                //平台设置商品的活动成本比例(%)
                                BigDecimal activityCostRate = BigDecimalUtil.safeDivide(goodsMapper.selectById(a).getActivityCostRate(), new BigDecimal(100), new BigDecimal(-1));
                                //查找商品对应的sku信息
                                List<PmGoodsSkuPo> skuPoList = skuMapper.selectList(new QueryWrapper<PmGoodsSkuPo>().eq("goods_id", a));
                                boolean flag = true;
                                for (PmGoodsSkuPo b : skuPoList) {
                                    //售价
                                    BigDecimal sellPrice = b.getSellPrice();
                                    //获取供货价、运营成本比例(%)、利润比例(%)
                                    BigDecimal supplierPrice = b.getSupplierPrice();
                                    BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
                                    BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
                                    //固定成本
                                    BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
                                    //活动成本
                                    BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
                                    //折扣满金额条件
                                    BigDecimal discountFullMoney = saveCouponDto.getDiscountFullMoney();
                                    //折扣比例
                                    BigDecimal discount = BigDecimalUtil.safeDivide(saveCouponDto.getDiscount(),new BigDecimal(10),new BigDecimal(-1));
                                    //折扣优惠金额
                                    BigDecimal denomination = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(new BigDecimal(1),discount),sellPrice);

                                    //不满足条件
                                    if (denomination.compareTo(activityCost)>0) {
                                        BaseVo goodsFail = new BaseVo();
                                        goodsFail.setId(a);
                                        goodsFail.setName(goodsMapper.selectById(a).getName());
                                        failList.add(goodsFail);
                                        flag = false;
                                        break;
                                    }
                                }
                                //满足条件操作
                                if (flag) {
                                    //添加操作
                                    if (saveCouponDto.getId() == 0) {
                                        mapper.insert(couponPo);
                                        AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                        amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                        amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                                        amCouponRelCouponGoodsPo.setAssociationId(a);
                                        relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                                        success++;
                                    }
                                    //修改操作
                                    else {
                                        mapper.updateById(couponPo);
                                        //删除关联表
                                        relCouponGoodsMapper.delete(new QueryWrapper<AmCouponRelCouponGoodsPo>().eq("coupon_id",couponPo.getId()));
                                        //重新添加关联表
                                        AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                        amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                        amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                                        amCouponRelCouponGoodsPo.setAssociationId(a);
                                        relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                                        success++;
                                    }
                                }
                            }
                            log.error("成功条数: {}", success);
                            log.error("失败条数: {},失败记录: {}", failList.size(), failList);
                            saveCouponResultVo.setSuccessCount(success);
                            saveCouponResultVo.setFailCount(failList.size());
                            saveCouponResultVo.setFailList(failList);
                            return saveCouponResultVo;
                    }
                //包邮
                case PACKAGE_MAIL:
                    //指定添加商品的范围
                    switch (couponScopeEnum) {
                        //添加商品范围为全部商品
                        case ALL_GOODS:
                            mapper.insert(couponPo);
                            break;
                        //添加商品范围为指定分类
                        case SPECIFIED_CATEGORY:
                            //保存分类ID
                            mapper.insert(couponPo);
                            AmCouponPo finalCouponPo1 = couponPo;
                            saveCouponDto.getIdList().forEach(a->{
                                if (categoryMapper.selectById(a)==null){
                                    throw new ServiceException(ResultCode.FAIL,"不存在该分类:[s%]");
                                }
                                AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                amCouponRelCouponGoodsPo.setCouponId(finalCouponPo1.getId());
                                amCouponRelCouponGoodsPo.setAssociationId(a);
                                relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                            });
                            break;
                        //添加商品范围为指定商品
                        case SPECIFIED_GOODS:
                            mapper.insert(couponPo);
                            AmCouponPo finalCouponPo = couponPo;
                            saveCouponDto.getIdList().forEach(a->{
                                AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                amCouponRelCouponGoodsPo.setCouponId(finalCouponPo.getId());
                                amCouponRelCouponGoodsPo.setAssociationId(a);
                                relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                            });
                            break;
                    }
                    break;
            }
        return null;
    }

    /**
     * 条件分页查询优惠券列表
     *
     * @param searchCouponListDto
     * @return
     */
    @Override
    public PageInfo<SearchCouponListVo> searchCouponList(SearchCouponListDto searchCouponListDto) {
        return null;
    }
}
