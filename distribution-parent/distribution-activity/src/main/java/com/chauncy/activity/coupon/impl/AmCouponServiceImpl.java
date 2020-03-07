package com.chauncy.activity.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.coupon.IAmCouponRelCouponGoodsService;
import com.chauncy.activity.coupon.IAmCouponService;
import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.common.enums.app.coupon.CouponScopeEnum;
import com.chauncy.common.enums.app.coupon.CouponUseStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.coupon.AmCouponPo;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponGoodsPo;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.coupon.CanUseCouponListDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponDto;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponRelationDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchCouponListDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchDetailAssociationsDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchReceiveRecordDto;
import com.chauncy.data.dto.manage.common.FindGoodsBaseByConditionDto;
import com.chauncy.data.mapper.activity.coupon.AmCouponMapper;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponGoodsMapper;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponUserMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsCategoryMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.coupon.SelectCouponVo;
import com.chauncy.data.vo.manage.activity.coupon.*;
import com.chauncy.data.vo.manage.common.goods.GoodsBaseVo;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private AmCouponRelCouponUserMapper relCouponUserMapper;

    @Autowired
    private PmGoodsSkuMapper skuMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private PmMemberLevelMapper levelMapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper relActivityGoodsMapper;

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

        CouponFormEnum couponFormEnum = CouponFormEnum.getCouponFormEnumById(saveCouponDto.getType());
        CouponScopeEnum couponScopeEnum = CouponScopeEnum.getCouponScopeEnumById(saveCouponDto.getScope());
        SaveCouponResultVo saveCouponResultVo = new SaveCouponResultVo();

        //添加操作
        if (saveCouponDto.getId() == 0) {
            BeanUtils.copyProperties(saveCouponDto, couponPo);
            couponPo.setCreateBy(userPo.getUsername());
            couponPo.setStock(saveCouponDto.getTotalNum());//初始化库存信息
            couponPo.setId(null);
            mapper.insert(couponPo);
            saveCouponAssociation(couponFormEnum, couponScopeEnum, saveCouponDto, couponPo, saveCouponResultVo);
        }
        //修改操作
        else {
            couponPo = mapper.selectById(saveCouponDto.getId());
            if (couponPo == null) {
                throw new ServiceException(ResultCode.FAIL, "该优惠券不存在,请检查！");
            }
            BeanUtils.copyProperties(saveCouponDto, couponPo);
            couponPo.setUpdateBy(userPo.getUsername());
            //删除优惠券与商品/分类的关联表
            /*relCouponGoodsMapper.delete(new QueryWrapper<AmCouponRelCouponGoodsPo>().lambda().eq(AmCouponRelCouponGoodsPo::getCouponId, saveCouponDto.getId()));
            saveCouponAssociation(couponFormEnum, couponScopeEnum, saveCouponDto, couponPo, saveCouponResultVo);*/

            mapper.updateById(couponPo);
        }
        return saveCouponResultVo;
    }

    public SaveCouponResultVo saveCouponAssociation(CouponFormEnum couponFormEnum, CouponScopeEnum couponScopeEnum,
                                                    SaveCouponDto saveCouponDto, AmCouponPo couponPo,
                                                    SaveCouponResultVo saveCouponResultVo) {
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

                            /*//查询该商品是否已经参加优惠券活动
                            List<AmCouponRelCouponGoodsPo> relCouponGoodsPos = relCouponGoodsMapper.selectList(new QueryWrapper<AmCouponRelCouponGoodsPo>()
                            .lambda().eq(AmCouponRelCouponGoodsPo::getAssociationId,a));
                            //查询该商品是否已经参加满减/积分/秒杀/拼团等活动
                            List<AmActivityRelActivityGoodsPo> relActivityGoodsPoList = relActivityGoodsMapper.selectListByGoodsId(a);

                            if (!ListUtil.isListNullAndEmpty(relCouponGoodsPos) || !ListUtil.isListNullAndEmpty(relActivityGoodsPoList)){
                                flag = false;
                            }*/

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
                                /*if ((saveCouponDto.getId() == 0) && (couponPo.getId() == null)) {
                                    mapper.insert(couponPo);
                                }*/
                                AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                                amCouponRelCouponGoodsPo.setAssociationId(a);
                                relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                                success++;
                                //修改操作
                                    /*else {
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
                                    }*/
                            }
                        }

                        /*if (saveCouponDto.getId() != 0) {
                            mapper.updateById(couponPo);
                        }*/
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
                                BigDecimal discount = BigDecimalUtil.safeDivide(saveCouponDto.getDiscount(), new BigDecimal(10), new BigDecimal(-1));
                                //折扣优惠金额
                                BigDecimal denomination = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(new BigDecimal(1), discount), sellPrice);

                                //不满足条件
                                if (denomination.compareTo(activityCost) > 0) {
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
                                /*if ((saveCouponDto.getId() == 0) && (couponPo.getId() == null)) {
                                    mapper.insert(couponPo);
                                }*/
                                AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                amCouponRelCouponGoodsPo.setCouponId(couponPo.getId());
                                amCouponRelCouponGoodsPo.setAssociationId(a);
                                relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                                success++;
                                    /*//修改操作
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
                                    }*/
                            }
                        }

                        /*if (saveCouponDto.getId() != 0) {
                            mapper.updateById(couponPo);
                        }*/

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
                        /*//添加操作
                        if (saveCouponDto.getId() == 0) {
                            mapper.insert(couponPo);
                        }
                        //修改操作
                        else {
                            mapper.updateById(couponPo);
                        }*/
                        break;
                    //添加商品范围为指定分类
                    case SPECIFIED_CATEGORY:
                        //添加操作
                        /*if ((saveCouponDto.getId() == 0) && (couponPo.getId() == null)) {
                            mapper.insert(couponPo);
                        }
                        //修改操作
                        else {
                            mapper.updateById(couponPo);
                        }*/
                        AmCouponPo finalCouponPo1 = couponPo;
                        if (saveCouponDto.getId() == 0) {
                            saveCouponDto.getIdList().forEach(a -> {
                                if (categoryMapper.selectById(a) == null) {
                                    throw new ServiceException(ResultCode.FAIL, "不存在该分类:[%s]");
                                }
                                if (!ListUtil.isListNullAndEmpty(relCouponGoodsMapper.selectList(new QueryWrapper<AmCouponRelCouponGoodsPo>().eq("association_id", a)))) {
                                    throw new ServiceException(ResultCode.FAIL, String.format("该分类已关联:[%s:%s]", a, categoryMapper.selectById(a).getName()));
                                }
                                AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                amCouponRelCouponGoodsPo.setCouponId(finalCouponPo1.getId());
                                amCouponRelCouponGoodsPo.setAssociationId(a);
                                relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                            });
                        }
                        break;
                    //添加商品范围为指定商品
                    case SPECIFIED_GOODS:
                        //添加操作
                        /*if ((saveCouponDto.getId() == 0) && (couponPo.getId() == null)) {
                            mapper.insert(couponPo);
                        }*/
                        //修改操作
                        /*else {
                            mapper.updateById(couponPo);
                        }*/
                        AmCouponPo finalCouponPo = couponPo;
                        if (saveCouponDto.getId() == 0) {
                            saveCouponDto.getIdList().forEach(a -> {
                                AmCouponRelCouponGoodsPo amCouponRelCouponGoodsPo = new AmCouponRelCouponGoodsPo();
                                amCouponRelCouponGoodsPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                                amCouponRelCouponGoodsPo.setCouponId(finalCouponPo.getId());
                                amCouponRelCouponGoodsPo.setAssociationId(a);
                                relCouponGoodsMapper.insert(amCouponRelCouponGoodsPo);
                            });
                        }
                        break;
                }
                break;
        }

        return saveCouponResultVo;
    }

    /**
     * 条件分页查询优惠券列表
     *
     * @param searchCouponListDto
     * @return
     */
    @Override
    public PageInfo<SearchCouponListVo> searchCouponList(SearchCouponListDto searchCouponListDto) {

        Integer pageNo = searchCouponListDto.getPageNo() == null ? defaultPageNo : searchCouponListDto.getPageNo();
        Integer pageSize = searchCouponListDto.getPageSize() == null ? defaultPageSize : searchCouponListDto.getPageSize();
        PageInfo<SearchCouponListVo> searchCouponListVo = new PageInfo<>();
        searchCouponListVo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchCouponList(searchCouponListDto));
        if (ListUtil.isListNullAndEmpty(searchCouponListVo.getList())) {
            return new PageInfo<>();
        }
        searchCouponListVo.getList().forEach(a -> {
            //已使用个数
            Integer usedNum = mapper.countNum(a.getCouponId(), CouponUseStatusEnum.USED.getId());
            //未使用个数
            Integer notUseNum = mapper.countNum(a.getCouponId(), CouponUseStatusEnum.NOT_USED.getId());
            //已失效个数
            Integer failureNum = mapper.countNum(a.getCouponId(), CouponUseStatusEnum.FAILURE.getId());
            a.setUseNum(usedNum);
            a.setNotUseNum(notUseNum);
            a.setFailureNum(failureNum);
        });

        return searchCouponListVo;
    }

    /**
     * 批量删除优惠券
     *
     * @param ids
     * @return
     */
    @Override
    public void delByIds(Long[] ids) {
        Arrays.asList(ids).forEach(a -> {
            if (mapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.FAIL, "数据库不存在该优惠券");
            }
            //优惠券已被领取不能进行删除操作
            if (!ListUtil.isListNullAndEmpty(relCouponUserMapper.selectList(new QueryWrapper<AmCouponRelCouponUserPo>().eq("coupon_id", a)))) {
                throw new ServiceException(ResultCode.FAIL, "优惠券已被领取不能进行删除操作");
            }

            relCouponGoodsMapper.delete(new QueryWrapper<AmCouponRelCouponGoodsPo>().lambda().eq(AmCouponRelCouponGoodsPo::getCouponId,a));
        });

        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 根据优惠券查找领取记录
     *
     * @param searchReceiveRecordDto
     * @return
     */
    @Override
    public PageInfo<SearchReceiveRecordVo> searchReceiveRecord(SearchReceiveRecordDto searchReceiveRecordDto) {

        Integer pageNo = searchReceiveRecordDto.getPageNo() == null ? defaultPageNo : searchReceiveRecordDto.getPageNo();
        Integer pageSize = searchReceiveRecordDto.getPageSize() == null ? defaultPageSize : searchReceiveRecordDto.getPageSize();
        PageInfo<SearchReceiveRecordVo> searchReceiveRecordVoPageInfo = new PageInfo<>();
        searchReceiveRecordVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchReceiveRecord(searchReceiveRecordDto));
        if (ListUtil.isListNullAndEmpty(searchReceiveRecordVoPageInfo.getList())) {
            return new PageInfo<>();
        }
        return searchReceiveRecordVoPageInfo;
    }

    /**
     * 根据ID查询优惠券详情除关联商品外的信息
     *
     * @param id
     * @return
     */
    @Override
    public FindCouponDetailByIdVo findCouponDetailById(Long id) {
        if (mapper.selectById(id) == null) {
            throw new ServiceException(ResultCode.FAIL, String.format("不存在该优惠券:[%s],请检查", id));
        }

        return mapper.findCouponDetailById(id);
    }

    /***
     * 条件分页获取优惠券详情下指定的商品信息
     *
     * @param searchDetailAssociationsDto
     * @return
     */
    @Override
    public PageInfo<SearchDetailAssociationsVo> searchDetailAssociations(SearchDetailAssociationsDto searchDetailAssociationsDto) {
        Integer pageNo = searchDetailAssociationsDto.getPageNo() == null ? defaultPageNo : searchDetailAssociationsDto.getPageNo();
        Integer pageSize = searchDetailAssociationsDto.getPageSize() == null ? defaultPageSize : searchDetailAssociationsDto.getPageSize();

        AmCouponPo couponPo = mapper.selectById(searchDetailAssociationsDto.getId());
        if (couponPo == null) {
            return new PageInfo<>();
        } else {
            CouponFormEnum couponFormEnum = CouponFormEnum.getCouponFormEnumById(couponPo.getType());
            CouponScopeEnum couponScopeEnum = CouponScopeEnum.getCouponScopeEnumById(couponPo.getScope());
            PageInfo<SearchDetailAssociationsVo> searchDetailAssociationsVoPageInfo = new PageInfo<>();
            //获取优惠形式 type 1-满减 2-固定折扣 3-包邮
            switch (couponFormEnum) {
                case WITH_PREFERENTIAL_REDUCTION://满减
                case FIXED_DISCOUNT://固定折扣
                    switch (couponScopeEnum) {
                        case ALL_GOODS:
                            break;
                        case SPECIFIED_CATEGORY:
                            break;
                        //指定商品
                        case SPECIFIED_GOODS:
                            searchDetailAssociationsVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                                    .doSelectPageInfo(() -> mapper.searchDetailGoods(searchDetailAssociationsDto));
                            if (ListUtil.isListNullAndEmpty(searchDetailAssociationsVoPageInfo.getList())) {
                                return new PageInfo<>();
                            }
                            searchDetailAssociationsVoPageInfo.getList().forEach(b -> {
                                PmGoodsCategoryPo goodsCategoryPo = categoryMapper.selectById(b.getGoodsCategoryId());
                                String level3 = goodsCategoryPo.getName();
                                PmGoodsCategoryPo goodsCategoryPo2 = categoryMapper.selectById(goodsCategoryPo.getParentId());
                                String level2 = goodsCategoryPo2.getName();
                                String level1 = categoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
                                String categoryName = level1 + "/" + level2 + "/" + level3;
                                b.setCategoryName(categoryName);
                            });
                            return searchDetailAssociationsVoPageInfo;
                    }
                    break;
                //包邮
                case PACKAGE_MAIL:
                    switch (couponScopeEnum) {
                        //所有商品
                        case ALL_GOODS:
                            break;
                        //指定分类
                        case SPECIFIED_CATEGORY:
                            searchDetailAssociationsVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                                    .doSelectPageInfo(() -> mapper.searchDetailCategory(searchDetailAssociationsDto));
                            if (ListUtil.isListNullAndEmpty(searchDetailAssociationsVoPageInfo.getList())) {
                                return new PageInfo<>();
                            }
                            searchDetailAssociationsVoPageInfo.getList().forEach(a -> {
                                PmGoodsCategoryPo goodsCategoryPo = categoryMapper.selectById(a.getId());
                                if (a.getLevel() == 1) {
                                    a.setCategoryName(goodsCategoryPo.getName().toString());
                                } else if (a.getLevel() == 2) {
                                    String level2 = goodsCategoryPo.getName();
                                    String level1 = categoryMapper.selectById(goodsCategoryPo.getParentId()).getName();
                                    String categoryName = level1 + "/" + level2;
                                    a.setCategoryName(categoryName);
                                } else if (a.getLevel() == 3) {
                                    String level3 = goodsCategoryPo.getName();
                                    PmGoodsCategoryPo goodsCategoryPo2 = categoryMapper.selectById(goodsCategoryPo.getParentId());
                                    String level2 = goodsCategoryPo2.getName();
                                    String level1 = categoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
                                    String categoryName = level1 + "/" + level2 + "/" + level3;
                                    a.setCategoryName(categoryName);
                                }
                            });
                            return searchDetailAssociationsVoPageInfo;

                        //指定商品
                        case SPECIFIED_GOODS:
                            searchDetailAssociationsVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                                    .doSelectPageInfo(() -> mapper.searchDetailGoods(searchDetailAssociationsDto));
                            if (ListUtil.isListNullAndEmpty(searchDetailAssociationsVoPageInfo.getList())) {
                                return new PageInfo<>();
                            }
                            searchDetailAssociationsVoPageInfo.getList().forEach(b -> {
                                PmGoodsCategoryPo goodsCategoryPo = categoryMapper.selectById(b.getGoodsCategoryId());
                                String level3 = goodsCategoryPo.getName();
                                PmGoodsCategoryPo goodsCategoryPo2 = categoryMapper.selectById(goodsCategoryPo.getParentId());
                                String level2 = goodsCategoryPo2.getName();
                                String level1 = categoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
                                String categoryName = level1 + "/" + level2 + "/" + level3;
                                b.setCategoryName(categoryName);
                            });
                            return searchDetailAssociationsVoPageInfo;
                    }
                    break;
            }
        }
        //获取优惠券添加商品指定范围 scope 1-所有商品 2-指定分类 3-指定商品

        return null;
    }

    /**
     * 条件获取商品的基础信息，作为给需要选择的功能的展示
     *
     * @param findGoodsBaseByConditionDto
     * @return
     */
    @Override
    public PageInfo<GoodsBaseVo> findGoodsBaseByCondition(FindGoodsBaseByConditionDto findGoodsBaseByConditionDto) {

        List<Long> goodsIds = Lists.newArrayList();
        AmCouponPo amCouponPo = mapper.selectById(findGoodsBaseByConditionDto.getCouponId());
        if (amCouponPo != null) {
            if (amCouponPo.getScope() == CouponScopeEnum.SPECIFIED_GOODS.getId()) {
                List<AmCouponRelCouponGoodsPo> relCouponGoodsPos = relCouponGoodsMapper.selectList(new QueryWrapper<AmCouponRelCouponGoodsPo>().eq("coupon_id", findGoodsBaseByConditionDto.getCouponId()));
                goodsIds = relCouponGoodsPos.stream().map(b -> b.getAssociationId()).collect(Collectors.toList());
            }
        }
        List<Long> finalGoodsIds = goodsIds;
        Integer pageNo = findGoodsBaseByConditionDto.getPageNo() == null ? defaultPageNo : findGoodsBaseByConditionDto.getPageNo();
        Integer pageSize = findGoodsBaseByConditionDto.getPageSize() == null ? defaultPageSize : findGoodsBaseByConditionDto.getPageSize();
        PageInfo<GoodsBaseVo> goodsBaseVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.findGoodsBaseByCondition(findGoodsBaseByConditionDto, finalGoodsIds));
        goodsBaseVoPageInfo.getList().forEach(a -> {
            PmGoodsCategoryPo goodsCategoryPo3 = categoryMapper.selectById(a.getCategoryId());
            String level3 = goodsCategoryPo3.getName();
            PmGoodsCategoryPo goodsCategoryPo2 = categoryMapper.selectById(goodsCategoryPo3.getParentId());
            String level2 = goodsCategoryPo2.getName();
            String level1 = categoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
            String categoryName = level1 + "/" + level2 + "/" + level3;
            a.setCategoryName(categoryName);
            //已经指定的商品isInclude返回true
//            if (!ListUtil.isListNullAndEmpty(finalGoodsIds)){
//                if (finalGoodsIds.contains(a.getGoodsId())){
//                    a.setIsInclude(true);
//                }
//            }
        });

        return goodsBaseVoPageInfo;
    }

    /**
     * 批量删除指定商品
     *
     * @param relIds
     * @return
     */
    @Override
    public void delByAssociationsId(Long[] relIds) {
        List<Long> associationsIdList = Arrays.asList(relIds);
        associationsIdList.forEach(a -> {
            if (relCouponGoodsMapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.FAIL, "数据库不存在该记录");
            }
        });
        relCouponGoodsMapper.deleteBatchIds(associationsIdList);
    }

    @Override
    public List<SelectCouponVo> getSelectCouPonVo(List<CanUseCouponListDto> canUseCouponListDtos) {

        List<Long> skuIds = canUseCouponListDtos.stream().map(CanUseCouponListDto::getSkuId).collect(Collectors.toList());

        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        List<SelectCouponVo> querySelectCouponVoList = mapper.getSelectCouPonVo(appCurrUser.getId(), skuIds, null);
        //sku对应的数量
        querySelectCouponVoList.forEach(x -> {
            //为查询后的id匹配上用户下单的数量
            x.setNumber(canUseCouponListDtos.stream().filter(y -> y.getSkuId().equals(x.getSkuId())).findFirst().get().getNumber());
        });
        if (ListUtil.isListNullAndEmpty(querySelectCouponVoList)) {
            throw new ServiceException(ResultCode.NO_EXISTS, "无可用的优惠券！");
        }
        //算出满足满减优惠的优惠券
        List<SelectCouponVo> selectCouponVoList = com.google.common.collect.Lists.newArrayList();
        Map<Long, List<SelectCouponVo>> map = querySelectCouponVoList.stream().collect(Collectors.groupingBy(SelectCouponVo::getCouponRelUserId));
        for (Map.Entry<Long, List<SelectCouponVo>> entry : map.entrySet()) {

            Long couponRelUserId = entry.getKey();
            List<SelectCouponVo> selectCouponVos = entry.getValue();
            //会员id
            Long levelId = selectCouponVos.get(0).getLevelId();
            PmMemberLevelPo queryLevel = levelMapper.selectById(levelId);
            //如果用户等级不够，不能用优惠券
            if (queryLevel.getLevel() > appCurrUser.getLevel()) {
                continue;
            }
            Integer type = selectCouponVos.get(0).getType();
            BigDecimal discountFullMoney = selectCouponVos.get(0).getDiscountFullMoney();
            BigDecimal reductionFullMoney = selectCouponVos.get(0).getReductionFullMoney();
            //使用同一优惠券商品的总销售价
            BigDecimal totalPrice = selectCouponVos.stream().map(x -> BigDecimalUtil.safeMultiply(x.getNumber(), x.getSellPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);
            //满减
            if (CouponFormEnum.WITH_PREFERENTIAL_REDUCTION.getId().equals(type)) {
                //不满足满减条件
                if (reductionFullMoney.compareTo(totalPrice) > 0) {
                    continue;
                }
            }
            //折扣
            if (CouponFormEnum.FIXED_DISCOUNT.getId().equals(type)) {
                //不满足折扣条件
                if (discountFullMoney.compareTo(totalPrice) > 0) {
                    continue;
                }
            }
            selectCouponVoList.add(selectCouponVos.get(0));
        }
        return selectCouponVoList;
    }

    @Override
    public SaveCouponResultVo saveCouponGoods(SaveCouponDto saveCouponDto) {

        /*List<AmCouponRelCouponGoodsPo> amCouponRelCouponGoodsPos = relCouponGoodsMapper.selectList(new QueryWrapper<AmCouponRelCouponGoodsPo>()
                .lambda().eq(AmCouponRelCouponGoodsPo::getCouponId, saveCouponDto.getId()));

        if (!ListUtil.isListNullAndEmpty(amCouponRelCouponGoodsPos)) {

            relCouponGoodsMapper.deleteBatchIds(amCouponRelCouponGoodsPos.stream().map(a -> a.getId()).collect(Collectors.toList()));

        }*/

        SysUserPo userPo = securityUtil.getCurrUser();
        AmCouponPo couponPo = new AmCouponPo();

        CouponFormEnum couponFormEnum = CouponFormEnum.getCouponFormEnumById(saveCouponDto.getType());
        CouponScopeEnum couponScopeEnum = CouponScopeEnum.getCouponScopeEnumById(saveCouponDto.getScope());
        SaveCouponResultVo saveCouponResultVo = new SaveCouponResultVo();

        BeanUtils.copyProperties(saveCouponDto, couponPo);
        couponPo.setUpdateBy(userPo.getUsername());
        saveCouponAssociation(couponFormEnum, couponScopeEnum, saveCouponDto, couponPo, saveCouponResultVo);

        return saveCouponResultVo;
    }
}
