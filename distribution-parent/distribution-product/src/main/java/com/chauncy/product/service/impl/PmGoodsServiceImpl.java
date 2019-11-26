package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.app.component.ShareTypeEnum;
import com.chauncy.common.enums.app.sort.SortFileEnum;
import com.chauncy.common.enums.app.sort.SortWayEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.goods.*;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.bo.app.order.reward.RewardShopTicketBo;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.rel.MmInformationForwardPo;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.component.ShareDto;
import com.chauncy.data.dto.app.product.FindStoreGoodsParamDto;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.good.select.AssociationGoodsDto;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockPo;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockTemplatePo;
import com.chauncy.data.dto.manage.good.select.SearchAttributesDto;
import com.chauncy.data.dto.manage.good.update.RejectGoodsDto;
import com.chauncy.data.dto.supplier.good.add.*;
import com.chauncy.data.dto.supplier.good.select.*;
import com.chauncy.data.dto.supplier.good.update.*;
import com.chauncy.data.dto.supplier.store.update.SelectStockTemplateGoodsDto;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.rel.MmInformationForwardMapper;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.mapper.product.stock.PmGoodsVirtualStockMapper;
import com.chauncy.data.mapper.product.stock.PmGoodsVirtualStockTemplateMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.store.GoodsSecondCategoryListVo;
import com.chauncy.data.vo.app.component.ScreenGoodsParamVo;
import com.chauncy.data.vo.app.component.ScreenParamVo;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.goods.ShareDetailVo;
import com.chauncy.data.vo.supplier.*;
import com.chauncy.data.vo.supplier.good.AssociationGoodsVo;
import com.chauncy.data.vo.supplier.good.ExcelGoodVo;
import com.chauncy.data.vo.supplier.good.RecommendGoodsVo;
import com.chauncy.product.service.IPmAssociationGoodsService;
import com.chauncy.data.vo.supplier.good.stock.GoodsStockTemplateVo;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateGoodsInfoVo;
import com.chauncy.product.service.IPmGoodsRelAttributeGoodService;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.product.service.IPmGoodsSkuService;
import com.chauncy.product.stock.IPmGoodsVirtualStockService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmGoodsServiceImpl extends AbstractService<PmGoodsMapper, PmGoodsPo> implements IPmGoodsService {

    @Autowired
    private PmGoodsMapper mapper;

    @Autowired
    private PmGoodsRelAttributeGoodMapper attributeGoodMapper;

    @Autowired
    private IPmGoodsRelAttributeGoodService saveBatch;

    @Autowired
    private IPmAssociationGoodsService saveBatch2;

    @Autowired
    private IPmGoodsSkuService skuSaveBatch;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MmInformationForwardMapper mmInformationForwardMapper;

    @Autowired
    private PmGoodsAttributeValueMapper goodsAttributeValueMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper goodsRelAttributeValueSkuMapper;

    @Autowired
    private PmGoodsRelAttributeValueGoodMapper goodsRelAttributeValueGoodMapper;

    @Autowired
    private IPmGoodsVirtualStockService pmGoodsVirtualStockService;

    @Autowired
    private UmUserMapper umUserMapper;

    @Autowired
    private MmInformationMapper mmInformationMapper;

    /*@Autowired
    private PmGoodsRelGoodsMemberLevelMapper goodsRelGoodsMemberLevelMapper;*/

    @Autowired
    private PmAssociationGoodsMapper associationGoodsMapper;

    @Autowired
    private PmGoodsRelAttributeGoodMapper goodsRelAttributeGoodMapper;

    @Autowired
    private PmGoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    private PmShippingTemplateMapper shippingTemplateMapper;

    @Autowired
    private PmGoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private SmStoreMapper smStoreMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private PmGoodsRelAttributeCategoryMapper goodsRelAttributeCategoryMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private PmGoodsRelAttributeValueGoodMapper relAttributeValueGoodMapper;

    @Autowired
    private PmGoodsLikedMapper goodsLikedMapper;

    @Autowired
    private PmGoodsForwardMapper goodsForwardMapper;

    @Autowired
    private AreaRegionMapper areaRegionMapper;

    @Autowired
    private PmGoodsVirtualStockTemplateMapper pmGoodsVirtualStockTemplateMapper;

    @Autowired
    private PmGoodsVirtualStockMapper pmGoodsVirtualStockMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    private static int defaultPageSize = 10;

    private static int defaultPageNo = 1;

    private static String defaultSoft = "sort desc";

    @Value("${distribution.share.addr}")
    private String shareAddr;

    @Override
    public List<String> findGoodsType() {
        List<String> types = Arrays.stream(GoodsTypeEnum.values()).map(a -> a.getName()).collect(Collectors.toList());
        return types;
    }

    /**
     * 店铺详情-商品分类
     * @param storeId
     * @return
     */
    @Override
    public List<GoodsSecondCategoryListVo> findGoodsCategory(Long storeId) {

        SmStorePo smStorePo = smStoreMapper.selectById(storeId);
        if(null == smStorePo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "店铺不存在");
        }

        return mapper.findGoodsCategory(storeId);
    }

    /**
     * 获取该商品所在类目下的不同类型的商品属性信息
     *
     * @param selectAttributeDto
     * @return
     */
    @Override
    public List<BaseVo> findAttByTypeAndCat(SelectAttributeDto selectAttributeDto) {

        List<BaseVo> attributes = goodsAttributeMapper.findAttByTypeAndCat(selectAttributeDto.getCategoryId(), selectAttributeDto.getType());

        return attributes;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/17 14:38
     * @Description 获取筛选商品的参数
     *
     * @Update yeJH
     *
     * @param  findStoreGoodsParamDto
     * @return com.chauncy.data.vo.app.component.ScreenParamVo
     **/
    @Override
    public ScreenParamVo findScreenGoodsParam(FindStoreGoodsParamDto findStoreGoodsParamDto) {

        if(findStoreGoodsParamDto.getGoodsType().equals(StoreGoodsListTypeEnum.CATEGORY_LIST.getId())
                && null == findStoreGoodsParamDto.getGoodsCategoryId()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "goodsCategoryId不能为空");
        }
        if(findStoreGoodsParamDto.getGoodsType().equals(StoreGoodsListTypeEnum.SEARCH_LIST.getId())
                && null == findStoreGoodsParamDto.getGoodsName()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "goodsName不能为空");
        }
        if(null == findStoreGoodsParamDto.getGoodsType()) {
            //默认全部商品列表
            findStoreGoodsParamDto.setGoodsType(StoreGoodsListTypeEnum.ALL_LIST.getId());
        }
        if(findStoreGoodsParamDto.getGoodsType().equals(StoreGoodsListTypeEnum.NEW_LIST.getId())) {
            //获取系统基本设置  新品的评判标准  上架几天内为新品
            BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
            findStoreGoodsParamDto.setNewGoodsDays(basicSettingPo.getNewProductDay());
        }

        ScreenParamVo screenParamVo = new ScreenParamVo();
        ScreenGoodsParamVo screenGoodsParamVo = mapper.findScreenGoodsParam(findStoreGoodsParamDto);
        screenParamVo.setScreenGoodsParamVo(screenGoodsParamVo);
        return screenParamVo;

    }

    /**
     * 根据不同运费模版类型获取运费信息
     *
     * @param shipType
     * @return
     */
    @Override
    public List<BaseVo> findShipByType(Integer shipType) {

        List<BaseVo> shipTemplates = Lists.newArrayList();
        Map<String, Object> map = new HashMap<>();
        map.put("type", shipType);
        List<Long> shipTemplateIds = shippingTemplateMapper.selectByMap(map).stream().map(a -> a.getId()).collect(Collectors.toList());
        shipTemplateIds.forEach(x -> {
            BaseVo shipTemplate = new BaseVo();
            shipTemplate.setId(x);
            shipTemplate.setName(shippingTemplateMapper.selectById(x).getName());
            shipTemplates.add(shipTemplate);
        });
        return shipTemplates;
    }

    /**
     * 添加商品基础信息
     *
     * @param addGoodBaseDto
     * @return
     */
    @Override
    public Long addBase(AddGoodBaseDto addGoodBaseDto) {

        LocalDateTime date = LocalDateTime.now();
        PmGoodsPo goodsPo = new PmGoodsPo();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        String userId = securityUtil.getCurrUser().getId();
        //判断用户是属于哪个系统,商家端或者是平台后台
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        //商家端
        if (storeId != null) {
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
            goodsPo.setStoreId(storeId);
        }
//        goodsPo.setCreateTime(date);
        //复制Dto对象到po
        BeanUtils.copyProperties(addGoodBaseDto, goodsPo);
        goodsPo.setCreateBy(user);
        goodsPo.setId(null);
        goodsPo.setShippingTemplateId(addGoodBaseDto.getShippingId());
        //先保存商品不关联信息
        mapper.insert(goodsPo);

        //处理商品属性和商品关联信息
        addAttribute(addGoodBaseDto, user, goodsPo);

        //处理商品参数属性
        if (addGoodBaseDto.getGoodsParamDtoList() != null && addGoodBaseDto.getGoodsParamDtoList().size() != 0)
            addParam(addGoodBaseDto, user, goodsPo);

        Long goodsId = goodsPo.getId();
        return goodsId;
    }

    /**
     * 根据商品ID获取商品的基本信息
     * <p>
     * 关联表：参数与参数值 一对多
     * 商品和属性  多对多
     * 商品和参数值 一对多
     * 商品参数值和属性参数没有逻辑上的关联
     *
     * @param id
     * @return
     */
    @Override
    public BaseGoodsVo findBase(Long id) {

        PmGoodsPo goodsPo = mapper.selectById(id);
        if (goodsPo == null) throw new ServiceException(ResultCode.PARAM_ERROR, "数据库不存在该商品");
        BaseGoodsVo baseGoodsVo = new BaseGoodsVo();
        BaseVo baseVo = new BaseVo();
        //复制非关联属性
        BeanUtils.copyProperties(goodsPo, baseGoodsVo);
        baseVo.setId(goodsPo.getShippingTemplateId());

        /**
         * 处理分类，根据分类ID获取分类名称
         *
         */
        String[] level = new String[3];
        PmGoodsCategoryPo goodsCategoryPo3 = goodsCategoryMapper.selectById(baseGoodsVo.getGoodsCategoryId());
        String level3 = goodsCategoryPo3.getName();
        PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo3.getParentId());
        String level2 = goodsCategoryPo2.getName();
        String level1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
        level[0] = level1;
        level[1] = level2;
        level[2] = level3;
        baseGoodsVo.setCategoryName(level);
        /**
         * 参数属性处理
         */
        List<PmGoodsParamVo> paramVoList = Lists.newArrayList();
        //根据商品ID查找关联参数值
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", id);
        List<PmGoodsRelAttributeValueGoodPo> relAttributeValueGoodPos = goodsRelAttributeValueGoodMapper.selectByMap(map);
        List<Long> attributeValueIds = relAttributeValueGoodPos.stream().map(b -> b.getGoodsAttributeValueId()).collect(Collectors.toList());
        //遍历商品对应的参数值通过属性值表获取属性对应的名称
        attributeValueIds.forEach(x -> {
            //获取参数值
            String paramValue = goodsAttributeValueMapper.selectById(x).getValue();
            //获取参数名ID
            Long attributeId = goodsAttributeValueMapper.selectById(x).getProductAttributeId();
            //获取参数名称
            String paramName = goodsAttributeMapper.selectById(attributeId).getName();

            //赋值
            PmGoodsParamVo goodsParamVo = new PmGoodsParamVo();
            goodsParamVo.setAttributeId(attributeId);
            goodsParamVo.setAttributeName(paramName);
            goodsParamVo.setValueId(x);
            goodsParamVo.setValueName(paramValue);
            paramVoList.add(goodsParamVo);

        });
        baseGoodsVo.setGoodsParams(paramVoList);

        /**
         * 处理其他属性
         */
        //根据商品ID查找关联属性
        PmGoodsRelAttributeGoodPo goodsRelAttributeGoodPo = new PmGoodsRelAttributeGoodPo();
        goodsRelAttributeGoodPo.setGoodsGoodId(id);
        QueryWrapper<PmGoodsRelAttributeGoodPo> queryWrapper = new QueryWrapper<>(goodsRelAttributeGoodPo);
        //获取与该商品关联的属性
        List<PmGoodsRelAttributeGoodPo> goodsRelAttributeGoodPos = goodsRelAttributeGoodMapper.selectList(queryWrapper);
        //获取属性IDs
        List<Long> attributeIds = goodsRelAttributeGoodPos.stream().map(a -> a.getGoodsAttributeId()).collect(Collectors.toList());
        List<BaseVo> labels = Lists.newArrayList();
        List<BaseVo> platformServices = Lists.newArrayList();
        List<BaseVo> merchantServices = Lists.newArrayList();
        attributeIds.forEach(x -> {
            //根据属性ID查找属性信息
            PmGoodsAttributePo goodsAttributePo = goodsAttributeMapper.selectById(x);
            //根据不同属性类型保存不同属性信息
            GoodsAttributeTypeEnum goodsAttributeById = GoodsAttributeTypeEnum.getGoodsAttributeById(goodsAttributePo.getType());
            switch (goodsAttributeById) {
                case BRAND:
                    BaseVo brandVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, brandVo);
                    baseGoodsVo.setBrandVo(brandVo);
                    break;
                case LABEL://多个
                    BaseVo labelVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, labelVo);
                    labels.add(labelVo);
                    baseGoodsVo.setLabelVo(labels);
                    break;
                case PLATFORM_SERVICE://多个
                    BaseVo platformServiceVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, platformServiceVo);
                    platformServices.add(platformServiceVo);
                    baseGoodsVo.setPlatformServiceVo(platformServices);
                    break;
                case MERCHANT_SERVICE://多个
                    BaseVo merchantServiceVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, merchantServiceVo);
                    merchantServices.add(merchantServiceVo);
                    baseGoodsVo.setMerchantServiceVo(merchantServices);
                    break;
            }
        });

        //根据不同类型运费模版保存不同运费信息
        PmShippingTemplatePo shippingTemplatePo = shippingTemplateMapper.selectById(goodsPo.getShippingTemplateId());

        GoodsShipTemplateEnum goodsShipTemplateById = GoodsShipTemplateEnum.getGoodsShipTemplateById(shippingTemplatePo.getType());
        switch (goodsShipTemplateById) {
            case MERCHANT_SHIP:
                BaseVo merchantShipVo = new BaseVo();
                BeanUtils.copyProperties(shippingTemplatePo, merchantShipVo);
                baseGoodsVo.setMerchantShipVo(merchantShipVo);
                break;
            case PLATFORM_SHIP:
                BaseVo platformShipVo = new BaseVo();
                BeanUtils.copyProperties(shippingTemplatePo, platformShipVo);
                baseGoodsVo.setPlatformShipVo(platformShipVo);
                break;

        }

        return baseGoodsVo;
    }

    /**
     * 修改商品基本信息
     * <p>
     * 关联表：参数与参数值 一对多
     * 商品和属性  多对多
     * 商品和参数值 一对多
     *
     * @param updateGoodBaseDto
     */
    @Override
    public void updateBase(AddGoodBaseDto updateGoodBaseDto) {

        String user = securityUtil.getCurrUser().getUsername();
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        PmGoodsPo goodsPo = new PmGoodsPo();
        //商家端操作
//        if (storeId != null) {
//            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
//        }
        //保存非关联信息
        BeanUtils.copyProperties(updateGoodBaseDto, goodsPo);
        goodsPo.setUpdateBy(user);
        goodsMapper.updateById(goodsPo);

        /**
         * 处理商品参数
         */
        //根据商品ID获取与商品参数值的信息，并删除参数值与商品关联表信息、删除参数值表相关的参数值
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", updateGoodBaseDto.getId());
        //获取与该商品关联的参数值ID,b并删除相关信息
        List<PmGoodsRelAttributeValueGoodPo> lists = goodsRelAttributeValueGoodMapper.selectByMap(map);
        List<Long> paramValueIds = lists.stream().map(a -> a.getGoodsAttributeValueId()).collect(Collectors.toList());
        //删除参数值与商品关联表信息
        goodsRelAttributeValueGoodMapper.deleteByMap(map);
        //删除商品对应的参数值
        paramValueIds.forEach(x -> {
            goodsAttributeValueMapper.deleteById(x);
        });
        //添加商品参数信息
        if (updateGoodBaseDto.getGoodsParamDtoList() != null && updateGoodBaseDto.getGoodsParamDtoList().size() != 0)
            addParam(updateGoodBaseDto, user, goodsPo);

        /**
         * 处理其他属性信息，商品与属性关联表
         */
        //删除关联表关联信息
        Map<String, Object> GoodIdMap = new HashMap<>();
        GoodIdMap.put("goods_good_id", updateGoodBaseDto.getId());
        goodsRelAttributeGoodMapper.deleteByMap(GoodIdMap);
        //插入新数据
        addAttribute(updateGoodBaseDto, user, goodsPo);
    }

    /**
     * 对商品添加商品属性(商品品牌、商品标签、服务说明)
     *
     * @param updateGoodBaseDto
     * @param user
     * @param goodsPo
     */
    private void addAttribute(AddGoodBaseDto updateGoodBaseDto, String user, PmGoodsPo goodsPo) {
        List<PmGoodsRelAttributeGoodPo> relAttributeGoodPoList = Lists.newArrayList();
        //保存品牌
        PmGoodsRelAttributeGoodPo attributeGoodPo1 = new PmGoodsRelAttributeGoodPo();
        attributeGoodPo1.setGoodsAttributeId(updateGoodBaseDto.getBrandId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
        relAttributeGoodPoList.add(attributeGoodPo1);
        //保存标签，多个
        for (Long labelId : updateGoodBaseDto.getLabelId()) {
            PmGoodsRelAttributeGoodPo attributeGoodPo2 = new PmGoodsRelAttributeGoodPo();
            attributeGoodPo2.setGoodsAttributeId(labelId).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
            relAttributeGoodPoList.add(attributeGoodPo2);
        }
        //保存服务说明，多个
        for (Long serviceId : updateGoodBaseDto.getServiceId()) {
            PmGoodsRelAttributeGoodPo attributeGoodPo3 = new PmGoodsRelAttributeGoodPo();
            attributeGoodPo3.setGoodsAttributeId(serviceId).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
            relAttributeGoodPoList.add(attributeGoodPo3);
        }

        //批量插入除商品参数之外的商品属性
        saveBatch.saveBatch(relAttributeGoodPoList);
    }

    /**
     * 为商品添加对应的参数信息
     *
     * @param updateGoodBaseDto
     * @param user
     * @param goodsPo
     */
    private void addParam(AddGoodBaseDto updateGoodBaseDto, String user, PmGoodsPo goodsPo) {
        updateGoodBaseDto.getGoodsParamDtoList().forEach(x -> {
            //商品参数属性值和商品参数属性绑定
            PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
            goodsAttributeValuePo.setCreateBy(user).setProductAttributeId(x.getAttributeId()).setValue(x.getAttributeName());
            //存入数据库
            goodsAttributeValueMapper.insert(goodsAttributeValuePo);

            //商品参数属性值和商品绑定
            PmGoodsRelAttributeValueGoodPo relAttributeValueGoodPo = new PmGoodsRelAttributeValueGoodPo();
            relAttributeValueGoodPo.setCreateBy(user).setGoodsAttributeValueId(goodsAttributeValuePo.getId())
                    .setGoodsId(goodsPo.getId());
            //存入数据库
            goodsRelAttributeValueGoodMapper.insert(relAttributeValueGoodPo);
        });
    }

    /**
     * 供应商添加商品时需要的商品属性规格值
     *
     * @param findStandardDto
     * @return
     */
    @Override
    public List<GoodsStandardVo> findStandard(FindStandardDto findStandardDto) {

        List<GoodsStandardVo> goodsStandardVos = new ArrayList<>();

        //获取属性信息
        List<BaseBo> goodsAttributePos = goodsAttributeMapper.findStandardName(findStandardDto.getCategoryId());
        goodsAttributePos.forEach(x -> {
            //获取属性信息
            GoodsStandardVo goodsStandardVo = new GoodsStandardVo();
            goodsStandardVo.setAttributeId(x.getId());
            goodsStandardVo.setAttributeName(x.getName());
            //根据分类下的属性ID获取默认的属性值
            List<BaseBo> defaultValues = goodsAttributeValueMapper.findDefaultValues(x.getId());
            List<Long> defaultValueIds = defaultValues.stream().map(a -> a.getId()).collect(Collectors.toList());
            //获取该商品下的属性下的所属的属性值
            List<GoodsValueBo> goodsValues = mapper.findGoodsValue(findStandardDto.getGoodsId(), x.getId());
            //获取该商品所有的属性值
            List<GoodsValueBo> goodsValueBoList = mapper.findGoodsValue(findStandardDto.getGoodsId(), null);
            List<Long> goodsValuesIds = goodsValueBoList.stream().map(a -> a.getId()).collect(Collectors.toList());

            List<StandardValueAndStatusVo> valueAndStatusVos = new ArrayList<>();
            /*//新增商品
            if (goodsValues == null) {
                defaultValues.forEach(b -> {
                    StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                    standardValueAndStatusVo.setAttributeValueId(b.getId());
                    standardValueAndStatusVo.setAttributeValue(b.getName());
                    standardValueAndStatusVo.setIsInclude(false);
                    valueAndStatusVos.add(standardValueAndStatusVo);
                });
            }
            //修改商品规格及其sku情况下
            else if (goodsValues != null) {*/
            //循环商品属性值,并设置isInclude的值为true
            goodsValues.forEach(d -> {
                StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                standardValueAndStatusVo.setAttributeValueId(d.getId());
                standardValueAndStatusVo.setAttributeValue(d.getName());

                standardValueAndStatusVo.setIsInclude(true);
                valueAndStatusVos.add(standardValueAndStatusVo);
            });

            //遍历商品未用到的规格值信息
            defaultValues.stream().filter(item -> !goodsValuesIds.contains(item.getId())).forEach(a -> {
                StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                standardValueAndStatusVo.setAttributeValueId(a.getId());
                standardValueAndStatusVo.setAttributeValue(a.getName());
                standardValueAndStatusVo.setIsInclude(false);
                valueAndStatusVos.add(standardValueAndStatusVo);
            });

            goodsStandardVo.setAttributeValueInfos(valueAndStatusVos);
            goodsStandardVos.add(goodsStandardVo);
//            }
        });
        return goodsStandardVos;
    }

    /**
     * 根据商品ID获取sku信息
     * <p>
     * 商品与sku为1对多
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<Map<String, Object>> findSkuAttribute(Long goodsId) {

//        List<FindSkuAttributeVo> findSkuAttributeVos = Lists.newArrayList();
        List<Map<String, Object>> mapList = Lists.newArrayList();
        //判断该商品是否存在
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "该商品不存在！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("del_flag",0);
        List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(map);
        if (goodsSkuPos == null && goodsSkuPos.size() == 0) {
            return null;
        }
        //循环获取sku信息
        goodsSkuPos.forEach(x -> {
            Map<String, Object> map1 = new HashMap<>();
            //获取除规格信息外的其他信息
            FindSkuAttributeVo findSkuAttributeVo = new FindSkuAttributeVo();
            BeanUtils.copyProperties(x, findSkuAttributeVo);
            findSkuAttributeVo.setSkuId(x.getId());
            map1 = JSONUtils.toBean(findSkuAttributeVo, Map.class);

            //获取每条sku对应的规格信息，规格值与sku多对多关系
            Map<String, Object> relMap = new HashMap<>();
            relMap.put("goods_sku_id", x.getId());
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(relMap);
            //根据skuId获取属性值ID列表
            List<Long> valueIds = relAttributeValueSkuPos.stream().map(a -> a.getGoodsAttributeValueId()).collect(Collectors.toList());
            //根据属性值id查找属性值表，获取属性值和属性ID
//            List<AttributeInfos> attributeInfos = Lists.newArrayList();

//            List<Map<Long, StandardValueAndStatusVo>> attributeValues = Lists.newArrayList();
            Map<String, Object> finalMap = map1;
            valueIds.forEach(b -> {
                PmGoodsAttributeValuePo valuePo = goodsAttributeValueMapper.selectById(b);
                if (valuePo == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在对应的属性值id[%s]",b));
                }
                StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                standardValueAndStatusVo.setAttributeValueId(b);
                standardValueAndStatusVo.setAttributeValue(valuePo.getValue());
                standardValueAndStatusVo.setIsInclude(true);
                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
                finalMap.put(attributeId.toString(), standardValueAndStatusVo);
//                attributeValues.add(map1);
            });

//            List<List<Map<String,String>>> skuList = Lists.newArrayList();
//            valueIds.forEach(b->{
//                //属性信息（map<属性ID，属性名称>、map<属性值ID,属性值>）
////                AttributeInfos attributeInfo = new AttributeInfos();
//                List<Map<String,String>> listMap = Lists.newArrayList();
//                //map<属性值ID,属性值>
//                Map<String,String> valueMap = new HashMap<>();
//                PmGoodsAttributeValuePo valuePo = goodsAttributeValueMapper.selectById(b);
//                if (valuePo==null){
//                    throw new ServiceException(ResultCode.NO_EXISTS,"数据库不存在对应的属性值",b);
//                }
//                String value = valuePo.getValue();
//                valueMap.put("valueId",b.toString());
//                valueMap.put("valueName",value);
//                //map<属性ID,属性名>
//                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
//                String name = goodsAttributeMapper.selectById(attributeId).getName();
//                Map<String,String> attributeMap = new HashMap<>();
//                attributeMap.put("attributeId",attributeId.toString());
//                attributeMap.put("attributeName",name);
//                listMap.add(attributeMap);
//                listMap.add(valueMap);
////                attributeInfo.setAttributeInfos(listMap);
//
////                skuList.add(attributeInfo);
//
//                skuList.add(listMap);
//
//            });

//            findSkuAttributeVo.setSkuList(attributeInfos);

//            findSkuAttributeVo.setAttributeValues(map1);

//            findSkuAttributeVos.add(findSkuAttributeVo);
            mapList.add(map1);
        });

        return mapList;
    }


//    /**
//     * 添加商品额外的属性值
//     *
//     * @param addExtraValueDto
//     * @return
//     */
//    public void addExtraValue(AddExtraValueDto addExtraValueDto) {
//
//        //获取当前用户
//        String user = securityUtil.getCurrUser().getUsername();
//        //判断该属性下是否已经存在属性值
//        List<PmGoodsAttributeValuePo> valuePoList = goodsAttributeValueMapper.findByAttributeId(addExtraValueDto.getGoodsAttributeId());
//
//        List<String> valueList = valuePoList.stream().map(s -> s.getValue()).collect(Collectors.toList());
//        if (valueList.contains(addExtraValueDto.getValue())) {
//            throw new ServiceException(ResultCode.DUPLICATION, "添加失败，属性值不能重复");
//        }
//        PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
//        goodsAttributeValuePo.setProductAttributeId(addExtraValueDto.getGoodsAttributeId());
//        goodsAttributeValuePo.setValue(addExtraValueDto.getValue());
//        goodsAttributeValuePo.setCreateBy(user);
//        //设置类型为自定义属性值
//        goodsAttributeValuePo.setIsCustom(true);
//        goodsAttributeValueMapper.insert(goodsAttributeValuePo);
//    }

    /**
     * 添加sku属性信息
     *
     * @param addOrUpdateSkuAttributeDto
     * @return
     */
    @Override
    public void addOrUpdateSkuAttribute(AddOrUpdateSkuAttributeDto addOrUpdateSkuAttributeDto) {

        //初始化商品总库存
//        int stock = 0;
        String user = securityUtil.getCurrUser().getUsername();
        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
//        if (storeId != null) {
//            PmGoodsPo goodsPo = new PmGoodsPo();
//            goodsPo.setId(addOrUpdateSkuAttributeDto.getGoodsId());
//            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
//            mapper.updateById(goodsPo);
//        }
        //通过goodsID商品ID获取sku信息
        Map<String, Object> skuMaps = new HashMap<>();
        skuMaps.put("goods_id", addOrUpdateSkuAttributeDto.getGoodsId());
        //获取该商品下的sku信息
        List<PmGoodsSkuPo> skus = goodsSkuMapper.selectByMap(skuMaps);
        //获取该商品下的skuIds,为与前端传过来的skuid对比
        List<Long> skuIds = skus.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<Long> compareSkuIds = Lists.newArrayList (skuIds);
        //获取前端传来的sku数据
        List<AddSkuAttributeDto> skuAttributeDtoList = addOrUpdateSkuAttributeDto.getSkuAttributeDtos ();
        List<Long> webSkuIds = skuAttributeDtoList.stream().map (b->b.getSkuId ()).collect(Collectors.toList());
        //保存需要添加的sku信息
        List<AddSkuAttributeDto> addSkuAttributeDtos = Lists.newArrayList();
        /**全部是添加*/
        //如果skuPos为空，则新增sku
        if (skus.size() == 0 && skus == null) {
            addSkus (addOrUpdateSkuAttributeDto.getSkuAttributeDtos (),addOrUpdateSkuAttributeDto.getGoodsId (), user);
        }else{
            addOrUpdateSkuAttributeDto.getSkuAttributeDtos ().forEach (a->{
                //判断是否为新增
                if (a.getSkuId ()==0){
                    addSkuAttributeDtos.add (a);
                }else{
                    PmGoodsSkuPo goodsSkuPo = goodsSkuMapper.selectById (a.getSkuId ());
                    BeanUtils.copyProperties (a, goodsSkuPo);
                    goodsSkuMapper.updateById (goodsSkuPo);
                    //判断商品虚拟库存是否需要修改
                    if(!a.getStock().equals(goodsSkuPo.getStock())) {
                        //库存发生变化
                        UpdateWrapper<PmGoodsVirtualStockPo> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.lambda().eq(PmGoodsVirtualStockPo::getStoreId, storeId)
                                .eq(PmGoodsVirtualStockPo::getGoodsSkuId, goodsSkuPo.getId())
                                .set(PmGoodsVirtualStockPo::getStockNum, a.getStock());
                        pmGoodsVirtualStockService.update(updateWrapper);
                    }
                }
            });
            //新增
            addSkus (addSkuAttributeDtos,addOrUpdateSkuAttributeDto.getGoodsId (), user);
            //获取被删除的sku
            List<Long> webSkuRemainIds = webSkuIds.stream ().filter (y->y!=0).collect(Collectors.toList());
           compareSkuIds.removeAll (webSkuRemainIds);
           if (compareSkuIds!=null && compareSkuIds.size ()!=0){
               compareSkuIds.forEach (id->{
                   Map<String, Object> valueMap = new HashMap<>();
                   valueMap.put("goods_sku_id", id);
                   valueMap.put ("del_flag", false);
                   List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(valueMap);
                   //获取属性值ID集合
                   List<Long> valueIds = relAttributeValueSkuPos.stream().map(a -> a.getGoodsAttributeValueId()).collect(Collectors.toList());
                   List<PmGoodsAttributeValuePo> goodsAttributeValuePos = goodsAttributeValueMapper.selectBatchIds(valueIds);
                   //获取除自定义属性值并删除
                   /*goodsAttributeValuePos.stream().filter(a -> a.getIsCustom() == false).forEach(b -> {
                       goodsAttributeValueMapper.deleteById(b.getId());
                   });*/
                   //删除sku与属性值关联表的信息
                   goodsRelAttributeValueSkuMapper.deleteByMap(valueMap);
                   //最后删除该条sku
                   goodsSkuMapper.deleteById(id);
               });
           }
//        }
//        else if (!compareSkuIds.retainAll (webSkuIds)){
//            //删除已有的记录
//            //根据skuId获取信息
//            skuIds.forEach(id -> {
//                Map<String, Object> valueMap = new HashMap<>();
//                valueMap.put("goods_sku_id", id);
//                valueMap.put ("del_flag", false);
//                List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(valueMap);
//                //获取属性值ID集合
//                List<Long> valueIds = relAttributeValueSkuPos.stream().map(a -> a.getGoodsAttributeValueId()).collect(Collectors.toList());
//                List<PmGoodsAttributeValuePo> goodsAttributeValuePos = goodsAttributeValueMapper.selectBatchIds(valueIds);
//                //获取自定义属性值并删除
//                goodsAttributeValuePos.stream().filter(a -> a.getIsCustom()).forEach(b -> {
//                    goodsAttributeValueMapper.deleteById(b.getId());
//                });
//                //删除sku与属性值关联表的信息
//                goodsRelAttributeValueSkuMapper.deleteByMap(valueMap);
//                //最后删除该条sku
//                goodsSkuMapper.deleteById(id);
//            });
//            addSkus (addOrUpdateSkuAttributeDto.getSkuAttributeDtos (),addOrUpdateSkuAttributeDto.getGoodsId (), user);
//        }
//        /***/
//        else{
//            Collections.sort(skuIds);
//            Collections.sort(webSkuIds);
//            boolean notDel = skuIds.equals (webSkuIds);
//            //全部是修改
//            if (notDel) {
//                skuAttributeDtoList.forEach (x -> {
//                    PmGoodsSkuPo goodsSkuPo = goodsSkuMapper.selectById (x.getSkuId ());
//                    BeanUtils.copyProperties (x, goodsSkuPo);
//                    goodsSkuMapper.updateById (goodsSkuPo);
//                });
//            }
//
//            //过滤出skuid为0的数据
//            List<AddSkuAttributeDto> addSkuAttributeDtoList = skuAttributeDtoList.stream ().filter (c->c.getSkuId ()==0).collect(Collectors.toList());
//            //只修改查出来sku,有可能删除
//            if (addSkuAttributeDtoList==null && addSkuAttributeDtoList.size ()==0){
//
//            }else{
//                addSkus (addSkuAttributeDtoList,addOrUpdateSkuAttributeDto.getGoodsId (), user);
//            }
//            //过滤出skuId不为0的数据
//            List<AddSkuAttributeDto> updateSkuAttributeDtoList = skuAttributeDtoList.stream ().filter (d->d.getSkuId ()!=0).collect(Collectors.toList());
//
//        }
//        //如果skuPos不为空，则更新sku
//        else {
//            //根据删除所有sku关联关系，pm_goods_sku删除该商品的sku,pm_goods_rel_attribute_value_sku获取该商品
//            //下对应的自定义属性值pm_goods_attribute_value并删除，删除sku与属性值的关联关系
//            //先获取自定义属性值并删除
//            List<Long> skuIds = skus.stream().map(a -> a.getId()).collect(Collectors.toList());
//            //根据skuId获取信息
//            skuIds.forEach(id -> {
//                Map<String, Object> valueMap = new HashMap<>();
//                valueMap.put("goods_sku_id", id);
//                valueMap.put ("del_flag", false);
//                List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(valueMap);
//                //获取属性值ID集合
//                List<Long> valueIds = relAttributeValueSkuPos.stream().map(a -> a.getGoodsAttributeValueId()).collect(Collectors.toList());
//                List<PmGoodsAttributeValuePo> goodsAttributeValuePos = goodsAttributeValueMapper.selectBatchIds(valueIds);
//                //获取自定义属性值并删除
//                goodsAttributeValuePos.stream().filter(a -> a.getIsCustom()).forEach(b -> {
//                    goodsAttributeValueMapper.deleteById(b.getId());
//                });
//                //删除sku与属性值关联表的信息
//                goodsRelAttributeValueSkuMapper.deleteByMap(valueMap);
//                //最后删除该条sku
//                goodsSkuMapper.deleteById(id);
//            });
//
//            /**
//             * 重新添加sku相关信息
//             */
//            addSkus(addOrUpdateSkuAttributeDto, /*stock,*/ user);
        }

    }

    /**
     * 获取店铺推荐商品
     * @param searchRecommendGoodsDto
     * @return
     */
    @Override
    public PageInfo<RecommendGoodsVo> storeRecommendGoods(SearchRecommendGoodsDto searchRecommendGoodsDto) {

        if(null != searchRecommendGoodsDto.getInformationId()) {
            MmInformationPo mmInformationPo = mmInformationMapper.selectById(searchRecommendGoodsDto.getInformationId());
            searchRecommendGoodsDto.setStoreId(mmInformationPo.getStoreId());
        } else {
            //保存资讯时还没有资讯id
            Long storeId = securityUtil.getCurrUser().getStoreId();
            if(null == storeId) {
                //当前登录用户跟操作不匹配
                throw  new ServiceException(ResultCode.FAIL, "当前登录用户跟操作不匹配");
            }
            searchRecommendGoodsDto.setStoreId(storeId);
        }

        Integer pageNo = searchRecommendGoodsDto.getPageNo() == null ? defaultPageNo : searchRecommendGoodsDto.getPageNo();
        Integer pageSize = searchRecommendGoodsDto.getPageSize() == null ? defaultPageSize : searchRecommendGoodsDto.getPageSize();
        PageInfo<RecommendGoodsVo> recommendGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.storeRecommendGoods(searchRecommendGoodsDto));

        return recommendGoodsVoPageInfo;
    }

    /**
     * 添加sku列表
     *
     * @param goodsId
     * @param user
     */
    private void addSkus(List<AddSkuAttributeDto> skuAttributeDtoList,Long goodsId,String user) {
        PmGoodsPo pmGoodsPo = mapper.selectById(goodsId);
        for (AddSkuAttributeDto addSkuAttributeDto : skuAttributeDtoList) {
            //保存非关联信息到sku
            PmGoodsSkuPo goodsSkuPo = new PmGoodsSkuPo();
            BeanUtils.copyProperties(addSkuAttributeDto, goodsSkuPo);
            goodsSkuPo.setCreateBy(user);
            goodsSkuPo.setGoodsId(goodsId);
            goodsSkuMapper.insert(goodsSkuPo);
            //插入商品虚拟库存
            PmGoodsVirtualStockPo pmGoodsVirtualStockPo = new PmGoodsVirtualStockPo();
            pmGoodsVirtualStockPo.setStoreId(pmGoodsPo.getStoreId())
                    .setGoodsId(goodsId)
                    .setGoodsSkuId(goodsSkuPo.getId())
                    .setStockNum(goodsSkuPo.getStock())
                    .setIsOwn(true)
                    .setCreateBy(user);
            pmGoodsVirtualStockMapper.insert(pmGoodsVirtualStockPo);
            //获取前端传的规格信息
            List<AddStandardToGoodDto> standardToGoodDtos = addSkuAttributeDto.getStandardInfos();
            standardToGoodDtos.forEach(x -> {
                //获取类目下默认的规格信息
                if (x.getValueId() != null) {
                    //保存商品对应的规格值到关联表
                    PmGoodsRelAttributeValueSkuPo goodsRelAttributeValueSkuPo = new PmGoodsRelAttributeValueSkuPo();
                    goodsRelAttributeValueSkuPo.setCreateBy(user);
                    goodsRelAttributeValueSkuPo.setGoodsAttributeValueId(x.getValueId());
                    goodsRelAttributeValueSkuPo.setGoodsSkuId(goodsSkuPo.getId());
                    goodsRelAttributeValueSkuMapper.insert(goodsRelAttributeValueSkuPo);
                }
                //获取商品对应的自定义规格信息
                else if (x.getValueId() == null) {

                    //判断该属性下是否已经存在属性值
                    List<PmGoodsAttributeValuePo> valuePoList = goodsAttributeValueMapper.findByAttributeId(x.getAttributeId());
                    List<String> valueList = valuePoList.stream().map(s -> s.getValue()).collect(Collectors.toList());
                    if (valueList.contains(x.getValue())) {
                        throw new ServiceException(ResultCode.DUPLICATION, "添加失败，属性值不能重复", x.getValue());
                    }
                    PmGoodsAttributeValuePo goodsAttributeValuePo1 = new PmGoodsAttributeValuePo();
                    goodsAttributeValuePo1.setValue(x.getValue());
                    goodsAttributeValuePo1.setProductAttributeId(x.getAttributeId());
                    QueryWrapper<PmGoodsAttributeValuePo> queryWrapper = new QueryWrapper<>(goodsAttributeValuePo1);
                    List<PmGoodsAttributeValuePo> goodsAttributeValuePo2 = goodsAttributeValueMapper.selectList(queryWrapper);
                    Long valueId = null;
                    if (ListUtil.isListNullAndEmpty(goodsAttributeValuePo2)) {
                        //保存自定义规格值
                        PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
                        goodsAttributeValuePo.setProductAttributeId(x.getAttributeId());
                        goodsAttributeValuePo.setValue(x.getValue());
                        goodsAttributeValuePo.setCreateBy(user);
                        //设置类型为自定义属性值
                        goodsAttributeValuePo.setIsCustom(true);
                        goodsAttributeValueMapper.insert(goodsAttributeValuePo);
                        valueId = goodsAttributeValuePo.getId();
                    }

                    //根据具体规格下的新增的规格值获取规格值ID
                    else {
                        valueId = goodsAttributeValuePo2.get(0).getId();
                    }

                    //保存商品对应的规格值到关联表
                    PmGoodsRelAttributeValueSkuPo goodsRelAttributeValueSkuPo = new PmGoodsRelAttributeValueSkuPo();
                    goodsRelAttributeValueSkuPo.setCreateBy(user);
                    goodsRelAttributeValueSkuPo.setGoodsAttributeValueId(valueId);
                    goodsRelAttributeValueSkuPo.setGoodsSkuId(goodsSkuPo.getId());
                    goodsRelAttributeValueSkuMapper.insert(goodsRelAttributeValueSkuPo);

                }
            });
//            stock += addSkuAttributeDto.getStock();
//            //更新商品总库存
//            PmGoodsPo goodsPo = new PmGoodsPo();
//            goodsPo.setStock(stock);
//            goodsPo.setId(addOrUpdateSkuAttributeDto.getGoodsId());
//            goodsMapper.updateById(goodsPo);
        }
    }


    /**
     * 根据商品ID查找财务的sku信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public GetSkuFinanceInfoVo findSkuFinance(Long goodsId) {

        GetSkuFinanceInfoVo getSkuFinanceInfoVo = new GetSkuFinanceInfoVo();
        List<GoodsStandardVo> standardVos = Lists.newArrayList();
        //获取商品对应的分类ID
        Long categoryId = mapper.selectById(goodsId).getGoodsCategoryId();
        //获取分类下所有的规格
        List<BaseBo> goodsAttributePos = goodsAttributeMapper.findStandardName(categoryId);
        //遍历规格名称
        goodsAttributePos.forEach(x -> {
            List<GoodsValueBo> goodsValues = mapper.findGoodsValue(goodsId, x.getId());
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
                standardVos.add(goodsStandardVo);
            }
        });
        getSkuFinanceInfoVo.setGoodsStandardVo(standardVos);


//        List<FindSkuFinanceVo> findSkuFinanceVos = Lists.newArrayList();
        List<Map<String, Object>> mapList = Lists.newArrayList();
        //判断该商品是否存在
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "该商品不存在！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("del_flag",false);
        List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(map);
        if (goodsSkuPos == null && goodsSkuPos.size() == 0) {
            return null;
        }
        //循环获取sku信息
        goodsSkuPos.forEach(x -> {
            Map<String, Object> mapBean = new HashMap<>();
            //获取除规格信息外的其他信息
            FindSkuFinanceVo findSkuFinanceVo = new FindSkuFinanceVo();
            BeanUtils.copyProperties(x, findSkuFinanceVo);
            findSkuFinanceVo.setSkuId(x.getId());
            mapBean = JSONUtils.toBean(findSkuFinanceVo, Map.class);

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
                    throw new ServiceException(ResultCode.NO_EXISTS, String.format("数据库不存在对应的属性值id[%s]",b));
                }
                StandardValueAndStatusVo standardValueAndStatusVo = new StandardValueAndStatusVo();
                standardValueAndStatusVo.setAttributeValueId(b);
                standardValueAndStatusVo.setAttributeValue(valuePo.getValue());
                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
                finalMap.put(attributeId.toString(), standardValueAndStatusVo);
//                attributeValues.add(map1);
            });

           /* List<List<Map<String, String>>> skuList = Lists.newArrayList();
            valueIds.forEach(b -> {
                //属性信息（map<属性ID，属性名称>、map<属性值ID,属性值>）
//                AttributeInfos attributeInfo = new AttributeInfos();
                List<Map<String, String>> listMap = Lists.newArrayList();
                //map<属性值ID,属性值>
                Map<String, String> valueMap = new HashMap<>();
                PmGoodsAttributeValuePo valuePo = goodsAttributeValueMapper.selectById(b);
                if (valuePo == null) {
                    throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在对应的属性值", b);
                }
                String value = valuePo.getValue();
                valueMap.put("valueId", b.toString());
                valueMap.put("valueName", value);
                //map<属性ID,属性名>
                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
                String name = goodsAttributeMapper.selectById(attributeId).getName();
                Map<String, String> attributeMap = new HashMap<>();
                attributeMap.put("attributeId", attributeId.toString());
                attributeMap.put("attributeName", name);
                listMap.add(attributeMap);
                listMap.add(valueMap);

                skuList.add(listMap);

            });*/

//            findSkuFinanceVo.setAttributeValues(attributeValues);
//
//            findSkuFinanceVos.add(findSkuFinanceVo);

            mapList.add(mapBean);
        });
        getSkuFinanceInfoVo.setMapList(mapList);

        return getSkuFinanceInfoVo;
    }

    /**
     * 添加或更新财务信息
     *
     * @param updateSkuFinanceDtos
     * @return
     */
    @Override
    public void updateSkuFinance(List<UpdateSkuFinanceDto> updateSkuFinanceDtos) {

        Long skuId = updateSkuFinanceDtos.stream().map(a -> a.getSkuId()).collect(Collectors.toList()).get(0);
        //根据skuId获取goodsId
        Long goodsId = goodsSkuMapper.selectById(skuId).getGoodsId();
        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        //商家端执行更新操作将审核状态修改为未审核状态
//        if (storeId != null) {
//            PmGoodsPo goodsPo = new PmGoodsPo();
//            goodsPo.setId(goodsId);
//            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
//            mapper.updateById(goodsPo);
//        }
        updateSkuFinanceDtos.forEach(updateSkuFinanceDto -> {
            PmGoodsSkuPo goodsSkuPo = goodsSkuMapper.selectById(updateSkuFinanceDto.getSkuId());
            BeanUtils.copyProperties(updateSkuFinanceDto, goodsSkuPo);
            goodsSkuMapper.updateById(goodsSkuPo);
        });
    }

    /**
     * 根据商品ID查找运营信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public FindGoodOperationVo findGoodOperation(Long goodsId) {

        FindGoodOperationVo findGoodOperationVo = new FindGoodOperationVo();
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo == null) {
            return null;
        }
        BeanUtils.copyProperties(goodsPo, findGoodOperationVo);
        List<MemberLevelInfos> memberLevelInfos = memberLevelMapper.memberLevelInfos ();
        //获取最低等级
        Integer lowestLevel = memberLevelMapper.getLowestLevelId();
        memberLevelInfos.stream ().filter (a -> memberLevelMapper.selectById (a.getMemberLevelId ()).getLevel () == lowestLevel).forEach (a -> {
            a.setLevelName (a.getLevelName () + "/全部用户");
        });
        findGoodOperationVo.setMemberLevelInfos (memberLevelInfos);

        //获取分类下的税率
        if (goodsPo.getGoodsType ()==GoodsTypeEnum.OVERSEA.getName () || goodsPo.getGoodsType ()==GoodsTypeEnum.BONDED.getName()) {
            BigDecimal taxRate = goodsCategoryMapper.selectById (goodsPo.getGoodsCategoryId ()).getTaxRate ();
            findGoodOperationVo.setTaxRate (taxRate);
        }
        findGoodOperationVo.setGoodsId (goodsId);
        //获取当前的限制会员等级
        if (goodsPo.getMemberLevelId ()!=0) {
            findGoodOperationVo.setMemberLevelId (goodsPo.getMemberLevelId ());
            if (memberLevelMapper.selectById  (goodsPo.getMemberLevelId ()).getLevel () == lowestLevel)
            findGoodOperationVo.setMemberLevelName (memberLevelMapper.selectById (goodsPo.getMemberLevelId ()).getLevelName ()+"/全部会员");
        }
        return findGoodOperationVo;

       /* //获取所有的会员列表
        List<PmMemberLevelPo> memberLevelPos = memberLevelMapper.selectList(null);
        //存放会员信息列表
        List<MemberLevelInfos> memberLevelInfos = Lists.newArrayList();
        FindGoodOperationVo findGoodOperationVo = new FindGoodOperationVo();
        Map<String, Object> mapp = new HashMap<>();
        mapp.put("level", 1);
        findGoodOperationVo.setLowestLevelId(memberLevelMapper.selectByMap(mapp).get(0).getId());
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        //获取goodsType
        findGoodOperationVo.setGoodsType(goodsPo.getGoodsType());
        if (goodsPo == null) {
            memberLevelPos.forEach(a -> {
                MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
                memberLevelInfo.setIsInclude(false);
                memberLevelInfo.setMemberLevelId(a.getId());
                memberLevelInfo.setLevelName(a.getLevelName());
                memberLevelInfos.add(memberLevelInfo);
            });
            findGoodOperationVo.setMemberLevelInfos(memberLevelInfos);

        } else {
            String memberLevelName = memberLevelMapper.selectById(goodsPo.getMemberLevelId()).getLevelName();
            BeanUtils.copyProperties(goodsPo, findGoodOperationVo);
            findGoodOperationVo.setGoodsId(goodsId);
            findGoodOperationVo.setMemberLevelName(memberLevelName);
            //绑定的会员等级设置为true
            MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
            memberLevelInfo.setIsInclude(true);
            memberLevelInfo.setMemberLevelId(findGoodOperationVo.getMemberLevelId());
            memberLevelInfo.setLevelName(memberLevelName);
            memberLevelInfos.add(memberLevelInfo);
            //未绑定的置为false
            memberLevelPos.stream().filter(a -> a.getId() != (goodsPo.getMemberLevelId())).forEach(b -> {
                MemberLevelInfos memberLevel = new MemberLevelInfos();
                memberLevel.setMemberLevelId(b.getId());
                memberLevel.setLevelName(b.getLevelName());
                memberLevel.setIsInclude(false);
                memberLevelInfos.add(memberLevel);
            });
            findGoodOperationVo.setMemberLevelInfos(memberLevelInfos);
        }
        return findGoodOperationVo;
        else {
            BeanUtils.copyProperties(findGoodOperationVo, goodsPo);
            findGoodOperationVo.setGoodsId(goodsId);
            //根据商品ID查找关联的会员等级购买权限,商品和会员等级多对多的关系
            Map<String, Object> map = new HashMap<>();
            map.put("goods_good_id", goodsId);
            //获取商品限制的会员等级信息
            List<PmGoodsRelGoodsMemberLevelPo> relGoodsMemberLevelPos = goodsRelGoodsMemberLevelMapper.selectByMap(map);
            //获取和该商品绑定的所有会员等级ID
            List<Long> assignMemberIds = relGoodsMemberLevelPos.stream().map(a->a.getMemberLevelId()).collect(Collectors.toList());
            List<PmMemberLevelPo> assignMembers = memberLevelMapper.selectBatchIds(assignMemberIds);
            //将所有与商品关联的会员的isInclude置为true
            assignMembers.forEach(a->{
                MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
                memberLevelInfo.setMemberLevelId(a.getId());
                memberLevelInfo.setLevelName(a.getLevelName());
                memberLevelInfo.setIsInclude(true);
                memberLevelInfos.add(memberLevelInfo);
            });
            //在所有会员等级列表中排除与商品关联的会员等级列表，并将sInclude置为false
            memberLevelPos.stream().filter(item->!assignMemberIds.contains(item.getId())).forEach(a->{
                MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
                memberLevelInfo.setMemberLevelId(a.getId());
                memberLevelInfo.setLevelName(a.getLevelName());
                memberLevelInfo.setIsInclude(false);
                memberLevelInfos.add(memberLevelInfo);
            });
            findGoodOperationVo.setMemberLevelInfos(memberLevelInfos);
            return findGoodOperationVo;
        }*/

    }

    /**
     * 添加或更新运营信息
     *
     * @param updateGoodOperationDto
     * @return
     */
    @Override
    public void updateGoodOperation(UpdateGoodOperationDto updateGoodOperationDto) {

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        PmGoodsPo goodsPo = mapper.selectById(updateGoodOperationDto.getGoodsId());
        BeanUtils.copyProperties(updateGoodOperationDto, goodsPo);

        //应对前端在新增商品时传了校验状态为0的情况
        if (updateGoodOperationDto.getVerifyStatus() == null || updateGoodOperationDto.getVerifyStatus() == 0) {
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId()).setPublishStatus(null);
        }

        if (!goodsPo.getGoodsType ().equals (GoodsTypeEnum.OVERSEA.getName ()) && !goodsPo.getGoodsType ().equals (GoodsTypeEnum.BONDED.getName())) {
            goodsPo.setCustomTaxRate (null);
            goodsPo.setTaxRateType (TaxRateTypeEnum.NULL_TAX_RATE.getId ());
        }
        //商家端执行更新操作将审核状态修改为未审核状态
//        if (storeId != null) {
//            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
//        }
        mapper.updateById(goodsPo);

        //删除商品对应的会员关系

        //根据商品ID查找关联的会员等级购买权限,商品和会员等级多对多的关系
        /*Map<String, Object> map = new HashMap<>();
        map.put("goods_good_id", updateGoodOperationDto.getGoodsId());
        //获取商品限制的会员等级信息
        List<PmGoodsRelGoodsMemberLevelPo> relGoodsMemberLevelPos = goodsRelGoodsMemberLevelMapper.selectByMap(map);
        //获取和该商品绑定的所有会员等级ID
        List<Long> ids = relGoodsMemberLevelPos.stream().map(a->a.getId()).collect(Collectors.toList());
        goodsRelGoodsMemberLevelMapper.deleteBatchIds(ids);

        //保存关联信息,限定会员关系
        PmGoodsRelGoodsMemberLevelPo relGoodsMemberLevelPo = new PmGoodsRelGoodsMemberLevelPo();
        for (Long id : updateGoodOperationDto.getMemberLevelIds()) {
            relGoodsMemberLevelPo.setCreateBy(user).setMemberLevelId(id).
                    setGoodsGoodId(updateGoodOperationDto.getGoodsId());
            goodsRelGoodsMemberLevelMapper.insert(relGoodsMemberLevelPo);
        }*/
        //如果是审核通过，则删除对应的驳回详情
        if (updateGoodOperationDto.getVerifyStatus() == VerifyStatusEnum.CHECKED.getId()) {

            PmGoodsPo goodsPo1 = mapper.selectById (updateGoodOperationDto.getGoodsId());
            goodsPo1.setContent(null);
            mapper.updateById(goodsPo1);
        }
    }

    /**
     * 根据商品ID查找销售信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public FindGoodSellerVo findGoodSeller(Long goodsId) {

        FindGoodSellerVo findGoodSellerVo = new FindGoodSellerVo();
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo == null) {
            return null;
        }
        //查找省市ID
        /*String cityCode = goodsPo.getLocation();
        QueryWrapper<AreaRegionPo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("city_code",cityCode);
        String provinceCode = areaRegionMapper.selectOne(queryWrapper).getParentCode();
        String[] locationCodes = {cityCode,provinceCode};*/
        BeanUtils.copyProperties(goodsPo, findGoodSellerVo);
        findGoodSellerVo.setGoodsId(goodsId);
//        findGoodSellerVo.setLocationCodes(locationCodes);

        return findGoodSellerVo;
    }

    /**
     * 销售角色添加或更新商品信息
     *
     * @param updateGoodSellerDto
     * @return
     */
    @Override
    public void updateGoodSeller(UpdateGoodSellerDto updateGoodSellerDto) {

        PmGoodsPo goodsPo = mapper.selectById(updateGoodSellerDto.getGoodsId());
        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        //商家端执行更新操作将审核状态修改为未审核状态
//        if (storeId != null) {
//            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
//        }
        BeanUtils.copyProperties(updateGoodSellerDto, goodsPo);
        mapper.updateById(goodsPo);

    }

    /**
     * 添加商品关联
     *
     * @param associationDto
     * @return
     */
    @Override
    public void addAssociationGoods(AddAssociationGoodsDto associationDto) {

        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        PmGoodsPo goodsPo = new PmGoodsPo();
        goodsPo.setId(associationDto.getGoodsId());
        //商家端执行更新操作将审核状态修改为未审核状态
//        if (storeId != null) {
//            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
//            mapper.updateById(goodsPo);
//        }
        List<PmAssociationGoodsPo> associationGoodsPos = Lists.newArrayList ();
        associationDto.getAssociatedGoodsId ().forEach (a->{
            PmAssociationGoodsPo associationGoodsPo = new PmAssociationGoodsPo();
            associationGoodsPo.setId (null);
            associationGoodsPo.setGoodsId (associationDto.getGoodsId());
            associationGoodsPo.setAssociatedGoodsId (a);
            associationGoodsPo.setCreateBy (securityUtil.getCurrUser().getUsername ());
            associationGoodsPo.setStoreId (mapper.selectById (associationDto.getGoodsId ()).getStoreId ());
            associationGoodsPos.add (associationGoodsPo);
        });
        saveBatch2.saveBatch (associationGoodsPos);
    }

    /**
     * 平台审核商品不通过
     *
     * @param rejectGoodsDto
     */
    @Override
    public void rejectGoods(RejectGoodsDto rejectGoodsDto) {

        PmGoodsPo goodsPo = mapper.selectById (rejectGoodsDto.getGoodsId ());
        goodsPo.setContent(rejectGoodsDto.getContent());
        goodsPo.setVerifyStatus (VerifyStatusEnum.NOT_APPROVED.getId ());
        mapper.updateById(goodsPo);

    }

    @Override
    public boolean updateStock(long goodId, int number) {
        return mapper.updateStock(goodId, number) > 0;
    }

    /**
     * 提交商品审核
     *
     * @param goodsIds
     */
    @Override
    public void submitAudit(Long[] goodsIds) {

        //根据ID获取商品信息
        List<PmGoodsPo> goodsPos = mapper.selectBatchIds(Arrays.asList(goodsIds));
        goodsPos.forEach(a -> {
            if (a.getVerifyStatus() != VerifyStatusEnum.UNCHECKED.getId() && a.getVerifyStatus() != VerifyStatusEnum.NOT_APPROVED.getId()) {
                throw new ServiceException(ResultCode.FAIL, "该商品状态不是待提交状态或不通过状态", a.getName());
            } else {
                //修改商品状态
                a.setVerifyStatus(VerifyStatusEnum.WAIT_CONFIRM.getId());
                mapper.updateById(a);
            }
        });

    }

    /**
     * 上架或下架商品
     *
     * @param updatePublishStatusDt
     */
    @Override
    public void publishStatus(UpdatePublishStatusDto updatePublishStatusDt) {

        List<PmGoodsPo> goodsPos = mapper.selectBatchIds(Arrays.asList(updatePublishStatusDt.getGoodIds()));
        //判断商品状态为审核通过状态才能执行上下架操作
        goodsPos.forEach(a -> {
            //状态为非审核通过状态
            if (a.getVerifyStatus() != VerifyStatusEnum.CHECKED.getId()) {
                throw new ServiceException(ResultCode.FAIL, "该商品的状态还未通过审核", a.getName());
            } else {
                //修改上架状态
                a.setPublishStatus(updatePublishStatusDt.getPulishStatus());
                mapper.updateById(a);
            }
        });
    }

    /**
     * 修改应用标签
     *
     * @param updateStarStatusDto
     */
    @Override
    public void updateStarStatus(UpdateStarStatusDto updateStarStatusDto) {

        List<PmGoodsPo> goodsPos = mapper.selectBatchIds(Arrays.asList(updateStarStatusDto.getGoodIds()));
        goodsPos.forEach(a -> {
            a.setStarStatus(updateStarStatusDto.getStarStatus());
            mapper.updateById(a);
        });

    }

    /**
     * 条件查询商品信息
     *
     * @param searchGoodInfosDto
     */
    @Override
    public PageInfo<PmGoodsVo> searchGoodsInfo(SearchGoodInfosDto searchGoodInfosDto) {
        SysUserPo userPo = securityUtil.getCurrUser ();
        if(userPo.getStoreId ()!=null){
            searchGoodInfosDto.setStoreId (userPo.getStoreId ());
        }
        Integer pageNo = searchGoodInfosDto.getPageNo() == null ? defaultPageNo : searchGoodInfosDto.getPageNo();
        Integer pageSize = searchGoodInfosDto.getPageSize() == null ? defaultPageSize : searchGoodInfosDto.getPageSize();
        PageInfo<PmGoodsVo> goodsVos = new PageInfo<>();

        goodsVos = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.searchGoodsInfo(searchGoodInfosDto));
        goodsVos.getList().forEach(a -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("goods_id", a.getId());
            int stock = goodsSkuMapper.selectByMap(map).stream().map(PmGoodsSkuPo::getStock).mapToInt(c -> c).sum();
            int saleVolumn = goodsSkuMapper.selectByMap(map).stream().map(PmGoodsSkuPo::getSalesVolume).mapToInt(c -> c).sum();
            a.setStock(stock);
            a.setSalesVolume (saleVolumn);
        });
        return goodsVos;
    }

    /**
     * 统计商品记录
     *
     * @return
     */
    @Override
    public GoodStatisticsVo statisticsGood() {

        GoodStatisticsVo goodStatisticsVo = new GoodStatisticsVo();
        //获取当前商家用户店铺id
        Long storeId = securityUtil.getCurrUser().getStoreId();
        PmGoodsPo goodsPo = new PmGoodsPo();
        goodsPo.setStoreId(storeId);
        QueryWrapper<PmGoodsPo> queryWrapper = new QueryWrapper<>(goodsPo);
        Integer goodSum = mapper.selectCount(queryWrapper);
        //商品总数
        goodStatisticsVo.setGoodSum(goodSum);

        PmGoodsPo goodsPo1 = new PmGoodsPo();
        goodsPo1.setStoreId(storeId);
        goodsPo1.setPublishStatus(true);
        QueryWrapper<PmGoodsPo> queryWrapper1 = new QueryWrapper<>(goodsPo1);
        //已上架数量
        Integer publishedNum = mapper.selectCount(queryWrapper1);
        goodStatisticsVo.setPublishedNum(publishedNum);

        PmGoodsPo goodsPo2 = new PmGoodsPo();
        goodsPo2.setStoreId(storeId);
        goodsPo2.setPublishStatus(false);
        QueryWrapper<PmGoodsPo> queryWrapper2 = new QueryWrapper<>(goodsPo2);
        //未上架商品数量
        Integer notPublishNum = mapper.selectCount(queryWrapper2);
        goodStatisticsVo.setNotPublishNum(notPublishNum);

        PmGoodsPo goodsPo3 = new PmGoodsPo();
        goodsPo3.setStoreId(storeId);
        goodsPo3.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
        QueryWrapper<PmGoodsPo> queryWrapper3 = new QueryWrapper<>(goodsPo3);
        //待提交商品数量
        Integer unCheckNum = mapper.selectCount(queryWrapper3);
        goodStatisticsVo.setUnCheckNum(unCheckNum);

        PmGoodsPo goodsPo4 = new PmGoodsPo();
        goodsPo4.setStoreId(storeId);
        goodsPo4.setVerifyStatus(VerifyStatusEnum.WAIT_CONFIRM.getId());
        QueryWrapper<PmGoodsPo> queryWrapper4 = new QueryWrapper<>(goodsPo4);
        //待审核商品数量
        Integer onCheckNum = mapper.selectCount(queryWrapper4);
        goodStatisticsVo.setOnCheckNum(onCheckNum);

        PmGoodsPo goodsPo5 = new PmGoodsPo();
        goodsPo5.setStoreId(storeId);
        goodsPo5.setVerifyStatus(VerifyStatusEnum.NOT_APPROVED.getId());
        QueryWrapper<PmGoodsPo> queryWrapper5 = new QueryWrapper<>(goodsPo5);
        //未通过商品数量
        Integer notApprovedNum = mapper.selectCount(queryWrapper5);
        goodStatisticsVo.setNotApprovedNum(notApprovedNum);

        PmGoodsPo goodsPo6 = new PmGoodsPo();
        goodsPo6.setStoreId(storeId);
        goodsPo6.setVerifyStatus(VerifyStatusEnum.CHECKED.getId());
        QueryWrapper<PmGoodsPo> queryWrapper6 = new QueryWrapper<>(goodsPo6);
        //审核通过商品数量
        Integer checkedNum = mapper.selectCount(queryWrapper6);
        goodStatisticsVo.setCheckedNum(checkedNum);

        return goodStatisticsVo;
    }

    /**
     * 获取分类下的商品属性信息 typeList:商品类型；brandList:品牌；labelList:标签；platformServiceList:平台服务说明;
     * merchantServiceList:商家服务说明；paramList:商品参数；platformShipList:平台运费模版;merchantShipList:店铺运费模版
     * <p>
     * 类型 1->平台服务说明 2->商家服务说明 3->平台活动说明
     * 4->商品参数 5->商品标签 6->购买须知说明 7->商品规格 8->品牌管理 9->敏感词
     *
     * @param searchAttributesDto
     * @return
     */
    @Override
    public AttributeVo findAttributes(SearchAttributesDto searchAttributesDto) {

        //获取当前正在修改的商品所属的店铺 或者 正在修改商品的店铺
        Long storeId = null;
        //获取当前店铺/平台用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null != sysUserPo.getStoreId()) {
            //店铺用户
            storeId = sysUserPo.getStoreId();
        } else {
            //平台用户  根据当前编辑的商品获取
            if(null != searchAttributesDto.getGoodsId() && searchAttributesDto.getGoodsId() != 0L) {
                PmGoodsPo pmGoodsPo = mapper.selectById(searchAttributesDto.getGoodsId());
                if (null != pmGoodsPo) {
                    storeId = pmGoodsPo.getStoreId();
                } else {
                    throw new ServiceException(ResultCode.PARAM_ERROR, "商品记录不存在");
                }
            }
        }
        AttributeVo attributeVo = new AttributeVo();

        List<String> typeList = Lists.newArrayList();
        List<BaseVo> brandList = Lists.newArrayList();
        List<BaseVo> labelList = Lists.newArrayList();
        List<BaseVo> platformServiceList = Lists.newArrayList();
        List<BaseVo> merchantServiceList = Lists.newArrayList();
        List<BaseVo> paramList = Lists.newArrayList();
        List<BaseVo> platformShipList = Lists.newArrayList();
        List<BaseVo> merchantShipList = Lists.newArrayList();

        typeList = Arrays.stream(GoodsTypeEnum.values()).map(a -> a.getName()).collect(Collectors.toList());
        brandList = goodsAttributeMapper.findAttByType(GoodsAttributeTypeEnum.BRAND.getId(), storeId);
        labelList = goodsAttributeMapper.findAttByType(GoodsAttributeTypeEnum.LABEL.getId(), storeId);
        platformServiceList = goodsAttributeMapper.findAttByTypeAndCat(searchAttributesDto.getCategoryId(),
                GoodsAttributeTypeEnum.PLATFORM_SERVICE.getId());
        //商家服务说明
        merchantServiceList = goodsAttributeMapper.findAttByType(GoodsAttributeTypeEnum.MERCHANT_SERVICE.getId(), storeId);
        paramList = goodsAttributeMapper.findAttByTypeAndCat(searchAttributesDto.getCategoryId(),
                GoodsAttributeTypeEnum.GOODS_PARAM.getId());
        platformShipList = shippingTemplateMapper.findByType(GoodsShipTemplateEnum.PLATFORM_SHIP.getId(), storeId);
        merchantShipList = shippingTemplateMapper.findByType(GoodsShipTemplateEnum.MERCHANT_SHIP.getId(), storeId);

        PmGoodsCategoryPo goodsCategoryPo3 = goodsCategoryMapper.selectById(searchAttributesDto.getCategoryId());
        String level3 = goodsCategoryPo3.getName();
        PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo3.getParentId());
        String level2 = goodsCategoryPo2.getName();
        String level1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();

        String categoryName = level1 + "/" + level2 + "/" + level3;

        attributeVo.setBrandList(brandList);
        attributeVo.setTypeList(typeList);
        attributeVo.setLabelList(labelList);
        attributeVo.setPlatformServiceList(platformServiceList);
        attributeVo.setMerchantServiceList(merchantServiceList);
        attributeVo.setParamList(paramList);
        attributeVo.setPlatformShipList(platformShipList);
        attributeVo.setMerchantShipList(merchantShipList);
        attributeVo.setCategoryName(categoryName);

        return attributeVo;
    }

    /**
     * 库存模板根据商品类型查询店铺商品信息
     *
     * @param selectStockTemplateGoodsDto
     * @return
     */
    @Override
    public PageInfo<StockTemplateGoodsInfoVo > selectGoodsByType(SelectStockTemplateGoodsDto selectStockTemplateGoodsDto) {
        Long storeId = securityUtil.getCurrUser().getStoreId();
        selectStockTemplateGoodsDto.setStoreId(storeId);

        Integer pageNo = selectStockTemplateGoodsDto.getPageNo() == null ? defaultPageNo : selectStockTemplateGoodsDto.getPageNo();
        Integer pageSize = selectStockTemplateGoodsDto.getPageSize() == null ? defaultPageSize : selectStockTemplateGoodsDto.getPageSize();

        if(selectStockTemplateGoodsDto.getType().equals(StoreGoodsTypeEnum.DISTRIBUTION_GOODS.getId())) {
            // 分配商品
            return PageHelper.startPage(pageNo,pageSize).
                    doSelectPageInfo(()->mapper.selectDistributionGoods(selectStockTemplateGoodsDto));
        } else if (selectStockTemplateGoodsDto.getType().equals(StoreGoodsTypeEnum.OWN_GOODS.getId())) {
            // 自有商品
            return PageHelper.startPage(pageNo,pageSize).
                    doSelectPageInfo(()->mapper.selectOwnGoods(selectStockTemplateGoodsDto));
        } else {
            return null;
        }
    }

    /**
     * 库存模板id获取询商品信息
     *
     * @param baseSearchDto
     */
    @Override
    public GoodsStockTemplateVo searchGoodsInfoByTemplateId(BaseSearchDto baseSearchDto) {
        if(null == baseSearchDto.getId()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "库存模板id不能为空") ;
        }
        PmGoodsVirtualStockTemplatePo pmGoodsVirtualStockTemplate = pmGoodsVirtualStockTemplateMapper.selectById(baseSearchDto.getId());
        if(null == pmGoodsVirtualStockTemplate) {
            throw new ServiceException(ResultCode.NO_EXISTS, "库存模板不存在") ;
        }
        GoodsStockTemplateVo goodsStockTemplateVo = new GoodsStockTemplateVo();
        BeanUtils.copyProperties(pmGoodsVirtualStockTemplate, goodsStockTemplateVo);
        goodsStockTemplateVo.setTypeName(StoreGoodsTypeEnum.getById(goodsStockTemplateVo.getType()).getName());

        Integer pageNo = baseSearchDto.getPageNo() == null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize() == null ? defaultPageSize : baseSearchDto.getPageSize();
        PageInfo<StockTemplateGoodsInfoVo> stockTemplateGoodsInfoVoPageInfo = new PageInfo<>();

        //获取跟库存模板关联的商品信息
        stockTemplateGoodsInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> mapper.searchGoodsInfoByTemplateId(baseSearchDto.getId()));
        stockTemplateGoodsInfoVoPageInfo.getList().forEach(a -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("goods_id", a.getGoodsId());
            map.put("store_id", pmGoodsVirtualStockTemplate.getStoreId());
            //获取库存模板关联的商品对应的库存
            int stock = pmGoodsVirtualStockMapper.selectByMap(map).stream().map(PmGoodsVirtualStockPo::getStockNum).mapToInt(c -> c).sum();
            a.setStock(stock);
        });
        goodsStockTemplateVo.setStockTemplateGoodsInfoPageInfo(stockTemplateGoodsInfoVoPageInfo);
        return goodsStockTemplateVo;
    }

    @Override
    public PageInfo<ExcelGoodVo> searchExcelGoods(SearchExcelDto searchExcelDto) {
        SysUserPo currentUser=securityUtil.getCurrUser();
        searchExcelDto.setStoreId(currentUser.getStoreId());
        Integer pageNo = searchExcelDto.getPageNo() == null ? defaultPageNo : searchExcelDto.getPageNo();
        Integer pageSize = searchExcelDto.getPageSize() == null ? defaultPageSize : searchExcelDto.getPageSize();
        PageInfo<ExcelGoodVo> excelGoodVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchExcelGoods(searchExcelDto));
        return excelGoodVoPageInfo;
    }

    /**
     * 批量删除商品
     *
     * @param ids
     * @return
     */
    @Override
    public void delGoodsByIds(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        idList.forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.NO_EXISTS,"数据库不存在该商品");
            }
        });
        idList.forEach(b->{
            //(商品有参数值前提下)删除参数值与商品关联表的信息(pm_goods_rel_attribute_value_good)、商品关联的参数值(属性值表)
            Map<String,Object> query = new HashMap<>();
            query.put("goods_id",b);
            query.put("del_flag",false);
            List<PmGoodsRelAttributeValueGoodPo> relAttributeValueGoodPos =relAttributeValueGoodMapper.selectByMap(query);
            if (relAttributeValueGoodPos!=null && relAttributeValueGoodPos.size()!=0){
                //1、删除商品关联的参数值(属性值表)
                List<Long> valueIds = relAttributeValueGoodPos.stream().map(c->c.getGoodsAttributeValueId()).collect(Collectors.toList());
                goodsAttributeMapper.deleteBatchIds(valueIds);
                //2、删除参数值与商品关联表的信息
                relAttributeValueGoodMapper.deleteByMap(query);

            }
            //3、删除商品与属性关联表
            Map<String,Object> query3 = new HashMap<>();
            query3.put("goods_good_id",b);
            query3.put("del_flag",false);
            goodsRelAttributeGoodMapper.deleteByMap(query3);
            //4、删除sku与规格值关联表
            List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(query);
            if (goodsSkuPos.size()!=0 && goodsSkuPos!=null){
                goodsSkuPos.forEach(d->{
                    Map<String,Object> query2 = Maps.newHashMap();
                    query2.put("goods_sku_id",d.getId());
                    query2.put("del_flag",false);
                    goodsRelAttributeValueSkuMapper.deleteByMap(query2);
                });
            }
            //5、删除对应的sku
            goodsSkuMapper.deleteByMap(query);
            //6、删除商品
            mapper.deleteById(b);
        });

    }


    /**
     *
     * 获取店铺下商品列表
     * 店铺id
     * 一级分类id
     * 商品列表： 1.店铺全部商品； 2.店铺推荐商品； 3.店铺新品列表； 4.店铺活动商品； 5.明星单品列表（按时间降序）； 6.最新推荐（按排序数值降序）
     * 排序内容;  1.综合排序  2.销量排序  3.价格排序
     * 排序方式   1.降序   2.升序
     *
     * @return
     */
    @Override
    public PageInfo<GoodsBaseInfoVo> searchStoreGoodsPaging(SearchStoreGoodsDto searchStoreGoodsDto) {
        Integer pageNo = searchStoreGoodsDto.getPageNo()==null ? defaultPageNo : searchStoreGoodsDto.getPageNo();
        Integer pageSize = searchStoreGoodsDto.getPageSize()==null ? defaultPageSize : searchStoreGoodsDto.getPageSize();

        if(null == searchStoreGoodsDto.getSortFile()) {
            //默认综合排序
            searchStoreGoodsDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchStoreGoodsDto.getSortWay()) {
            //默认降序
            searchStoreGoodsDto.setSortWay(SortWayEnum.DESC);
        }
        if(null == searchStoreGoodsDto.getGoodsType()) {
            //默认全部商品列表
            searchStoreGoodsDto.setGoodsType(StoreGoodsListTypeEnum.ALL_LIST.getId());
        } else if(searchStoreGoodsDto.getGoodsType().equals(StoreGoodsListTypeEnum.NEW_LIST)) {
            //获取系统基本设置
            BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
            searchStoreGoodsDto.setNewGoodsDays(basicSettingPo.getNewProductDay());
        }
        PageInfo<GoodsBaseInfoVo> goodsBaseInfoVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchInfoBasePaging(searchStoreGoodsDto));
        return goodsBaseInfoVoPageInfo;
    }

    /**
     * 获取店铺下商品列表
     * @param searchStoreGoodsDto
     * @return
     */
    @Override
    public PageInfo<SearchGoodsBaseListVo> searchGoodsBaseList(SearchStoreGoodsDto searchStoreGoodsDto) {

        if(searchStoreGoodsDto.getGoodsType().equals(StoreGoodsListTypeEnum.CATEGORY_LIST.getId())) {
            //根据商品分类获取商品列表
            if(null == searchStoreGoodsDto.getGoodsCategoryId()) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "goodsCategoryId不能为空");
            }
        }
        if(searchStoreGoodsDto.getGoodsType().equals(StoreGoodsListTypeEnum.SEARCH_LIST.getId())) {
            //根据商品分类获取商品列表
            if(null == searchStoreGoodsDto.getGoodsName()) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "goodsName不能为空");
            }
        }

        UmUserPo user = securityUtil.getAppCurrUser();
        Long memberLevelId = user.getMemberLevelId();

        Integer pageNo = searchStoreGoodsDto.getPageNo()==null ? defaultPageNo : searchStoreGoodsDto.getPageNo();
        Integer pageSize = searchStoreGoodsDto.getPageSize()==null ? defaultPageSize : searchStoreGoodsDto.getPageSize();

        if(null == searchStoreGoodsDto.getSortFile()) {
            //默认综合排序
            searchStoreGoodsDto.setSortFile(SortFileEnum.COMPREHENSIVE_SORT);
        }
        if(null == searchStoreGoodsDto.getSortWay()) {
            //默认降序
            searchStoreGoodsDto.setSortWay(SortWayEnum.DESC);
        }
        if(null == searchStoreGoodsDto.getGoodsType()) {
            //默认全部商品列表
            searchStoreGoodsDto.setGoodsType(StoreGoodsListTypeEnum.ALL_LIST.getId());
        }
        if(searchStoreGoodsDto.getGoodsType().equals(StoreGoodsListTypeEnum.NEW_LIST.getId())) {
            //获取系统基本设置  新品的评判标准  上架几天内为新品
            BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
            searchStoreGoodsDto.setNewGoodsDays(basicSettingPo.getNewProductDay());
        }
        PageInfo<SearchGoodsBaseListVo> searchGoodsBaseListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchStoreGoodsBaseList(searchStoreGoodsDto));

        searchGoodsBaseListVoPageInfo.getList().forEach(a->{

            //获取最高返券值
            List<Double> rewardShopTickes = Lists.newArrayList();
            List<RewardShopTicketBo> rewardShopTicketBos = goodsSkuMapper.findRewardShopTicketInfos(a.getGoodsId());
            rewardShopTicketBos.forEach(b->{
                //商品活动百分比
                b.setActivityCostRate(a.getActivityCostRate());
                //让利成本比例
                b.setProfitsRate(a.getProfitsRate());
                //会员等级比例
                BigDecimal purchasePresent = memberLevelMapper.selectById(memberLevelId).getPurchasePresent();
                b.setPurchasePresent(purchasePresent);
                //购物券比例
                BigDecimal moneyToShopTicket = basicSettingMapper.selectList(null).get(0).getMoneyToShopTicket();
                b.setMoneyToShopTicket(moneyToShopTicket);
                BigDecimal rewardShopTicket= b.getRewardShopTicket();
                rewardShopTickes.add(rewardShopTicket.doubleValue());
            });
            //获取RewardShopTickes列表最大返券值
            Double maxRewardShopTicket = rewardShopTickes.stream().mapToDouble((x)->x).summaryStatistics().getMax();
            a.setMaxRewardShopTicket(BigDecimal.valueOf(maxRewardShopTicket));
        });
        return searchGoodsBaseListVoPageInfo;
    }

    /**
     * 条件查询需要被关联商品信息
     *
     * @param associationGoodsDto
     * @return
     */
    @Override
    public PageInfo<BaseVo> searchAssociationGoods (AssociationGoodsDto associationGoodsDto) {
        //获取店铺下的所有商品信息
        Long storeId = mapper.selectById (associationGoodsDto.getGoodsId ()).getStoreId ();
        Map<String,Object> query = Maps.newHashMap ();
        query.put ("del_flag" , 0);
        query.put("store_id",storeId);
        List<PmGoodsPo> goodsPoList = goodsMapper.selectByMap (query);
        if (goodsPoList==null && goodsPoList.size ()==0) {
            return null;
        }
        //获取该商品已经关联的商品
        List<PmAssociationGoodsPo> associationGoodsPoList = associationGoodsMapper.selectList (new QueryWrapper<PmAssociationGoodsPo> ().lambda ().
                eq(PmAssociationGoodsPo::getGoodsId,associationGoodsDto.getGoodsId ()).eq(PmAssociationGoodsPo::getDelFlag,false));
//        if (associationGoodsPoList.size ()!=0 && associationGoodsPoList!=null){
            List<Long> associatedIds = associationGoodsPoList.stream().map (b->b.getAssociatedGoodsId ()).collect(Collectors.toList());
        //排除掉已经关联的商品和当前商品
        List<Long> idList = goodsPoList.stream().filter (d->!associatedIds.contains (d.getId ()) && !d.getId ().equals (associationGoodsDto.getGoodsId())).map (e->e.getId ()).collect(Collectors.toList());
        if (idList.size ()==0 ){
            return new PageInfo<>();
        }
        Integer pageNo = associationGoodsDto.getPageNo() == null ? defaultPageNo : associationGoodsDto.getPageNo();
        Integer pageSize = associationGoodsDto.getPageSize() == null ? defaultPageSize : associationGoodsDto.getPageSize();
        PageInfo<BaseVo> goodsVos = new PageInfo<>();
        associationGoodsDto.setIdList (idList);
        goodsVos = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> mapper.selectIds (associationGoodsDto));

        return goodsVos;
    }

    /**
     * 查询已被关联的商品信息
     * @param associationGoodsDto
     * @return
     */
    @Override
    public PageInfo<AssociationGoodsVo> searchAssociatedGoods (AssociationGoodsDto associationGoodsDto) {

        Integer pageNo = associationGoodsDto.getPageNo() == null ? defaultPageNo : associationGoodsDto.getPageNo();
        Integer pageSize = associationGoodsDto.getPageSize() == null ? defaultPageSize : associationGoodsDto.getPageSize();
        PageInfo<AssociationGoodsVo> associationGoodsVoPageInfo = new PageInfo<>();
        associationGoodsVoPageInfo = PageHelper.startPage(pageNo, pageSize/*, defaultSoft*/)
                .doSelectPageInfo(() -> associationGoodsMapper.searchAssociatedGoods(associationGoodsDto));
        if (associationGoodsVoPageInfo.getList ().size ()==0 && associationGoodsVoPageInfo.getList ()==null){
            return new PageInfo<> ();
        }
        associationGoodsVoPageInfo.getList ().forEach (a->{
            PmGoodsCategoryPo goodsCategoryPo3 = goodsCategoryMapper.selectById(mapper.selectById (a.getGoodsId ()).getGoodsCategoryId ());
            String level3 = goodsCategoryPo3.getName();
            PmGoodsCategoryPo goodsCategoryPo2 = goodsCategoryMapper.selectById(goodsCategoryPo3.getParentId());
            String level2 = goodsCategoryPo2.getName();
            String level1 = goodsCategoryMapper.selectById(goodsCategoryPo2.getParentId()).getName();
            String categoryName = level1 + "/" + level2 + "/" + level3;
            a.setCategory (categoryName);
            BigDecimal lowestSellPrice = goodsSkuMapper.getLowestPrice (a.getGoodsId ());
            BigDecimal highestSellPrice = goodsSkuMapper.getHighestPrice (a.getGoodsId ());
            String sellPrice ="";
            if (lowestSellPrice.equals (highestSellPrice)){
                sellPrice=lowestSellPrice.toString ();
            }else{
                sellPrice = lowestSellPrice.toString ()+"-"+highestSellPrice;
            }
            a.setSellPrice (sellPrice);
        });

        return associationGoodsVoPageInfo;
    }

    /**
     * 批量删除关联商品
     *
     * @param ids
     * @return
     */
    @Override
    public void delAssociationsByIds (Long[] ids) {
        associationGoodsMapper.deleteBatchIds (Arrays.asList (ids));
    }

    /**
     * 分享商品
     *
     * @param shareDto
     * @return
     */
    @Override
    public void share(ShareDto shareDto) {

        ShareTypeEnum shareTypeEnum = shareDto.getShareType();
        UmUserPo userPo = securityUtil.getAppCurrUser();
        if(userPo == null){
            throw new ServiceException(ResultCode.FAIL,"您不是App用户");
        }

        if (shareTypeEnum == null){
            throw new ServiceException(ResultCode.NO_EXISTS,String.format("shareType所传的值在枚举类中不存在！"));
        }

        switch (shareTypeEnum) {
            case GOODS:
                PmGoodsPo goodsPo = mapper.selectById((shareDto.getShareId()));
                if (goodsPo == null){
                    throw new ServiceException(ResultCode.NO_EXISTS,"商品不存在");
                }
                PmGoodsForwardPo goodsForwardPo = goodsForwardMapper.selectForUpdate(shareDto.getShareId(), userPo.getId());
                if (goodsForwardPo == null) {
                    //未转发过
                    goodsForwardPo = new PmGoodsForwardPo();
                    goodsForwardPo.setGoodsId(shareDto.getShareId());
                    goodsForwardPo.setCreateBy(userPo.getId().toString());
                    goodsForwardPo.setUserId(userPo.getId());
                    //新增转发记录
                    goodsForwardMapper.insert(goodsForwardPo);
                    //转发量+1
                    mapper.shareGoods(shareDto);
                }
                break;

            case INFORMATION:
//                mmInformationMapper.shareInformation(shareDto);
                MmInformationPo mmInformationPo = mmInformationMapper.selectById(shareDto.getShareId());
                if(null == mmInformationPo){
                    throw new ServiceException(ResultCode.NO_EXISTS,"资讯不存在");
                }
                //查询是否转发过
                MmInformationForwardPo mmInformationForwardPo =
                        mmInformationForwardMapper.selectForUpdate(shareDto.getShareId(), userPo.getId());
                if(null == mmInformationForwardPo) {
                    //未转发过
                    mmInformationForwardPo = new MmInformationForwardPo();
                    mmInformationForwardPo.setInfoId(shareDto.getShareId());
                    mmInformationForwardPo.setCreateBy(userPo.getId().toString());
                    mmInformationForwardPo.setUserId(userPo.getId());
                    //新增转发记录
                    mmInformationForwardMapper.insert(mmInformationForwardPo);
                    //转发量+1
                    mmInformationMapper.shareInformation(shareDto);
                }
                break;
        }

        userPo.setShareNum(userPo.getShareNum()+1);

        umUserMapper.updateById(userPo);

    }

    /**
     * @Author yeJH
     * @Date 2019/11/17 21:30
     * @Description 获取分享商品/资讯/注册页面信息
     *
     * @Update yeJH
     *
     * @param  shareDto
     * @return com.chauncy.data.vo.app.goods.ShareDetailVo
     **/
    @Override
    public ShareDetailVo getShareDetail(ShareDto shareDto, UmUserPo umUserPo) {
        if(null == shareDto.getShareType()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "分享类型参数错误");
        }
        ShareDetailVo shareDetailVo = new ShareDetailVo();
        //分享标题
        shareDetailVo.setShareTitle(ServiceConstant.SHARE_TITLE);
        switch (shareDto.getShareType()) {
            case GOODS:
                if(null == shareDto.getShareId()) {
                    throw new ServiceException(ResultCode.PARAM_ERROR, "商品id参数不能为空");
                }
                ShareDetailVo temp = mapper.getShareGoodsDetail(shareDto.getShareId());
                if(null == temp) {
                    throw new ServiceException(ResultCode.PARAM_ERROR, "商品不存在");
                }
                BeanUtils.copyProperties(temp,shareDetailVo);
                shareDetailVo.setShareTitle(ServiceConstant.SHARE_TITLE);
                if(shareDetailVo.getMaxPrice().compareTo(shareDetailVo.getMinPrice()) == 0) {
                    shareDetailVo.setMaxPrice(BigDecimal.ZERO);
                }
                //分享描述
                shareDetailVo.setShareDescribe(temp.getGoodsName());
                //分享图片
                shareDetailVo.setSharePicture(temp.getGoodsPicture());
                //分享链接
                shareDetailVo.setShareUrl(MessageFormat.format(ServiceConstant.SHARE_URL_GOODS, shareAddr,
                        String.valueOf(shareDto.getShareId()), String.valueOf(umUserPo.getInviteCode())));
                break;
            case INFORMATION:
                if(null == shareDto.getShareId()) {
                    throw new ServiceException(ResultCode.PARAM_ERROR, "资讯id参数不能为空");
                }
                MmInformationPo mmInformationPo = mmInformationMapper.selectById(shareDto.getShareId());
                if(null == mmInformationPo) {
                    throw new ServiceException(ResultCode.PARAM_ERROR, "资讯不存在");
                }
                //分享描述
                shareDetailVo.setShareDescribe(mmInformationPo.getTitle());
                //分享图片
                List<String> images = Splitter.on(";").splitToList(mmInformationPo.getCoverImage());
                String sharePicture = (null != images && images.size() > 0) ?
                        images.get(0) : MessageFormat.format(ServiceConstant.ICON_PATH, "congya");
                shareDetailVo.setSharePicture(sharePicture);
                //分享链接
                shareDetailVo.setShareUrl(MessageFormat.format(ServiceConstant.SHARE_URL_INFO, shareAddr,
                        String.valueOf(shareDto.getShareId()), String.valueOf(umUserPo.getInviteCode())));
                break;
            case REGISTER:
                //用户头像
                shareDetailVo.setUserPhoto(umUserPo.getPhoto());
                //用户昵称
                shareDetailVo.setNickName(umUserPo.getName());
                //用户邀请码
                shareDetailVo.setInviteCode(umUserPo.getInviteCode());
                //分享描述
                shareDetailVo.setShareDescribe(ServiceConstant.SHARE_DESCRIBE);
                //分享图片
                String photo = null != umUserPo.getPhoto() ?
                        umUserPo.getPhoto() : MessageFormat.format(ServiceConstant.ICON_PATH, "congya");
                shareDetailVo.setSharePicture(photo);
                //分享链接
                shareDetailVo.setShareUrl(MessageFormat.format(ServiceConstant.SHARE_URL_REGISTER, shareAddr,
                        String.valueOf(umUserPo.getInviteCode())));
                break;
        }
        return shareDetailVo;
    }

}
