package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.app.activity.SpellGroupMainStatusEnum;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.message.ArticleLocationEnum;
import com.chauncy.common.enums.message.KeyWordTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.*;
import com.chauncy.data.bo.app.car.ActivitySkuBo;
import com.chauncy.data.bo.app.car.FullDiscountSkuBo;
import com.chauncy.data.bo.app.car.MoneyShipBo;
import com.chauncy.data.bo.app.order.reward.RewardShopTicketBo;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.bo.manage.pay.PayUserMessage;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.domain.po.activity.integrals.AmIntegralsPo;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupMainPo;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupMemberPo;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.domain.po.order.*;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.pay.PayUserRelationPo;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.domain.po.user.UmUserFavoritesPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.car.*;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.dto.app.order.cart.update.RemoveToFavoritesDto;
import com.chauncy.data.dto.app.order.cart.update.UpdateCartSkuDto;
import com.chauncy.data.mapper.activity.coupon.AmCouponMapper;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponGoodsMapper;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponUserMapper;
import com.chauncy.data.mapper.activity.integrals.AmIntegralsMapper;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelActivityGoodsMapper;
import com.chauncy.data.mapper.activity.registration.AmActivityRelGoodsSkuMapper;
import com.chauncy.data.mapper.activity.seckill.AmSeckillMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMainMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMapper;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMemberMapper;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.mapper.message.advice.MmAdviceMapper;
import com.chauncy.data.mapper.message.content.MmArticleMapper;
import com.chauncy.data.mapper.order.*;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmAreaShippingMapper;
import com.chauncy.data.mapper.user.UmUserFavoritesMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.temp.order.service.IOmGoodsTempService;
import com.chauncy.data.vo.app.activity.coupon.SelectCouponVo;
import com.chauncy.data.vo.app.activity.spell.SpellGroupInfoVo;
import com.chauncy.data.vo.app.advice.activity.*;
import com.chauncy.data.vo.app.advice.coupon.FindCouponListVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.evaluate.EvaluateLevelNumVo;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.app.order.cart.SubmitOrderVo;
import com.chauncy.order.service.IPayUserRelationNextLevelService;
import com.chauncy.data.vo.app.car.*;
import com.chauncy.data.vo.app.goods.*;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.cart.MyCartVo;
import com.chauncy.data.vo.app.order.cart.StoreGoodsVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.order.service.IPayUserRelationService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车列表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmShoppingCartServiceImpl extends AbstractService<OmShoppingCartMapper, OmShoppingCartPo> implements IOmShoppingCartService {

    @Autowired
    private OmShoppingCartMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PmGoodsSkuMapper skuMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private PmGoodsLikedMapper goodsLikedMapper;

    @Autowired
    private MmArticleMapper articleMapper;

    @Autowired
    private AmCouponRelCouponGoodsMapper relCouponGoodsMapper;

    @Autowired
    private AmCouponRelCouponUserMapper relCouponUserMapper;

    @Autowired
    private AmActivityRelActivityGoodsMapper relActivityGoodsMapper;

    @Autowired
    private AmActivityRelGoodsSkuMapper activityRelGoodsSkuMapper;

    @Autowired
    private AmReducedMapper reducedMapper;

    @Autowired
    private AmIntegralsMapper integralsMapper;

    @Autowired
    private AmSeckillMapper seckillMapper;

    @Autowired
    private AmSpellGroupMapper spellGroupMapper;

    @Autowired
    private AmSpellGroupMainMapper spellGroupMainMapper;

    @Autowired
    private AmSpellGroupMemberMapper spellGroupMemberMapper;

    @Autowired
    private AmCouponMapper couponMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private MmAdviceMapper adviceMapper;

    @Autowired
    private UmUserFavoritesMapper userFavoritesMapper;

    @Autowired
    private PmAssociationGoodsMapper associationGoodsMapper;

    @Autowired
    private OmEvaluateMapper evaluateMapper;

    @Autowired
    private OmEvaluateLikedMapper evaluateLikedMapper;

    @Autowired
    private PmGoodsCategoryMapper categoryMapper;

    @Autowired
    private PmGoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper relAttributeValueSkuMapper;

    @Autowired
    private PmGoodsRelAttributeCategoryMapper relAttributeCategoryMapper;

    @Autowired
    private PmGoodsAttributeValueMapper attributeValueMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private PmMemberLevelMapper levelMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private UmAreaShippingMapper umAreaShippingMapper;

    @Autowired
    private AreaRegionMapper areaRegionMapper;

    @Autowired
    private PmNumberShippingMapper numberShippingMapper;

    @Autowired
    private PmShippingTemplateMapper shippingTemplateMapper;

    @Autowired
    private PmMoneyShippingMapper moneyShippingMapper;

    @Autowired
    private PmGoodsRelAttributeGoodMapper relAttributeGoodMapper;

    @Autowired
    private IPayOrderMapper payOrderMapper;

    @Autowired
    private IOmGoodsTempService goodsTempService;

    @Autowired
    private IOmOrderService omOrderService;

    @Autowired
    private OmEvaluateQuartzMapper evaluateQuartzMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IPayUserRelationService payUserRelationService;

    @Autowired
    private IPayUserRelationNextLevelService payUserRelationNextLevelService;

    @Autowired
    private AmActivityRelGoodsSkuMapper amActivityRelGoodsSkuMapper;

    @Autowired
    private OmRealUserMapper realUserMapper;

    @Value("${distribution.im.account}")
    private String imAccount;


    //拆单后每个订单至少要支付0.01
    private BigDecimal orderMinPayMoney = BigDecimal.valueOf(0.01);


    //需要进行实行认证且计算税率的商品类型
    private final List<String> needRealGoodsType = Lists.newArrayList("保税仓", "海外直邮");

    /**
     * 添加商品到购物车
     *
     * @param addCartDto
     * @return
     */
    @Override
    public void addToCart(AddCartDto addCartDto) {
        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if (umUserPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "您不是APP用户!");
        }
        //判断购物车是否存在该商品
        Map<String, Object> map = new HashMap<>();
        map.put("sku_id", addCartDto.getSkuId());
        map.put("user_id", umUserPo.getId());
        List<OmShoppingCartPo> shoppingCartPos = mapper.selectByMap(map);
        boolean exit = shoppingCartPos != null && shoppingCartPos.size() != 0;
        //判断当前库存是否足够
        Integer originStock = skuMapper.selectById(addCartDto.getSkuId()).getStock();

        //查找购物车是否已经存在该商品
        if (exit) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("sku_id", addCartDto.getSkuId());
            OmShoppingCartPo shoppingCartPo = mapper.selectOne(queryWrapper);
            shoppingCartPo.setNum(shoppingCartPo.getNum() + addCartDto.getNum());
            if (originStock < shoppingCartPo.getNum()) {
                throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY, "库存不足!");
            }
            mapper.updateById(shoppingCartPo);
        }
        //不存在
        else {
            if (originStock < addCartDto.getNum()) {
                throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY, "库存不足!");
            }
            OmShoppingCartPo omShoppingCartPo = new OmShoppingCartPo();
            BeanUtils.copyProperties(addCartDto, omShoppingCartPo);
            omShoppingCartPo.setUserId(umUserPo.getId());
            omShoppingCartPo.setId(null);
            omShoppingCartPo.setCreateBy(umUserPo.getName());
            mapper.insert(omShoppingCartPo);
        }
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @Override
    public MyCartVo SearchCart(SearchCartDto searchCartDto) {

        MyCartVo myCartVo = new MyCartVo();

        //获取当前用户
        UmUserPo userPo = securityUtil.getAppCurrUser();
        Long memberLevelId = userPo.getMemberLevelId();

        Integer pageNo = searchCartDto.getPageNo() == null ? defaultPageNo : searchCartDto.getPageNo();
        Integer pageSize = searchCartDto.getPageSize() == null ? defaultPageSize : searchCartDto.getPageSize();
        PageInfo<CartVo> cartVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchCart(userPo.getId()));
        //记录已经失效的商品
//        List<StoreGoodsVo> disableList = Lists.newArrayList();

        final Integer[] num = {0};
        //对购物车库存处理
        cartVoPageInfo.getList().forEach(a -> {
            List<StoreGoodsVo> storeGoodsVos = Lists.newArrayList();
            List<StoreGoodsVo> validGoodsVos = Lists.newArrayList();
            a.getStoreGoodsVoList().forEach(b -> {
                Integer sum = skuMapper.selectById(b.getSkuId()).getStock();
                b.setSum(sum);
                //库存不足处理
                if (sum == 0) {
                    b.setIsSoldOut(true);
                    b.setNum(0);
                } else if (b.getNum() >= sum) {
                    b.setNum(sum);
                }

                RewardShopTicketBo rewardShopTicketBo = skuMapper.getRewardShopTicket(b.getSkuId());
                //商品信息
                PmGoodsPo goods = goodsMapper.selectById(skuMapper.selectById(b.getSkuId()).getGoodsId());

                //商品活动百分比
                rewardShopTicketBo.setActivityCostRate(goods.getActivityCostRate());
                //让利成本比例
                rewardShopTicketBo.setProfitsRate(goods.getProfitsRate());

                //会员等级比例
                BigDecimal purchasePresent = levelMapper.selectById(memberLevelId).getPurchasePresent();
                rewardShopTicketBo.setPurchasePresent(purchasePresent);
                //购物券比例
                BigDecimal moneyToShopTicket = basicSettingMapper.selectList(null).get(0).getMoneyToShopTicket();
                rewardShopTicketBo.setMoneyToShopTicket(moneyToShopTicket);
                BigDecimal rewardShopTicket = rewardShopTicketBo.getRewardShopTicket();

                b.setRewardShopTicke(rewardShopTicket);


                //活动处理
                /*********************** 商品参加的活动,点击商品进来需要展示的信息 start***************************/
                //查询已经开始的活动
                AmActivityRelActivityGoodsPo relActivityGoodsPo = relActivityGoodsMapper.findGoodsActivity(b.getGoodsId());
                if (relActivityGoodsPo != null) {
                    ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.getActivityTypeEnumById(relActivityGoodsPo.getActivityType());
                    switch (activityTypeEnum) {
                        //满减
                        case REDUCED:
                            AmReducedPo reducedPo = reducedMapper.selectOne(new QueryWrapper<AmReducedPo>().lambda().and(obj ->
                                    obj.eq(AmReducedPo::getEnable, true).eq(AmReducedPo::getId, relActivityGoodsPo.getActivityId())));
                            if (reducedPo == null) {
//                        throw new ServiceException(ResultCode.NO_EXISTS,String.format("该商品参加的满减活动不存在"));
                                b.setActivityType(ActivityTypeEnum.NON.getId());
                            } else {
                                b.setActivityType(ActivityTypeEnum.REDUCED.getId());
                                CartReducedVo reducedVo = new CartReducedVo();
                                reducedVo.setReductionFullMoney(reducedPo.getReductionFullMoney());
                                reducedVo.setReductionPostMoney(reducedPo.getReductionPostMoney());
                                b.setCartReducedVo(reducedVo);
                            }
                            break;
                        //积分--积分抵扣
                        case INTEGRALS:
                            AmIntegralsPo integralsPo = integralsMapper.selectOne(new QueryWrapper<AmIntegralsPo>().lambda().and(obj ->
                                    obj.eq(AmIntegralsPo::getEnable, true).eq(AmIntegralsPo::getId, relActivityGoodsPo.getActivityId())));
                            if (integralsPo == null) {
//                        throw new ServiceException(ResultCode.NO_EXISTS,String.format("该商品参加的积分活动不存在"));
                                b.setActivityType(ActivityTypeEnum.NON.getId());
                            } else {
                                b.setActivityType(ActivityTypeEnum.INTEGRALS.getId());
                                CartIntegralsVo integralsVo = new CartIntegralsVo();

                                //获取该sku参加活动的信息
                                AmActivityRelGoodsSkuPo relGoodsSkuPo = activityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                                        .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())
                                                .eq(AmActivityRelGoodsSkuPo::getSkuId, b.getSkuId())));
                                //判断sku是否存在
                                PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(b.getSkuId());
                                BigDecimal integralsDeduction = new BigDecimal(0);
                                if (goodsSkuPo != null && relGoodsSkuPo != null) {
                                    //获取原来sku销售价
                                    BigDecimal skuSellPrice = goodsSkuPo.getSellPrice();
                                    //积分抵扣
                                    integralsDeduction = BigDecimalUtil.safeSubtract(true, skuSellPrice, relGoodsSkuPo.getActivityPrice());
                                }
                                integralsVo.setDiscountPrice(integralsDeduction);
                                b.setCartIntegralsVo(integralsVo);

                            }
                            break;
                        case SECKILL:
                            AmSeckillPo seckillPo = seckillMapper.selectOne(new QueryWrapper<AmSeckillPo>().lambda().and(obj ->
                                    obj.eq(AmSeckillPo::getEnable, true).eq(AmSeckillPo::getId, relActivityGoodsPo.getActivityId())));
                            if (seckillPo == null) {
                                b.setActivityType(ActivityTypeEnum.NON.getId());
                            } else {
                                b.setActivityType(ActivityTypeEnum.SECKILL_ING.getId());
                                CartSeckillVo seckillVo = new CartSeckillVo();
                                //秒杀结束时间
                                seckillVo.setActivityEndTime(seckillPo.getActivityEndTime());

                                //获取该sku参与活动的信息
                                AmActivityRelGoodsSkuPo relGoodsSkuPo = activityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                                        .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())
                                                .eq(AmActivityRelGoodsSkuPo::getSkuId, b.getSkuId())));
                                //判断sku是否存在
                                PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(b.getSkuId());
                                if (goodsSkuPo != null && relGoodsSkuPo != null) {
                                    //保存sku对应的活动价格
                                    seckillVo.setActivityPrice(relGoodsSkuPo.getActivityPrice());
                                    seckillVo.setSeckillName(seckillPo.getName());
                                }

                                b.setCartSeckillVo(seckillVo);

                            }
                            break;
                    }
                }

                /********* 查询是否为距离当前时间为一天的秒杀商品 start ***************/
                AmActivityRelActivityGoodsPo relSecKilllGoodsPo = relActivityGoodsMapper.findPreSeckill(b.getGoodsId());
                if (relSecKilllGoodsPo != null) {
                    AmSeckillPo seckillPo = seckillMapper.selectOne(new QueryWrapper<AmSeckillPo>().lambda().and(obj ->
                            obj.eq(AmSeckillPo::getEnable, true).eq(AmSeckillPo::getId, relSecKilllGoodsPo.getActivityId())));
                    //活动是否取消
                    if (seckillPo == null) {
                        b.setActivityType(ActivityTypeEnum.NON.getId());
                    } else {
                        b.setActivityType(ActivityTypeEnum.SECKILL_PRE.getId());
                        CartSeckillVo seckillVo = new CartSeckillVo();
                        //秒杀开始时间
                        seckillVo.setActivityStartTime(seckillPo.getActivityStartTime());

                        AmActivityRelGoodsSkuPo relGoodsSkuPos = activityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                                .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())
                                        .eq(AmActivityRelGoodsSkuPo::getSkuId, b.getSkuId())));
                        //判断sku是否存在
                        PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(b.getSkuId());
                        if (goodsSkuPo != null && seckillPo != null) {
                            //保存sku对应的活动价格
                            seckillVo.setSeckillName(seckillPo.getName());
                            seckillVo.setActivityPrice(relGoodsSkuPos.getActivityPrice());
                        }


                        b.setCartSeckillVo(seckillVo);

                    }
                }
                /********* 查询是否为距离当前时间为一天的秒杀商品 end ***************/

                /*********************** 商品参加的活动,点击商品进来需要展示的信息 end***************************/

                storeGoodsVos.add(b);
            });
            //失效id
//            List<Long> disableIds = disableList.stream().map(g -> g.getSkuId()).collect(Collectors.toList());
            //获取除失效的商品
//            validGoodsVos = storeGoodsVos.stream().filter(d -> !disableIds.contains(d.getSkuId())).collect(Collectors.toList());
//            storeGoodsVos.removeAll(disableList);

//            a.setStoreGoodsVoList(validGoodsVos);
            num[0] += a.getStoreGoodsVoList().size();
        });

        List<StoreGoodsVo> disableList = mapper.searchDisableList(userPo.getId());
        Integer disableNum = 0;
        if (!ListUtil.isListNullAndEmpty(disableList)) {
            disableNum = disableList.size();
        }

        myCartVo.setNum(num[0] + disableNum);
        myCartVo.setCartVo(cartVoPageInfo);
        myCartVo.setDisableList(disableList);

        return myCartVo;
    }

    /**
     * 批量删除购物车
     *
     * @return
     */
    @Override
    public void delCart(Long[] ids) {
        //判断商品是否存在
        Arrays.asList(ids).forEach(a -> {
            if (mapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, "数据不存在");
            }
        });
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 修改购物车商品
     *
     * @param updateCartDto
     * @return
     */
    @Override
    public void updateCart(UpdateCartSkuDto updateCartDto) {

        UmUserPo user = securityUtil.getAppCurrUser();

        OmShoppingCartPo cartPo = new OmShoppingCartPo();
        BeanUtils.copyProperties(updateCartDto, cartPo);

        Long skuId = mapper.selectById(updateCartDto.getId()).getSkuId();
        if (!skuId.equals(updateCartDto.getNeedChangeId())) {
            throw new ServiceException(ResultCode.FAIL, String.format("需要修改的sku:【%s】不属于该购物车id:[%s],请检查",
                    updateCartDto.getNeedChangeId(), updateCartDto.getId()));
        }

        if (updateCartDto.getSelectId() != 0) {

            //获取该用户收藏的所有sku
            List<Long> skuIds = mapper.selectList(new QueryWrapper<OmShoppingCartPo>().lambda()
                    .eq(OmShoppingCartPo::getUserId, user.getId())).stream().map(a -> a.getSkuId()).collect(Collectors.toList());

            //是否针对相同的商品
            Long goodsId1 = skuMapper.selectById(updateCartDto.getNeedChangeId()).getGoodsId();
            Long goodsId2 = skuMapper.selectById(updateCartDto.getSelectId()).getGoodsId();
            if (!goodsId1.equals(goodsId2)) {
                throw new ServiceException(ResultCode.FAIL, "更改的sku不属于需要被更改的商品");
            }
            //判断更改前的sku和更改后的sku是否一样
            if (updateCartDto.getSelectId().equals(updateCartDto.getNeedChangeId())) {
                throw new ServiceException(ResultCode.FAIL, "您修改后的购买信息和修改前一致");
            } else if (skuIds.contains(updateCartDto.getSelectId())) {
                mapper.deleteById(updateCartDto.getId());
            } else {
                //更改后的sku保存到购物车的数量为1
                cartPo.setNum(1);
                cartPo.setSkuId(updateCartDto.getSelectId());
            }
        }

        mapper.updateById(cartPo);
    }

    @Override
    public TotalCarVo searchByIds(SettleDto settleDto, UmUserPo currentUser) {
        List<SettleAccountsDto> settleAccountsDtos = settleDto.getSettleAccountsDtos();
        List<Long> skuIds = settleAccountsDtos.stream().map(x -> x.getSkuId()).collect(Collectors.toList());
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos = mapper.searchByIds(skuIds);

        final Integer[] activityType = {0};

        //sku对应的数量
        shopTicketSoWithCarGoodVos.forEach(x -> {
            //为查询后的id匹配上用户下单的数量
            x.setNumber(settleAccountsDtos.stream().filter(y -> y.getSkuId().equals(x.getId())).findFirst().get().getNumber());
        });

        //判断限购
        List<ActivitySkuBo> boByIds = amActivityRelGoodsSkuMapper.getBoByIds(skuIds);
        boByIds.forEach(x -> {
            Integer buyNumber = shopTicketSoWithCarGoodVos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().get().getNumber();
            if (x.getBuyLimit() != 0 && x.getBuyLimit() < buyNumber) {
                String goodsName = skuMapper.getGoodsName(x.getSkuId());
                throw new ServiceException(ResultCode.FAIL,
                        String.format("商品名称【%s】购买数量【%s】大于限购数量【%s】，请重新选择。", goodsName, buyNumber, x.getBuyLimit()));
            }

        });
        //如果是拼团结算
        BigDecimal groupDiscountMoney = BigDecimal.ZERO;
        if (settleDto.getIsGroup() != null && settleDto.getIsGroup()) {
            ActivitySkuBo groupSku = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.SPELL_GROUP).findFirst().orElse(null);
            if (groupSku == null) {
                throw new ServiceException(ResultCode.FAIL, "当前商品无参加拼团活动");
            }
            if (settleDto.getSettleAccountsDtos().size() != 1) {
                throw new ServiceException(ResultCode.FAIL, "拼团只能有一个商品！");
            }
            //如果是参团，判断人数有没有满
            if (settleDto.getMainId() != null) {
                AmSpellGroupMainPo queryGroupMain = spellGroupMainMapper.selectById(settleDto.getMainId());
                if (queryGroupMain.getStatus() == SpellGroupMainStatusEnum.SPELL_GROUP_SUCCESS.getId()) {
                    throw new ServiceException(ResultCode.FAIL, "手太慢了！该拼团人员满了！");
                }
                AmActivityRelActivityGoodsPo queryRelActivityGoods = relActivityGoodsMapper.selectById(queryGroupMain.getRelId());
                if (queryRelActivityGoods == null) {
                    throw new ServiceException(ResultCode.FAIL, "数据错误！参加的团所关联的商品为空");
                }
                if (!groupSku.getActivityId().equals(queryRelActivityGoods.getActivityId())) {
                    throw new ServiceException(ResultCode.FAIL, "数据错误！商品未参加该拼团活动！");
                }
            }
            AmSpellGroupPo querySpellGroup = spellGroupMapper.selectById(groupSku.getActivityId());
            PmMemberLevelPo memberLevel = memberLevelMapper.selectById(querySpellGroup.getMemberLevelId());
            //用户会员等级大于等于活动限定的等级
            if (currentUser.getLevel() < memberLevel.getLevel()) {
                throw new ServiceException(ResultCode.FAIL,
                        String.format("该活动参与最低等级为【%s】,用户等级为【%s】，无法购买此活动商品", memberLevel.getLevel(), currentUser.getLevel()));
            }
            shopTicketSoWithCarGoodVos.get(0).setRealPayMoney(groupSku.getActivityPrice()).setType(ActivityTypeEnum.SPELL_GROUP.getId());
            activityType[0] =4;


            groupDiscountMoney = BigDecimalUtil.safeMultiply(shopTicketSoWithCarGoodVos.get(0).getNumber(),
                    BigDecimalUtil.safeSubtract(shopTicketSoWithCarGoodVos.get(0).getSellPrice(), groupSku.getActivityPrice()));


        }


        //秒杀销售价改为活动价
        List<ActivitySkuBo> seckills = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.SECKILL).collect(Collectors.toList());
        seckills.forEach(x -> {
            //查询秒杀限定的会员等级
            AmSeckillPo querySeckill = seckillMapper.selectById(x.getActivityId());
            PmMemberLevelPo memberLevel = memberLevelMapper.selectById(querySeckill.getMemberLevelId());
            //用户会员等级大于等于活动限定的等级
            if (currentUser.getLevel() >= memberLevel.getLevel()) {
                ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo = shopTicketSoWithCarGoodVos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().get();
                shopTicketSoWithCarGoodVo.setRealPayMoney(x.getActivityPrice()).setType(ActivityTypeEnum.SECKILL.getId());
                activityType[0] =3;
            }
        });

        //积分不足用原价。足够用积分+活动价
        List<ActivitySkuBo> integrals = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.INTEGRALS).collect(Collectors.toList());
        final BigDecimal[] totalIntegral = {BigDecimal.ZERO};
        final BigDecimal[] currentIntegral = {currentUser.getCurrentIntegral()};
        //积分抵扣金额
        final BigDecimal[] totalIntegralMoney = {BigDecimal.ZERO};
        integrals.forEach(x -> {
            //销售价与活动价之间的差价
            BasicSettingPo queryBasic = basicSettingMapper.selectOne(new QueryWrapper<>());
            //售价与活动价的差价
            BigDecimal price = BigDecimalUtil.safeSubtract(x.getSellPrice(), x.getActivityPrice());
            ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo = shopTicketSoWithCarGoodVos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().get();
            //需要多少积分
            BigDecimal integral = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(price, queryBasic.getMoneyToIntegrate()), shopTicketSoWithCarGoodVo.getNumber());
            //查询积分活动限定的会员等级
            AmIntegralsPo queryIntegral = integralsMapper.selectById(x.getActivityId());
            PmMemberLevelPo memberLevel = memberLevelMapper.selectById(queryIntegral.getMemberLevelId());
            //如果积分足够且用户的等级足够
            if (currentIntegral[0].compareTo(integral) >= 0 && currentUser.getLevel() >= memberLevel.getLevel()) {
                currentIntegral[0] = BigDecimalUtil.safeSubtract(currentIntegral[0], integral);
                totalIntegralMoney[0] = BigDecimalUtil.safeAdd(totalIntegralMoney[0], price);
                //实付价改为活动价
                shopTicketSoWithCarGoodVo.setRealPayMoney(x.getActivityPrice()).setType(ActivityTypeEnum.INTEGRALS.getId());
                activityType[0] =2;

                totalIntegral[0] = BigDecimalUtil.safeAdd(totalIntegral[0], integral);
            }
        });

        BigDecimal totalFullDiscount = BigDecimal.ZERO;
        //判断sku是否参加了满减活动
        List<FullDiscountSkuBo> fullDiscountSkuBos = amActivityRelGoodsSkuMapper.getFullDiscountSkuBo(skuIds);
        if (!ListUtil.isListNullAndEmpty(fullDiscountSkuBos)) {

            //根据满减的活动id分组
            Map<Long, List<FullDiscountSkuBo>> groupFullDiscountMap = fullDiscountSkuBos.stream().collect(Collectors.groupingBy(x -> x.getActivityId()));
            //满减优惠了多少钱
            //遍历map,算出整个支付单满减优惠了多少钱
            for (Map.Entry<Long, List<FullDiscountSkuBo>> entry : groupFullDiscountMap.entrySet()) {
                Long activityId = entry.getKey();
                List<FullDiscountSkuBo> fullDiscountSkuBoList = entry.getValue();
                //满多少钱可以减
                BigDecimal reductionFullMoney = fullDiscountSkuBoList.get(0).getReductionFullMoney();
                //减多少钱
                BigDecimal reductionPostMoney = fullDiscountSkuBoList.get(0).getReductionPostMoney();
                //该支付单下同个满减活动下商品的销售总价
                BigDecimal sumSellPrice = fullDiscountSkuBoList.stream().map(x -> x.getSellPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                //查询满减活动限定的会员等级
                AmReducedPo queryReduce = reducedMapper.selectById(activityId);
                PmMemberLevelPo memberLevel = memberLevelMapper.selectById(queryReduce.getMemberLevelId());
                //如果满足满减优惠且会员等级满足
                if (sumSellPrice.compareTo(reductionFullMoney) >= 0 && currentUser.getLevel() >= memberLevel.getLevel()) {
                    //找出满足满减优惠的skuid集合，计算他们的付现价
                    List<Long> fullSkuIds = fullDiscountSkuBoList.stream().map(FullDiscountSkuBo::getSkuId).collect(Collectors.toList());
                    List<ShopTicketSoWithCarGoodVo> fullRedetionSkus = shopTicketSoWithCarGoodVos.stream().filter(x -> fullSkuIds.contains(x)).collect(Collectors.toList());
                    //计算满减的付现价
                    setRealPayMoneyForFullRedution2(fullRedetionSkus, reductionPostMoney, ActivityTypeEnum.REDUCED);
                    activityType[0] =1;

                    totalFullDiscount = BigDecimalUtil.safeAdd(totalFullDiscount, reductionPostMoney);
                }

            }
        }
        //优惠券信息
        final String[] couponMessage = new String[1];

        //优惠券满减的金额
        BigDecimal reduceCouponMoney = BigDecimal.ZERO;
        //优惠券
        if (settleDto.getCouponRelUserId() != null && !settleDto.getCouponRelUserId().equals(0L)) {
            List<SelectCouponVo> querySelectCouponVoList = couponMapper.getSelectCouPonVo(currentUser.getId(), skuIds, settleDto.getCouponRelUserId());

            //如果是包邮类型，将sku设置为包邮
            querySelectCouponVoList.stream().filter(x -> x.getType() == 3).forEach(x -> {
                shopTicketSoWithCarGoodVos.stream().filter(y -> y.getId().equals(x.getSkuId())).forEach(y -> y.setIsFreePostage(true));
                activityType[0] =5;
                couponMessage[0] ="包邮";

            });
            //如果是满减,加入总优惠
            SelectCouponVo reduceCoupon = querySelectCouponVoList.stream().filter(x -> x.getType() == 1).findFirst().orElse(null);
            if (reduceCoupon != null) {
                BigDecimal reducePriceSum = querySelectCouponVoList.stream().filter(x -> x.getType() == 1).map(SelectCouponVo::getSellPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                //商品价格是否满足打折标准
                if (reducePriceSum.compareTo(reduceCoupon.getReductionFullMoney()) >= 0) {
                    //找出满足满减优惠的skuid集合，计算他们的付现价
                    List<Long> fullSkuIds = querySelectCouponVoList.stream().map(SelectCouponVo::getSkuId).collect(Collectors.toList());
                    List<ShopTicketSoWithCarGoodVo> fullRedetionSkus = shopTicketSoWithCarGoodVos.stream().filter(x -> fullSkuIds.contains(x)).collect(Collectors.toList());
                    //计算满减的付现价
                    setRealPayMoneyForFullRedution2(fullRedetionSkus, reduceCoupon.getReductionFullMoney(), ActivityTypeEnum.COUPON);
                    //满减优惠券金额加入总优惠
                    reduceCouponMoney = reduceCoupon.getReductionPostMoney();
                    activityType[0] =6;
                    couponMessage[0] ="满"+reduceCoupon.getReductionFullMoney().intValue()+"减"+reduceCoupon.getReductionPostMoney();


                }

            }
            //如果是满折扣，将sku价格改变
            SelectCouponVo discountCoupon = querySelectCouponVoList.stream().filter(x -> x.getType() == 2).findFirst().orElse(null);
            if (discountCoupon != null) {
                BigDecimal discountPriceSum = querySelectCouponVoList.stream().filter(x -> x.getType() == 2).map(SelectCouponVo::getSellPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                //商品价格是否满足打折标准
                if (discountPriceSum.compareTo(discountCoupon.getDiscountFullMoney()) >= 0) {
                    //折扣换算成小数
                    BigDecimal discount = BigDecimalUtil.safeDivide(discountCoupon.getDiscount(), 10);
                    //sku价格打折
                    querySelectCouponVoList.stream().filter(x -> x.getType() == 2).forEach(x -> {
                        shopTicketSoWithCarGoodVos.stream().filter(y -> y.getId().equals(x.getSkuId())).
                                forEach(y -> y.setRealPayMoney(BigDecimalUtil.safeMultiply(y.getSellPrice(), discount)).setType(ActivityTypeEnum.REDUCED.getId()));
                    });
                    activityType[0] =7;
                    couponMessage[0] =discountCoupon.getDiscount()+"折券";

                }
            }
        }
       /* if (!ListUtil.isListNullAndEmpty(boByIds)){
            boByIds.forEach(x->{
                ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo=shopTicketSoWithCarGoodVos.stream().filter(y->x.getSkuId().equals(y.getId())).findFirst().orElse(null);
                switch (x.getActivityType()) {
                    case NON:
                        break;
                    case REDUCED:

                        break;
                    case INTEGRALS:
                        break;
                    case SECKILL:
                        break;
                    case SPELL_GROUP:
                        break;
                }
            });
        }*/


        TotalCarVo totalCarVo = new TotalCarVo();

        totalCarVo.setCouponMessage(couponMessage[0]);

        String receivePhone = "";
        if (settleDto.getAreaShipId() == null) {
            QueryWrapper<UmAreaShippingPo> myAreaWrapper = new QueryWrapper<>();
            myAreaWrapper.lambda().eq(UmAreaShippingPo::getUmUserId, currentUser.getId());
            myAreaWrapper.lambda().eq(UmAreaShippingPo::getIsDefault, true);
            UmAreaShippingPo umAreaShippingPo = umAreaShippingMapper.selectOne(myAreaWrapper);
            //如果用户没有设置默认地址
            if (umAreaShippingPo != null) {
                receivePhone = umAreaShippingPo.getMobile();
            }
        } else {
            UmAreaShippingPo queryAreaShipping = umAreaShippingMapper.selectById(settleDto.getAreaShipId());
            receivePhone = queryAreaShipping.getMobile();
        }

        //判断商品是否保税仓或者海外直邮，是则可能需要进行实名认证
        List<ShopTicketSoWithCarGoodVo> needRealSkuList = shopTicketSoWithCarGoodVos.stream().filter(x -> needRealGoodsType.contains(x.getGoodsType())).collect(Collectors.toList());
        if (!ListUtil.isListNullAndEmpty(needRealSkuList)) {
            if (StringUtils.isBlank(receivePhone)) {
                totalCarVo.setNeedCertification(true);
            } else {
                QueryWrapper<OmRealUserPo> realUserWrapper = new QueryWrapper<>();
                realUserWrapper.lambda().eq(OmRealUserPo::getPhone, receivePhone);
                Integer queryRealUserCount = realUserMapper.selectCount(realUserWrapper);
                //如果该手机号码实名认证过
                if (queryRealUserCount > 0) {
                    totalCarVo.setNeedCertification(false);
                } else {
                    totalCarVo.setNeedCertification(true);
                }

            }
        }
        //获取系统基本设置
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());

        //设置会员等级比例和购物券比例
        setPurchasePresentAndMoneyToShopTicket(shopTicketSoWithCarGoodVos, currentUser, basicSettingPo);
        //拆单后的信息包括红包 购物券 金额 优惠 运费等
        List<StoreOrderVo> storeOrderVos = getBySkuIds(shopTicketSoWithCarGoodVos, currentUser, settleDto.getAreaShipId());

        totalCarVo.setStoreOrderVos(storeOrderVos);

        //订单总额=所有订单金额的总和（包括运费、税费）
        BigDecimal totalMoney = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(GoodsTypeOrderVo::getTotalMoney).reduce(BigDecimal.ZERO, BigDecimal::add)
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
        //总数量
        int totalNumber = settleAccountsDtos.stream().mapToInt(SettleAccountsDto::getNumber).sum();
        //总邮费=所有订单的总和
        BigDecimal totalShipMoney = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(GoodsTypeOrderVo::getShipMoney).reduce(BigDecimal.ZERO, BigDecimal::add)
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
        //总税费=所有订单的总和
        BigDecimal totalTaxMoney = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(y -> y.getTaxMoney() == null ? BigDecimal.ZERO : y.getTaxMoney()).reduce(BigDecimal.ZERO, BigDecimal::add)
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
       /* //总优惠=所有订单的总和
        BigDecimal totalDiscount = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(GoodsTypeOrderVo::getTotalDiscount).reduce(BigDecimal.ZERO, BigDecimal::add)
        ).reduce(BigDecimal.ZERO, BigDecimal::add);*/


       /* //总实付金额=所有订单的总和
        BigDecimal totalRealPayMoney = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(GoodsTypeOrderVo::getRealPayMoney).reduce(BigDecimal.ZERO, BigDecimal::add)
        ).reduce(BigDecimal.ZERO, BigDecimal::add);*/

        totalCarVo.setTotalMoney(totalMoney).setTotalNumber(totalNumber);
        //红包、购物券、可抵扣金额
        setDiscount(totalCarVo, currentUser, basicSettingPo);
        //积分
        totalCarVo.setTotalIntegralMoney(totalIntegralMoney[0]);
        //计算实付金额=商品活动价格总和-红包购物券
        BigDecimal totalGoodsRealPayMoney = shopTicketSoWithCarGoodVos.stream().map(x ->
                BigDecimalUtil.safeMultiply(x.getNumber(), x.getRealPayMoney())
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRealPayMoney = BigDecimalUtil.safeSubtract(totalGoodsRealPayMoney, totalCarVo.getTotalDeductionMoney());
        //葱鸭钱包总优惠加上积分的优惠
        totalCarVo.setTotalDeductionMoney(BigDecimalUtil.safeAdd(totalCarVo.getTotalDeductionMoney(), totalIntegralMoney[0]));
        totalCarVo.setTotalShipMoney(totalShipMoney)
                .setTotalTaxMoney(totalTaxMoney).setTotalRealPayMoney(totalRealPayMoney);
        //优惠活动抵扣金额
        totalCarVo.setFullDiscountMoney(totalFullDiscount).setReduceCouponMoney(reduceCouponMoney).setGroupDiscountMoney(groupDiscountMoney);

        return totalCarVo;
    }

    /**
     * 根据skuids组装成订单，并根据商家和商品类型进行拆单
     *
     * @param shopTicketSoWithCarGoodVo
     * @return
     */
    private List<StoreOrderVo> getBySkuIds(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVo, UmUserPo currentUser,
                                           Long areaShipId) {

        //加快sql查询，用代码对店铺和商品类型进行分组拆单
        Map<List, Map<String, List<ShopTicketSoWithCarGoodVo>>> map
                = shopTicketSoWithCarGoodVo.stream().collect(
                Collectors.groupingBy(
                        //店铺名称+id第一层分组，商品类型第二层分组
                        x -> Lists.newArrayList(x.getStoreId(), x.getStoreName()), Collectors.groupingBy(ShopTicketSoWithCarGoodVo::getGoodsType)
                )
        );

        //商家分组集合
        List<StoreOrderVo> storeOrderVos = Lists.newArrayList();
        //遍历map,将map组装成vo
        Iterator<Map.Entry<List, Map<String, List<ShopTicketSoWithCarGoodVo>>>> it = map.entrySet().iterator();
        //遍历店铺
        while (it.hasNext()) {
            Map.Entry<List, Map<String, List<ShopTicketSoWithCarGoodVo>>> entry = it.next();
            StoreOrderVo storeOrderVo = new StoreOrderVo();
            storeOrderVo.setStoreName(entry.getKey().get(1).toString());
            storeOrderVo.setStoreId(Long.parseLong(entry.getKey().get(0).toString()));
            //商品类型分组集合
            List<GoodsTypeOrderVo> goodsTypeOrderVos = Lists.newArrayList();
            //遍历商品类型,每个类型为一个订单
            for (Map.Entry<String, List<ShopTicketSoWithCarGoodVo>> entry1 : entry.getValue().entrySet()) {
                List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos = entry1.getValue();
                GoodsTypeOrderVo goodsTypeOrderVo = new GoodsTypeOrderVo();
                goodsTypeOrderVo.setGoodsType(entry1.getKey());
                BigDecimal taxMoney = BigDecimal.ZERO;
                //判断商品是否保税仓或者海外直邮，且计算税率
                if (needRealGoodsType.contains(entry1.getKey())) {
                    //商品的税率=数量*销售价*百分比/100
                    taxMoney = shopTicketSoWithCarGoodVos.stream().map(x -> BigDecimalUtil.safeMultiply(x.getNumber(),
                            BigDecimalUtil.safeMultiply(x.getSellPrice(), transfromDecimal(x.getCustomTaxRate())))).
                            reduce(BigDecimal.ZERO, BigDecimal::add);
                    goodsTypeOrderVo.setTaxMoney(taxMoney);
                } else {
                    goodsTypeOrderVo.setTaxMoney(BigDecimal.ZERO);
                }
                //计算邮费
                BigDecimal shipMoney = getShipMoney(shopTicketSoWithCarGoodVos, areaShipId, currentUser);
                goodsTypeOrderVo.setShipMoney(shipMoney);
                //商品详细信息
                goodsTypeOrderVo.setShopTicketSoWithCarGoodVos(shopTicketSoWithCarGoodVos);
                //拆单后商品总数量
                goodsTypeOrderVo.setTotalNumber(shopTicketSoWithCarGoodVos.stream().mapToInt(x -> x.getNumber()).sum());
                //商品总额=数量*单价（销售价）+邮费+运费
                BigDecimal totalMoney = shopTicketSoWithCarGoodVos.stream().map(x -> BigDecimalUtil.safeMultiply(x.getNumber(), x.getSellPrice())).
                        reduce(BigDecimal.ZERO, BigDecimal::add);
                totalMoney = BigDecimalUtil.safeAdd(totalMoney, shipMoney, taxMoney);
                goodsTypeOrderVo.setTotalMoney(totalMoney);
                goodsTypeOrderVos.add(goodsTypeOrderVo);
            }
            storeOrderVo.setGoodsTypeOrderVos(goodsTypeOrderVos);
            storeOrderVos.add(storeOrderVo);
        }
        return storeOrderVos;

    }

    /**
     * 设置会员等级比例和购物券比例
     *
     * @param shopTicketSoWithCarGoodVoList
     */
    private void setPurchasePresentAndMoneyToShopTicket(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList
            , UmUserPo currentUser, BasicSettingPo basicSettingPo
    ) {
        PmMemberLevelPo memberLevelPo = levelMapper.selectById(currentUser.getMemberLevelId());
        shopTicketSoWithCarGoodVoList.forEach(x -> x.setPurchasePresent(memberLevelPo.getPurchasePresent())
                .setMoneyToShopTicket(basicSettingPo.getMoneyToShopTicket())
        );

    }


    /**
     * 设置会员等级比例和购物券比例
     *
     * @param shopTicketSoWithCarGoodDtos
     */
    private void setPurchaseAndShopTicket(List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos
            , UmUserPo currentUser, BasicSettingPo basicSettingPo
    ) {
        PmMemberLevelPo memberLevelPo = levelMapper.selectById(currentUser.getMemberLevelId());
        shopTicketSoWithCarGoodDtos.forEach(x -> x.setPurchasePresent(memberLevelPo.getPurchasePresent())
                .setMoneyToShopTicket(basicSettingPo.getMoneyToShopTicket())
        );

    }

    /**
     * 设置可用红包、购物券、可抵扣金额
     *
     * @param totalCarVo
     */
    private void setDiscount(TotalCarVo totalCarVo, UmUserPo currentUser, BasicSettingPo basicSettingPo) {
        BigDecimal totalMoney = totalCarVo.getTotalMoney();
        //一个购物券=多少元
        BigDecimal shopTicketToMoney = BigDecimalUtil.safeDivideDown(1, basicSettingPo.getMoneyToShopTicket());
        //当前用户所拥有的红包折算后的金额
        BigDecimal redEnvelopMoney = currentUser.getCurrentRedEnvelops();
        //当前用户所拥有的购物券折算后的金额
        BigDecimal shopTicketMoney = BigDecimalUtil.safeMultiplyDown(shopTicketToMoney, currentUser.getCurrentShopTicket());
        //-1表示小于，0是等于，1是大于。
        //如果总金额<=红包折算后的金额
        if (totalMoney.compareTo(redEnvelopMoney) <= 0) {
            //需要用多少个红包
            totalCarVo.setTotalRedEnvelopsMoney(totalMoney);
            totalCarVo.setTotalShopTicketMoney(BigDecimal.ZERO);
            totalCarVo.setTotalDeductionMoney(totalMoney);
        } else {
            //总金额>红包，红包可以全用
            totalCarVo.setTotalRedEnvelopsMoney(redEnvelopMoney);
            //红包抵扣后还剩下多少钱
            BigDecimal removeRed = BigDecimalUtil.safeSubtract(totalMoney, redEnvelopMoney);
            //如果剩下的金额小于等于购物券折算后的金额
            if (removeRed.compareTo(shopTicketMoney) <= 0) {
                totalCarVo.setTotalShopTicketMoney(removeRed);
                totalCarVo.setTotalDeductionMoney(totalMoney);
            } else {
                //剩下金额大于可抵扣购物券，购物券可以全用
                totalCarVo.setTotalShopTicketMoney(shopTicketMoney);
                //扣除优惠券和红包剩下的金额
                totalCarVo.setTotalDeductionMoney(BigDecimalUtil.safeAdd(redEnvelopMoney, shopTicketMoney));
            }
        }
    }


    /**
     * @return void
     * @Author zhangrt
     * @Date 2019/10/17 18:29
     * @Description 设置可用红包、购物券的可抵扣金额
     * @Update
     * @Param [realPayMoney, submitOrderDto, currentUser, basicSettingPo]  扣去活动后的金额
     **/

    private void setDiscountInSubmit(BigDecimal realPayMoney, SubmitOrderDto submitOrderDto, UmUserPo currentUser, BasicSettingPo basicSettingPo) {
        //至少要支付0.01
        BigDecimal totalMoney = realPayMoney;
        //一个购物券=多少元
        BigDecimal shopTicketToMoney = BigDecimalUtil.safeDivide(1, basicSettingPo.getMoneyToShopTicket());
        //一个红包=多少元
        Integer redEnvelopsToMoney = 1;
        //当前用户所拥有的购物券折算后的金额
        BigDecimal shopTicketMoney = BigDecimalUtil.safeMultiply(shopTicketToMoney, currentUser.getCurrentShopTicket());
        //当前用户所拥有的红包折算后的金额
        BigDecimal redEnvelopMoney = BigDecimalUtil.safeMultiply(redEnvelopsToMoney, currentUser.getCurrentRedEnvelops());
        //-1表示小于，0是等于，1是大于。
        //如果总金额<=红包折算后的金额
        if (totalMoney.compareTo(redEnvelopMoney) <= 0) {
            //需要用多少个红包
            submitOrderDto.setTotalRedEnvelopsMoney(totalMoney);
            submitOrderDto.setTotalShopTicketMoney(BigDecimal.ZERO);
        } else {
            //总金额>红包，红包可以全用
            submitOrderDto.setTotalRedEnvelopsMoney(redEnvelopMoney);
            //红包抵扣后还剩下多少钱
            BigDecimal removeRed = BigDecimalUtil.safeSubtract(totalMoney, redEnvelopMoney);
            //如果剩下的金额小于等于购物券折算后的金额
            if (removeRed.compareTo(shopTicketMoney) <= 0) {
                submitOrderDto.setTotalShopTicketMoney(removeRed);
            } else {
                //剩下金额大于可抵扣购物券，购物券可以全用
                submitOrderDto.setTotalShopTicketMoney(shopTicketMoney);
                //扣除优惠券和红包剩下的金额
            }
        }
    }

    /**
     * 算出运费
     *
     * @param shopTicketSoWithCarGoodVos
     * @return
     */
    private BigDecimal getShipMoney(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos, Long areaShipId, UmUserPo currentUser) {

        //收货城市
        Long myCityId;
        //地址为空，采用默认地址
        if (areaShipId == null) {
            QueryWrapper<UmAreaShippingPo> myAreaWrapper = new QueryWrapper<>();
            myAreaWrapper.lambda().eq(UmAreaShippingPo::getUmUserId, currentUser.getId());
            myAreaWrapper.lambda().eq(UmAreaShippingPo::getIsDefault, true);
            UmAreaShippingPo umAreaShippingPo = umAreaShippingMapper.selectOne(myAreaWrapper);
            //如果用户没有设置默认地址
            if (umAreaShippingPo == null) {
                return BigDecimal.ZERO;
            } else {
                areaShipId = umAreaShippingPo.getId();
            }
        }
        //收货城市
        myCityId = umAreaShippingMapper.getCityId(areaShipId);
        //找出非免邮的sku
        List<ShopTicketSoWithCarGoodVo> needShipMoneys = shopTicketSoWithCarGoodVos.stream().filter(x -> !x.getIsFreePostage())
                .collect(Collectors.toList());
        //按照运费模板分组
        Map<Long, List<ShopTicketSoWithCarGoodVo>> map = needShipMoneys.stream().
                collect(Collectors.groupingBy(ShopTicketSoWithCarGoodVo::getShippingTemplateId));
        //订单总运费
        BigDecimal shipMoney = BigDecimal.ZERO;
        for (Map.Entry<Long, List<ShopTicketSoWithCarGoodVo>> entry : map.entrySet()) {
            Long shipTemplateId = entry.getKey();
            List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList = entry.getValue();
            //运费模板对应的运费信息
            List<MoneyShipBo> moneyShipBos = moneyShippingMapper.loadByTemplateId(shipTemplateId);

            //匹配是否在指定地区内
            MoneyShipBo moneyShipBo = moneyShipBos.stream().filter(x -> myCityId.equals(x.getDestinationId())).
                    findFirst().orElse(null);
            //采用默认运费
            if (moneyShipBo == null) {
                shipMoney = BigDecimalUtil.safeAdd(getShipByMoney(shopTicketSoWithCarGoodVoList, moneyShipBos.get(0).getDefaultFullMoney()
                        , moneyShipBos.get(0).getDefaultPostMoney(), moneyShipBos.get(0).getDefaultFreight()), shipMoney);
            }
            //采用指定运费
            else {
                shipMoney = BigDecimalUtil.safeAdd(shipMoney, getShipByMoney(shopTicketSoWithCarGoodVoList, moneyShipBo.getDestinationFullMoney()
                        , moneyShipBo.getDestinationPostMoney(), moneyShipBo.getDestinationBasisFreight())
                );
            }

        }
        return shipMoney;
    }

    /*  *//**
     * 算出运费
     *
     * @param shopTicketSoWithCarGoodVo
     * @return
     *//*
    private BigDecimal getShipMoney(ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo, Long areaShipId, UmUserPo currentUser) {
        //如果该商品包邮
        if (shopTicketSoWithCarGoodVo.getIsFreePostage()) {
            return BigDecimal.ZERO;
        }
        //收货城市
        Long myCityId;
        //地址为空，采用默认地址
        if (areaShipId == null) {
            QueryWrapper<UmAreaShippingPo> myAreaWrapper = new QueryWrapper();
            myAreaWrapper.lambda().eq(UmAreaShippingPo::getIsDefault, true);
            myAreaWrapper.lambda().eq(UmAreaShippingPo::getUmUserId, currentUser.getId());
            UmAreaShippingPo umAreaShippingPo = umAreaShippingMapper.selectOne(myAreaWrapper);
            //如果用户没有设置默认地址
            if (umAreaShippingPo == null) {
                return BigDecimal.ZERO;
            } else {
                areaShipId = umAreaShippingPo.getId();
            }
        }
        //收货城市
        myCityId = umAreaShippingMapper.getCityId(areaShipId);
        //sku对应的运费信息
        List<MoneyShipBo> moneyShipBos = moneyShippingMapper.loadByTemplateId(shopTicketSoWithCarGoodVo.getShippingTemplateId());
        //匹配是否在指定地区内
        MoneyShipBo moneyShipBo = moneyShipBos.stream().filter(x -> myCityId.equals(x.getDestinationId())).
                findFirst().orElse(null);
        //采用默认运费
        if (moneyShipBo == null) {
            return getShipByMoney(shopTicketSoWithCarGoodVo, moneyShipBos.get(0).getDefaultFullMoney()
                    , moneyShipBos.get(0).getDefaultPostMoney(), moneyShipBos.get(0).getDefaultFreight()
            );

        }
        //采用指定运费
        else {
            return getShipByMoney(shopTicketSoWithCarGoodVo, moneyShipBo.getDestinationFullMoney()
                    , moneyShipBo.getDestinationPostMoney(), moneyShipBo.getDestinationBasisFreight()
            );
        }

    }*/

    /**
     * @param fullMoney    满金额条件
     * @param postMoney    满足后的运费
     * @param basisFreight 基础运费
     * @return
     */
    private BigDecimal getShipByMoney(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos, BigDecimal fullMoney,
                                      BigDecimal postMoney, BigDecimal basisFreight) {
        //同个模板下所有商品的总价
        BigDecimal price = shopTicketSoWithCarGoodVos.stream().map(x -> BigDecimalUtil.safeMultiply(x.getNumber(), x.getRealPayMoney()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //满足金额优惠条件
        if (price.compareTo(fullMoney) >= 0) {
            return postMoney;
        } else {
            return BigDecimalUtil.safeMultiply(shopTicketSoWithCarGoodVos.stream().mapToInt(ShopTicketSoWithCarGoodVo::getNumber).sum(), basisFreight);
        }


    }

    /**
     * 将百分比转换成小数
     *
     * @param bigDecimal
     * @return
     */
    private BigDecimal transfromDecimal(BigDecimal bigDecimal) {
        return BigDecimalUtil.safeDivide(bigDecimal, 100);
    }


    /**
     * 查看商品详情
     *
     * @param goodsId
     * @return
     */
    @Override
    public SpecifiedGoodsVo selectSpecifiedGoods(Long goodsId, UmUserPo umUserPo) {

        Long umUserId = null == umUserPo ? null : umUserPo.getId();

        SpecifiedGoodsVo specifiedGoodsVo = new SpecifiedGoodsVo();
        List<GoodsStandardVo> goodsStandardVoList = Lists.newArrayList();

        PmGoodsPo goodsPo = goodsMapper.selectById(goodsId);
        //判断该商品是否存在
        if (goodsPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该商品！");
        }
        /**获取商品基本信息：名称、标题、轮播图、发货地等信息*/
        specifiedGoodsVo = goodsMapper.findGoodsBase(goodsId);
        /** 获取用户是否已经收藏该商品*/
        UmUserFavoritesPo userFavoritesPo = userFavoritesMapper.selectOne(new QueryWrapper<UmUserFavoritesPo>().lambda()
                .and(obj -> obj.eq(UmUserFavoritesPo::getFavoritesId, goodsId)
                        .eq(UmUserFavoritesPo::getUserId, umUserId)));
        Boolean isFavorites = false;
        if (userFavoritesPo == null) {
            isFavorites = false;
        } else if (userFavoritesPo.getIsFavorites()) {
            isFavorites = true;
        } else if (!userFavoritesPo.getIsFavorites()) {
            isFavorites = false;
        }
        specifiedGoodsVo.setIsFavorites(isFavorites);

        /** 获取用户是否已经关注该商品*/
        PmGoodsLikedPo goodsLikedPo = goodsLikedMapper.selectOne(new QueryWrapper<PmGoodsLikedPo>().lambda()
                .and(obj -> obj.eq(PmGoodsLikedPo::getGoodsId, goodsId)
                        .eq(PmGoodsLikedPo::getUserId, umUserId)));
        Boolean isliked = false;
        if (goodsLikedPo == null) {
            isliked = false;
        } else if (goodsLikedPo.getIsLiked()) {
            isliked = true;
        } else if (!goodsLikedPo.getIsLiked()) {
            isliked = false;
        }
        specifiedGoodsVo.setIsliked(isliked);


        String carouselImage = goodsMapper.selectById(goodsId).getCarouselImage();
        //string转list
        List<String> carouselImages = Splitter.on(",").trimResults().splitToList(carouselImage);
//        List<String> carouselImages1 = GuavaUtil.StringToList(carouselImage,String.class,",");
//        System.out.println(carouselImages1);
        specifiedGoodsVo.setCarouselImages(carouselImages);
        /**获取商品详情显示的价格区间*/
        BigDecimal lowestSellPrice = skuMapper.getLowestPrice(goodsId);
        BigDecimal highestSellPrice = skuMapper.getHighestPrice(goodsId);
        String sellPrice = "";
        if (lowestSellPrice.equals(highestSellPrice)) {
            sellPrice = lowestSellPrice.toString();
        } else {
            sellPrice = lowestSellPrice.toString() + "~" + highestSellPrice;
        }
        specifiedGoodsVo.setDisplayPrice(sellPrice);

        //默认运费
        BigDecimal shipFreight = new BigDecimal(0.00);
        /**运费详情*/
        ShipFreightInfoVo shipFreightInfoVo = shippingTemplateMapper.findByGoodsId(goodsId);
        if (shipFreightInfoVo != null && !specifiedGoodsVo.getIsFreePostage()) {
            shipFreight = shipFreightInfoVo.getDefaultFreight();

            if (shipFreightInfoVo.getCalculateWay() == 1) {
                //按金额计算的运费详情
                List<MoneyShippingVo> moneyShippingVos = moneyShippingMapper.findByTemplateId(shipFreightInfoVo.getTemplateId());
                shipFreightInfoVo.setMoneyShippingVos(moneyShippingVos);

            } else {
                //按件数计算运费详情
                List<NumberShippingVo> numberShippingVos = numberShippingMapper.findByTemplateId(shipFreightInfoVo.getTemplateId());
                shipFreightInfoVo.setNumberShippingVos(numberShippingVos);
            }
        }
        specifiedGoodsVo.setShipFreightInfoVo(shipFreightInfoVo);
        specifiedGoodsVo.setShipFreight(shipFreight);

        if (null == umUserId) {
            specifiedGoodsVo.setMaxRewardShopTicket(new BigDecimal("0"));
        } else {
            //获取最高返券值
            List<Double> rewardShopTickes = com.google.common.collect.Lists.newArrayList();
            List<RewardShopTicketBo> rewardShopTicketBos = skuMapper.findRewardShopTicketInfos(goodsId);
            rewardShopTicketBos.forEach(b -> {
                //商品活动百分比
                b.setActivityCostRate(goodsPo.getActivityCostRate());
                //让利成本比例
                b.setProfitsRate(goodsPo.getProfitsRate());
                //会员等级比例
                BigDecimal purchasePresent = levelMapper.selectById(umUserPo.getMemberLevelId()).getPurchasePresent();
                b.setPurchasePresent(purchasePresent);
                //购物券比例
                BigDecimal moneyToShopTicket = basicSettingMapper.selectList(null).get(0).getMoneyToShopTicket();
                b.setMoneyToShopTicket(moneyToShopTicket);
                BigDecimal rewardShopTicket = b.getRewardShopTicket();
                rewardShopTickes.add(rewardShopTicket.doubleValue());
            });
            //获取RewardShopTickes列表最大返券值
            Double maxRewardShopTicket = rewardShopTickes.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
            specifiedGoodsVo.setMaxRewardShopTicket(BigDecimal.valueOf(maxRewardShopTicket));
        }

        //返券规则
        String returnTicketRule = "";
        MmArticlePo articlePo = articleMapper.selectOne(new QueryWrapper<MmArticlePo>().lambda().and(obj -> obj
                .eq(MmArticlePo::getEnabled, true)
                .eq(MmArticlePo::getArticleLocation, ArticleLocationEnum.RETURN_TICKET_RULES.getName())));
        if (articlePo != null) {
            articlePo.getArticleLocation();
        }
        specifiedGoodsVo.setReturnTicketRules(returnTicketRule);

        //获取税率
        BigDecimal taxRate = null;
        // 1--平台税率 2--自定义税率 3—无税率
        if (specifiedGoodsVo.getTaxRateType() == 1) {
            taxRate = categoryMapper.selectById(specifiedGoodsVo.getCategoryId()).getTaxRate();
        } else if (specifiedGoodsVo.getTaxRateType() == 2) {
            taxRate = specifiedGoodsVo.getCustomTaxRate();
        } else {
            taxRate = new BigDecimal(0);
        }
        specifiedGoodsVo.setTaxRate(taxRate);
        BigDecimal taxCost = BigDecimalUtil.safeMultiply(lowestSellPrice, taxRate);
        specifiedGoodsVo.setTaxCost(taxCost);

        /**优惠券列表*/
        //优惠券列表,满减和固定折扣
        List<FindCouponListVo> findCouponListVos = Lists.newArrayList();
        findCouponListVos = relCouponGoodsMapper.findCouponList(goodsId);
        //优惠券列表--包邮-指定全部商品
        List<FindCouponListVo> findCouponListVos1 = relCouponGoodsMapper.findCouponList1(goodsId);
        //优惠券列表--包邮-指定分类
        List<FindCouponListVo> findCouponListVos2 = relCouponGoodsMapper.findCouponList2(goodsPo.getGoodsCategoryId());

        findCouponListVos.addAll(findCouponListVos1);
        findCouponListVos.addAll(findCouponListVos2);
        if (!ListUtil.isListNullAndEmpty(findCouponListVos)) {
            findCouponListVos.forEach(a -> {
                List<AmCouponRelCouponUserPo> relCouponUserPos = relCouponUserMapper.selectList(new QueryWrapper<AmCouponRelCouponUserPo>().lambda().and(obj -> obj
                        .eq(AmCouponRelCouponUserPo::getUserId, umUserId).eq(AmCouponRelCouponUserPo::getCouponId, a.getCouponId())));
                if (!ListUtil.isListNullAndEmpty(relCouponUserPos)) {
                    a.setIsReceive(true);
                }
            });
        }
        specifiedGoodsVo.setFindCouponList(findCouponListVos);

        /**服务列表*/
        List<AttributeVo> services = relAttributeGoodMapper.findServices(goodsId);
        specifiedGoodsVo.setServiceList(services);

        /**参数*/
        List<AttributeVo> paramList = relAttributeGoodMapper.findParam(goodsId);
        specifiedGoodsVo.setParamList(paramList);

        /** 购买须知*/
        List<AttributeVo> purchaseList = relAttributeCategoryMapper.findPurchase(goodsId);
        specifiedGoodsVo.setPurchaseList(purchaseList);

        /**店铺信息*/
        Long storeId = goodsMapper.selectById(goodsId).getStoreId();

        /**店铺IM账号*/
        specifiedGoodsVo.setStoreImId(String.valueOf(storeId));

        /**平台IM账号*/
        //TODO 暂时写死Admin账号
//        specifiedGoodsVo.setPlatImId(sysUserMapper.selectOne(new QueryWrapper<SysUserPo>().lambda().eq(SysUserPo::getUsername, "admin")).getId());
        specifiedGoodsVo.setPlatImId(imAccount);
        //商品评价数据
        GoodsDetailEvaluateVo goodsDetailEvaluateVo = new GoodsDetailEvaluateVo();

        EvaluateLevelNumVo evaluateLevelNumVo = evaluateMapper.findEvaluateLevelNum(goodsId);
        if (evaluateLevelNumVo != null) {
            //该商品评价数量
            Integer evaluateNum = evaluateLevelNumVo.getSum();
            //获取最新的五星好评的一条记录
            GoodsEvaluateVo goodsEvaluateVo = evaluateMapper.getGoodsEvaluateOne(goodsId);

            goodsDetailEvaluateVo.setEvaluateNum(evaluateNum);
            if (goodsEvaluateVo != null) {
                OmEvaluateLikedPo evaluateLikedPo = evaluateLikedMapper.selectOne(new QueryWrapper<OmEvaluateLikedPo>().lambda()
                        .eq(OmEvaluateLikedPo::getUserId, umUserId).eq(OmEvaluateLikedPo::getEvaluateId, goodsEvaluateVo.getId())
                        .eq(OmEvaluateLikedPo::getIsLiked, true));
                if (evaluateLikedPo == null) {
                    goodsEvaluateVo.setIsLiked(false);
                } else {
                    goodsEvaluateVo.setIsLiked(true);
                }
                goodsDetailEvaluateVo.setGoodsEvaluateVo(goodsEvaluateVo);
            } else {
                goodsEvaluateVo = null;
            }
            specifiedGoodsVo.setGoodsDetailEvaluateVo(goodsDetailEvaluateVo);
        }

        //获取店铺详情
        SmStorePo storePo = storeMapper.selectById(storeId);

        StoreVo storeVo = new StoreVo();

        //获取定时任务刷新的表的数据
        OmEvaluateQuartzPo evaluateQuartzPo = evaluateQuartzMapper.selectOne(new QueryWrapper<OmEvaluateQuartzPo>()
                .lambda().eq(OmEvaluateQuartzPo::getStoreId, storeId));

        if (evaluateQuartzPo == null) {
            storeVo.setBabyDescription(new BigDecimal(0))
                    .setLogisticsServices(new BigDecimal(0))
                    .setSellerService(new BigDecimal(0))
                    .setBabyDescriptionLevel("无")
                    .setLogisticsServicesLevel("无")
                    .setSellerServiceLevel("无")
                    .setStoreId(storeId)
                    .setStoreIcon(storePo.getStoreImage())
                    .setStoreName(storePo.getName());
        } else {
            storeVo.setBabyDescription(evaluateQuartzPo.getDescriptionStartLevel())
                    .setLogisticsServices(evaluateQuartzPo.getShipStartLevel())
                    .setSellerService(evaluateQuartzPo.getAttitudeStartLevel())
                    .setBabyDescriptionLevel(evaluateQuartzPo.getBabyDescriptionLevel())
                    .setLogisticsServicesLevel(evaluateQuartzPo.getLogisticsServicesLevel())
                    .setSellerServiceLevel(evaluateQuartzPo.getSellerServiceLevel())
                    .setStoreId(storeId)
                    .setStoreIcon(storePo.getStoreImage())
                    .setStoreName(storePo.getName());
            //综合体验星数
            BigDecimal avgExperienceStar = BigDecimalUtil.safeDivide(BigDecimalUtil.safeAdd(evaluateQuartzPo.getDescriptionStartLevel(), evaluateQuartzPo.getShipStartLevel(), evaluateQuartzPo.getAttitudeStartLevel()), 3, 1, new BigDecimal(0.00));
            storeVo.setAvgExperienceStar(avgExperienceStar);
        }
        //粉丝量
        Integer fansNum = storePo.getCollectionNum();
        //是否关注
        UmUserFavoritesPo userFavoritesStore = userFavoritesMapper.selectOne(new QueryWrapper<UmUserFavoritesPo>().lambda().and(obj -> obj
                .eq(UmUserFavoritesPo::getUserId, umUserId).eq(UmUserFavoritesPo::getFavoritesId, storePo.getId()).eq(UmUserFavoritesPo::getIsFavorites, true)));
        if (userFavoritesStore == null) {
            storeVo.setIsAttention(false);
        } else {
            storeVo.setIsAttention(true);
        }
        storeVo.setFansNum(fansNum);
        specifiedGoodsVo.setStoreVo(storeVo);

        /**获取商品下的所有规格信息*/
        //获取对应的分类ID
        Long categoryId = goodsPo.getGoodsCategoryId();
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
                goodsStandardVoList.add(goodsStandardVo);
            }
        });
        specifiedGoodsVo.setGoodsStandardVoList(goodsStandardVoList);

        /*********************** 商品参加的活动,点击商品进来需要展示的信息 start***************************/
        //查询已经开始的活动
        AmActivityRelActivityGoodsPo relActivityGoodsPo = relActivityGoodsMapper.findGoodsActivity(goodsId);
        GoodsActivityVo goodsActivityVo = new GoodsActivityVo();
        //默认不参加活动
        goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
        if (relActivityGoodsPo != null) {
            ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.getActivityTypeEnumById(relActivityGoodsPo.getActivityType());
            switch (activityTypeEnum) {
                //满减
                case REDUCED:
                    AmReducedPo reducedPo = reducedMapper.selectOne(new QueryWrapper<AmReducedPo>().lambda().and(obj ->
                            obj.eq(AmReducedPo::getEnable, true).eq(AmReducedPo::getId, relActivityGoodsPo.getActivityId())));
                    if (reducedPo == null) {
//                        throw new ServiceException(ResultCode.NO_EXISTS,String.format("该商品参加的满减活动不存在"));
                        goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                    } else {
                        goodsActivityVo.setType(ActivityTypeEnum.REDUCED.getId());
                        ReducedVo reducedVo = new ReducedVo();
                        reducedVo.setReductionFullMoney(reducedPo.getReductionFullMoney());
                        reducedVo.setReductionPostMoney(reducedPo.getReductionPostMoney());
                        goodsActivityVo.setReducedVo(reducedVo);
                    }
                    break;
                //积分--活动价格范围+积分抵扣价格
                case INTEGRALS:
                    AmIntegralsPo integralsPo = integralsMapper.selectOne(new QueryWrapper<AmIntegralsPo>().lambda().and(obj ->
                            obj.eq(AmIntegralsPo::getEnable, true).eq(AmIntegralsPo::getId, relActivityGoodsPo.getActivityId())));
                    if (integralsPo == null) {
//                        throw new ServiceException(ResultCode.NO_EXISTS,String.format("该商品参加的积分活动不存在"));
                        goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                    } else {
                        goodsActivityVo.setType(ActivityTypeEnum.INTEGRALS.getId());
                        IntegralsVo integralsVo = new IntegralsVo();
                        //获取参与活动商品所有sku活动价格，并获取最低价格和最高价格
                        //保存所有sku活动价格
                        List<Double> skuActivityPrices = new ArrayList<>();
                        //保存所有sku对应的积分抵扣
                        List<Double> integralsDeductions = new ArrayList<>();
                        //保存所有sku对应的积分抵扣百分比
                        List<Double> integralsPercentages = new ArrayList<>();

                        //获取该商品参与活动的所有sku信息
                        List<AmActivityRelGoodsSkuPo> relGoodsSkuPos = activityRelGoodsSkuMapper.selectList(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                                .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())));
                        if (!ListUtil.isListNullAndEmpty(relGoodsSkuPos)) {
                            relGoodsSkuPos.forEach(a -> {
                                //判断sku是否存在
                                PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(a.getSkuId());
                                if (goodsSkuPo != null) {
                                    //获取原来sku销售价
                                    BigDecimal skuSellPrice = goodsSkuPo.getSellPrice();
                                    //积分抵扣
                                    BigDecimal integralsDeduction = BigDecimalUtil.safeSubtract(true, skuSellPrice, a.getActivityPrice());
                                    //保存sku对应的活动价格
                                    skuActivityPrices.add(a.getActivityPrice().doubleValue());
                                    //保存积分抵扣
                                    integralsDeductions.add(integralsDeduction.doubleValue());
                                    //保存积分抵扣百分比
                                    integralsPercentages.add(BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(integralsDeduction, skuSellPrice, 2, new BigDecimal(0)), new BigDecimal(100), 2).doubleValue());
                                }
                            });
                        }

                        //获取最低和最高的活动价格
                        Double lowestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMin();
                        Double highestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                        String discountDisplayPrice = "";
                        if (lowestActivityPrice.equals(highestActivityPrice)) {
                            discountDisplayPrice = lowestActivityPrice.toString();
                        } else {
                            discountDisplayPrice = lowestActivityPrice.toString() + "~" + highestActivityPrice.toString();
                        }
                        integralsVo.setLowestActivityPrice(lowestActivityPrice);
                        integralsVo.setHighestActivityPrice(highestActivityPrice);
                        integralsVo.setDiscountDisplayPrice(discountDisplayPrice);

                        //获取最低和最高积分抵扣
                        Double lowestIntegralsPrice = integralsDeductions.stream().mapToDouble((x) -> x).summaryStatistics().getMin();
                        Double highestIntegralsPrice = integralsDeductions.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                        String integralsDispalyPrice = "";
                        if (lowestIntegralsPrice.equals(highestIntegralsPrice)) {
                            integralsDispalyPrice = lowestIntegralsPrice.toString();
                        } else {
                            integralsDispalyPrice = lowestIntegralsPrice.toString() + "~" + highestIntegralsPrice.toString();
                        }
                        integralsVo.setHighestIntegralsPrice(highestIntegralsPrice);
                        integralsVo.setLowestIntegralsPrice(lowestIntegralsPrice);
                        integralsVo.setIntegralsDispalyPrice(integralsDispalyPrice);

                        //获取最低和最高积分抵扣比例
                        Double lowestIntegralsPercentage = integralsPercentages.stream().mapToDouble((x) -> x).summaryStatistics().getMin();
                        Double highestIntegralsPercentage = integralsPercentages.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                        String integralsDispalyPercentage = "";
                        if (lowestIntegralsPercentage.equals(highestIntegralsPercentage)) {
                            integralsDispalyPercentage = lowestIntegralsPercentage.toString();
                        } else {
                            integralsDispalyPercentage = lowestIntegralsPercentage.toString() + "~" + highestIntegralsPercentage.toString();
                        }
                        integralsVo.setHighestIntegralsPrice(highestIntegralsPercentage);
                        integralsVo.setLowestIntegralsPrice(lowestIntegralsPercentage);
                        integralsVo.setIntegralsDispalyPercentage(integralsDispalyPercentage);

                        //获取抵扣说明文章
                        String integralsDescription = "";
                        MmArticlePo mmArticlePo = articleMapper.selectOne(new QueryWrapper<MmArticlePo>().lambda().and(obj -> obj
                                .eq(MmArticlePo::getEnabled, true)
                                .eq(MmArticlePo::getArticleLocation, ArticleLocationEnum.INTEGRALS_DESCRIPTION.getName())));
                        if (mmArticlePo != null) {
                            integralsDescription = mmArticlePo.getArticleLocation();
                        }
                        integralsVo.setIntegralsDescription(integralsDescription);

                        goodsActivityVo.setIntegralsVo(integralsVo);

                    }
                    break;
                case SECKILL:
                    AmSeckillPo seckillPo = seckillMapper.selectOne(new QueryWrapper<AmSeckillPo>().lambda().and(obj ->
                            obj.eq(AmSeckillPo::getEnable, true).eq(AmSeckillPo::getId, relActivityGoodsPo.getActivityId())));
                    if (seckillPo == null) {
                        goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                    } else {
                        goodsActivityVo.setType(ActivityTypeEnum.SECKILL_ING.getId());
                        SeckillVo seckillVo = new SeckillVo();
                        //秒杀结束时间
                        seckillVo.setActivityEndTime(seckillPo.getActivityEndTime());

                        //获取参与活动商品所有sku活动价格，并获取最低价格和最高价格
                        //保存所有sku活动价格
                        List<Double> skuActivityPrices = new ArrayList<>();
                        //活动总秒杀量
                        final Integer[] saleVolumes = {0};

                        //获取该商品参与活动的所有sku信息
                        List<AmActivityRelGoodsSkuPo> relGoodsSkuPos = activityRelGoodsSkuMapper.selectList(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                                .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())));
                        if (!ListUtil.isListNullAndEmpty(relGoodsSkuPos)) {
                            relGoodsSkuPos.forEach(a -> {
                                //判断sku是否存在
                                PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(a.getSkuId());
                                if (goodsSkuPo != null) {
                                    //保存sku对应的活动价格
                                    skuActivityPrices.add(a.getActivityPrice().doubleValue());
                                    //获取每个sku活动销量(已秒杀数量)
                                    saleVolumes[0] = saleVolumes[0] + a.getSalesVolume();
                                }
                            });
                        }
                        //活动总秒杀量
                        seckillVo.setSecNum(saleVolumes[0]);

                        //获取最低和最高的活动价格
                        Double lowestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMin();
                        Double highestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                        String secDisplayPrice = "";
                        if (lowestActivityPrice.equals(highestActivityPrice)) {
                            secDisplayPrice = lowestActivityPrice.toString();
                        } else {
                            secDisplayPrice = lowestActivityPrice.toString() + "~" + highestActivityPrice.toString();
                        }
                        seckillVo.setLowestActivityPrice(lowestActivityPrice);
                        seckillVo.setHighestActivityPrice(highestActivityPrice);
                        seckillVo.setSecDisplayPrice(secDisplayPrice);

                        goodsActivityVo.setSeckillVo(seckillVo);

                    }
                    break;
                case SPELL_GROUP:

                    AmSpellGroupPo spellGroupPo = spellGroupMapper.selectOne(new QueryWrapper<AmSpellGroupPo>().lambda().and(obj ->
                            obj.eq(AmSpellGroupPo::getEnable, true).eq(AmSpellGroupPo::getId, relActivityGoodsPo.getActivityId())));
                    if (spellGroupPo == null) {
                        goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                    } else {
                        goodsActivityVo.setType(ActivityTypeEnum.SPELL_GROUP.getId());
                        SpellGroupVo spellGroupVo = new SpellGroupVo();
                        //成团人数
                        spellGroupVo.setGroupNum(spellGroupPo.getGroupNum());

                        //获取参与拼团活动商品所有sku活动价格，并获取最低价格和最高价格
                        //保存所有sku活动价格
                        List<Double> skuActivityPrices = new ArrayList<>();
                        //已拼总人数
                        /*List<Integer> payedNum = spellGroupMainMapper.selectList(new QueryWrapper<AmSpellGroupMainPo>().lambda().and(obj -> obj
                                .eq(AmSpellGroupMainPo::getRelId, relActivityGoodsPo.getId()))).stream().map(n -> n.getPayedNum()).collect(Collectors.toList());
                        Integer sumPayedNum = payedNum.stream().mapToInt((x) -> x).sum();
                        spellGroupVo.setSpellSum(sumPayedNum);*/
                        //已拼总人数  已拼件数
                        spellGroupVo = spellGroupMainMapper.getSpellGroup(relActivityGoodsPo.getId());

                        //获取该商品参与活动的所有sku信息
                        List<AmActivityRelGoodsSkuPo> relGoodsSkuPos = activityRelGoodsSkuMapper.selectList(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                                .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())));
                        if (!ListUtil.isListNullAndEmpty(relGoodsSkuPos)) {
                            relGoodsSkuPos.forEach(a -> {
                                //判断sku是否存在
                                PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(a.getSkuId());
                                if (goodsSkuPo != null) {
                                    //保存sku对应的活动价格
                                    skuActivityPrices.add(a.getActivityPrice().doubleValue());
                                }
                            });
                        }

                        //获取最低和最高的活动价格
                        Double lowestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMin();
                        Double highestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                        String spellGroupDisplayPrice = "";
                        if (lowestActivityPrice.equals(highestActivityPrice)) {
                            spellGroupDisplayPrice = lowestActivityPrice.toString();
                        } else {
                            spellGroupDisplayPrice = lowestActivityPrice.toString() + "~" + highestActivityPrice.toString();
                        }
                        spellGroupVo.setLowestActivityPrice(lowestActivityPrice);
                        spellGroupVo.setHighestActivityPrice(highestActivityPrice);
                        spellGroupVo.setSpellGroupDisplayPrice(spellGroupDisplayPrice);

                        //获取拼团说明
                        //获取抵扣说明文章
                        String spellGroupDescription = "";
                        MmArticlePo mmArticlePo = articleMapper.selectOne(new QueryWrapper<MmArticlePo>().lambda().and(obj -> obj
                                .eq(MmArticlePo::getEnabled, true)
                                .eq(MmArticlePo::getArticleLocation, ArticleLocationEnum.SPELL_GROUP_SHOW.getName())));
                        if (mmArticlePo != null) {
                            spellGroupDescription = mmArticlePo.getArticleLocation();
                        }
                        spellGroupVo.setSpellGroupDescription(spellGroupDescription);

                        goodsActivityVo.setSpellGroupVo(spellGroupVo);

                    }

                    break;
            }
        }

        /********* 查询是否为距离当前时间为一天的秒杀商品 start ***************/
        AmActivityRelActivityGoodsPo relSecKilllGoodsPo = relActivityGoodsMapper.findPreSeckill(goodsId);
        if (relSecKilllGoodsPo != null) {
            AmSeckillPo seckillPo = seckillMapper.selectOne(new QueryWrapper<AmSeckillPo>().lambda().and(obj ->
                    obj.eq(AmSeckillPo::getEnable, true).eq(AmSeckillPo::getId, relSecKilllGoodsPo.getActivityId())));
            //活动是否取消
            if (seckillPo == null) {
                goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
            } else {
                goodsActivityVo.setType(ActivityTypeEnum.SECKILL_PRE.getId());
                SeckillVo seckillVo = new SeckillVo();
                //秒杀结束时间
                seckillVo.setActivityStartTime(seckillPo.getActivityStartTime());

                //获取参与活动商品所有sku活动价格，并获取最低价格和最高价格
                //保存所有sku活动价格
                List<Double> skuActivityPrices = new ArrayList<>();
                //活动总秒杀量
                final Integer[] saleVolumes = {0};

                //获取该商品参与活动的所有sku信息
                List<AmActivityRelGoodsSkuPo> relGoodsSkuPos = activityRelGoodsSkuMapper.selectList(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda()
                        .and(obj -> obj.eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())));
                if (!ListUtil.isListNullAndEmpty(relGoodsSkuPos)) {
                    relGoodsSkuPos.forEach(a -> {
                        //判断sku是否存在
                        PmGoodsSkuPo goodsSkuPo = skuMapper.selectById(a.getSkuId());
                        if (goodsSkuPo != null) {
                            //保存sku对应的活动价格
                            skuActivityPrices.add(a.getActivityPrice().doubleValue());
                        }
                    });
                }

                //获取最低和最高的活动价格
                Double lowestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMin();
                Double highestActivityPrice = skuActivityPrices.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                String secDisplayPrice = "";
                if (lowestActivityPrice.equals(highestActivityPrice)) {
                    secDisplayPrice = lowestActivityPrice.toString();
                } else {
                    secDisplayPrice = lowestActivityPrice.toString() + "~" + highestActivityPrice.toString();
                }
                seckillVo.setLowestActivityPrice(lowestActivityPrice);
                seckillVo.setHighestActivityPrice(highestActivityPrice);
                seckillVo.setSecDisplayPrice(secDisplayPrice);

                goodsActivityVo.setSeckillVo(seckillVo);

            }
        }
        /********* 查询是否为距离当前时间为一天的秒杀商品 end ***************/

        /*********************** 商品参加的活动,点击商品进来需要展示的信息 end***************************/

        /**获取商品规格的具体信息*/
        Map<String, Object> query = new HashMap<>();
        query.put("goods_id", goodsId);
        query.put("del_flag", false);
        List<PmGoodsSkuPo> goodsSkuPos = skuMapper.selectByMap(query);
        if (goodsSkuPos == null && goodsSkuPos.size() == 0) {
            return null;
        }
        Map<String, SpecifiedSkuVo> skuDetail = Maps.newHashMap();
        //循环获取sku信息
        SpecifiedGoodsVo finalSpecifiedGoodsVo = specifiedGoodsVo;
        goodsSkuPos.forEach(b -> {
            SpecifiedSkuVo specifiedSkuVo = new SpecifiedSkuVo();
            specifiedSkuVo.setHoldQuantity(goodsPo.getPurchaseLimit());
            specifiedSkuVo.setStock(b.getStock());
            specifiedSkuVo.setSellAbleQuantity(b.getStock());
            specifiedSkuVo.setOverSold(false);
            specifiedSkuVo.setPrice(b.getSellPrice());
            specifiedSkuVo.setSkuId(b.getId());
            specifiedSkuVo.setPicture(b.getPicture());

            if (b.getStock() == 0) {
                specifiedSkuVo.setOverSold(true);
            }
            if (b.getLinePrice() == null || b.getLinePrice().compareTo(new BigDecimal(0)) == 0) {
                specifiedSkuVo.setLinePrice(new BigDecimal(0));
            } else {
                specifiedSkuVo.setLinePrice(b.getLinePrice());
            }
            /*QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("del_flag", false);
            queryWrapper.eq("goods_sku_id", b.getId());*/
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPoList = relAttributeValueSkuMapper.selectLists(b.getId());
            //拼接规格ID和规格值ID
            StringBuffer relIds = new StringBuffer();
            relAttributeValueSkuPoList.forEach(c -> {
                PmGoodsAttributeValuePo attributeValuePo = attributeValueMapper.selectById(c.getGoodsAttributeValueId());

                Long attributeValueId = attributeValuePo.getId();
                String attributeValue = attributeValuePo.getValue();
                Long attributeId = attributeValuePo.getProductAttributeId();
                relIds.append(attributeId).append(":").append(attributeValueId).append(";");
            });


            /*********************** 商品参加的活动,sku对应的活动价格、活动库存 start***************************/
            if (relActivityGoodsPo != null) {
                ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.getActivityTypeEnumById(relActivityGoodsPo.getActivityType());
                switch (activityTypeEnum) {
                    //积分--sku对应的活动价格+积分抵扣+活动库存
                    case INTEGRALS:
                        AmIntegralsPo integralsPo = integralsMapper.selectOne(new QueryWrapper<AmIntegralsPo>().lambda().and(obj ->
                                obj.eq(AmIntegralsPo::getEnable, true).eq(AmIntegralsPo::getId, relActivityGoodsPo.getActivityId())));
                        if (integralsPo == null) {
                            goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                        } else {
                            goodsActivityVo.setType(ActivityTypeEnum.INTEGRALS.getId());
                            //获取该sku对应的积分活动的价格
                            AmActivityRelGoodsSkuPo activityRelGoodsSkuPo = amActivityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda().and(obj -> obj
                                    .eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())
                                    .eq(AmActivityRelGoodsSkuPo::getSkuId, b.getId())));
                            if (activityRelGoodsSkuPo != null) {
                                Integer activityStock = activityRelGoodsSkuPo.getActivityStock();
                                BigDecimal activityPrice = activityRelGoodsSkuPo.getActivityPrice();
                                //积分抵扣多少钱
                                BigDecimal integralsPrice = BigDecimalUtil.safeSubtract(true, b.getSellPrice(), activityPrice);
                                specifiedSkuVo.setActivityPrice(activityPrice);
                                specifiedSkuVo.setActivityStock(activityStock);
                                specifiedSkuVo.setIntegralsPrice(integralsPrice);
                            }

                        }
                        break;
                    case SECKILL:
                        //这里是活动进行中的商品
                        AmSeckillPo seckillPo = seckillMapper.selectOne(new QueryWrapper<AmSeckillPo>().lambda().and(obj ->
                                obj.eq(AmSeckillPo::getEnable, true).eq(AmSeckillPo::getId, relActivityGoodsPo.getActivityId())));
                        if (seckillPo == null) {
                            goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                        } else {
                            goodsActivityVo.setType(ActivityTypeEnum.SECKILL_ING.getId());
                            //获取该sku对应的秒杀活动的价格、库存
                            AmActivityRelGoodsSkuPo activityRelGoodsSkuPo = amActivityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda().and(obj -> obj
                                    .eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())
                                    .eq(AmActivityRelGoodsSkuPo::getSkuId, b.getId())));
                            if (activityRelGoodsSkuPo != null) {
                                Integer activityStock = activityRelGoodsSkuPo.getActivityStock();
                                BigDecimal activityPrice = activityRelGoodsSkuPo.getActivityPrice();
                                specifiedSkuVo.setActivityPrice(activityPrice);
                                specifiedSkuVo.setActivityStock(activityStock);
                            }

                        }

                        break;
                    case SPELL_GROUP:

                        //这里是活动进行中的商品
                        AmSpellGroupPo spellGroupPo = spellGroupMapper.selectOne(new QueryWrapper<AmSpellGroupPo>().lambda().and(obj ->
                                obj.eq(AmSpellGroupPo::getEnable, true).eq(AmSpellGroupPo::getId, relActivityGoodsPo.getActivityId())));
                        if (spellGroupPo == null) {
                            goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                        } else {
                            goodsActivityVo.setType(ActivityTypeEnum.SPELL_GROUP.getId());
                            //获取该sku对应的秒杀活动的价格、库存
                            AmActivityRelGoodsSkuPo activityRelGoodsSkuPo = amActivityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda().and(obj -> obj
                                    .eq(AmActivityRelGoodsSkuPo::getRelId, relActivityGoodsPo.getId())
                                    .eq(AmActivityRelGoodsSkuPo::getSkuId, b.getId())));
                            if (activityRelGoodsSkuPo != null) {
                                Integer activityStock = activityRelGoodsSkuPo.getActivityStock();
                                BigDecimal activityPrice = activityRelGoodsSkuPo.getActivityPrice();
                                specifiedSkuVo.setActivityPrice(activityPrice);
                                specifiedSkuVo.setActivityStock(activityStock);
                            }

                        }

                        break;
                }
            }

            //获取距离当前时间为一天的待开始的秒杀活动信息,------秒杀待开始不需要算出sku对应的活动库存和活动价格
            /*if (relSecKilllGoodsPo != null) {
                AmSeckillPo seckillPo = seckillMapper.selectOne(new QueryWrapper<AmSeckillPo>().lambda().and(obj ->
                        obj.eq(AmSeckillPo::getEnable, true).eq(AmSeckillPo::getId, relSecKilllGoodsPo.getActivityId())));
                //活动是否取消
                if (seckillPo == null) {
                    goodsActivityVo.setType(ActivityTypeEnum.NON.getId());
                } else {
                    goodsActivityVo.setType(ActivityTypeEnum.SECKILL_PRE.getId());

                    //获取该sku对应的秒杀活动的价格、库存
                    AmActivityRelGoodsSkuPo activityRelGoodsSkuPo = amActivityRelGoodsSkuMapper.selectOne(new QueryWrapper<AmActivityRelGoodsSkuPo>().lambda().and(obj->obj
                            .eq(AmActivityRelGoodsSkuPo::getRelId,relActivityGoodsPo.getId())
                            .eq(AmActivityRelGoodsSkuPo::getSkuId,b.getId())));
                    if (activityRelGoodsSkuPo != null){
                        Integer activityStock = activityRelGoodsSkuPo.getActivityStock();
                        BigDecimal activityPrice = activityRelGoodsSkuPo.getActivityPrice();
                        specifiedSkuVo.setActivityPrice(activityPrice);
                        specifiedSkuVo.setActivityStock(activityStock);
                    }

                }
            }*/


            /*********************** 商品参加的活动,sku对应的活动价格、活动库存 end***************************/

            skuDetail.put(String.valueOf(relIds), specifiedSkuVo);
            finalSpecifiedGoodsVo.setSkuDetail(skuDetail);
        });

        specifiedGoodsVo.setGoodsActivityVo(goodsActivityVo);

        return specifiedGoodsVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubmitOrderVo submitOrder(SubmitOrderDto submitOrderDto, UmUserPo umUserPo) {

        UmUserPo currentUser = securityUtil.getAppCurrUser();

        //检查库存,设置一些需要计算购物券的值，并把所有商品抽出来
        List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos = checkStock(submitOrderDto);
        List<Long> skuIds = shopTicketSoWithCarGoodDtos.stream().map(ShopTicketSoWithCarGoodDto::getId).collect(Collectors.toList());

        //查出活动商品
        List<ActivitySkuBo> boByIds = amActivityRelGoodsSkuMapper.getBoByIds(skuIds);
        //满足参加活动条件的商品，用于减去活动库存的
        List<ActivitySkuBo> activitySkuBos = com.google.common.collect.Lists.newArrayList();
        //活动商品设置数量
        boByIds.forEach(x -> {
            x.setNumber(shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().orElse(null).getNumber());
        });
        boByIds.forEach(x -> {
            Integer buyNumber = shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().get().getNumber();
            //判断限购
            if (x.getBuyLimit() != 0 && x.getBuyLimit() < buyNumber) {
                String goodsName = skuMapper.getGoodsName(x.getSkuId());
                throw new ServiceException(ResultCode.FAIL,
                        String.format("商品名称【%s】购买数量【%s】大于限购数量【%s】，请重新选择。", goodsName, buyNumber, x.getBuyLimit()));
            }
            //判断活动库存
            if (x.getActivityStock() < buyNumber) {
                String goodsName = skuMapper.getGoodsName(x.getSkuId());
                throw new ServiceException(ResultCode.FAIL,
                        String.format("商品名称【%s】购买数量【%s】大于活动库存【%s】，请重新选择。", goodsName, buyNumber, x.getActivityStock()));
            }
        });

        //如果是拼团结算
        BigDecimal groupDiscountMoney = BigDecimal.ZERO;
        if (submitOrderDto.getIsGroup() != null && submitOrderDto.getIsGroup()) {
            ActivitySkuBo groupSku = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.SPELL_GROUP).findFirst().orElse(null);
            if (groupSku == null) {
                throw new ServiceException(ResultCode.FAIL, "当前商品无参加拼团活动");
            }
            if (submitOrderDto.getGoodsTypeOrderDtos().size() != 1 ||
                    submitOrderDto.getGoodsTypeOrderDtos().get(0).getShopTicketSoWithCarGoodDtos().size() != 1) {
                throw new ServiceException(ResultCode.FAIL, "拼团只能有一个商品！");
            }
            //如果是参团，判断人数有没有满
            if (submitOrderDto.getMainId() != null) {
                AmSpellGroupMainPo queryGroupMain = spellGroupMainMapper.selectById(submitOrderDto.getMainId());
                if (queryGroupMain.getLockStock().equals(queryGroupMain.getConditionNum())) {
                    throw new ServiceException(ResultCode.FAIL, "手太慢了！该拼团人员满了！");
                }
                AmActivityRelActivityGoodsPo queryRelActivityGoods = relActivityGoodsMapper.selectById(queryGroupMain.getRelId());
                if (queryRelActivityGoods == null) {
                    throw new ServiceException(ResultCode.FAIL, "数据错误！参加的团所关联的商品为空");
                }
                if (!groupSku.getActivityId().equals(queryRelActivityGoods.getActivityId())) {
                    throw new ServiceException(ResultCode.FAIL, "数据错误！商品未参加该拼团活动！");
                }
            }
            AmSpellGroupPo querySpellGroup = spellGroupMapper.selectById(groupSku.getActivityId());
            PmMemberLevelPo memberLevel = memberLevelMapper.selectById(querySpellGroup.getMemberLevelId());
            //用户会员等级大于等于活动限定的等级
            if (currentUser.getLevel() < memberLevel.getLevel()) {
                throw new ServiceException(ResultCode.FAIL,
                        String.format("该活动参与最低等级为【%s】,用户等级为【%s】，无法购买此活动商品", memberLevel.getLevel(), currentUser.getLevel()));
            }
            //用于减活动库存
            activitySkuBos.add(groupSku);
            shopTicketSoWithCarGoodDtos.get(0).setRealPayMoney(groupSku.getActivityPrice());
            shopTicketSoWithCarGoodDtos.get(0).setActivityType(4);
            groupDiscountMoney = BigDecimalUtil.safeMultiply(shopTicketSoWithCarGoodDtos.get(0).getNumber(),
                    BigDecimalUtil.safeSubtract(shopTicketSoWithCarGoodDtos.get(0).getSellPrice(), groupSku.getActivityPrice()));


        }

        //秒杀销售价改为活动价
        List<ActivitySkuBo> seckills = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.SECKILL).collect(Collectors.toList());
        seckills.forEach(x -> {
            //查询秒杀限定的会员等级
            AmSeckillPo querySeckill = seckillMapper.selectById(x.getActivityId());
            PmMemberLevelPo memberLevel = memberLevelMapper.selectById(querySeckill.getMemberLevelId());
            //用户会员等级大于等于活动限定的等级
            if (currentUser.getLevel() >= memberLevel.getLevel()) {
                //用于减活动库存
                activitySkuBos.add(x);
                ShopTicketSoWithCarGoodDto shopTicketSoWithCarGoodDto = shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().get();
                shopTicketSoWithCarGoodDto.setRealPayMoney(x.getActivityPrice());
                shopTicketSoWithCarGoodDto.setActivityType(3);

            }
        });

        //积分不足用原价。足够用积分+活动价
        final BigDecimal[] totalIntegral = {BigDecimal.ZERO};
        //使用葱鸭钱包才可以使用积分抵扣，没有则按照原价
        if (submitOrderDto.getIsUseWallet()) {
            List<ActivitySkuBo> integrals = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.INTEGRALS).collect(Collectors.toList());
            //用户当前积分
            final BigDecimal[] currentIntegral = {currentUser.getCurrentIntegral()};
            integrals.forEach(x -> {
                BasicSettingPo queryBasic = basicSettingMapper.selectOne(new QueryWrapper<>());
                //售价与活动价的差价
                BigDecimal price = BigDecimalUtil.safeSubtract(x.getSellPrice(), x.getActivityPrice());
                ShopTicketSoWithCarGoodDto shopTicketSoWithCarGoodDto = shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().get();
                //需要多少积分
                BigDecimal integral = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(price, queryBasic.getMoneyToIntegrate()), shopTicketSoWithCarGoodDto.getNumber());
                //查询积分活动限定的会员等级
                AmIntegralsPo queryIntegral = integralsMapper.selectById(x.getActivityId());
                PmMemberLevelPo memberLevel = memberLevelMapper.selectById(queryIntegral.getMemberLevelId());
                //如果积分足够且用户的等级足够
                if (currentIntegral[0].compareTo(integral) >= 0 && currentUser.getLevel() >= memberLevel.getLevel()) {
                    //用于减活动库存
                    activitySkuBos.add(x);
                    //销售价改为活动价
                    shopTicketSoWithCarGoodDto.setRealPayMoney(x.getActivityPrice()).setIntegral(integral);
                    shopTicketSoWithCarGoodDto.setActivityType(2);
                    shopTicketSoWithCarGoodDto.setIntegralMoney(price);
                    currentIntegral[0] = BigDecimalUtil.safeSubtract(currentIntegral[0], integral);
                    totalIntegral[0] = BigDecimalUtil.safeAdd(totalIntegral[0], integral);
                }
            });
        }

        //判断sku是否参加了满减活动
        List<FullDiscountSkuBo> fullDiscountSkuBos = amActivityRelGoodsSkuMapper.getFullDiscountSkuBo(skuIds);
        //活动商品设置数量
        fullDiscountSkuBos.forEach(x -> {
            x.setNumber(shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).findFirst().orElse(null).getNumber());
        });

        //满减金额
        BigDecimal totalFullDiscount = BigDecimal.ZERO;
        if (!ListUtil.isListNullAndEmpty(fullDiscountSkuBos)) {

            //根据满减的活动id分组
            Map<Long, List<FullDiscountSkuBo>> groupFullDiscountMap = fullDiscountSkuBos.stream().collect(Collectors.groupingBy(x -> x.getActivityId()));
            //满减优惠了多少钱
            //遍历map,算出整个支付单满减优惠了多少钱
            for (Map.Entry<Long, List<FullDiscountSkuBo>> entry : groupFullDiscountMap.entrySet()) {
                Long activityId = entry.getKey();
                List<FullDiscountSkuBo> fullDiscountSkuBoList = entry.getValue();
                //满多少钱可以减
                BigDecimal reductionFullMoney = fullDiscountSkuBoList.get(0).getReductionFullMoney();
                //减多少钱
                BigDecimal reductionPostMoney = fullDiscountSkuBoList.get(0).getReductionPostMoney();
                //该支付单下同个满减活动下商品的销售总价
                BigDecimal sumSellPrice = fullDiscountSkuBoList.stream().map(x -> BigDecimalUtil.safeMultiply(x.getSellPrice(), x.getNumber())).reduce(BigDecimal.ZERO, BigDecimal::add);
                //查询满减活动限定的会员等级
                AmReducedPo queryReduce = reducedMapper.selectById(activityId);
                PmMemberLevelPo memberLevel = memberLevelMapper.selectById(queryReduce.getMemberLevelId());
                //如果满足满减优惠且会员等级满足
                if (sumSellPrice.compareTo(reductionFullMoney) >= 0 && currentUser.getLevel() >= memberLevel.getLevel()) {
                    //找出满足满减优惠的skuid集合，计算他们的付现价
                    List<Long> fullSkuIds = fullDiscountSkuBoList.stream().map(FullDiscountSkuBo::getSkuId).collect(Collectors.toList());
                    List<ShopTicketSoWithCarGoodDto> fullRedetionSkus = shopTicketSoWithCarGoodDtos.stream().filter(x -> fullSkuIds.contains(x)).collect(Collectors.toList());
                    //计算满减的付现价
                    setRealPayMoneyForFullRedution(fullRedetionSkus, reductionPostMoney);
                    fullRedetionSkus.forEach(x->x.setActivityType(1));

                    //用于减活动库存
                    fullDiscountSkuBoList.forEach(x -> {
                        ActivitySkuBo activitySkuBo = new ActivitySkuBo();
                        activitySkuBo.setSkuRelId(x.getRelId()).setNumber(x.getNumber());
                        activitySkuBos.add(activitySkuBo);
                    });

                    totalFullDiscount = BigDecimalUtil.safeAdd(totalFullDiscount, reductionPostMoney);
                }

            }
        }

        BigDecimal reduceCouponMoney = BigDecimal.ZERO;
        //优惠券
        if (submitOrderDto.getCouponRelUserId() != null && !submitOrderDto.getCouponRelUserId().equals(0L)) {
            List<SelectCouponVo> querySelectCouponVoList = couponMapper.getSelectCouPonVo(currentUser.getId(), skuIds, submitOrderDto.getCouponRelUserId());

            //如果是包邮类型，将sku设置为包邮
            querySelectCouponVoList.stream().filter(x -> x.getType() == 3).forEach(x -> {
                shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).forEach(y -> y.setIsFreePostage(true));
            });
            //如果是满减,加入总优惠
            SelectCouponVo reduceCoupon = querySelectCouponVoList.stream().filter(x -> x.getType() == 1).findFirst().orElse(null);
            if (reduceCoupon != null) {
                BigDecimal reducePriceSum = querySelectCouponVoList.stream().filter(x -> x.getType() == 1).map(SelectCouponVo::getSellPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                //商品价格是否满足打折标准
                if (reducePriceSum.compareTo(reduceCoupon.getReductionFullMoney()) >= 0) {
                    //找出满足满减优惠的skuid集合，计算他们的付现价
                    List<Long> fullSkuIds = querySelectCouponVoList.stream().map(SelectCouponVo::getSkuId).collect(Collectors.toList());
                    List<ShopTicketSoWithCarGoodDto> fullRedetionSkus = shopTicketSoWithCarGoodDtos.stream().filter(x -> fullSkuIds.contains(x)).collect(Collectors.toList());
                    //计算满减的付现价
                    setRealPayMoneyForFullRedution(fullRedetionSkus, reduceCoupon.getReductionFullMoney());
                    //满减优惠券金额加入总优惠
                    reduceCouponMoney = reduceCoupon.getReductionPostMoney();
                    //优惠券状态变为已使用
                    AmCouponRelCouponUserPo updateCouponRelCouponUser = new AmCouponRelCouponUserPo();
                    updateCouponRelCouponUser.setId(submitOrderDto.getCouponRelUserId()).setUseStatus(1);
                    relCouponUserMapper.updateById(updateCouponRelCouponUser);
                }

            }
            //如果是满折扣，将sku价格改变
            SelectCouponVo discountCoupon = querySelectCouponVoList.stream().filter(x -> x.getType() == 2).findFirst().orElse(null);
            if (discountCoupon != null) {
                BigDecimal discountPriceSum = querySelectCouponVoList.stream().filter(x -> x.getType() == 2).map(SelectCouponVo::getSellPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                //商品价格是否满足打折标准
                if (discountPriceSum.compareTo(discountCoupon.getDiscountFullMoney()) >= 0) {
                    //折扣换算成小数
                    BigDecimal discount = BigDecimalUtil.safeDivide(discountCoupon.getDiscount(), 10);
                    //sku价格打折
                    querySelectCouponVoList.stream().filter(x -> x.getType() == 2).forEach(x -> {
                        shopTicketSoWithCarGoodDtos.stream().filter(y -> y.getId().equals(x.getSkuId())).
                                forEach(y -> {
                                    y.setRealPayMoney(BigDecimalUtil.safeMultiply(y.getSellPrice(), discount));
                                    //商品优惠券抵扣金额
                                    y.setCouponMoney(BigDecimalUtil.safeSubtract(y.getSellPrice(),y.getRealPayMoney()));
                                });
                    });
                    //优惠券状态变为已使用
                    AmCouponRelCouponUserPo updateCouponRelCouponUser = new AmCouponRelCouponUserPo();
                    updateCouponRelCouponUser.setId(submitOrderDto.getCouponRelUserId()).setUseStatus(1);
                    relCouponUserMapper.updateById(updateCouponRelCouponUser);
                }
            }
        }

        //收货相关信息
        UmAreaShippingPo umAreaShippingPo = umAreaShippingMapper.selectById(submitOrderDto.getUmAreaShipId());

        //判断商品是否保税仓或者海外直邮，如果没传就表示之前实名认证过，找出之前的
        List<ShopTicketSoWithCarGoodDto> needRealSkuList = shopTicketSoWithCarGoodDtos.stream().filter(x -> needRealGoodsType.contains(x.getGoodsType())).collect(Collectors.toList());
        if (!ListUtil.isListNullAndEmpty(needRealSkuList)) {
            if (submitOrderDto.getRealUserId() == null) {
                QueryWrapper<OmRealUserPo> realUserWrapper = new QueryWrapper<>();
                realUserWrapper.lambda().eq(OmRealUserPo::getPhone, umAreaShippingPo.getMobile());
                List<OmRealUserPo> omRealUserPos = realUserMapper.selectList(realUserWrapper);
                submitOrderDto.setRealUserId(omRealUserPos.get(0).getId());
            }
        }

        //获取系统基本参数
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        //设置会员等级比例和购物券比例
        setPurchaseAndShopTicket(shopTicketSoWithCarGoodDtos, umUserPo, basicSettingPo);
        //遍历订单
        final BigDecimal[] payMoney = {BigDecimal.ZERO};
        final BigDecimal[] totalShipMoney = {BigDecimal.ZERO};
        final BigDecimal[] totalTaxMoney = {BigDecimal.ZERO};
        submitOrderDto.getGoodsTypeOrderDtos().forEach(x -> {

            BigDecimal taxMoney = BigDecimal.ZERO;
            //判断商品是否保税仓或者海外直邮，且计算税率
            if (needRealGoodsType.contains(x.getGoodsType())) {
                //商品的税率=数量*销售价*百分比/100
                taxMoney = x.getShopTicketSoWithCarGoodDtos().stream().map(y -> BigDecimalUtil.safeMultiply(y.getNumber(),
                        BigDecimalUtil.safeMultiply(y.getRealPayMoney(), transfromDecimal(y.getCustomTaxRate())))).
                        reduce(BigDecimal.ZERO, BigDecimal::add);
                x.setTaxMoney(taxMoney);
            } else {
                x.setTaxMoney(BigDecimal.ZERO);
            }
            //将dto转为vo是（兼容之前计算运费需要传vo）
            List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos = Lists.newArrayList();
            x.getShopTicketSoWithCarGoodDtos().forEach(y -> {
                ShopTicketSoWithCarGoodVo shopTicketSoWithCarGoodVo = new ShopTicketSoWithCarGoodVo();
                BeanUtils.copyProperties(y, shopTicketSoWithCarGoodVo);
                shopTicketSoWithCarGoodVos.add(shopTicketSoWithCarGoodVo);
            });
            //计算邮费
            BigDecimal shipMoney = getShipMoney(shopTicketSoWithCarGoodVos, submitOrderDto.getUmAreaShipId(), currentUser);
            x.setShipMoney(shipMoney);

            //商品总额=数量*单价(销售价)
            BigDecimal goodsMoney = x.getShopTicketSoWithCarGoodDtos().stream().map(y -> BigDecimalUtil.safeMultiply(y.getNumber(), y.getSellPrice())).
                    reduce(BigDecimal.ZERO, BigDecimal::add);

            //订单总额=商品价格+运费+税费
            x.setTotalMoney(BigDecimalUtil.safeAdd(taxMoney, shipMoney, goodsMoney));

            //支付单总额
            payMoney[0] = BigDecimalUtil.safeAdd(payMoney[0], goodsMoney, shipMoney, taxMoney);
            //支付单总运费
            totalShipMoney[0] = BigDecimalUtil.safeAdd(totalShipMoney[0], shipMoney);
            //支付单总税费
            totalTaxMoney[0] = BigDecimalUtil.safeAdd(totalTaxMoney[0], taxMoney);

        });
        submitOrderDto.setTotalMoney(payMoney[0]).setTotalShipMoney(totalShipMoney[0])
                .setTotalTaxMoney(totalTaxMoney[0]);

        //支付单商品活动价格总和
        BigDecimal goodsActivityPayInPayOrder = shopTicketSoWithCarGoodDtos.stream().map(x ->
                BigDecimalUtil.safeMultiply(x.getRealPayMoney(), x.getNumber())).reduce(BigDecimal.ZERO, BigDecimal::add);
        //支付单应付金额（还没减去葱鸭钱包的）
        BigDecimal payRealPayNoRed = BigDecimalUtil.safeAdd(goodsActivityPayInPayOrder, totalShipMoney[0], totalTaxMoney[0]);


        //如果不使用葱鸭钱包
        if (!submitOrderDto.getIsUseWallet()) {
            /*//实付金额=订单总金额(活动优惠后的金额)
            submitOrderDto.setTotalRealPayMoney(payMoney[0]);*/
            submitOrderDto.setTotalRedEnvelopsMoney(BigDecimal.ZERO);
            submitOrderDto.setTotalShopTicketMoney(BigDecimal.ZERO);
        } else {
            //设置总红包和购物券分别抵扣的金额
            setDiscountInSubmit(payRealPayNoRed, submitOrderDto, currentUser, basicSettingPo);

        }


        //生成商品快照
        List<OmGoodsTempPo> saveGoodsTemps = Lists.newArrayList();
        //商品订单
        List<OmOrderPo> saveOrders = Lists.newArrayList();
        //支付单实付金额
        BigDecimal payTotalRealMoney = BigDecimalUtil.safeSubtract(payRealPayNoRed, submitOrderDto.getTotalRedEnvelopsMoney(),
                submitOrderDto.getTotalShopTicketMoney());
        //总支付单
        PayOrderPo savePayOrderPo = new PayOrderPo();
        //红包金额换算成红包个数
        BigDecimal totalRedEnvelops = submitOrderDto.getTotalRedEnvelopsMoney();
        //优惠券金额换算成优惠券个数
        BigDecimal totalShopTicket = BigDecimalUtil.safeMultiply(submitOrderDto.getTotalShopTicketMoney(), basicSettingPo.getMoneyToShopTicket());
        //设置商品总额、使用购物券、使用红包、运费、税费
        //实际付款、总优惠
        savePayOrderPo.setTotalMoney(payMoney[0]).setTotalShopTicketMoney(submitOrderDto.getTotalShopTicketMoney())
                .setTotalRedEnvelopsMoney(submitOrderDto.getTotalRedEnvelopsMoney()).setTotalShipMoney(totalShipMoney[0])
                .setTotalTaxMoney(totalTaxMoney[0]).setTotalRealPayMoney(payTotalRealMoney).
                //总优惠=红包+购物券+满减活动+满减优惠券（没有算秒杀、积分、折扣优惠券的差价）
                        setTotalDiscount(BigDecimalUtil.safeSubtract(payMoney[0], payTotalRealMoney))
                .setTotalRedEnvelops(totalRedEnvelops).setTotalShopTicket(totalShopTicket);

        savePayOrderPo.setShipName(umAreaShippingPo.getShipName()).setPhone(umAreaShippingPo.getMobile())
                .setShipAddress(umAreaShippingPo.getDetailedAddress()).setUmUserId(umUserPo.getId()).
                setAreaName(umAreaShippingPo.getAreaName()).setTotalNumber(0).setId(SnowFlakeUtil.getFlowIdInstance().nextId());


        //用户所属店铺id
        Long userStoreId = userMapper.getUserStoreId(umUserPo.getId());


        //循环遍历 生成订单
        submitOrderDto.getGoodsTypeOrderDtos().forEach(x -> {
            //循环遍历店铺下的订单
            //生成订单
            OmOrderPo saveOrder = new OmOrderPo();
            if (submitOrderDto.getRealUserId() != null) {
                saveOrder.setRealUserId(submitOrderDto.getRealUserId());
            }
            //remark  storeId goodsType shipMoney taxMoney totalMoney
            BeanUtils.copyProperties(x, saveOrder);
            saveOrder.setUmUserId(umUserPo.getId()).setAreaShippingId(submitOrderDto.getUmAreaShipId())
                    .setStoreId(x.getStoreId()).setPayOrderId(savePayOrderPo.getId()).setCreateBy(umUserPo.getId() + "")
                    .setId(SnowFlakeUtil.getFlowIdInstance().nextId()).setUpdateTime(LocalDateTime.now());
            //设置一些优惠信息：红包、购物券(个数以及抵扣金额)
            setDiscountMessage(saveOrder, savePayOrderPo);


            //设置订单数量
            Integer goodsNumberInOrder = x.getShopTicketSoWithCarGoodDtos().stream().mapToInt(ShopTicketSoWithCarGoodDto::getNumber)
                    .sum();
            saveOrder.setTotalNumber(goodsNumberInOrder);

            SmStorePo queryStore = storeMapper.selectById(x.getStoreId());
            //设置用户所属店铺
            saveOrder.setUserStoreId(userStoreId).setIncomeRate(queryStore.getIncomeRate());


            //商品活动总价
            BigDecimal goodsActivityMoneyInOrder = x.getShopTicketSoWithCarGoodDtos().stream().map(y ->
                    BigDecimalUtil.safeMultiply(y.getRealPayMoney(), y.getNumber())
            ).reduce(BigDecimal.ZERO, BigDecimal::add);
            //订单实付金额（还没减去红包购物券）
            BigDecimal realMoneyInOrderNoRed = BigDecimalUtil.safeAdd(goodsActivityMoneyInOrder, x.getShipMoney(), x.getTaxMoney());
            //订单实付金额
            BigDecimal realMoneyInOrder = BigDecimalUtil.safeSubtract(realMoneyInOrderNoRed, saveOrder.getRedEnvelopsMoney(), saveOrder.getShopTicketMoney());

            saveOrder.setRealMoney(realMoneyInOrder);
            saveOrders.add(saveOrder);
            if (savePayOrderPo.getTotalRealPayMoney().compareTo(BigDecimal.ZERO) == 0) {
                saveOrder.setRealMoney(orderMinPayMoney);
            }
            //每个订单至少要支付一分钱
            else {
                if (realMoneyInOrder.compareTo(orderMinPayMoney) < 0) {
                    saveOrder.setRealMoney(orderMinPayMoney);
                }
            }

            //订单设置优惠券抵扣金额和积分抵扣金额
            final BigDecimal[] couponMoney = {BigDecimal.ZERO};
            final BigDecimal[] integralMoney = {BigDecimal.ZERO};
            //生成商品快照
            x.getShopTicketSoWithCarGoodDtos().forEach(g -> {
                couponMoney[0] =BigDecimalUtil.safeAdd(couponMoney[0],g.getCouponMoney());
                integralMoney[0] =BigDecimalUtil.safeAdd(integralMoney[0],g.getIntegralMoney());
                PmGoodsSkuPo skuPo = skuMapper.selectById(g.getId());
                OmGoodsTempPo saveGoodsTemp = new OmGoodsTempPo();
                //number sellPrice profitRate supplierPrice icon name standardStr
                //realPayMoney integral
                BeanUtils.copyProperties(g, saveGoodsTemp, "id");
                saveGoodsTemp.setCreateBy(umUserPo.getId() + "").setOrderId(saveOrder.getId()).setSkuId(g.getId());
                saveGoodsTemp.setSupplierPrice(skuPo.getSupplierPrice()).setProfitRate(skuPo.getProfitRate())
                        .setGoodsId(skuPo.getGoodsId()).setArticleNumber(skuPo.getArticleNumber()).
                        //预计奖励购物券
                                setRewardShopTicket(g.getRewardShopTicket())
                        //预计奖励经验值
                        .setRewardExperience(BigDecimalUtil.safeMultiply(basicSettingPo.getMoneyToExperience(), g.getRealPayMoney()))
                        //预计奖励积分
                        .setRewardIntegrate(BigDecimalUtil.safeMultiply(basicSettingPo.getOwnRewardIntegrate(), transfromDecimal(g.getRealPayMoney())));
                ;
                //商品活动价格占订单商品活动价格的比例
                BigDecimal ratio = BigDecimalUtil.safeDivide(BigDecimalUtil.safeMultiply(g.getRealPayMoney(), g.getNumber()),
                        goodsActivityMoneyInOrder);
                //快照购物券、红包、以及对应的金额
                BigDecimal goodsShopTicket = BigDecimalUtil.safeMultiply(ratio, saveOrder.getShopTicket());
                BigDecimal goodsRedEnvelops = BigDecimalUtil.safeMultiply(ratio, saveOrder.getRedEnvelops());
                BigDecimal goodsShopTicketMoney = BigDecimalUtil.safeMultiply(ratio, saveOrder.getShopTicketMoney());
                BigDecimal goodsRedEnvelopsMoney = BigDecimalUtil.safeMultiply(ratio, saveOrder.getRedEnvelopsMoney());
                //saveGoodsTemp.setRealPayMoney(BigDecimalUtil.safeMultiply(ratio, realPayMoney));
                saveGoodsTemp.setRealPayMoney(BigDecimalUtil.safeSubtract(g.getRealPayMoney(), goodsShopTicketMoney, goodsRedEnvelopsMoney));
                saveGoodsTemp.setRed(goodsRedEnvelops).setShopTicket(goodsShopTicket);
                saveGoodsTemp.setActivityType(g.getActivityType());
                saveGoodsTemps.add(saveGoodsTemp);

            });
            saveOrder.setCouponMoney(couponMoney[0]);
            saveOrder.setIntegralMoney(integralMoney[0]);
        });
        savePayOrderPo.setTotalRealPayMoney(saveOrders.stream().map(OmOrderPo::getRealMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
        //生成支付单
        payOrderMapper.insert(savePayOrderPo);

        //保存订单
        omOrderService.saveBatch(saveOrders);
        //保存商品快照
        goodsTempService.saveBatch(saveGoodsTemps);

        //实名认证添加收货人手机
        if (submitOrderDto.getRealUserId() != null) {
            OmRealUserPo updateRealUser = new OmRealUserPo();
            updateRealUser.setId(submitOrderDto.getRealUserId()).setPhone(umAreaShippingPo.getMobile());
            realUserMapper.updateById(updateRealUser);
        }
        //减去库存
        skuMapper.updateStock2(shopTicketSoWithCarGoodDtos);
        if (!ListUtil.isListNullAndEmpty(activitySkuBos)) {
            //减去活动库存
            amActivityRelGoodsSkuMapper.updateStock(boByIds);
        }
        //减去购物券和红包
        mapper.updateDiscount(totalRedEnvelops, totalShopTicket, totalIntegral[0], umUserPo.getId());
        //减去购物车
        delShopCatAfterOrder2(shopTicketSoWithCarGoodDtos, umUserPo.getId());
        //保存返佣用户信息
        PayUserRelationPo queryPayUser = getPayUserMessage(umUserPo.getId());
        if (queryPayUser != null) {
            saveOrders.forEach(x -> {
                PayUserRelationPo savePayUser = new PayUserRelationPo();
                BeanUtils.copyProperties(queryPayUser, savePayUser);
                savePayUser.setCreateBy(umUserPo.getId() + "").setOrderId(x.getId());
                payUserRelationService.save(savePayUser);
                //保存下级用户集合
                List<PayUserRelationNextLevelPo> savePayUserRelationNextLevels = com.google.common.collect.Lists.newArrayList();
                if (!ListUtil.isListNullAndEmpty(queryPayUser.getNextUserIds())) {
                    queryPayUser.getNextUserIds().forEach(y -> {
                        PayUserRelationNextLevelPo savePayUserRelationNextLevel = new PayUserRelationNextLevelPo();
                        savePayUserRelationNextLevel.setNextUserId(y).setPayUserRealtionId(savePayUser.getId());
                        savePayUserRelationNextLevels.add(savePayUserRelationNextLevel);
                    });
                    payUserRelationNextLevelService.saveBatch(savePayUserRelationNextLevels);
                }
            });
        }

        AmSpellGroupMemberPo saveMember = new AmSpellGroupMemberPo();
        //如果是拼团(在前面满足条件都判断过了，这里不需要重复判断)
        if (submitOrderDto.getIsGroup() != null && submitOrderDto.getIsGroup()) {
            ActivitySkuBo groupSku = boByIds.stream().filter(x -> x.getActivityType() == ActivityTypeEnum.SPELL_GROUP).findFirst().orElse(null);
            //如果是参团
            if (submitOrderDto.getMainId() != null) {
                AmSpellGroupMainPo queryGroupMain = spellGroupMainMapper.selectById(submitOrderDto.getMainId());
                //锁定库存加1
                AmSpellGroupMainPo updateMain = new AmSpellGroupMainPo();
                updateMain.setId(submitOrderDto.getMainId()).setLockStock(queryGroupMain.getLockStock() + 1);
                spellGroupMainMapper.updateById(updateMain);
                //增加拼团成员数据
                saveMember.setIsHead(false).setUserId(currentUser.getId()).setCreateBy(currentUser.getId() + "")
                        .setGroupMainId(submitOrderDto.getMainId()).setOrderId(saveOrders.get(0).getId())
                        .setPayStatus(false).setOrderTime(LocalDateTime.now());
                spellGroupMemberMapper.insert(saveMember);
            }
            //发起团
            else {
                //保存拼团详情
                AmSpellGroupMainPo saveMain = new AmSpellGroupMainPo();
                AmSpellGroupPo querySpellGroup = spellGroupMapper.selectById(groupSku.getActivityId());
                saveMain.setActivityId(groupSku.getActivityId()).setLockStock(1).setRelId(groupSku.getGoodsRelId()).setCreateBy(currentUser.getId() + "")
                        .setConditionNum(querySpellGroup.getGroupNum()).setExpireTime(LocalDateTime.now().plusDays(1l))
                        .setStatus(SpellGroupMainStatusEnum.NEED_PAY.getId()).setStoreId(shopTicketSoWithCarGoodDtos.get(0).getStoreId());
                spellGroupMainMapper.insert(saveMain);

                //保存拼团成员
                saveMember.setOrderTime(LocalDateTime.now()).setPayStatus(false).setOrderId(saveOrders.get(0).getId())
                        .setGroupMainId(saveMain.getId()).setCreateBy(currentUser.getId() + "").setUserId(currentUser.getId())
                        .setIsHead(true);
                spellGroupMemberMapper.insert(saveMember);

                // 24小时内未拼团成功
                this.rabbitTemplate.convertAndSend(RabbitConstants.CLOSE_GROUP_EXCHANGE, RabbitConstants.CLOSE_GROUP_ROUTING_KEY, saveMain.getId(), message -> {

                    message.getMessageProperties().setDelay(RabbitConstants.CLOSE_GROUP_TIME);
                    return message;
                });
                LoggerUtil.info("【评团开始，24小时后人数不足取消该评团】:" + LocalDateTime.now());
            }

            // 半小时内未付款，订单关闭，失去拼团名额
            this.rabbitTemplate.convertAndSend(RabbitConstants.DEL_MEMBER_EXCHANGE, RabbitConstants.DEL_MEMBER_ROUTING_KEY, saveMember.getId(), message -> {
                message.getMessageProperties().setDelay(RabbitConstants.DEL_MEMBER_TIME);
                return message;
            });
            LoggerUtil.info("【拼团下单成功，半小时内未付款取消关闭订单，取消拼团资格】:" + LocalDateTime.now());


        }

        //过期时间
        Integer expireTime = basicSettingPo.getAutoCloseOrderDay() * 24 * 60 * 60 * 1000;

        if (expireTime!=0) {
            // 添加超时未支付自动取消订单延时队列
            this.rabbitTemplate.convertAndSend(RabbitConstants.CLOSE_ORDER_EXCHANGE, RabbitConstants.CLOSE_ORDER_ROUTING_KEY, savePayOrderPo.getId(), message -> {

                // message.getMessageProperties().setExpiration(basicSettingPo.getAutoCloseOrderDay()*24*60*60*1000 + "");
                message.getMessageProperties().setDelay(expireTime);
                return message;
            });
            LoggerUtil.info("【下单等待取消订单发送时间】:" + LocalDateTime.now());
        }

        SubmitOrderVo submitOrderVo = new SubmitOrderVo();
        submitOrderVo.setPayOrderId(savePayOrderPo.getId()).setTotalRealPayMoney(savePayOrderPo.getTotalRealPayMoney())
                .setTotalRedEnvelops(totalRedEnvelops).setTotalShopTicket(totalShopTicket).setTotalIntegral(totalIntegral[0]);

        //1.订单下单流水生成  下单的时候就扣除红包购物券积分
        AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
        addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.APP_ORDER);
        addAccountLogBo.setRelId(savePayOrderPo.getId());
        addAccountLogBo.setOperator(String.valueOf(umUserPo.getId()));
        addAccountLogBo.setMarginIntegral(submitOrderVo.getTotalIntegral());
        //listenerOrderLogQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.ACCOUNT_LOG_EXCHANGE, RabbitConstants.ACCOUNT_LOG_ROUTING_KEY, addAccountLogBo);

        return submitOrderVo;
    }

    /**
     * @return void
     * @Author zhangrt
     * @Date 2019/10/17 23:15
     * @Description 计算满减商品的付现价
     * @Update
     * @Param [shopTicketSoWithCarGoodDtos]
     **/

    private void setRealPayMoneyForFullRedution(List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos, BigDecimal reductionPostMoney) {
        //固定成本总和
        BigDecimal fixedCostSum = shopTicketSoWithCarGoodDtos.stream().map(x -> x.computeFixedCost()).reduce(BigDecimal.ZERO, BigDecimal::add);
        shopTicketSoWithCarGoodDtos.forEach(x -> {
            //实际优惠金额
            BigDecimal discountMoney = BigDecimalUtil.safeMultiply(reductionPostMoney,
                    BigDecimalUtil.safeDivide(x.computeFixedCost(), fixedCostSum));
            x.setRealPayMoney(BigDecimalUtil.safeSubtract(x.getSellPrice(), discountMoney));
            x.setCouponMoney(discountMoney);
        });
    }

    private void setRealPayMoneyForFullRedution2(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos, BigDecimal reductionPostMoney, ActivityTypeEnum activityTypeEnum) {
        //固定成本总和
        BigDecimal fixedCostSum = shopTicketSoWithCarGoodVos.stream().map(x -> x.computeFixedCost()).reduce(BigDecimal.ZERO, BigDecimal::add);
        shopTicketSoWithCarGoodVos.forEach(x -> {
            //实际优惠金额
            BigDecimal discountMoney = BigDecimalUtil.safeMultiply(reductionPostMoney,
                    BigDecimalUtil.safeDivide(x.computeFixedCost(), fixedCostSum));
            x.setRealPayMoney(BigDecimalUtil.safeSubtract(x.getSellPrice(), discountMoney)).setType(activityTypeEnum.getId());
        });
    }

    /**
     * @return java.util.List<com.chauncy.data.vo.app.goods.AssociatedGoodsVo>
     * @Author chauncy
     * @Date 2019-09-22 12:33
     * @Description //获取该商品关联的商品--相关推荐
     * @Update chauncy
     * @Param [goodsId]
     **/
    @Override
    public List<AssociatedGoodsVo> getAssociatedGoods(Long goodsId) {
        // 获取关联商品--限制6条
        List<AssociatedGoodsVo> associatedGoodsVos = associationGoodsMapper.searchAssociatedGoodList(goodsId);

        return associatedGoodsVos;
    }

    /**
     * @return java.util.List<com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo>
     * @Author chauncy
     * @Date 2019-09-22 18:00
     * @Description //猜你喜欢
     * @Update chauncy
     * @Param [goodsId]
     **/
    @Override
    public List<SearchGoodsBaseListVo> guessYourLike(Long goodsId, UmUserPo user) {

        Long umUserId = null == user ? null : user.getId();

        PmGoodsPo goodsPo = goodsMapper.selectById(goodsId);
        if (goodsPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "不存在该商品");
        }
        String name = goodsPo.getName();
        List<SearchGoodsBaseListVo> searchGoodsBaseListVos = adviceMapper.guessYourLike(name);

        searchGoodsBaseListVos.forEach(a -> {

            //获取商品的标签
            List<String> labelNames = adviceMapper.getLabelNames(a.getGoodsId());
            a.setLabelNames(labelNames);
            if (null == umUserId) {
                a.setMaxRewardShopTicket(new BigDecimal("0"));
            } else {
                //获取最高返券值
                List<Double> rewardShopTickes = com.google.common.collect.Lists.newArrayList();
                List<RewardShopTicketBo> rewardShopTicketBos = skuMapper.findRewardShopTicketInfos(a.getGoodsId());
                rewardShopTicketBos.forEach(b -> {
                    //商品活动百分比
                    b.setActivityCostRate(a.getActivityCostRate());
                    //让利成本比例
                    b.setProfitsRate(a.getProfitsRate());
                    //会员等级比例
                    BigDecimal purchasePresent = memberLevelMapper.selectById(user.getMemberLevelId()).getPurchasePresent();
                    b.setPurchasePresent(purchasePresent);
                    //购物券比例
                    BigDecimal moneyToShopTicket = basicSettingMapper.selectList(null).get(0).getMoneyToShopTicket();
                    b.setMoneyToShopTicket(moneyToShopTicket);
                    BigDecimal rewardShopTicket = b.getRewardShopTicket();
                    rewardShopTickes.add(rewardShopTicket.doubleValue());
                });
                //获取RewardShopTickes列表最大返券值
                Double maxRewardShopTicket = rewardShopTickes.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
                a.setMaxRewardShopTicket(BigDecimal.valueOf(maxRewardShopTicket));
            }
        });

        return searchGoodsBaseListVos;
    }

    /**
     * @param removeToFavoritesDto
     * @return void
     * @Author chauncy
     * @Date 2019-09-29 20:17
     * @Description //移动购物车商品至收藏夹
     * @Update chauncy
     **/
    @Override
    public void removeToFavorites(RemoveToFavoritesDto removeToFavoritesDto) {

        UmUserPo userPo = securityUtil.getAppCurrUser();
        if (userPo == null) {
            throw new ServiceException(ResultCode.FAIL, "您不是app用户！");
        }

        List<Long> goodsIdList = Arrays.asList(removeToFavoritesDto.getGoodsIds());
        if (!ListUtil.isListNullAndEmpty(goodsIdList)) {
            goodsIdList.forEach(a -> {
                //判断商品是否存在
                if (goodsMapper.selectById(a) == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该商品，请检查");
                }
                //查询是否收藏过,并发情况，锁住行
                UmUserFavoritesPo userFavoritesPo = userFavoritesMapper.selectOne(new QueryWrapper<UmUserFavoritesPo>().lambda()
                        .eq(UmUserFavoritesPo::getUserId, userPo.getId())
                        .eq(UmUserFavoritesPo::getFavoritesId, a)
                        .last("for update"));
                if (userFavoritesPo == null) { //未收藏过

                    userFavoritesPo = new UmUserFavoritesPo();
                    userFavoritesPo.setCreateBy(userPo.getTrueName()).setUserId(userPo.getId())
                            .setId(null).setFavoritesId(a)
                            .setType(KeyWordTypeEnum.GOODS.getName()).setIsFavorites(true);
                    userFavoritesMapper.insert(userFavoritesPo);
                    //不用updateById  update a=a+1
                    goodsMapper.addFavorites(a);

                } else if (!userFavoritesPo.getIsFavorites()) { //收藏过且取消收藏再次收藏
                    userFavoritesPo.setIsFavorites(true);
                    userFavoritesMapper.updateById(userFavoritesPo);
                    //不用updateById  update a=a+1
                    goodsMapper.addFavorites(a);

                }
            });

            List<Long> cartIds = Arrays.asList(removeToFavoritesDto.getCartIds());
            if (!ListUtil.isListNullAndEmpty(cartIds)) {
                cartIds.forEach(b -> {
                    if (mapper.selectById(b) == null) {
                        throw new ServiceException(ResultCode.NO_EXISTS, "购物车数据库不存在该商品，请检查");
                    }
                });
                mapper.deleteBatchIds(cartIds);
            }
        }
    }

    /**
     * @param
     * @return java.util.List<com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo>
     * @Author chauncy
     * @Date 2019-10-09 13:22
     * @Description //购物车空车猜你喜欢
     * @Update chauncy
     **/
    @Override
    public List<SearchGoodsBaseListVo> guessLike() {
        UmUserPo user = securityUtil.getAppCurrUser();
        if (user == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "您不是APP用户");
        }

        List<SearchGoodsBaseListVo> searchGoodsBaseListVos = adviceMapper.guessLike();

        searchGoodsBaseListVos.forEach(a -> {

            //获取商品的标签
            List<String> labelNames = adviceMapper.getLabelNames(a.getGoodsId());
            a.setLabelNames(labelNames);

            //获取最高返券值
            List<Double> rewardShopTickes = com.google.common.collect.Lists.newArrayList();
            List<RewardShopTicketBo> rewardShopTicketBos = skuMapper.findRewardShopTicketInfos(a.getGoodsId());
            rewardShopTicketBos.forEach(b -> {
                //商品活动百分比
                b.setActivityCostRate(a.getActivityCostRate());
                //让利成本比例
                b.setProfitsRate(a.getProfitsRate());
                //会员等级比例
                BigDecimal purchasePresent = memberLevelMapper.selectById(user.getMemberLevelId()).getPurchasePresent();
                b.setPurchasePresent(purchasePresent);
                //购物券比例
                BigDecimal moneyToShopTicket = basicSettingMapper.selectList(null).get(0).getMoneyToShopTicket();
                b.setMoneyToShopTicket(moneyToShopTicket);
                BigDecimal rewardShopTicket = b.getRewardShopTicket();
                rewardShopTickes.add(rewardShopTicket.doubleValue());
            });
            //获取RewardShopTickes列表最大返券值
            Double maxRewardShopTicket = rewardShopTickes.stream().mapToDouble((x) -> x).summaryStatistics().getMax();
            a.setMaxRewardShopTicket(BigDecimal.valueOf(maxRewardShopTicket));
        });

        return searchGoodsBaseListVos;
    }

    /**
     * 下单后减去购物车对应的sku
     *
     * @param shopTicketSoWithCarGoodVos
     * @param userId
     */
    private void delShopCatAfterOrder(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("sku_id", shopTicketSoWithCarGoodVos.stream().map(x -> x.getId()).collect(Collectors.toList()));
        queryWrapper.eq("user_id", userId);
        mapper.delete(queryWrapper);
    }

    /**
     * 下单后减去购物车对应的sku
     *
     * @param shopTicketSoWithCarGoodDtos
     * @param userId
     */
    private void delShopCatAfterOrder2(List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("sku_id", shopTicketSoWithCarGoodDtos.stream().map(x -> x.getId()).collect(Collectors.toList()));
        queryWrapper.eq("user_id", userId);
        mapper.delete(queryWrapper);
    }

    /**
     * 获取需要返佣的用户信息
     *
     * @param userId
     * @return
     */
    public PayUserRelationPo getPayUserMessage(Long userId) {
        PayUserRelationPo savePayUser = new PayUserRelationPo();
        List<PayUserMessage> payUserMessages = userMapper.getPayUserMessage(userId);
        int size = payUserMessages.size();
        //如果长度为1，就是表示没有子级和父级
        if (size == 1) {
            return null;
        }
        //查出总共有多少个子级(与顺序有关。只能这样遍历)
        List<Long> nextUserIds = Lists.newArrayList();
        int childrenSize = 0;
        for (int i = 0; i < size; i++) {
            if (payUserMessages.get(i).getUserId().equals(userId)) {
                break;
            }
            nextUserIds.add(payUserMessages.get(i).getUserId());
            childrenSize++;
        }
        savePayUser.setNextUserIds(nextUserIds);

        //除了子级就只有自己和上一级
        if (size - childrenSize == 2) {
            savePayUser.setLastOneUserId(payUserMessages.get(childrenSize + 1).getUserId());
            savePayUser.setFirstUserId(payUserMessages.get(childrenSize + 1).getUserId());
        }
        //如果存在两个以上的父级
        if (size - childrenSize > 2) {
            savePayUser.setLastOneUserId(payUserMessages.get(childrenSize + 1).getUserId());
            savePayUser.setLastTwoUserId(payUserMessages.get(childrenSize + 2).getUserId());

            //找出最高等级的会员且最接近的用户
            PayUserMessage firstLevel = (PayUserMessage) payUserMessages.get(childrenSize + 1).clone();
            //从第三个开始是父级，假设第3个为最大所以从第四个开始遍历
            for (int i = childrenSize + 2; i < payUserMessages.size(); i++) {
                if (payUserMessages.get(i).getLevel() > firstLevel.getLevel()) {
                    firstLevel = (PayUserMessage) payUserMessages.get(i).clone();
                }
            }

            //找出第二高等级且最接近的用户
            PayUserMessage secondLevel = new PayUserMessage();
            secondLevel.setLevel(0);
            //从第三个开始是父级
            for (int i = childrenSize + 1; i < payUserMessages.size(); i++) {
                //第二等级的
                if (payUserMessages.get(i).getLevel() > secondLevel.getLevel() && payUserMessages.get(i).getLevel() < firstLevel.getLevel()) {
                    secondLevel = (PayUserMessage) payUserMessages.get(i).clone();
                }
            }
            savePayUser.setFirstUserId(firstLevel.getUserId());
            if (!secondLevel.getLevel().equals(0)) {
                savePayUser.setSecondUserId(secondLevel.getUserId());
            }
        }
        return savePayUser;

    }

    /**
     * 保存订单一些优惠信息
     *
     * @param saveOrder
     * @param payOrderPo
     */
    private void setDiscountMessage(OmOrderPo saveOrder, PayOrderPo payOrderPo) {
        /**
         * 订单金额
         */
        BigDecimal orderMoney = saveOrder.getTotalMoney();
        //总支付单
        BigDecimal payMoney = payOrderPo.getTotalMoney();
        //订单占总金额的比例
        BigDecimal ration = BigDecimalUtil.safeDivideDown(orderMoney, payMoney);
        saveOrder.setRedEnvelops(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalRedEnvelops()));
        saveOrder.setRedEnvelopsMoney(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalRedEnvelopsMoney()));
        saveOrder.setShopTicket(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalShopTicket()));
        saveOrder.setShopTicketMoney(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalShopTicketMoney()));
    }

    /**
     * 检查下单的sku的库存是否足够
     *
     * @param
     */
    private List<ShopTicketSoWithCarGoodDto> checkStock(SubmitOrderDto submitOrderDto) {
        List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos = Lists.newArrayList();
        submitOrderDto.getGoodsTypeOrderDtos().forEach(x -> {
            shopTicketSoWithCarGoodDtos.addAll(x.getShopTicketSoWithCarGoodDtos());
        });
        //查出sku实体
        List<PmGoodsSkuPo> pmGoodsSkuPos = skuMapper.selectBatchIds(shopTicketSoWithCarGoodDtos.stream()
                .map(ShopTicketSoWithCarGoodDto::getId).collect(Collectors.toList()));
        if (shopTicketSoWithCarGoodDtos.size() != pmGoodsSkuPos.size()) {
            throw new ServiceException(ResultCode.FAIL, "操作失败！某些商品已被下架！");
        }
        //库存不足的skuid和数量
        List<OutOfStockVo> outOfStockVos = Lists.newArrayList();
        shopTicketSoWithCarGoodDtos.forEach(x -> {
            PmGoodsSkuPo pmGoodsSkuPo = pmGoodsSkuPos.stream().filter(y -> y.getId().equals(x.getId())).findFirst().orElse(null);
            //如果库存不足
            if (x.getNumber() > pmGoodsSkuPo.getStock()) {
                OutOfStockVo outOfStockVo = new OutOfStockVo();
                outOfStockVo.setSkuId(x.getId());
                outOfStockVo.setStock(pmGoodsSkuPo.getStock());
                outOfStockVos.add(outOfStockVo);
            }
        });
        if (!ListUtil.isListNullAndEmpty(outOfStockVos)) {
            throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY, "某些商品库存不足", outOfStockVos);
        }
        //查出sku的相关属性，然后算出预返购物券
        shopTicketSoWithCarGoodDtos.forEach(x -> {
            ShopTicketSoWithCarGoodDto tempById = mapper.getTempById(x.getId());
            tempById.setNumber(x.getNumber());
            BeanUtils.copyProperties(tempById, x);
            x.setRealPayMoney(x.getSellPrice());
        });
        return shopTicketSoWithCarGoodDtos;
    }
}
