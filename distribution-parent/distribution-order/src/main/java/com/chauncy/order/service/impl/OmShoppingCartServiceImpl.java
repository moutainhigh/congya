package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.car.SettleAccountsDto;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.app.car.*;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.goods.SpecifiedSkuVo;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.cart.StoreGoodsVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.experimental.Accessors;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.math.BigDecimal;
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

    //需要进行实行认证且计算税率的商品类型
    private final List<String> needRealGoodsType=Lists.newArrayList("BONDED","OVERSEA");

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
        Map<String,Object> map = new HashMap<>();
        map.put("sku_id",addCartDto.getSkuId());
        List<OmShoppingCartPo> shoppingCartPos = mapper.selectByMap(map);
        boolean exit = shoppingCartPos!=null && shoppingCartPos.size()!=0;
        //判断当前库存是否足够
        Integer originStock = skuMapper.selectById(addCartDto.getSkuId()).getStock();

        //查找购物车是否已经存在该商品
        if (exit){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("sku_id",addCartDto.getSkuId());
            OmShoppingCartPo shoppingCartPo = mapper.selectOne(queryWrapper);
            shoppingCartPo.setNum(shoppingCartPo.getNum()+addCartDto.getNum());
            if (originStock<shoppingCartPo.getNum()){
                throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY,"库存不足!");
            }
            mapper.updateById(shoppingCartPo);
        }
        //不存在
        else {
            if (originStock<addCartDto.getNum()){
                throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY,"库存不足!");
            }
            OmShoppingCartPo omShoppingCartPo = new OmShoppingCartPo();
            BeanUtils.copyProperties(addCartDto,omShoppingCartPo);
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
        cartVoPageInfo.getList().forEach(a->{
            List<StoreGoodsVo> storeGoodsVos = Lists.newArrayList();
            a.getStoreGoodsVoList().forEach(b->{
                Integer sum = skuMapper.selectById(b.getSkuId()).getStock();
                b.setSum(sum);
                //库存不足处理
                if (sum==0){
                    b.setIsSoldOut(true);
                    b.setNum(0);
                }else if(b.getNum()>=sum){
                    b.setNum(sum);
                }
                //下架处理,宝贝失效处理
                if (goodsMapper.selectById(skuMapper.selectById(b.getSkuId()).getGoodsId()).getPublishStatus()!=null) {
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
        Arrays.asList(ids).forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.NO_EXISTS,"数据不存在");
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
        BeanUtils.copyProperties(updateCartDto,cartPo);
        mapper.updateById(cartPo);
    }

    @Override
    public TotalCarVo searchByIds(List<SettleAccountsDto> settleAccountsDtos,UmUserPo currentUser) {
        List<Long> skuIds=settleAccountsDtos.stream().map(x->x.getSkuId()).collect(Collectors.toList());
        List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos = mapper.searchByIds(skuIds);

        //sku对应的数量
        shopTicketSoWithCarGoodVos.forEach(x->{
            //为查询后的id匹配上用户下单的数量
            x.setNumber(settleAccountsDtos.stream().filter(y->y.getSkuId()==x.getId()).findFirst().get().getNumber());
        });

        TotalCarVo totalCarVo=new TotalCarVo();


        //判断商品是否保税仓或者海外直邮，是则需要进行实名认证，且计算税率
        List<ShopTicketSoWithCarGoodVo> needRealSkuList = shopTicketSoWithCarGoodVos.stream().filter(x -> needRealGoodsType.contains(x)).collect(Collectors.toList());
        if (!ListUtil.isListNullAndEmpty(needRealSkuList)){
            // TODO: 2019/7/14  实名认证
            //商品的税率=数量*销售价*百分比/100
           BigDecimal taxMoney=needRealSkuList.stream().map(x-> BigDecimalUtil.safeMultiply(x.getNumber(),
                    BigDecimalUtil.safeMultiply(x.getSellPrice(),transfromDecimal(x.getCustomTaxRate())))).
                    reduce(BigDecimal.ZERO, BigDecimal::add);
           totalCarVo.setTaxMoney(taxMoney);

        }

        //获取系统基本设置
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());

        //设置会员等级比例和购物券比例
        setPurchasePresentAndMoneyToShopTicket(shopTicketSoWithCarGoodVos,currentUser,basicSettingPo);
        //拆单后的信息
        List<StoreOrderVo> storeOrderVos = getBySkuIds(shopTicketSoWithCarGoodVos);

        totalCarVo.setStoreOrderVos(storeOrderVos);

        //商品总额=数量*单价
        BigDecimal totalMoney=shopTicketSoWithCarGoodVos.stream().map(x-> BigDecimalUtil.safeMultiply(x.getNumber(),x.getSellPrice())).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        //总数量
        int totalNumber=settleAccountsDtos.stream().mapToInt(x->x.getNumber()).sum();
        //奖励购物券
        BigDecimal rewardShopTicket=shopTicketSoWithCarGoodVos.stream().map(x->BigDecimalUtil.safeMultiply(x.getNumber(),x.getRewardShopTicket()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCarVo.setTotalMoney(totalMoney).setTotalNumber(totalNumber).setRewardShopTicket(rewardShopTicket);
        //设置可用红包、购物券、可抵扣金额4
        setDiscount(totalCarVo,currentUser,basicSettingPo);










        return null;
    }

    /**
     * 根据skuids组装成订单，并根据商家和商品类型进行拆单
     * @param shopTicketSoWithCarGoodVo
     * @return
     */
    private List<StoreOrderVo> getBySkuIds( List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVo){

        //加快sql查询，用代码对店铺和商品类型进行分组拆单
        Map<String, Map<String, List<ShopTicketSoWithCarGoodVo>>> map
                = shopTicketSoWithCarGoodVo.stream().collect(
                Collectors.groupingBy(
                        ShopTicketSoWithCarGoodVo::getStoreName, Collectors.groupingBy(ShopTicketSoWithCarGoodVo::getGoodsType)
                )
        );

        //遍历map,将map组装成vo
        //商家分组集合
        List<StoreOrderVo> storeOrderVos= Lists.newArrayList();
        Iterator<Map.Entry<String, Map<String, List<ShopTicketSoWithCarGoodVo>>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, List<ShopTicketSoWithCarGoodVo>>> entry = it.next();
            StoreOrderVo storeOrderVo = new StoreOrderVo();
            storeOrderVo.setStoreName(entry.getKey());
            //商品类型分组集合
            List<GoodsTypeOrderVo> goodsTypeOrderVos= Lists.newArrayList();
            for (Map.Entry<String, List<ShopTicketSoWithCarGoodVo>> entry1 :entry.getValue().entrySet()) {
                GoodsTypeOrderVo goodsTypeOrderVo = new GoodsTypeOrderVo();
                goodsTypeOrderVo.setGoodsType(entry1.getKey());
                goodsTypeOrderVo.setShopTicketSoWithCarGoodVos(entry1.getValue());
                goodsTypeOrderVos.add(goodsTypeOrderVo);
            }
            storeOrderVo.setGoodsTypeOrderVos(goodsTypeOrderVos);
            storeOrderVos.add(storeOrderVo);
        }
        return storeOrderVos;

    }

    /**
     * 设置会员等级比例和购物券比例
     * @param shopTicketSoWithCarGoodVoList
     */
    private void setPurchasePresentAndMoneyToShopTicket(List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVoList
    ,UmUserPo currentUser,BasicSettingPo basicSettingPo
    ){
        PmMemberLevelPo memberLevelPo = levelMapper.selectById(currentUser.getMemberLevelId());
        shopTicketSoWithCarGoodVoList.forEach(x->x.setPurchasePresent(memberLevelPo.getPurchasePresent())
        .setMoneyToShopTicket(basicSettingPo.getMoneyToShopTicket())
        );

    }
    /**
     * 设置可用红包、购物券、可抵扣金额
     * @param totalCarVo
     */
    private void setDiscount(TotalCarVo totalCarVo,UmUserPo currentUser,BasicSettingPo basicSettingPo){
        //一个购物券=多少元
        BigDecimal shopTicketToMoney=BigDecimalUtil.safeDivide(1,basicSettingPo.getMoneyToShopTicket());
        //一个红包=多少元
        BigDecimal redEnvelopsToMoney=BigDecimalUtil.safeDivide(1,basicSettingPo.getMoneyToCurrentRedEnvelops());
        //当前用户所拥有的红包折算后的金额
        BigDecimal redEnvelopMoney = BigDecimalUtil.safeMultiply(redEnvelopsToMoney, currentUser.getCurrentRedEnvelops());
        //当前用户所拥有的购物券折算后的金额
        BigDecimal shopTicketMoney = BigDecimalUtil.safeMultiply(shopTicketToMoney, currentUser.getCurrentShopTicket());
        //-1表示小于，0是等于，1是大于。
        //如果总金额<=红包折算后的金额
        if (totalCarVo.getTotalMoney().compareTo(redEnvelopMoney)<=0){
            //需要用多少个红包
           totalCarVo.setRedEnvelops(BigDecimalUtil.safeMultiply(totalCarVo.getTotalMoney(),basicSettingPo.getMoneyToCurrentRedEnvelops()));
           totalCarVo.setShopTicket(BigDecimal.ZERO);
           totalCarVo.setDeductionMoney(totalCarVo.getTotalMoney());
        }
        else {
            //总金额>红包，红包可以全用
            totalCarVo.setRedEnvelops(currentUser.getCurrentRedEnvelops());
            //红包抵扣后还剩下多少钱
            BigDecimal removeRed=BigDecimalUtil.safeSubtract(totalCarVo.getTotalMoney(),redEnvelopMoney);
            //如果剩下的金额小于等于购物券折算后的金额
            if (removeRed.compareTo(shopTicketMoney)<=0){
                totalCarVo.setShopTicket(BigDecimalUtil.safeMultiply(removeRed,basicSettingPo.getMoneyToShopTicket()));
                totalCarVo.setDeductionMoney(totalCarVo.getTotalMoney());
            }
            else {
                //剩下金额大于可抵扣优惠券，优惠券可以全用
                totalCarVo.setShopTicket(currentUser.getCurrentShopTicket());
                //扣除优惠券和红包剩下的金额
                totalCarVo.setDeductionMoney(BigDecimalUtil.safeAdd(redEnvelopMoney,shopTicketMoney));

            }
        }
    }

    /**
     * 将百分比转换成小数
     * @param bigDecimal
     * @return
     */
    private BigDecimal transfromDecimal(BigDecimal bigDecimal){
        return BigDecimalUtil.safeDivide(bigDecimal,100);
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
        if (goodsPo==null){
            throw new ServiceException(ResultCode.NO_EXISTS,"数据库不存在该商品！");
        }

        /**获取商品下的所有规格信息*/
        //获取对应的分类ID
        Long categoryId = goodsPo.getGoodsCategoryId();
        //获取分类下所有的规格
        List<BaseBo> goodsAttributePos = goodsAttributeMapper.findStandardName(categoryId);
        //遍历规格名称
        goodsAttributePos.forEach(x -> {
            List<GoodsValueBo> goodsValues = goodsMapper.findGoodsValue(goodsId, x.getId());
            if (goodsValues!=null && goodsValues.size()!=0) {
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
        query.put("del_flag",false);
        List<PmGoodsSkuPo> goodsSkuPos = skuMapper.selectByMap(query);
        if (goodsSkuPos == null && goodsSkuPos.size() == 0) {
            return null;
        }
        Map<String,SpecifiedSkuVo> skuDetail = Maps.newHashMap();
        //循环获取sku信息
        goodsSkuPos.forEach(b->{
            SpecifiedSkuVo specifiedSkuVo = new SpecifiedSkuVo();
            specifiedSkuVo.setHoldQuantity(goodsPo.getPurchaseLimit());
            specifiedSkuVo.setStock(b.getStock());
            specifiedSkuVo.setSellAbleQuantity(b.getStock());
            specifiedSkuVo.setOverSold(false);
            specifiedSkuVo.setPrice(b.getSellPrice());
            specifiedSkuVo.setSkuId(b.getId());
            specifiedSkuVo.setPictrue(b.getPicture());
            if (b.getStock()==0){
                specifiedSkuVo.setOverSold(true);
            }
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("del_flag",false);
            queryWrapper.eq("goods_sku_id",b.getId());
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPoList = relAttributeValueSkuMapper.selectList(queryWrapper);
            //拼接规格ID和规格值ID
            StringBuffer relIds = new StringBuffer(";");
            relAttributeValueSkuPoList.forEach(c->{
                PmGoodsAttributeValuePo attributeValuePo = attributeValueMapper.selectById(c.getGoodsAttributeValueId());

                Long attributeValueId = attributeValuePo.getId();
                String attributeValue = attributeValuePo.getValue();
                Long attributeId = attributeValuePo.getProductAttributeId();
                relIds.append(attributeId).append(":").append(attributeValueId).append(";");
            });
            skuDetail.put(String.valueOf(relIds),specifiedSkuVo);
            specifiedGoodsVo.setSkuDetail(skuDetail);
        });
        return specifiedGoodsVo;
    }
}
