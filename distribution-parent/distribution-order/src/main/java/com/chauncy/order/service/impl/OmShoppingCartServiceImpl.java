package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetEvaluatesDto;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.app.goods.AttributeVo;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.goods.SpecifiedSkuVo;
import com.chauncy.data.vo.app.goods.StoreVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.cart.StoreGoodsVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
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
    private PmShippingTemplateMapper shippingTemplateMapper;

    @Autowired
    private PmGoodsRelAttributeGoodMapper relAttributeGoodMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private PmGoodsRelAttributeValueGoodMapper relAttributeValueGoodMapper;

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
        /**商品基本信息**/
        specifiedGoodsVo.setGoodsName (goodsPo.getName ());
        specifiedGoodsVo.setCarousel (goodsPo.getCarouselImage ());
        specifiedGoodsVo.setOriginPlace (goodsPo.getLocation ());
        specifiedGoodsVo.setIsPostage (goodsPo.getIsFreePostage ());
        if (!goodsPo.getIsFreePostage ()){
            //获取运费模板设置的默认运费
            PmShippingTemplatePo shippingTemplatePo = shippingTemplateMapper.selectById (goodsPo.getShippingTemplateId ());
            specifiedGoodsVo.setDefaultFreight (shippingTemplatePo.getDefaultFreight ());
        }
        Map<String,Object> query2 = Maps.newHashMap ();
        query2.put("del_flag",false);
        query2.put("goods_good_id",goodsId);
        List<Long> attributeIds = relAttributeGoodMapper.selectByMap (query2).stream ().map (d->d.getGoodsAttributeId ()).collect(Collectors.toList());
        List<PmGoodsAttributePo> pmGoodsAttributePoList = goodsAttributeMapper.selectBatchIds (attributeIds);
        List<AttributeVo> serviceList = Lists.newArrayList ();
        List<AttributeVo> paramList = Lists.newArrayList ();
        List<AttributeVo> activityVoList = Lists.newArrayList ();
        pmGoodsAttributePoList.forEach (e->{
            GoodsAttributeTypeEnum attributeTypeEnum = GoodsAttributeTypeEnum.getGoodsAttributeById (e.getType ());
            AttributeVo attributeVo = new AttributeVo ();
            attributeVo.setValue (e.getContent ());
            attributeVo.setName (e.getName ());
            switch (attributeTypeEnum) {
                case PLATFORM_SERVICE:
                case MERCHANT_SERVICE:
                    serviceList.add (attributeVo);
                    break;
                case PLATFORM_ACTIVITY:
                    activityVoList.add (attributeVo);
                    break;
                case GOODS_PARAM:
                    QueryWrapper queryWrapper = new QueryWrapper ();
                    queryWrapper.eq("goods_id",goodsId);
                    queryWrapper.eq("del_flag",false);
                    String paramValue = attributeValueMapper.selectById (relAttributeValueGoodMapper.selectOne (queryWrapper).getGoodsAttributeValueId ()).getValue ();
                    attributeVo.setValue (paramValue);
                    paramList.add (attributeVo);
                    break;
                case LABEL:
                    break;
                case PURCHASE:
                    break;
                case STANDARD:
                    break;
                case BRAND:
                    break;
                case SENSITIVE:
                    break;
            }
        });
        specifiedGoodsVo.setServiceList (serviceList);
        specifiedGoodsVo.setParamList (paramList);
        specifiedGoodsVo.setActivityVoList (activityVoList);
        /**店铺基本信息*/
        StoreVo storeVo = new StoreVo ();
        SmStorePo storePo = storeMapper.selectById (goodsId);
        if (storePo!=null) {
            storeVo.setStoreIcon (storePo.getStoreImage ());
            storeVo.setStoreId (storePo.getId ());
            storeVo.setStoreName (storePo.getName ());
            //获取店铺的平均星级
            storeVo.setBabyDescription (storePo.getBabyDescribeScore ());
            storeVo.setLogisticsServices (storePo.getLogisticsServiceScore ());
            storeVo.setSellerService (storePo.getServiceAttitudeScore ());
            specifiedGoodsVo.setStoreVo (storeVo);
        }

        //显示商品价格，拼接
        BigDecimal lowestPrice = skuMapper.getLowestPrice(goodsId);
        BigDecimal highestPrice = skuMapper.getHighestPrice(goodsId);
        StringBuffer price = new StringBuffer ();
        price.append (lowestPrice).append ("-").append (highestPrice);
        specifiedGoodsVo.setDisplayPrice (price.toString ());

        return specifiedGoodsVo;
    }

}
