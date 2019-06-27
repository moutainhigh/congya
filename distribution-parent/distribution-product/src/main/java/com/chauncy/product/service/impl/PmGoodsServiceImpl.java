package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.goods.GoodsShipTemplateEnum;
import com.chauncy.common.enums.goods.GoodsTypeEnum;
import com.chauncy.common.enums.common.VerifyStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.dto.manage.good.update.RejectGoodsDto;
import com.chauncy.data.dto.supplier.good.add.*;
import com.chauncy.data.dto.supplier.good.select.FindStandardDto;
import com.chauncy.data.dto.supplier.good.select.SearchGoodInfosDto;
import com.chauncy.data.dto.supplier.good.select.SelectAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdatePublishStatusDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.supplier.*;
import com.chauncy.product.service.IPmGoodsRelAttributeGoodService;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SecurityUtil securityUtil;

    @Autowired
    private PmGoodsAttributeValueMapper goodsAttributeValueMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper goodsRelAttributeValueSkuMapper;

    @Autowired
    private PmGoodsRelAttributeValueGoodMapper goodsRelAttributeValueGoodMapper;

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
    private PmGoodsRelAttributeCategoryMapper goodsRelAttributeCategoryMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    private static int defaultPageSize = 10;

    private static int defaultPageNo = 1;

    private static String defaultSoft = "sort desc";

    @Override
    public List<String> findGoodsType() {
        List<String> types = Arrays.stream(GoodsTypeEnum.values()).map(a -> a.getName()).collect(Collectors.toList());
        return types;
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
    public void addBase(AddGoodBaseDto addGoodBaseDto) {

        LocalDateTime date = LocalDateTime.now();
        PmGoodsPo goodsPo = new PmGoodsPo();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        String userId = securityUtil.getCurrUser().getId();
        //判断用户是属于哪个系统,商家端或者是平台后台
        Long storeId =sysUserMapper.selectById(userId).getStoreId();
        //商家端
        if (storeId!=null){
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
        }
        goodsPo.setCreateBy(user);
        goodsPo.setId(null);
        goodsPo.setShippingTemplateId(addGoodBaseDto.getShippingId());
//        goodsPo.setCreateTime(date);
        //复制Dto对象到po
        BeanUtils.copyProperties(addGoodBaseDto, goodsPo);
        //先保存商品不关联信息
        mapper.insert(goodsPo);

        //处理商品属性和商品关联信息
        addAttribute(addGoodBaseDto, user, goodsPo);

        //处理商品参数属性
        addParam(addGoodBaseDto, user, goodsPo);

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
        attributeIds.forEach(x -> {
            List<BaseVo> labels = Lists.newArrayList();
            List<BaseVo> platformServices = Lists.newArrayList();
            List<BaseVo> merchantServices = Lists.newArrayList();
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
        if (storeId!=null){
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
        }
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
        for(Long labelId : updateGoodBaseDto.getLabelId()) {
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
                    standardValueAndStatusVo.setAttributeValueId(b.getValue());
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
     *
     * 商品与sku为1对多
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<FindSkuAttributeVo> findSkuAttribute(Long goodsId) {

        List<FindSkuAttributeVo> findSkuAttributeVos = Lists.newArrayList();
        //判断该商品是否存在
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo==null){
            throw new ServiceException(ResultCode.NO_EXISTS,"该商品不存在！");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("goods_id",goodsId);
        List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(map);
        if (goodsSkuPos==null && goodsSkuPos.size()==0){
            return null;
        }
        //循环获取sku信息
        goodsSkuPos.forEach(x->{
            //获取除规格信息外的其他信息
            FindSkuAttributeVo findSkuAttributeVo = new FindSkuAttributeVo();
            BeanUtils.copyProperties(x,findSkuAttributeVo);
            findSkuAttributeVo.setSkuId(x.getId());
            //获取每条sku对应的规格信息，规格值与sku多对多关系
            Map<String,Object> relMap = new HashMap<>();
            relMap.put("goods_sku_id",x.getId());
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(relMap);
            //根据skuId获取属性值ID列表
            List<Long> valueIds = relAttributeValueSkuPos.stream().map(a->a.getGoodsAttributeValueId()).collect(Collectors.toList());
            //根据属性值id查找属性值表，获取属性值和属性ID
//            List<AttributeInfos> attributeInfos = Lists.newArrayList();
            List<List<Map<String,String>>> skuList = Lists.newArrayList();
            valueIds.forEach(b->{
                //属性信息（map<属性ID，属性名称>、map<属性值ID,属性值>）
//                AttributeInfos attributeInfo = new AttributeInfos();
                List<Map<String,String>> listMap = Lists.newArrayList();
                //map<属性值ID,属性值>
                Map<String,String> valueMap = new HashMap<>();
                PmGoodsAttributeValuePo valuePo = goodsAttributeValueMapper.selectById(b);
                if (valuePo==null){
                    throw new ServiceException(ResultCode.NO_EXISTS,"数据库不存在对应的属性值",b);
                }
                String value = valuePo.getValue();
                valueMap.put("valueId",b.toString());
                valueMap.put("valueName",value);
                //map<属性ID,属性名>
                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
                String name = goodsAttributeMapper.selectById(attributeId).getName();
                Map<String,String> attributeMap = new HashMap<>();
                attributeMap.put("attributeId",attributeId.toString());
                attributeMap.put("attributeName",name);
                listMap.add(attributeMap);
                listMap.add(valueMap);
//                attributeInfo.setAttributeInfos(listMap);

//                skuList.add(attributeInfo);

                skuList.add(listMap);

            });

//            findSkuAttributeVo.setSkuList(attributeInfos);

            findSkuAttributeVo.setSkuList(skuList);

            findSkuAttributeVos.add(findSkuAttributeVo);
        });

        return findSkuAttributeVos;
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
        int stock = 0;
        String user = securityUtil.getCurrUser().getUsername();
        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        if (storeId!=null){
            PmGoodsPo goodsPo = new PmGoodsPo();
            goodsPo.setId(addOrUpdateSkuAttributeDto.getGoodsId());
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
            mapper.updateById(goodsPo);
        }
        //通过goodsID商品ID获取sku信息
        Map<String,Object> skuMaps = new HashMap<>();
        skuMaps.put("goods_id",addOrUpdateSkuAttributeDto.getGoodsId());
        List<PmGoodsSkuPo> skus = goodsSkuMapper.selectByMap(skuMaps);
        //如果skuPos为空，则新增sku
        if (skus.size()==0 && skus==null) {
            addSkus(addOrUpdateSkuAttributeDto, stock, user);
        }
        //如果skuPos不为空，则更新sku
        else{
            //根据删除所有sku关联关系，pm_goods_sku删除该商品的sku,pm_goods_rel_attribute_value_sku获取该商品
            //下对应的自定义属性值pm_goods_attribute_value并删除，删除sku与属性值的关联关系
            //先获取自定义属性值并删除
            List<Long> skuIds = skus.stream().map(a->a.getId()).collect(Collectors.toList());
            //根据skuId获取信息
            skuIds.forEach(id->{
                Map<String,Object> valueMap = new HashMap<>();
                valueMap.put("goods_sku_id",id);
                List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(valueMap);
                //获取属性值ID集合
                List<Long> valueIds = relAttributeValueSkuPos.stream().map(a->a.getGoodsAttributeValueId()).collect(Collectors.toList());
                List<PmGoodsAttributeValuePo> goodsAttributeValuePos = goodsAttributeValueMapper.selectBatchIds(valueIds);
                //获取自定义属性值并删除
                goodsAttributeValuePos.stream().filter(a->a.getIsCustom()).forEach(b->{
                    goodsAttributeValueMapper.deleteById(b.getId());
                });
                //删除sku与属性值关联表的信息
                goodsRelAttributeValueSkuMapper.deleteByMap(valueMap);
                //最后删除该条sku
                goodsSkuMapper.deleteById(id);
            });

            /**
             * 重新添加sku相关信息
             */
            addSkus(addOrUpdateSkuAttributeDto, stock, user);
        }

    }

    /**
     * 添加sku列表
     *
     * @param addOrUpdateSkuAttributeDto
     * @param stock
     * @param user
     */
    private void addSkus(AddOrUpdateSkuAttributeDto addOrUpdateSkuAttributeDto, int stock, String user) {
        for (AddSkuAttributeDto addSkuAttributeDto : addOrUpdateSkuAttributeDto.getSkuAttributeDtos()) {
            //保存非关联信息到sku
            PmGoodsSkuPo goodsSkuPo = new PmGoodsSkuPo();
            BeanUtils.copyProperties(addSkuAttributeDto, goodsSkuPo);
            goodsSkuPo.setCreateBy(user);
            goodsSkuPo.setGoodsId(addOrUpdateSkuAttributeDto.getGoodsId());
            goodsSkuMapper.insert(goodsSkuPo);
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
                    //保存自定义规格值
                    PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
                    goodsAttributeValuePo.setProductAttributeId(x.getAttributeId());
                    goodsAttributeValuePo.setValue(x.getValue());
                    goodsAttributeValuePo.setCreateBy(user);
                    //设置类型为自定义属性值
                    goodsAttributeValuePo.setIsCustom(true);
                    goodsAttributeValueMapper.insert(goodsAttributeValuePo);

                    //根据具体规格下的新增的规格值获取规格值ID
                    PmGoodsAttributeValuePo goodsAttributeValuePo1 = new PmGoodsAttributeValuePo();
                    goodsAttributeValuePo1.setValue(x.getValue());
                    goodsAttributeValuePo1.setProductAttributeId(x.getAttributeId());
                    QueryWrapper<PmGoodsAttributeValuePo> queryWrapper = new QueryWrapper<>(goodsAttributeValuePo1);
                    Long valueId = goodsAttributeValueMapper.selectOne(queryWrapper).getId();

                    //保存商品对应的规格值到关联表
                    PmGoodsRelAttributeValueSkuPo goodsRelAttributeValueSkuPo = new PmGoodsRelAttributeValueSkuPo();
                    goodsRelAttributeValueSkuPo.setCreateBy(user);
                    goodsRelAttributeValueSkuPo.setGoodsAttributeValueId(valueId);
                    goodsRelAttributeValueSkuPo.setGoodsSkuId(goodsSkuPo.getId());
                    goodsRelAttributeValueSkuMapper.insert(goodsRelAttributeValueSkuPo);

                }
            });
            stock += addSkuAttributeDto.getStock();
            //更新商品总库存
            PmGoodsPo goodsPo = new PmGoodsPo();
            goodsPo.setStock(stock);
            goodsPo.setId(addOrUpdateSkuAttributeDto.getGoodsId());
            goodsMapper.updateById(goodsPo);
        }
    }


    /**
     * 根据商品ID查找财务的sku信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<FindSkuFinanceVo> findSkuFinance(Long goodsId) {

        List<FindSkuFinanceVo> findSkuFinanceVos = Lists.newArrayList();
        //判断该商品是否存在
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo==null){
            throw new ServiceException(ResultCode.NO_EXISTS,"该商品不存在！");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("goods_id",goodsId);
        List<PmGoodsSkuPo> goodsSkuPos = goodsSkuMapper.selectByMap(map);
        if (goodsSkuPos==null && goodsSkuPos.size()==0){
            return null;
        }
        //循环获取sku信息
        goodsSkuPos.forEach(x->{
            //获取除规格信息外的其他信息
            FindSkuFinanceVo findSkuFinanceVo = new FindSkuFinanceVo();
            BeanUtils.copyProperties(x,findSkuFinanceVo);
            findSkuFinanceVo.setSkuId(x.getId());
            //获取每条sku对应的规格信息，规格值与sku多对多关系
            Map<String,Object> relMap = new HashMap<>();
            relMap.put("goods_sku_id",x.getId());
            List<PmGoodsRelAttributeValueSkuPo> relAttributeValueSkuPos = goodsRelAttributeValueSkuMapper.selectByMap(relMap);
            //根据skuId获取属性值ID列表
            List<Long> valueIds = relAttributeValueSkuPos.stream().map(a->a.getGoodsAttributeValueId()).collect(Collectors.toList());
            //根据属性值id查找属性值表，获取属性值和属性ID
//            List<AttributeInfos> attributeInfos = Lists.newArrayList();
            List<List<Map<String,String>>> skuList = Lists.newArrayList();
            valueIds.forEach(b->{
                //属性信息（map<属性ID，属性名称>、map<属性值ID,属性值>）
//                AttributeInfos attributeInfo = new AttributeInfos();
                List<Map<String,String>> listMap = Lists.newArrayList();
                //map<属性值ID,属性值>
                Map<String,String> valueMap = new HashMap<>();
                PmGoodsAttributeValuePo valuePo = goodsAttributeValueMapper.selectById(b);
                if (valuePo==null){
                    throw new ServiceException(ResultCode.NO_EXISTS,"数据库不存在对应的属性值",b);
                }
                String value = valuePo.getValue();
                valueMap.put("valueId",b.toString());
                valueMap.put("valueName",value);
                //map<属性ID,属性名>
                Long attributeId = goodsAttributeValueMapper.selectById(b).getProductAttributeId();
                String name = goodsAttributeMapper.selectById(attributeId).getName();
                Map<String,String> attributeMap = new HashMap<>();
                attributeMap.put("attributeId",attributeId.toString());
                attributeMap.put("attributeName",name);
                listMap.add(attributeMap);
                listMap.add(valueMap);

                skuList.add(listMap);

            });

            findSkuFinanceVo.setSkuList(skuList);

            findSkuFinanceVos.add(findSkuFinanceVo);
        });

        return findSkuFinanceVos;
    }

    /**
     * 添加或更新财务信息
     *
     * @param updateSkuFinanceDtos
     * @return
     */
    @Override
    public void updateSkuFinance(List<UpdateSkuFinanceDto> updateSkuFinanceDtos) {

        Long skuId = updateSkuFinanceDtos.stream().map(a->a.getSkuId()).collect(Collectors.toList()).get(0);
        //根据skuId获取goodsId
        Long goodsId = goodsSkuMapper.selectById(skuId).getGoodsId();
        //判断用户属于平台后台或者商家端
        String userId = securityUtil.getCurrUser().getId();
        Long storeId = sysUserMapper.selectById(userId).getStoreId();
        //商家端执行更新操作将审核状态修改为未审核状态
        if (storeId!=null){
            PmGoodsPo goodsPo = new PmGoodsPo();
            goodsPo.setId(goodsId);
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
            mapper.updateById(goodsPo);
        }
        updateSkuFinanceDtos.forEach(updateSkuFinanceDto->{
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

        //获取所有的会员列表
        List<PmMemberLevelPo> memberLevelPos = memberLevelMapper.selectList(null);
        //存放会员信息列表
        List<MemberLevelInfos> memberLevelInfos = Lists.newArrayList();
        FindGoodOperationVo findGoodOperationVo = new FindGoodOperationVo();
        Map<String,Object> mapp = new HashMap<>();
        mapp.put("level",1);
        findGoodOperationVo.setLowestLevelId(memberLevelMapper.selectByMap(mapp).get(0).getId());
        PmGoodsPo goodsPo = mapper.selectById(goodsId);
        if (goodsPo==null){
            memberLevelPos.forEach(a->{
                MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
                memberLevelInfo.setIsInclude(false);
                memberLevelInfo.setMemberLevelId(a.getId());
                memberLevelInfo.setLevelName(a.getLevelName());
                memberLevelInfos.add(memberLevelInfo);
            });
            findGoodOperationVo.setMemberLevelInfos(memberLevelInfos);

        }
        else{
            String memberLevelName = memberLevelMapper.selectById(goodsPo.getMemberLevelId()).getLevelName();
            BeanUtils.copyProperties(goodsPo,findGoodOperationVo);
            findGoodOperationVo.setGoodsId(goodsId);
            findGoodOperationVo.setMemberLevelName(memberLevelName);
            //绑定的会员等级设置为true
            MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
            memberLevelInfo.setIsInclude(true);
            memberLevelInfo.setMemberLevelId(findGoodOperationVo.getMemberLevelId());
            memberLevelInfo.setLevelName(memberLevelName);
            memberLevelInfos.add(memberLevelInfo);
            //未绑定的置为false
            memberLevelPos.stream().filter(a->a.getId()!=(goodsPo.getMemberLevelId())).forEach(b->{
                MemberLevelInfos memberLevel = new MemberLevelInfos();
                memberLevel.setMemberLevelId(b.getId());
                memberLevel.setLevelName(b.getLevelName());
                memberLevel.setIsInclude(false);
                memberLevelInfos.add(memberLevel);
            });
            findGoodOperationVo.setMemberLevelInfos(memberLevelInfos);
        }
        return findGoodOperationVo;
        /*else {
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
                memberLevelInfo.setMemberLevelId(a.getValue());
                memberLevelInfo.setLevelName(a.getLevelName());
                memberLevelInfo.setIsInclude(true);
                memberLevelInfos.add(memberLevelInfo);
            });
            //在所有会员等级列表中排除与商品关联的会员等级列表，并将sInclude置为false
            memberLevelPos.stream().filter(item->!assignMemberIds.contains(item.getValue())).forEach(a->{
                MemberLevelInfos memberLevelInfo = new MemberLevelInfos();
                memberLevelInfo.setMemberLevelId(a.getValue());
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
        //商家端执行更新操作将审核状态修改为未审核状态
        if (storeId!=null){
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
        }
        BeanUtils.copyProperties(updateGoodOperationDto, goodsPo);
        mapper.updateById(goodsPo);

        //删除商品对应的会员关系

        //根据商品ID查找关联的会员等级购买权限,商品和会员等级多对多的关系
        /*Map<String, Object> map = new HashMap<>();
        map.put("goods_good_id", updateGoodOperationDto.getGoodsId());
        //获取商品限制的会员等级信息
        List<PmGoodsRelGoodsMemberLevelPo> relGoodsMemberLevelPos = goodsRelGoodsMemberLevelMapper.selectByMap(map);
        //获取和该商品绑定的所有会员等级ID
        List<Long> ids = relGoodsMemberLevelPos.stream().map(a->a.getValue()).collect(Collectors.toList());
        goodsRelGoodsMemberLevelMapper.deleteBatchIds(ids);

        //保存关联信息,限定会员关系
        PmGoodsRelGoodsMemberLevelPo relGoodsMemberLevelPo = new PmGoodsRelGoodsMemberLevelPo();
        for (Long value : updateGoodOperationDto.getMemberLevelIds()) {
            relGoodsMemberLevelPo.setCreateBy(user).setMemberLevelId(value).
                    setGoodsGoodId(updateGoodOperationDto.getGoodsId());
            goodsRelGoodsMemberLevelMapper.insert(relGoodsMemberLevelPo);
        }*/
        //如果是审核通过，则删除对应的驳回详情
        if (updateGoodOperationDto.getVerifyStatus()== VerifyStatusEnum.CHECKED.getId() ){
            PmGoodsPo goodsPo1 = new PmGoodsPo();
            goodsPo1.setContent(null);
            goodsPo1.setId(updateGoodOperationDto.getGoodsId());
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
        if (goodsPo==null){
            return null;
        }
        BeanUtils.copyProperties(goodsPo,findGoodSellerVo);
        findGoodSellerVo.setGoodsId(goodsId);

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
        if (storeId!=null){
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
        }
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
        if (storeId!=null){
            goodsPo.setVerifyStatus(VerifyStatusEnum.UNCHECKED.getId());
            mapper.updateById(goodsPo);
        }

        PmAssociationGoodsPo associationGoodsPo = new PmAssociationGoodsPo();
        BeanUtils.copyProperties(associationDto, associationGoodsPo);
        associationGoodsMapper.insert(associationGoodsPo);

    }

    /**
     * 平台审核商品不通过
     *
     * @param rejectGoodsDto
     */
    @Override
    public void rejectGoods(RejectGoodsDto rejectGoodsDto) {

        PmGoodsPo goodsPo = new PmGoodsPo();
        goodsPo.setId(rejectGoodsDto.getGoodsId());
        goodsPo.setContent(rejectGoodsDto.getContent());
        mapper.updateById(goodsPo);

    }

    @Override
    public boolean updateStock(long goodId, int number) {
       return mapper.updateStock(goodId,number)>0;
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
        goodsPos.forEach(a->{
            if (a.getVerifyStatus()!= VerifyStatusEnum.UNCHECKED.getId()){
                throw new ServiceException(ResultCode.FAIL,"该商品状态不是待提交状态",a.getName());
            }
            else{
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
        goodsPos.forEach(a->{
           //状态为非审核通过状态
           if (a.getVerifyStatus()!= VerifyStatusEnum.CHECKED.getId()){
               throw new ServiceException(ResultCode.FAIL,"该商品的状态还未通过审核",a.getName());
           }
           else{
               //修改上架状态
               a.setPublishStatus(updatePublishStatusDt.getPulishStatus());
               mapper.updateById(a);
           }
       });
    }

    /**
     * 修改应用标签
     *
     * @param updatePublishStatusDto
     */
    @Override
    public void updateStarStatus(UpdatePublishStatusDto updatePublishStatusDto) {

        List<PmGoodsPo> goodsPos = mapper.selectBatchIds(Arrays.asList(updatePublishStatusDto.getGoodIds()));
        goodsPos.forEach(a->{
            a.setStarStatus(updatePublishStatusDto.getPulishStatus());
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
        Integer pageNo = searchGoodInfosDto.getPageNo() == null ? defaultPageNo : searchGoodInfosDto.getPageNo();
        Integer pageSize = searchGoodInfosDto.getPageSize() == null ? defaultPageSize : searchGoodInfosDto.getPageSize();
        PageInfo<PmGoodsVo> goodsVos = new PageInfo<>();

        goodsVos = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                .doSelectPageInfo(() -> mapper.searchGoodsInfo(searchGoodInfosDto));
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
     *
     * 类型 1->平台服务说明 2->商家服务说明 3->平台活动说明
     * 4->商品参数 5->商品标签 6->购买须知说明 7->商品规格 8->品牌管理 9->敏感词
     *
     * @param categoryId
     * @return
     */
    @Override
    public AttributeVo findAttributes(Long categoryId) {

        Long storeId = securityUtil.getCurrUser().getStoreId();
        Map<String,Object> map = new HashMap<>();
        map.put("store_id",storeId);

        AttributeVo attributeVo = new AttributeVo();

        List<String> typeList = Arrays.stream(GoodsTypeEnum.values()).map(a -> a.getName()).collect(Collectors.toList());
        List<BaseVo> brandList = goodsAttributeMapper.findAttByType(GoodsAttributeTypeEnum.BRAND.getId());
        List<BaseVo> labelList = goodsAttributeMapper.findAttByType(GoodsAttributeTypeEnum.LABEL.getId());
        List<BaseVo> platformServiceList = goodsAttributeMapper.findAttByTypeAndCat(categoryId,GoodsAttributeTypeEnum.PLATFORM_SERVICE.getId());
        //商家服务说明
        List<BaseVo> merchantServiceList = goodsAttributeMapper.findAttByType(GoodsAttributeTypeEnum.MERCHANT_SERVICE.getId());
        List<BaseVo> paramList = goodsAttributeMapper.findAttByTypeAndCat(categoryId,GoodsAttributeTypeEnum.GOODS_PARAM.getId());
        List<BaseVo> platformShipList = shippingTemplateMapper.findByType(GoodsShipTemplateEnum.PLATFORM_SHIP.getId());
        List<BaseVo> merchantShipList = shippingTemplateMapper.findByType(GoodsShipTemplateEnum.MERCHANT_SHIP.getId());

        attributeVo.setBrandList(brandList);
        attributeVo.setTypeList(typeList);
        attributeVo.setLabelList(labelList);
        attributeVo.setPlatformServiceList(platformServiceList);
        attributeVo.setMerchantServiceList(merchantServiceList);
        attributeVo.setParamList(paramList);
        attributeVo.setPlatformShipList(platformShipList);
        attributeVo.setMerchantShipList(merchantShipList);

        return attributeVo;
    }

}
