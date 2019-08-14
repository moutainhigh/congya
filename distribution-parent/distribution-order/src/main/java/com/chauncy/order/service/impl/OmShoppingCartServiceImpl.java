package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.data.bo.app.car.MoneyShipBo;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.manage.pay.PayUserMessage;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.pay.PayUserRelationPo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueSkuPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.car.SettleAccountsDto;
import com.chauncy.data.dto.app.car.SettleDto;
import com.chauncy.data.dto.app.car.SubmitOrderDto;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.pay.PayUserRelationMapper;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmAreaShippingMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.temp.order.service.IOmGoodsTempService;
import com.chauncy.data.vo.app.car.*;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.goods.SpecifiedSkuVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.cart.StoreGoodsVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PmGoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper relAttributeValueSkuMapper;

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
    private PmMoneyShippingMapper moneyShippingMapper;

    @Autowired
    private IPayOrderMapper payOrderMapper;

    @Autowired
    private IOmGoodsTempService goodsTempService;

    @Autowired
    private IOmOrderService omOrderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private PayUserRelationMapper payUserRelationMapper;


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
        //判断购物车是否存在该商品
        Map<String, Object> map = new HashMap<>();
        map.put("sku_id", addCartDto.getSkuId());
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
    public PageInfo<CartVo> SearchCart(SearchCartDto searchCartDto) {
        //获取当前用户
        UmUserPo userPo = securityUtil.getAppCurrUser();
        Integer pageNo = searchCartDto.getPageNo() == null ? defaultPageNo : searchCartDto.getPageNo();
        Integer pageSize = searchCartDto.getPageSize() == null ? defaultPageSize : searchCartDto.getPageSize();
        PageInfo<CartVo> cartVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchCart(userPo.getId()));
        //对购物车库存处理
        cartVoPageInfo.getList().forEach(a -> {
            List<StoreGoodsVo> storeGoodsVos = Lists.newArrayList();
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
                //下架处理,宝贝失效处理
                if (goodsMapper.selectById(skuMapper.selectById(b.getSkuId()).getGoodsId()).getPublishStatus() != null) {
                    boolean publish = goodsMapper.selectById(skuMapper.selectById(b.getSkuId()).getGoodsId()).getPublishStatus();
                    if (!publish) {
                        b.setIsObtained(true);
                    }
                }
                storeGoodsVos.add(b);
            });
            a.setStoreGoodsVoList(storeGoodsVos);
        });

        return cartVoPageInfo;
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
    public void updateCart(AddCartDto updateCartDto) {
        OmShoppingCartPo cartPo = new OmShoppingCartPo();
        BeanUtils.copyProperties(updateCartDto, cartPo);
        mapper.updateById(cartPo);
    }

    @Override
    public TotalCarVo searchByIds(SettleDto settleDto, UmUserPo currentUser) {
        List<SettleAccountsDto> settleAccountsDtos = settleDto.getSettleAccountsDtos();
        List<Long> skuIds = settleAccountsDtos.stream().map(x -> x.getSkuId()).collect(Collectors.toList());
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos = mapper.searchByIds(skuIds);

        //sku对应的数量
        shopTicketSoWithCarGoodVos.forEach(x -> {
            //为查询后的id匹配上用户下单的数量
            x.setNumber(settleAccountsDtos.stream().filter(y -> y.getSkuId().equals(x.getId())).findFirst().get().getNumber());
        });

        TotalCarVo totalCarVo = new TotalCarVo();

        //判断商品是否保税仓或者海外直邮，是则需要进行实名认证
        List<ShopTicketSoWithCarGoodVo> needRealSkuList = shopTicketSoWithCarGoodVos.stream().filter(x -> needRealGoodsType.contains(x.getGoodsType())).collect(Collectors.toList());
        if (!ListUtil.isListNullAndEmpty(needRealSkuList)) {
            totalCarVo.setNeedCertification(true);
        }
       //获取系统基本设置
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());

         //设置会员等级比例和购物券比例
        setPurchasePresentAndMoneyToShopTicket(shopTicketSoWithCarGoodVos, currentUser, basicSettingPo);
        //拆单后的信息包括红包 购物券 金额 优惠 运费等
        List<StoreOrderVo> storeOrderVos = getBySkuIds(shopTicketSoWithCarGoodVos, currentUser, settleDto.getAreaShipId());

        totalCarVo.setStoreOrderVos(storeOrderVos);

        //商品总额=所有订单金额的总和
        BigDecimal totalMoney = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(GoodsTypeOrderVo::getTotalMoney).reduce(BigDecimal.ZERO, BigDecimal::add)
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
        //总数量
        int totalNumber = settleAccountsDtos.stream().mapToInt(SettleAccountsDto::getNumber).sum();
        //总邮费=所有订单的总和
        BigDecimal totalShipMoney = storeOrderVos.stream().map(x ->
                x.getGoodsTypeOrderVos().stream().map(y -> y.getShipMoney()).reduce(BigDecimal.ZERO, BigDecimal::add)
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
        //合计优惠暂时=0
        totalCarVo.setTotalShipMoney(totalShipMoney)
                .setTotalTaxMoney(totalTaxMoney).setTotalRealPayMoney(totalCarVo.calculationRealPayMoney());


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
                //商品总额=数量*单价
                BigDecimal totalMoney = shopTicketSoWithCarGoodVos.stream().map(x -> BigDecimalUtil.safeMultiply(x.getNumber(), x.getSellPrice())).
                        reduce(BigDecimal.ZERO, BigDecimal::add);
                totalMoney=BigDecimalUtil.safeAdd(totalMoney,shipMoney,taxMoney);
               /* //奖励购物券
                BigDecimal rewardShopTicket = shopTicketSoWithCarGoodVos.stream().map(x -> BigDecimalUtil.safeMultiply(x.getNumber(), x.getRewardShopTicket()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);*/
                goodsTypeOrderVo.setTotalMoney(totalMoney);/*.setRewardShopTicket(rewardShopTicket)*/
                /*//设置可用红包、购物券、可抵扣金额 需要用到：totalMoney shipMoney1 taxMoney1
                setDiscount(goodsTypeOrderVo, currentUser, basicSettingPo);*/
                /*//第一版本总优惠=红包+抵用券
                goodsTypeOrderVo.setTotalDiscount(goodsTypeOrderVo.getDeductionMoney());*/
                //实付金额 需要用到： totalMoney, shipMoney1, taxMoney1,totalDiscount
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
     * 设置可用红包、购物券、可抵扣金额
     *
     * @param totalCarVo
     */
    private void setDiscount(TotalCarVo totalCarVo, UmUserPo currentUser, BasicSettingPo basicSettingPo) {
        BigDecimal totalMoney = BigDecimalUtil.safeAdd(totalCarVo.getTotalMoney(), totalCarVo.getTotalShipMoney()
                , totalCarVo.getTotalTaxMoney());
        //一个购物券=多少元
        BigDecimal shopTicketToMoney = BigDecimalUtil.safeDivide(1, basicSettingPo.getMoneyToShopTicket());
        //一个红包=多少元
        BigDecimal redEnvelopsToMoney = BigDecimalUtil.safeDivide(1, basicSettingPo.getMoneyToCurrentRedEnvelops());
        //当前用户所拥有的红包折算后的金额
        BigDecimal redEnvelopMoney = BigDecimalUtil.safeMultiply(redEnvelopsToMoney, currentUser.getCurrentRedEnvelops());
        //当前用户所拥有的购物券折算后的金额
        BigDecimal shopTicketMoney = BigDecimalUtil.safeMultiply(shopTicketToMoney, currentUser.getCurrentShopTicket());
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
                //剩下金额大于可抵扣优惠券，优惠券可以全用
                totalCarVo.setTotalShopTicketMoney(shopTicketMoney);
                //扣除优惠券和红包剩下的金额
                totalCarVo.setTotalDeductionMoney(BigDecimalUtil.safeAdd(redEnvelopMoney, shopTicketMoney));
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
            QueryWrapper<UmAreaShippingPo> myAreaWrapper = new QueryWrapper();
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
        List<ShopTicketSoWithCarGoodVo> needShipMoneys=shopTicketSoWithCarGoodVos.stream().filter(x->!x.getIsFreePostage())
                .collect(Collectors.toList());
        //按照运费模板分组
        Map<Long, List<ShopTicketSoWithCarGoodVo>> map = needShipMoneys.stream().
                collect(Collectors.groupingBy(ShopTicketSoWithCarGoodVo::getShippingTemplateId));
        //订单总运费
        BigDecimal shipMoney=BigDecimal.ZERO;
        for (Map.Entry<Long, List<ShopTicketSoWithCarGoodVo>> entry : map.entrySet())
        {
            Long shipTemplateId=entry.getKey();
            List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList=entry.getValue();
            //运费模板对应的运费信息
            List<MoneyShipBo> moneyShipBos = moneyShippingMapper.loadByTemplateId(shipTemplateId);

            //匹配是否在指定地区内
            MoneyShipBo moneyShipBo = moneyShipBos.stream().filter(x -> myCityId.equals(x.getDestinationId())).
                    findFirst().orElse(null);
            //采用默认运费
            if (moneyShipBo == null) {
                shipMoney=BigDecimalUtil.safeAdd( getShipByMoney(shopTicketSoWithCarGoodVoList, moneyShipBos.get(0).getDefaultFullMoney()
                        , moneyShipBos.get(0).getDefaultPostMoney(), moneyShipBos.get(0).getDefaultFreight()),shipMoney);
            }
            //采用指定运费
            else {
                shipMoney=BigDecimalUtil.safeAdd(shipMoney, getShipByMoney(shopTicketSoWithCarGoodVoList, moneyShipBo.getDestinationFullMoney()
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
        BigDecimal price = shopTicketSoWithCarGoodVos.stream().map(x->BigDecimalUtil.safeMultiply(x.getNumber(),x.getSellPrice()))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
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
    public SpecifiedGoodsVo selectSpecifiedGoods(Long goodsId) {

        SpecifiedGoodsVo specifiedGoodsVo = new SpecifiedGoodsVo();
        List<GoodsStandardVo> goodsStandardVoList = Lists.newArrayList();

        PmGoodsPo goodsPo = goodsMapper.selectById(goodsId);
        //判断该商品是否存在
        if (goodsPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该商品！");
        }

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
        goodsSkuPos.forEach(b -> {
            SpecifiedSkuVo specifiedSkuVo = new SpecifiedSkuVo();
            specifiedSkuVo.setHoldQuantity(goodsPo.getPurchaseLimit());
            specifiedSkuVo.setStock(b.getStock());
            specifiedSkuVo.setSellAbleQuantity(b.getStock());
            specifiedSkuVo.setOverSold(false);
            specifiedSkuVo.setPrice(b.getSellPrice());
            specifiedSkuVo.setSkuId(b.getId());
            specifiedSkuVo.setPictrue(b.getPicture());
            if (b.getStock() == 0) {
                specifiedSkuVo.setOverSold(true);
            }
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("del_flag", false);
            queryWrapper.eq("goods_sku_id", b.getId());
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPoList = relAttributeValueSkuMapper.selectList(queryWrapper);
            //拼接规格ID和规格值ID
            StringBuffer relIds = new StringBuffer(";");
            relAttributeValueSkuPoList.forEach(c -> {
                PmGoodsAttributeValuePo attributeValuePo = attributeValueMapper.selectById(c.getGoodsAttributeValueId());

                Long attributeValueId = attributeValuePo.getId();
                String attributeValue = attributeValuePo.getValue();
                Long attributeId = attributeValuePo.getProductAttributeId();
                relIds.append(attributeId).append(":").append(attributeValueId).append(";");
            });
            skuDetail.put(String.valueOf(relIds), specifiedSkuVo);
            specifiedGoodsVo.setSkuDetail(skuDetail);
        });
        return specifiedGoodsVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitOrder(SubmitOrderDto submitOrderDto, UmUserPo currentUser) {
        //检查库存,设置一些需要计算购物券的值，并把所有商品抽出来
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList = checkStock(submitOrderDto);
        //判断商品是否保税仓或者海外直邮，是则需要进行实名认证
        List<ShopTicketSoWithCarGoodVo> needRealSkuList = shopTicketSoWithCarGoodVoList.stream().filter(x -> needRealGoodsType.contains(x.getGoodsType())).collect(Collectors.toList());
        if (!ListUtil.isListNullAndEmpty(needRealSkuList)) {
            if (submitOrderDto.getRealUserId()==null){
                throw new ServiceException(ResultCode.PARAM_ERROR,"请先进行实名认证！");
            }
        }
        //获取系统基本参数
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        //设置会员等级比例和购物券比例
        setPurchasePresentAndMoneyToShopTicket(shopTicketSoWithCarGoodVoList, currentUser, basicSettingPo);

        //红包金额换算成红包个数
        BigDecimal totalRedEnvelops = BigDecimalUtil.safeMultiply(submitOrderDto.getTotalRedEnvelopsMoney(), basicSettingPo.getMoneyToCurrentRedEnvelops());
        //优惠券金额换算成优惠券个数
        BigDecimal totalShopTicket = BigDecimalUtil.safeMultiply(submitOrderDto.getTotalShopTicketMoney(), basicSettingPo.getMoneyToShopTicket());


        //生成商品快照
        List<com.chauncy.data.domain.po.order.OmGoodsTempPo> saveGoodsTemps = Lists.newArrayList();
        //商品订单
        List<OmOrderPo> saveOrders = Lists.newArrayList();
        //总支付单
        PayOrderPo savePayOrderPo = new PayOrderPo();
        //复制商品总额、使用购物券、使用红包、运费、税费
        //实际付款、总优惠
        BeanUtils.copyProperties(submitOrderDto, savePayOrderPo);
        //如果不使用葱鸭钱包
        if (!submitOrderDto.getIsUseWallet()) {
            submitOrderDto.setTotalRedEnvelopsMoney(BigDecimal.ZERO);
            submitOrderDto.setTotalShopTicketMoney(BigDecimal.ZERO);
            submitOrderDto.setTotalRealPayMoney(BigDecimalUtil.safeAdd(submitOrderDto.getTotalMoney()
                    , submitOrderDto.getTotalShipMoney(), submitOrderDto.getTotalTaxMoney()
            ));
        }
        UmAreaShippingPo umAreaShippingPo = umAreaShippingMapper.selectById(submitOrderDto.getUmAreaShipId());
        savePayOrderPo.setShipName(umAreaShippingPo.getShipName()).setPhone(umAreaShippingPo.getMobile())
                .setShipAddress(umAreaShippingPo.getDetailedAddress()).setTotalRedEnvelops(totalRedEnvelops)
                .setTotalShopTicket(totalShopTicket).setUmUserId(currentUser.getId()).setAreaName(umAreaShippingPo.getAreaName())
        .setTotalDiscount(BigDecimal.ZERO);
        //生成支付单
        payOrderMapper.insert(savePayOrderPo);

        //用户所属店铺id
        Long userStoreId = userMapper.getUserStoreId(currentUser.getId());


        //循环遍历 生成订单
        submitOrderDto.getStoreOrderVos().forEach(x -> {
            //店铺id
            QueryWrapper<SmStorePo> storeWrapper = new QueryWrapper();
            storeWrapper.lambda().eq(SmStorePo::getName, x.getStoreName());
            SmStorePo queryStore = storeMapper.selectOne(storeWrapper);
            Long storeId = queryStore.getId();
            //循环遍历店铺下的订单
            x.getGoodsTypeOrderVos().forEach(y -> {
                //生成订单
                OmOrderPo saveOrder = new OmOrderPo();
                if (submitOrderDto.getRealUserId()!=null){
                    saveOrder.setRealUserId(submitOrderDto.getRealUserId());
                }
                BeanUtils.copyProperties(y, saveOrder);
                saveOrder.setUmUserId(currentUser.getId()).setAreaShippingId(submitOrderDto.getUmAreaShipId())
                        .setStoreId(storeId).setPayOrderId(savePayOrderPo.getId()).setCreateBy(currentUser.getPhone())
                        .setId(SnowFlakeUtil.getFlowIdInstance().nextId());
                //设置一些优惠信息：红包、购物券
                setDiscountMessage(saveOrder, savePayOrderPo);
                //设置用户所属店铺
                saveOrder.setUserStoreId(userStoreId).setIncomeRate(queryStore.getIncomeRate());
                saveOrders.add(saveOrder);

                //生成商品快照
                y.getShopTicketSoWithCarGoodVos().forEach(g -> {
                    PmGoodsSkuPo skuPo = skuMapper.selectById(g.getId());
                    com.chauncy.data.domain.po.order.OmGoodsTempPo saveGoodsTemp = new com.chauncy.data.domain.po.order.OmGoodsTempPo();
                    BeanUtils.copyProperties(g, saveGoodsTemp);
                    saveGoodsTemp.setCreateBy(currentUser.getPhone()).setOrderId(saveOrder.getId()).setSkuId(g.getId());
                    saveGoodsTemp.setId(null).setSupplierPrice(skuPo.getSupplierPrice()).setProfitRate(skuPo.getProfitRate())
                            .setGoodsId(skuPo.getGoodsId()).setArticleNumber(skuPo.getArticleNumber()).
                            //预计奖励购物券
                            setRewardShopTicket(g.getRewardShopTicket())
                            //预计奖励经验值
                    .setRewardExperience(BigDecimalUtil.safeMultiply(basicSettingPo.getMoneyToExperience(),g.getSellPrice()))
                            //预计奖励积分
                    .setRewardIntegrate(BigDecimalUtil.safeMultiply(basicSettingPo.getMoneyToIntegrate(),g.getSellPrice()))
                    ;
                    saveGoodsTemps.add(saveGoodsTemp);

                });
            });
        });

        //保存订单
        omOrderService.saveBatch(saveOrders);
        //保存商品快照
        goodsTempService.saveBatch(saveGoodsTemps);
        //减去库存
        skuMapper.updateStock(shopTicketSoWithCarGoodVoList);
        //减去购物券和红包
        mapper.updateDiscount(totalRedEnvelops,
                totalShopTicket
                , currentUser.getId()
        );
        //减去购物车
        delShopCatAfterOrder(shopTicketSoWithCarGoodVoList,currentUser.getId());
        //保存返佣用户信息
        PayUserRelationPo savePayUser = getPayUserMessage(currentUser.getId());
        if (savePayUser!=null){
            savePayUser.setCreateBy(currentUser.getPhone()).setPayId(savePayOrderPo.getId());
            payUserRelationMapper.insert(savePayUser);
        }

        // 添加延时队列
        this.rabbitTemplate.convertAndSend(RabbitConstants.ORDER_UNPAID_DELAY_EXCHANGE, RabbitConstants.DELAY_ROUTING_KEY, savePayOrderPo.getId(), message -> {
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间

            message.getMessageProperties().setExpiration(basicSettingPo.getAutoCloseOrderDay()*24*60*60*1000 + "");
            return message;
        });
        LoggerUtil.info("【下单等待取消订单发送时间】:" + LocalDateTime.now());

        return savePayOrderPo.getId();
    }

    /**
     * 下单后减去购物车对应的sku
     * @param shopTicketSoWithCarGoodVos
     * @param userId
     */
    private void delShopCatAfterOrder(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos,Long userId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("sku_id",shopTicketSoWithCarGoodVos.stream().map(x->x.getId()).collect(Collectors.toList()));
        queryWrapper.eq("user_id",userId);
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
        //如果查出的第一行不是该用户信息，就表示存在子用户
        if (!payUserMessages.get(0).getUserId().equals(userId)) {
            savePayUser.setNextUserId(payUserMessages.get(0).getUserId());
        }
        else {
            //如果不存在子用户，在集合第一位插入空的实体，保证无论有没有子级从第三个开始才是父级
            payUserMessages.add(0, new PayUserMessage());
        }
        //如果长度小于3，则表示不存在子级和父级，就没有返佣返积分的流程
        if (size == 3) {
            savePayUser.setLastOneUserId(payUserMessages.get(2).getUserId());
            savePayUser.setFirstUserId(payUserMessages.get(2).getUserId());
        }
        //如果存在两个以上的父级
        if (size>3) {
            savePayUser.setLastOneUserId(payUserMessages.get(2).getUserId());
            savePayUser.setLastTwoUserId(payUserMessages.get(3).getUserId());

            //找出最高等级的会员且最接近的用户
            PayUserMessage firstLevel= (PayUserMessage) payUserMessages.get(2).clone();
            //从第三个开始是父级，假设第3个为最大所以从第四个开始遍历
            for (int i=3;i<payUserMessages.size();i++){
                if (payUserMessages.get(i).getLevel()>firstLevel.getLevel()){
                    firstLevel= (PayUserMessage) payUserMessages.get(i).clone();
                }
            }

            //找出第二高等级且最接近的用户
            PayUserMessage secondLevel= new PayUserMessage();
            secondLevel.setLevel(0);
            //从第三个开始是父级
            for (int i=2;i<payUserMessages.size();i++){
                //第二等级的
                if (payUserMessages.get(i).getLevel()>secondLevel.getLevel()&&payUserMessages.get(i).getLevel()<firstLevel.getLevel()){
                    secondLevel= (PayUserMessage) payUserMessages.get(i).clone();
                }
            }
            savePayUser.setFirstUserId(firstLevel.getUserId());
            if (!secondLevel.getLevel().equals(0)){
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
        BigDecimal orderMoney = BigDecimalUtil.safeAdd(saveOrder.getTotalMoney(), saveOrder.getShipMoney(), saveOrder.getTaxMoney());
        //总支付单
        BigDecimal payMoney = BigDecimalUtil.safeAdd(payOrderPo.getTotalMoney(), payOrderPo.getTotalShipMoney(), payOrderPo.getTotalTaxMoney());
        //订单占总金额的比例
        BigDecimal ration = BigDecimalUtil.safeDivide(orderMoney, payMoney);
        saveOrder.setRedEnvelops(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalRedEnvelops()));
        saveOrder.setRedEnvelopsMoney(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalRedEnvelopsMoney()));
        saveOrder.setShopTicket(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalShopTicket()));
        saveOrder.setShopTicketMoney(BigDecimalUtil.safeMultiply(ration, payOrderPo.getTotalShopTicketMoney()));
    }

    /**
     * 检查下单的sku的库存是否足够
     *
     * @param submitOrderDto
     */
    private List<ShopTicketSoWithCarGoodVo> checkStock(SubmitOrderDto submitOrderDto) {
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList = Lists.newArrayList();
        submitOrderDto.getStoreOrderVos().forEach(x -> {
            x.getGoodsTypeOrderVos().forEach(y -> {
                shopTicketSoWithCarGoodVoList.addAll(y.getShopTicketSoWithCarGoodVos());
            });
        });
        //查出sku实体
        List<PmGoodsSkuPo> pmGoodsSkuPos = skuMapper.selectBatchIds(shopTicketSoWithCarGoodVoList.stream()
                .map(ShopTicketSoWithCarGoodVo::getId).collect(Collectors.toList()));
        if (shopTicketSoWithCarGoodVoList.size() != pmGoodsSkuPos.size()) {
            throw new ServiceException(ResultCode.FAIL, "操作失败！某些商品已被下架！");
        }
        //库存不足的skuid和数量
        List<OutOfStockVo> outOfStockVos = Lists.newArrayList();
        shopTicketSoWithCarGoodVoList.forEach(x -> {
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
        shopTicketSoWithCarGoodVoList.forEach(x->{
            ShopTicketSoWithCarGoodVo tempById = mapper.getTempById(x.getId());
            BeanUtils.copyProperties(tempById,x,"number");
        });
        return shopTicketSoWithCarGoodVoList;
    }
}
