package com.chauncy.activity.registration.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.registration.IAmActivityRelActivityGoodsService;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.activity.StoreActivityBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.AmActivityRelActivityCategoryPo;
import com.chauncy.data.domain.po.activity.integrals.AmIntegralsPo;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueSkuPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.app.user.favorites.add.UpdateFavoritesDto;
import com.chauncy.data.dto.manage.activity.spell.select.SearchSpellRecordDto;
import com.chauncy.data.dto.supplier.activity.add.SaveRegistrationDto;
import com.chauncy.data.dto.supplier.activity.delete.CancelRegistrationDto;
import com.chauncy.data.dto.supplier.activity.select.FindActivitySkuDto;
import com.chauncy.data.dto.supplier.activity.select.SearchAssociatedGoodsDto;
import com.chauncy.data.dto.supplier.activity.select.SearchSupplierActivityDto;
import com.chauncy.data.dto.supplier.activity.select.UpdateVerifyStatusDto;
import com.chauncy.data.mapper.activity.AmActivityRelActivityCategoryMapper;
import com.chauncy.data.mapper.activity.integrals.AmIntegralsMapper;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelGoodsSkuMapper;
import com.chauncy.data.mapper.activity.seckill.AmSeckillMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMainMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMapper;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.data.vo.supplier.activity.*;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台活动与商品关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmActivityRelActivityGoodsServiceImpl extends AbstractService<AmActivityRelActivityGoodsMapper, AmActivityRelActivityGoodsPo> implements IAmActivityRelActivityGoodsService {

    @Autowired
    private AmActivityRelActivityGoodsMapper mapper;

    @Autowired
    private AmReducedMapper reducedMapper;

    @Autowired
    private AmIntegralsMapper integralsMapper;

    @Autowired
    private AmSeckillMapper seckillMapper;

    @Autowired
    private AmSpellGroupMapper spellGroupMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AmActivityRelActivityCategoryMapper relActivityCategoryMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    @Autowired
    private PmGoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper goodsRelAttributeValueSkuMapper;

    @Autowired
    private PmGoodsAttributeValueMapper goodsAttributeValueMapper;

    @Autowired
    private AmActivityRelGoodsSkuMapper activityRelGoodsSkuMapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper activityRelActivityGoodsMapper;

    @Autowired
    private AmSpellGroupMainMapper spellGroupMainMapper;


    /**
     * 条件查询需要被选参与活动的商品
     *
     * @param searchAssociatedGoodsDto
     * @return
     */
    @Override
    public PageInfo<SearchAssociatedGoodsVo> searchAssociatedGoods(SearchAssociatedGoodsDto searchAssociatedGoodsDto) {

        Long activityId = searchAssociatedGoodsDto.getActivityId();
        Long storeId = securityUtil.getCurrUser().getStoreId();

        int pageNo = searchAssociatedGoodsDto.getPageNo() == null ? defaultPageNo : searchAssociatedGoodsDto.getPageNo();
        int pageSize = searchAssociatedGoodsDto.getPageSize() == null ? defaultPageSize : searchAssociatedGoodsDto.getPageSize();

        List<Long> categoryIds = relActivityCategoryMapper.selectList(new QueryWrapper<AmActivityRelActivityCategoryPo>()
                .lambda().and(a -> a.eq(AmActivityRelActivityCategoryPo::getDelFlag, 0)
                        .eq(AmActivityRelActivityCategoryPo::getActivityId, activityId))).stream().map(b -> b.getCategoryId())
                .collect(Collectors.toList());

        PageInfo<SearchAssociatedGoodsVo> searchAssociatedGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> goodsMapper.searchAssociatedGoods(searchAssociatedGoodsDto, activityId, storeId, categoryIds));

        ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.fromName(searchAssociatedGoodsDto.getActivityType());
        //获取最低价格和最高价格
        searchAssociatedGoodsVoPageInfo.getList().forEach(x -> {
            BigDecimal lowestSellPrice = goodsSkuMapper.getLowestPrice(x.getGoodsId());
            BigDecimal highestSellPrice = goodsSkuMapper.getHighestPrice(x.getGoodsId());
            String sellPrice = "";
            if (lowestSellPrice.equals(highestSellPrice)) {
                sellPrice = lowestSellPrice.toString();
            } else {
                sellPrice = lowestSellPrice.toString() + "-" + highestSellPrice;
            }
            x.setSalePrice(sellPrice);
            switch (activityTypeEnum) {
                case REDUCED:
                    AmReducedPo amReducedPo = reducedMapper.selectById(activityId);
                    x.setActivityId(amReducedPo.getId());
                    x.setActivityName(amReducedPo.getName());
                    break;
                case INTEGRALS:
                    AmIntegralsPo integralsPo = integralsMapper.selectById(activityId);
                    x.setActivityId(integralsPo.getId());
                    x.setActivityName(integralsPo.getName());
                    break;
                case SECKILL:
                    AmSeckillPo seckillPo = seckillMapper.selectById(activityId);
                    x.setActivityId(seckillPo.getId());
                    x.setActivityName(seckillPo.getName());
                    break;
                case SPELL_GROUP:
                    AmSpellGroupPo spellGroupPo = spellGroupMapper.selectById(activityId);
                    x.setActivityId(spellGroupPo.getId());
                    x.setActivityName(spellGroupPo.getName());
                    break;
            }
        });

        return searchAssociatedGoodsVoPageInfo;
    }

    /**
     * 判断商品是否符合不能参加在同一时间段内的活动、不符合活动要求以及查找对应的sku信息
     *
     * @param findActivitySkuDto
     * @return
     */

    @Override
    public GetActivitySkuInfoVo findActivitySku(FindActivitySkuDto findActivitySkuDto) {

        Long goodsId = findActivitySkuDto.getGoodsId();
        SysUserPo userPo = securityUtil.getCurrUser();
        Long activityId = findActivitySkuDto.getActivityId();
        ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.fromName(findActivitySkuDto.getActivityType());
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("del_flag", false);
        List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(map);
        if (goodsSkuPos == null && goodsSkuPos.size() == 0) {
            return new GetActivitySkuInfoVo();
        }
//        //平台设置商品的活动成本比例(%)
//        BigDecimal activityCostRate = BigDecimalUtil.safeDivide(goodsMapper.selectById(goodsId).getActivityCostRate(), new BigDecimal(100), new BigDecimal(-1));
//        //获取当前活动时间并判断该商品是否已经参与同时段活动
//        switch (activityTypeEnum) {
//            case REDUCED:
//                AmReducedPo reducedPo = reducedMapper.selectById(activityId);
//                startTime = reducedPo.getActivityStartTime();
//                endTime = reducedPo.getActivityEndTime();
//                break;
//            case INTEGRALS:
//                AmIntegralsPo integralsPo = integralsMapper.selectById(activityId);
//                startTime = integralsPo.getActivityStartTime();
//                endTime = integralsPo.getActivityEndTime();
//                break;
//            case SECKILL:
//                AmSeckillPo seckillPo = seckillMapper.selectById(activityId);
//                startTime = seckillPo.getActivityStartTime();
//                endTime = seckillPo.getActivityEndTime();
//                break;
//            case SPELL_GROUP:
//                AmSpellGroupPo spellGroupPo = spellGroupMapper.selectById(activityId);
//                startTime = spellGroupPo.getActivityStartTime();
//                endTime = spellGroupPo.getActivityEndTime();
//                break;
//        }
//        //查询该商家参与的所有有效的活动 am_activity_rel_activity_goods
//        List<StoreActivityBo> storeActivityBos = activityRelActivityGoodsMapper.findStoreActivity(userPo.getStoreId(), findActivitySkuDto.getGoodsId());
//        if (!ListUtil.isListNullAndEmpty(storeActivityBos)) {
//            LocalDateTime finalStartTime = startTime;
//            LocalDateTime finalEndTime = endTime;
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            storeActivityBos.forEach(y -> {
//                ActivityTypeEnum activityType = ActivityTypeEnum.getActivityTypeEnumById(y.getActivityType());
//                switch (activityType) {
//                    case REDUCED:
//                        AmReducedPo reducedPo = reducedMapper.selectById(y.getActivityId());
//                        LocalDateTime reduceStartTime = reducedPo.getActivityStartTime();
//                        LocalDateTime reduceEndTime = reducedPo.getActivityEndTime();
//                        if ((reduceStartTime.isAfter(finalStartTime) && reduceStartTime.isBefore(finalEndTime)) ||
//                                (reduceEndTime.isAfter(finalStartTime) && reduceEndTime.isBefore(finalEndTime))) {
//                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
//                        }
//                        /**满减活动条件限制：4、活动成本/售价>=满减活动比例
//                         *
//                         * 1、固定成本(sku)=供货价(sku)+运营比例(sku)*销售价+利润比例(sku)*销售价
//                         * 2、活动成本=（商品售价(sku)-固定成本）*商品活动成本百分比(商品运营信息Tab)
//                         * 3、满减活动比例 = 减/满
//                         */
//                        for (PmGoodsSkuPo b : goodsSkuPos) {
//                            //售价
//                            BigDecimal sellPrice = b.getSellPrice();
//                            //获取供货价、运营成本比例(%)、利润比例(%)
//                            BigDecimal supplierPrice = b.getSupplierPrice();
//                            BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            //固定成本
//                            BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
//                            //活动成本
//                            BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
//                            //满金额条件
//                            BigDecimal reductionFullMoney = reducedPo.getReductionFullMoney();
//                            //减金额
//                            BigDecimal reductionPostMoney = reducedPo.getReductionPostMoney();
//                            //满减比例
//                            BigDecimal rate = BigDecimalUtil.safeDivide(reductionPostMoney, reductionFullMoney, new BigDecimal(-1));
//                            //活动成本/售价
//                            BigDecimal activitySaleRate = BigDecimalUtil.safeDivide(activityCost, sellPrice, new BigDecimal(-1));
//                            //不满足条件
//                            if (activitySaleRate.compareTo(rate) < 0){
//                                throw new ServiceException(ResultCode.FAIL,String.format("该商品:[%s]不满足该活动:[%s]的条件",goodsMapper.selectById(goodsId).getName(),reducedPo.getName()));
//                            }
//                        }
//                        break;
//                    /**
//                     * 活动积分判断
//                     *
//                     * 活动成本-优惠金额>=0
//                     */
//                    case INTEGRALS:
//                        AmIntegralsPo integralsPo = integralsMapper.selectById(y.getActivityId());
//                        LocalDateTime intergralsStartTime = integralsPo.getActivityStartTime();
//                        LocalDateTime integralsEndTime = integralsPo.getActivityEndTime();
//                        if ((intergralsStartTime.isAfter(finalStartTime) && intergralsStartTime.isBefore(finalEndTime)) ||
//                                (integralsEndTime.isAfter(finalStartTime) && integralsEndTime.isBefore(finalEndTime))) {
//                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
//                        }
//                        for (PmGoodsSkuPo b : goodsSkuPos) {
//                            //售价
//                            BigDecimal sellPrice = b.getSellPrice();
//                            //获取供货价、运营成本比例(%)、利润比例(%)
//                            BigDecimal supplierPrice = b.getSupplierPrice();
//                            BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            //固定成本
//                            BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
//                            //活动成本
//                            BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
//                            //优惠金额
//                            BigDecimal discount = BigDecimalUtil.safeMultiply(sellPrice,BigDecimalUtil.safeDivide(integralsPo.getDiscountPriceRatio(),new BigDecimal(100),new BigDecimal(-1)));
//                            if (activityCost.compareTo(discount)<0){
//                                throw new ServiceException(ResultCode.FAIL,String.format("该商品:[%s]不满足该活动:[%s]的条件",goodsMapper.selectById(goodsId).getName(),integralsPo.getName()));
//                            }
//
//                        }
//                        break;
//                    case SECKILL:
//                        AmSeckillPo seckillPo = seckillMapper.selectById(y.getActivityId());
//                        LocalDateTime seckillStartTime = seckillPo.getActivityStartTime();
//                        LocalDateTime seckillEndTime = seckillPo.getActivityEndTime();
//                        if ((seckillStartTime.isAfter(finalStartTime) && seckillStartTime.isBefore(finalEndTime)) ||
//                                (seckillEndTime.isAfter(finalStartTime) && seckillEndTime.isBefore(finalEndTime))) {
//                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
//                        }
//                        for (PmGoodsSkuPo b : goodsSkuPos) {
//                            //售价
//                            BigDecimal sellPrice = b.getSellPrice();
//                            //获取供货价、运营成本比例(%)、利润比例(%)
//                            BigDecimal supplierPrice = b.getSupplierPrice();
//                            BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            //固定成本
//                            BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
//                            //活动成本
//                            BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
//                            //优惠金额
//                            BigDecimal discount = BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(seckillPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1)));
//                            if (activityCost.compareTo(discount) < 0) {
//                                throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]不满足该活动:[%s]的条件", goodsMapper.selectById(goodsId).getName(), seckillPo.getName()));
//                            }
//                        }
//                        break;
//                    case SPELL_GROUP:
//                        AmSpellGroupPo spellGroupPo = spellGroupMapper.selectById(y.getActivityId());
//                        LocalDateTime spellGroupStartTime = spellGroupPo.getActivityStartTime();
//                        LocalDateTime spellGroupEndTime = spellGroupPo.getActivityEndTime();
//                        if ((spellGroupStartTime.isAfter(finalStartTime) && spellGroupStartTime.isBefore(finalEndTime)) ||
//                                (spellGroupEndTime.isAfter(finalStartTime) && spellGroupEndTime.isBefore(finalEndTime))) {
//                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
//                        }
//                        for (PmGoodsSkuPo b : goodsSkuPos) {
//                            //售价
//                            BigDecimal sellPrice = b.getSellPrice();
//                            //获取供货价、运营成本比例(%)、利润比例(%)
//                            BigDecimal supplierPrice = b.getSupplierPrice();
//                            BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(b.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
//                            //固定成本
//                            BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
//                            //活动成本
//                            BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
//                            //优惠金额
//                            BigDecimal discount = BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(spellGroupPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1)));
//                            if (activityCost.compareTo(discount) < 0) {
//                                throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]不满足该活动:[%s]的条件", goodsMapper.selectById(goodsId).getName(), spellGroupPo.getName()));
//                            }
//                        }
//                        break;
//                }
//            });
//        }

        GetActivitySkuInfoVo getActivitySkuInfoVo = new GetActivitySkuInfoVo();

        List<GoodsStandardVo> standardVos = Lists.newArrayList();
        //获取商品对应的分类ID
        Long categoryId = goodsMapper.selectById(goodsId).getGoodsCategoryId();
        //获取分类下所有的规格
        List<BaseBo> goodsAttributePos = goodsAttributeMapper.findStandardName(categoryId);
        //遍历规格名称
        goodsAttributePos.forEach(x -> {
            List<GoodsValueBo> goodsValues = goodsMapper.findGoodsValue(goodsId, x.getId());
            if (goodsValues != null && goodsValues.size() != 0) {
                //获取规格名称和规格ID
                GoodsStandardVo goodsStandardVo = new GoodsStandardVo();
                goodsStandardVo.setAttributeId(x.getId());
                goodsStandardVo.setAttributeName(x.getName());
                //获取该商品下的属性下的所属的规格值信息
                List<StandardValueAndStatusVo> attributeValueInfos = Lists.newArrayList();
                goodsValues.forEach(a -> {
                    StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                    standardValueAndStatusVo.setIsInclude(true);
                    standardValueAndStatusVo.setAttributeValueId(a.getId());
                    standardValueAndStatusVo.setAttributeValue(a.getName());
                    attributeValueInfos.add(standardValueAndStatusVo);
                });
                goodsStandardVo.setAttributeValueInfos(attributeValueInfos);
                standardVos.add(goodsStandardVo);
            }
        });
        getActivitySkuInfoVo.setGoodsStandardVo(standardVos);


        List<Map<String, Object>> mapList = Lists.newArrayList();
        //判断该商品是否存在
        PmGoodsPo goodsPo = goodsMapper.selectById(goodsId);
        if (goodsPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "该商品不存在！");
        }
        AmActivityRelActivityGoodsPo activityRelActivityGoodsPo = new AmActivityRelActivityGoodsPo();
        if (userPo.getStoreId() !=null) {
            //从am_activity_rel_activity_goods 平台活动与商品关联表 获取活动商品信息
            activityRelActivityGoodsPo = activityRelActivityGoodsMapper.selectOne(new QueryWrapper<AmActivityRelActivityGoodsPo>()
                    .lambda().and(obj -> obj.eq(AmActivityRelActivityGoodsPo::getStoreId, userPo.getStoreId())
                            .eq(AmActivityRelActivityGoodsPo::getActivityId, findActivitySkuDto.getActivityId())
                            .eq(AmActivityRelActivityGoodsPo::getGoodsId, findActivitySkuDto.getGoodsId())));
            if (activityRelActivityGoodsPo.getActivityType() != ActivityTypeEnum.fromName(findActivitySkuDto.getActivityType()).getId()) {
                throw new ServiceException(ResultCode.FAIL, String.format("所传的活动类型:[%s]不对,该活动对应的类型应该是:[%s]", findActivitySkuDto.getActivityType(), ActivityTypeEnum.getActivityTypeEnumById(activityRelActivityGoodsPo.getActivityType()).getName()));
            }
        }else {
            //从am_activity_rel_activity_goods 平台活动与商品关联表 获取活动商品信息
            activityRelActivityGoodsPo = activityRelActivityGoodsMapper.selectOne(new QueryWrapper<AmActivityRelActivityGoodsPo>()
                    .lambda().and(obj -> obj.eq(AmActivityRelActivityGoodsPo::getActivityId, findActivitySkuDto.getActivityId())
                            .eq(AmActivityRelActivityGoodsPo::getGoodsId, findActivitySkuDto.getGoodsId())));
            if (activityRelActivityGoodsPo.getActivityType() != ActivityTypeEnum.fromName(findActivitySkuDto.getActivityType()).getId()) {
                throw new ServiceException(ResultCode.FAIL, String.format("所传的活动类型:[%s]不对,该活动对应的类型应该是:[%s]", findActivitySkuDto.getActivityType(), ActivityTypeEnum.getActivityTypeEnumById(activityRelActivityGoodsPo.getActivityType()).getName()));
            }
        }

        /**
         * 1.活动建议价的最低值：商品售价-活动成本
         *2.活动建议价的最大值：商品售价-商品售价*该次活动的比例
         * 3、固定成本(sku)=供货价(sku)+运营比例(sku)*销售价+利润比例(sku)*销售价
         * 4、活动成本=（商品售价(sku)-固定成本）*商品活动成本百分比(商品运营信息Tab)
         */
        //平台设置商品的活动成本比例(%)
        BigDecimal activityCostRate = BigDecimalUtil.safeDivide(goodsMapper.selectById(goodsId).getActivityCostRate(), new BigDecimal(100), new BigDecimal(-1));
        //循环获取sku信息
        AmActivityRelActivityGoodsPo finalActivityRelActivityGoodsPo = activityRelActivityGoodsPo;
        goodsSkuPos.forEach(x -> {
            Map<String, Object> mapBean = new HashMap<>();
            //获取除规格信息外的其他信息
            FindActivitySkuVo findActivitySkuVo = new FindActivitySkuVo();
            BeanUtils.copyProperties(x, findActivitySkuVo);
            findActivitySkuVo.setSkuId(x.getId());
            getActivitySkuInfoVo.setStoreId(finalActivityRelActivityGoodsPo.getStoreId());
            //计算活动建议价
            //售价
            BigDecimal sellPrice = x.getSellPrice();
            //获取供货价、运营成本比例(%)、利润比例(%)
            BigDecimal supplierPrice = x.getSupplierPrice();
            BigDecimal operationPrice = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(x.getOperationCost(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
            BigDecimal profitRate = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(x.getProfitRate(), new BigDecimal(100), new BigDecimal(-1)), sellPrice);
            //固定成本
            BigDecimal fixedCosts = BigDecimalUtil.safeAdd(supplierPrice, operationPrice, profitRate);
            //活动成本
            BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeSubtract(false, sellPrice, fixedCosts), activityCostRate);
            BigDecimal lowActivityPrice = BigDecimalUtil.safeSubtract(false, sellPrice, activityCost);
            BigDecimal highActivityPrice = null;
            switch (activityTypeEnum) {
                case REDUCED:
                    AmReducedPo amReducedPo = reducedMapper.selectById(activityId);
                    if (finalActivityRelActivityGoodsPo == null){
                        throw new ServiceException(ResultCode.FAIL,String.format("商品:[%s]不参与该活动:[%s],请检查",goodsPo.getName(),amReducedPo.getName()));
                    }
                    break;
                case INTEGRALS:
                    AmIntegralsPo integralsPo = integralsMapper.selectById(activityId);
                    if (finalActivityRelActivityGoodsPo == null){
                        throw new ServiceException(ResultCode.FAIL,String.format("商品:[%s]不参与该活动:[%s],请检查",goodsPo.getName(),integralsPo.getName()));
                    }
                    if (integralsPo != null) {
                        highActivityPrice = BigDecimalUtil.safeSubtract(false, sellPrice, BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(integralsPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1))));
                    }
                    break;
                case SECKILL:
                    AmSeckillPo seckillPo = seckillMapper.selectById(activityId);
                    if (finalActivityRelActivityGoodsPo == null){
                        throw new ServiceException(ResultCode.FAIL,String.format("商品:[%s]不参与该活动:[%s],请检查",goodsPo.getName(),seckillPo.getName()));
                    }
                    if (seckillPo != null) {
                        highActivityPrice = BigDecimalUtil.safeSubtract(false, sellPrice, BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(seckillPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1))));
                    }
                    break;
                case SPELL_GROUP:
                    AmSpellGroupPo spellGroupPo = spellGroupMapper.selectById(activityId);
                    if (finalActivityRelActivityGoodsPo == null){
                        throw new ServiceException(ResultCode.FAIL,String.format("商品:[%s]不参与该活动:[%s],请检查",goodsPo.getName(),spellGroupPo.getName()));
                    }
                    if (spellGroupPo != null) {
                        highActivityPrice = BigDecimalUtil.safeSubtract(false, sellPrice, BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(spellGroupPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1))));
                    }
                    break;
            }
            String recommendedActivityPrice;
            if (lowActivityPrice.equals(highActivityPrice) || highActivityPrice == null) {
                highActivityPrice = lowActivityPrice;
                recommendedActivityPrice = lowActivityPrice.toString();
            } else {
                recommendedActivityPrice = lowActivityPrice.toString() + "-" + highActivityPrice;
            }
            findActivitySkuVo.setRecommendedActivityPrice(recommendedActivityPrice);
            findActivitySkuVo.setLowActivityPrice(lowActivityPrice);
            findActivitySkuVo.setHighActivityPrice(highActivityPrice);
            //从am_activity_rel_goods_sku 平台活动的商品与sku关联表 获取活动价格和活动库存
            if (finalActivityRelActivityGoodsPo != null) {
                getActivitySkuInfoVo.setActivityGoodsRelId(finalActivityRelActivityGoodsPo.getId());
                getActivitySkuInfoVo.setActivityType(finalActivityRelActivityGoodsPo.getActivityType());
                getActivitySkuInfoVo.setPicture(finalActivityRelActivityGoodsPo.getPicture());
                getActivitySkuInfoVo.setRemark(finalActivityRelActivityGoodsPo.getRemark());
                getActivitySkuInfoVo.setStatus(VerifyStatusEnum.getVerifyStatusById(finalActivityRelActivityGoodsPo.getVerifyStatus()).getName());
                getActivitySkuInfoVo.setActivityId(findActivitySkuDto.getActivityId());
                getActivitySkuInfoVo.setActivityCostRate(goodsMapper.selectById(goodsId).getActivityCostRate());
                getActivitySkuInfoVo.setBuyLimit(finalActivityRelActivityGoodsPo.getBuyLimit());
                getActivitySkuInfoVo.setGoodsId(finalActivityRelActivityGoodsPo.getGoodsId());

                AmActivityRelGoodsSkuPo activityRelGoodsSkuPo = activityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                        .eq(AmActivityRelGoodsSkuPo::getRelId, finalActivityRelActivityGoodsPo.getId())
                        .eq(AmActivityRelGoodsSkuPo::getSkuId, x.getId()));
                if (activityRelGoodsSkuPo != null) {
                    findActivitySkuVo.setActivityPrice(activityRelGoodsSkuPo.getActivityPrice());
                    findActivitySkuVo.setActivityStock(activityRelGoodsSkuPo.getActivityStock());
                    findActivitySkuVo.setGoodsSkuRelId(activityRelGoodsSkuPo.getId());
                }
            }
            mapBean = JSONUtils.toBean(findActivitySkuVo, Map.class);

            //获取每条sku对应的规格信息，规格值与sku多对多关系
            Map<String, Object> relMap = new HashMap<>();
            relMap.put("goods_sku_id", x.getId());
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(relMap);
            //根据skuId获取属性值ID列表
            List<Long> valueIds = relAttributeValueSkuPos.stream().map(a -> a.getGoodsAttributeValueId()).collect(Collectors.toList());
            //根据属性值id查找属性值表，获取属性值和属性ID
//            List<AttributeInfos> attributeInfos = Lists.newArrayList();

            Map<String, Object> finalMap = mapBean;
            valueIds.forEach(b -> {
                PmGoodsAttributeValuePo valuePo = goodsAttributeValueMapper.selectById(b);
                if (valuePo == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在对应的属性值", b);
                }
                StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                standardValueAndStatusVo.setAttributeValueId(b);
                standardValueAndStatusVo.setAttributeValue(valuePo.getValue());
                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
                finalMap.put(attributeId.toString(), standardValueAndStatusVo);
//                attributeValues.add(map1);
            });

            mapList.add(mapBean);
        });
        getActivitySkuInfoVo.setSkuList(mapList);

        return getActivitySkuInfoVo;
    }

    /**
     * 判断商品是否符合不能参加在同一时间段内的活动、不符合活动要求
     *
     * @param isConformDto
     * @return
     */
    @Override
    public Boolean isComform(FindActivitySkuDto isConformDto) {

        Long goodsId = isConformDto.getGoodsId();
        SysUserPo userPo = securityUtil.getCurrUser();
        Long activityId = isConformDto.getActivityId();
        ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.fromName(isConformDto.getActivityType());
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("del_flag", false);
        List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(map);
        if (goodsSkuPos == null && goodsSkuPos.size() == 0) {
            return null;
        }
        //平台设置商品的活动成本比例(%)
        BigDecimal activityCostRate = BigDecimalUtil.safeDivide(goodsMapper.selectById(goodsId).getActivityCostRate(), new BigDecimal(100), new BigDecimal(-1));
        //获取当前活动时间并判断该商品是否已经参与同时段活动
        switch (activityTypeEnum) {
            case REDUCED:
                AmReducedPo reducedPo = reducedMapper.selectById(activityId);
                startTime = reducedPo.getActivityStartTime();
                endTime = reducedPo.getActivityEndTime();
                break;
            case INTEGRALS:
                AmIntegralsPo integralsPo = integralsMapper.selectById(activityId);
                startTime = integralsPo.getActivityStartTime();
                endTime = integralsPo.getActivityEndTime();
                break;
            case SECKILL:
                AmSeckillPo seckillPo = seckillMapper.selectById(activityId);
                startTime = seckillPo.getActivityStartTime();
                endTime = seckillPo.getActivityEndTime();
                break;
            case SPELL_GROUP:
                AmSpellGroupPo spellGroupPo = spellGroupMapper.selectById(activityId);
                startTime = spellGroupPo.getActivityStartTime();
                endTime = spellGroupPo.getActivityEndTime();
                break;
        }
        //查询该商家参与的所有有效的活动 am_activity_rel_activity_goods
        List<StoreActivityBo> storeActivityBos = activityRelActivityGoodsMapper.findStoreActivity(userPo.getStoreId(), isConformDto.getGoodsId());
        if (!ListUtil.isListNullAndEmpty(storeActivityBos)) {
            LocalDateTime finalStartTime = startTime;
            LocalDateTime finalEndTime = endTime;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            storeActivityBos.forEach(y -> {
                ActivityTypeEnum activityType = ActivityTypeEnum.getActivityTypeEnumById(y.getActivityType());
                switch (activityType) {
                    case REDUCED:
                        AmReducedPo reducedPo = reducedMapper.selectById(y.getActivityId());
                        LocalDateTime reduceStartTime = reducedPo.getActivityStartTime();
                        LocalDateTime reduceEndTime = reducedPo.getActivityEndTime();
                        if ((reduceStartTime.isAfter(finalStartTime) && reduceStartTime.isBefore(finalEndTime)) ||
                                (reduceEndTime.isAfter(finalStartTime) && reduceEndTime.isBefore(finalEndTime))) {
                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
                        }
                        /**满减活动条件限制：4、活动成本/售价>=满减活动比例
                         *
                         * 1、固定成本(sku)=供货价(sku)+运营比例(sku)*销售价+利润比例(sku)*销售价
                         * 2、活动成本=（商品售价(sku)-固定成本）*商品活动成本百分比(商品运营信息Tab)
                         * 3、满减活动比例 = 减/满
                         */
                        for (PmGoodsSkuPo b : goodsSkuPos) {
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
                            BigDecimal reductionFullMoney = reducedPo.getReductionFullMoney();
                            //减金额
                            BigDecimal reductionPostMoney = reducedPo.getReductionPostMoney();
                            //满减比例
                            BigDecimal rate = BigDecimalUtil.safeDivide(reductionPostMoney, reductionFullMoney, new BigDecimal(-1));
                            //活动成本/售价
                            BigDecimal activitySaleRate = BigDecimalUtil.safeDivide(activityCost, sellPrice, new BigDecimal(-1));
                            //不满足条件
                            if (activitySaleRate.compareTo(rate) < 0) {
                                throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]不满足该活动:[%s]的条件", goodsMapper.selectById(goodsId).getName(), reducedPo.getName()));
                            }
                        }
                        break;
                    /**
                     * 活动积分判断
                     *
                     * 活动成本-优惠金额>=0
                     */
                    case INTEGRALS:
                        AmIntegralsPo integralsPo = integralsMapper.selectById(y.getActivityId());
                        LocalDateTime intergralsStartTime = integralsPo.getActivityStartTime();
                        LocalDateTime integralsEndTime = integralsPo.getActivityEndTime();
                        if ((intergralsStartTime.isAfter(finalStartTime) && intergralsStartTime.isBefore(finalEndTime)) ||
                                (integralsEndTime.isAfter(finalStartTime) && integralsEndTime.isBefore(finalEndTime))) {
                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
                        }
                        for (PmGoodsSkuPo b : goodsSkuPos) {
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
                            //优惠金额
                            BigDecimal discount = BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(integralsPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1)));
                            if (activityCost.compareTo(discount) < 0) {
                                throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]不满足该活动:[%s]的条件", goodsMapper.selectById(goodsId).getName(), integralsPo.getName()));
                            }

                        }
                        break;
                    case SECKILL:
                        AmSeckillPo seckillPo = seckillMapper.selectById(y.getActivityId());
                        LocalDateTime seckillStartTime = seckillPo.getActivityStartTime();
                        LocalDateTime seckillEndTime = seckillPo.getActivityEndTime();
                        if ((seckillStartTime.isAfter(finalStartTime) && seckillStartTime.isBefore(finalEndTime)) ||
                                (seckillEndTime.isAfter(finalStartTime) && seckillEndTime.isBefore(finalEndTime))) {
                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
                        }
                        for (PmGoodsSkuPo b : goodsSkuPos) {
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
                            //优惠金额
                            BigDecimal discount = BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(seckillPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1)));
                            if (activityCost.compareTo(discount) < 0) {
                                throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]不满足该活动:[%s]的条件", goodsMapper.selectById(goodsId).getName(), seckillPo.getName()));
                            }
                        }
                        break;
                    case SPELL_GROUP:
                        AmSpellGroupPo spellGroupPo = spellGroupMapper.selectById(y.getActivityId());
                        LocalDateTime spellGroupStartTime = spellGroupPo.getActivityStartTime();
                        LocalDateTime spellGroupEndTime = spellGroupPo.getActivityEndTime();
                        if ((spellGroupStartTime.isAfter(finalStartTime) && spellGroupStartTime.isBefore(finalEndTime)) ||
                                (spellGroupEndTime.isAfter(finalStartTime) && spellGroupEndTime.isBefore(finalEndTime))) {
                            throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]在该时间段:[%s]内已参加其他活动,请重新选择!", goodsMapper.selectById(goodsId).getName(), (dateTimeFormatter.format(finalStartTime) + "～" + dateTimeFormatter.format(finalEndTime))));
                        }
                        for (PmGoodsSkuPo b : goodsSkuPos) {
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
                            //优惠金额
                            BigDecimal discount = BigDecimalUtil.safeMultiply(sellPrice, BigDecimalUtil.safeDivide(spellGroupPo.getDiscountPriceRatio(), new BigDecimal(100), new BigDecimal(-1)));
                            if (activityCost.compareTo(discount) < 0) {
                                throw new ServiceException(ResultCode.FAIL, String.format("该商品:[%s]不满足该活动:[%s]的条件", goodsMapper.selectById(goodsId).getName(), spellGroupPo.getName()));
                            }
                        }
                        break;
                }
            });
        }

        return true;
    }

    /**
     * 保存商家端的报名活动信息
     *
     * @param saveRegistrationDto
     * @return
     */
    @Override
    public void saveRegistration(SaveRegistrationDto saveRegistrationDto) {
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if (sysUserPo.getStoreId() == null){
            throw new ServiceException(ResultCode.FAIL,String.format("用户:[%s]不是商家用户,不能执行此操作!",sysUserPo.getUsername()));
        }
        //判断添加或修改操作
        if (saveRegistrationDto.getActivityGoodsRelId() == 0){
            //先保存活动与商品关联表
            AmActivityRelActivityGoodsPo relActivityGoodsPo = new AmActivityRelActivityGoodsPo();
            BeanUtils.copyProperties(saveRegistrationDto,relActivityGoodsPo);
            relActivityGoodsPo.setStoreId(sysUserPo.getStoreId());
            relActivityGoodsPo.setCreateBy(sysUserPo.getUsername());
            relActivityGoodsPo.setId(null);
            relActivityGoodsPo.setActivityType(ActivityTypeEnum.fromName(saveRegistrationDto.getActivityType()).getId());
            activityRelActivityGoodsMapper.insert(relActivityGoodsPo);
            //再保存参与活动的商品与sku关联表
            saveRegistrationDto.getActivitySkuDtoList().forEach(a->{
                AmActivityRelGoodsSkuPo relGoodsSkuPo = new AmActivityRelGoodsSkuPo();
                BeanUtils.copyProperties(a,relGoodsSkuPo);
                relGoodsSkuPo.setCreateBy(sysUserPo.getUsername());
                relGoodsSkuPo.setRelId(relActivityGoodsPo.getId());
                relGoodsSkuPo.setGoodsId(saveRegistrationDto.getGoodsId());
                activityRelGoodsSkuMapper.insert(relGoodsSkuPo);
            });
        }
        //修改操作
        else{
            AmActivityRelActivityGoodsPo relActivityGoodsPo = activityRelActivityGoodsMapper.selectById(saveRegistrationDto.getActivityGoodsRelId());
            if (relActivityGoodsPo.getVerifyStatus() != VerifyStatusEnum.MODIFY.getId()){
                throw new ServiceException(ResultCode.FAIL,String.format("该状态:[%s]不是返回修改状态，不能执行修改操作!",VerifyStatusEnum.getVerifyStatusById(relActivityGoodsPo.getActivityType()).getName()));
            }
            BeanUtils.copyProperties(saveRegistrationDto,relActivityGoodsPo);
            relActivityGoodsPo.setUpdateBy(sysUserPo.getUsername());
            activityRelActivityGoodsMapper.updateById(relActivityGoodsPo);
            List<AmActivityRelGoodsSkuPo> relGoodsSkuPoList = activityRelGoodsSkuMapper.selectList(new QueryWrapper<AmActivityRelGoodsSkuPo>().eq("rel_id",relActivityGoodsPo.getId()).eq("goods_id",relActivityGoodsPo.getGoodsId()));
            relGoodsSkuPoList.forEach(b->{
                AmActivityRelGoodsSkuPo relGoodsSkuPo = activityRelGoodsSkuMapper.selectById(b.getId());
                BeanUtils.copyProperties(b,relGoodsSkuPo);
                relGoodsSkuPo.setUpdateBy(sysUserPo.getUsername());
                activityRelGoodsSkuMapper.updateById(relGoodsSkuPo);
            });

        }
    }

    /**
     * 商家端查找参与的活动
     *
     * @param searchSupplierActivityDto
     * @return
     */
    @Override
    public PageInfo<SearchSupplierActivityVo> searchSupplierActivity(SearchSupplierActivityDto searchSupplierActivityDto) {

        SysUserPo sysUserPo = securityUtil.getCurrUser();
        Long storeId = sysUserPo.getStoreId();
        Integer pageNum = searchSupplierActivityDto.getPageNo() == null ? defaultPageNo : searchSupplierActivityDto.getPageNo();
        Integer pageSize = searchSupplierActivityDto.getPageSize() == null ? defaultPageSize : searchSupplierActivityDto.getPageSize();
        ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.fromName(searchSupplierActivityDto.getActivityType());
        String tableName = null;
        switch (activityTypeEnum) {
            case REDUCED:
                tableName = "am_reduced";
                break;
            case INTEGRALS:
                tableName = "am_integrals";
                break;
            case SECKILL:
                tableName = "am_seckill";
                break;
            case SPELL_GROUP:
                tableName = "am_spell_group";
                break;
        }
        String finalTableName = tableName;
        PageInfo<SearchSupplierActivityVo> searchSupplierActivityVoPageInfo = PageHelper.startPage(pageNum,pageSize)
                .doSelectPageInfo(()->mapper.searchSupplierActivity(searchSupplierActivityDto, finalTableName,storeId));
        searchSupplierActivityVoPageInfo.getList().forEach(a->{
            FindActivitySkuDto findActivitySkuDto = new FindActivitySkuDto();
            findActivitySkuDto.setGoodsId(a.getGoodsId());
            findActivitySkuDto.setActivityId(a.getActivityId());
            findActivitySkuDto.setActivityType(ActivityTypeEnum.getActivityTypeEnumById(a.getActivityType()).getName());
            a.setGoodsDetail(this.findActivitySku(findActivitySkuDto));
//            List<Object> skuIds = this.findActivitySku(findActivitySkuDto).getSkuList().stream().map(b->b.get("skuId")).collect(Collectors.toList());
//            List<Long> skuIdList = JSONUtils.toList(skuIds,Long.class);
//            a.setSkuIds(skuIdList);
            List<Object> goodsSkuRelIds = this.findActivitySku(findActivitySkuDto).getSkuList().stream().map(b->b.get("goodsSkuRelId")).collect(Collectors.toList());
            List<Long> goodsSkuRelIdList = JSONUtils.toList(goodsSkuRelIds,Long.class);
            a.setGoodsSkuRelIds(goodsSkuRelIdList);
        });

        return searchSupplierActivityVoPageInfo;
    }

    /**
     * 商家端取消活动报名
     * @param cancelRegistrationDtos
     * @return
     */
    @Override
    public void cancelRegistration(List<CancelRegistrationDto> cancelRegistrationDtos) {

        cancelRegistrationDtos.forEach(a->{
            //获取审核状态
            Integer verifyStatus = activityRelActivityGoodsMapper.selectById(a.getActivityGoodsRelId()).getVerifyStatus();
            if (verifyStatus != VerifyStatusEnum.WAIT_CONFIRM.getId()){
                throw new ServiceException(ResultCode.FAIL,"该活动审核状态:[%s]不是待审核,不能取消报名！",VerifyStatusEnum.getVerifyStatusById(verifyStatus));
            }

            //删除平台活动与商品关联表
            activityRelActivityGoodsMapper.deleteById(a.getActivityGoodsRelId());
            //删除活动商品和对应的sku信息
            List<Long> goodsSkuRelIdList = a.getGoodsSkuRelIds().stream().filter(b->!b.equals(0)).collect(Collectors.toList());
            activityRelGoodsSkuMapper.deleteBatchIds(goodsSkuRelIdList);
        });
    }

    /**
     * 修改报名的活动的状态
     *
     * @param updateVerifyStatusDto
     * @return
     */
    @Override
    public void updateVerifyStatus(UpdateVerifyStatusDto updateVerifyStatusDto) {

        VerifyStatusEnum verifyStatusEnum = VerifyStatusEnum.getVerifyStatusById(updateVerifyStatusDto.getVerifyStatus());
        Long activityGoodsRelId = updateVerifyStatusDto.getActivityGoodsRelId();
        AmActivityRelActivityGoodsPo relActivityGoodsPo = activityRelActivityGoodsMapper.selectById(activityGoodsRelId);
        if (!relActivityGoodsPo.getVerifyStatus().equals(VerifyStatusEnum.WAIT_CONFIRM.getId())){
            throw new ServiceException(ResultCode.FAIL,String.format("该状态:[%s]不是待审核状态，不能执行[%s]操作",VerifyStatusEnum.getVerifyStatusById(relActivityGoodsPo.getVerifyStatus()).getName(),VerifyStatusEnum.getVerifyStatusById(updateVerifyStatusDto.getVerifyStatus()).getName()));
        }
        relActivityGoodsPo.setUpdateBy(securityUtil.getCurrUser().getUsername());
        relActivityGoodsPo.setVerifyStatus(updateVerifyStatusDto.getVerifyStatus());
        relActivityGoodsPo.setRefuseCase(updateVerifyStatusDto.getRefuseCase());
        relActivityGoodsPo.setVerifier(securityUtil.getCurrUser().getUsername());
        relActivityGoodsPo.setVerifyTime(LocalDateTime.now());
        activityRelActivityGoodsMapper.updateById(relActivityGoodsPo);
    }

    /**
     *  条件查询拼团记录
     * @param searchSpellRecordDto
     * @return
     */
    @Override
    public PageInfo<SearchSpellRecordVo> searchSpellRecord(SearchSpellRecordDto searchSpellRecordDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        searchSpellRecordDto.setStoreId(userPo.getStoreId());
        Integer pageNo = searchSpellRecordDto.getPageNo() == null ? defaultPageNo : searchSpellRecordDto.getPageNo();
        Integer pageSize = searchSpellRecordDto.getPageSize() == null ? defaultPageSize : searchSpellRecordDto.getPageSize();
        PageInfo<SearchSpellRecordVo> searchSpellRecordDtoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->spellGroupMainMapper.searchSpellRecord(searchSpellRecordDto));

        return searchSpellRecordDtoPageInfo;
    }
}
