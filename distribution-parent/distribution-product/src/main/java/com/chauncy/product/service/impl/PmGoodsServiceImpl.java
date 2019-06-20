package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.goods.GoodsShipTemplateEnum;
import com.chauncy.common.enums.goods.GoodsTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.dto.supplier.good.add.*;
import com.chauncy.data.dto.supplier.good.select.FindStandardDto;
import com.chauncy.data.dto.supplier.good.select.SelectAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.supplier.BaseGoodsVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.data.vo.supplier.PmGoodsParamVo;
import com.chauncy.data.vo.supplier.StandardValueAndStatusVo;
import com.chauncy.product.service.IPmGoodsRelAttributeGoodService;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import com.google.common.collect.Lists;
import org.jsoup.Connection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private PmGoodsRelGoodsMemberLevelMapper goodsRelGoodsMemberLevelMapper;

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
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        PmGoodsPo goodsPo = new PmGoodsPo();
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
                case LABEL:
                    BaseVo labelVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, labelVo);
                    baseGoodsVo.setLabelVo(labelVo);
                    break;
                case PLATFORM_SERVICE:
                    BaseVo platformServiceVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, platformServiceVo);
                    baseGoodsVo.setPlatformServiceVo(platformServiceVo);
                    break;
                case MERCHANT_SERVICE:
                    BaseVo merchantServiceVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo, merchantServiceVo);
                    baseGoodsVo.setMerchantServiceVo(merchantServiceVo);
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
        PmGoodsPo goodsPo = new PmGoodsPo();
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
        //保存标签
        PmGoodsRelAttributeGoodPo attributeGoodPo2 = new PmGoodsRelAttributeGoodPo();
        attributeGoodPo2.setGoodsAttributeId(updateGoodBaseDto.getLabelId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
        relAttributeGoodPoList.add(attributeGoodPo2);
        //保存服务说明
        PmGoodsRelAttributeGoodPo attributeGoodPo3 = new PmGoodsRelAttributeGoodPo();
        attributeGoodPo3.setGoodsAttributeId(updateGoodBaseDto.getServiceId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
        relAttributeGoodPoList.add(attributeGoodPo3);

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
     * 供应商添加商品时需要的规格值
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
     * @param addSkuAttributeDtos
     * @return
     */
    @Override
    public void addSkuAttribute(List<AddSkuAttributeDto> addSkuAttributeDtos) {
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();


        for (AddSkuAttributeDto addSkuAttributeDto : addSkuAttributeDtos) {
            //保存非关联信息到sku
            PmGoodsSkuPo goodsSkuPo = new PmGoodsSkuPo();
            BeanUtils.copyProperties(addSkuAttributeDto, goodsSkuPo);
            goodsSkuPo.setCreateBy(user);
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
                        throw new ServiceException(ResultCode.DUPLICATION, "添加失败，属性值不能重复");
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
        }

    }

    /**
     * 添加或更新财务信息
     *
     * @param updateSkuFinanceDto
     * @return
     */
    @Override
    public void updateSkuFinance(UpdateSkuFinanceDto updateSkuFinanceDto) {

        PmGoodsSkuPo goodsSkuPo = goodsSkuMapper.selectById(updateSkuFinanceDto.getSkuId());
        BeanUtils.copyProperties(updateSkuFinanceDto, goodsSkuPo);
        goodsSkuMapper.updateById(goodsSkuPo);

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

        PmGoodsPo goodsPo = mapper.selectById(updateGoodOperationDto.getGoodsId());
        BeanUtils.copyProperties(updateGoodOperationDto, goodsPo);
        mapper.updateById(goodsPo);

        //保存关联信息
        PmGoodsRelGoodsMemberLevelPo relGoodsMemberLevelPo = new PmGoodsRelGoodsMemberLevelPo();
        for (Long id : updateGoodOperationDto.getMemberLevelIds()) {
            relGoodsMemberLevelPo.setCreateBy(user).setMemberLevelId(id).
                    setGoodsGoodId(updateGoodOperationDto.getGoodsId());
            goodsRelGoodsMemberLevelMapper.insert(relGoodsMemberLevelPo);
        }
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

        PmAssociationGoodsPo associationGoodsPo = new PmAssociationGoodsPo();
        BeanUtils.copyProperties(associationDto, associationGoodsPo);
        associationGoodsMapper.insert(associationGoodsPo);

    }


}
