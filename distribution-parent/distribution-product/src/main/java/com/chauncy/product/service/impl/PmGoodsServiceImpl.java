package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.goods.GoodsShipTemplateEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddExtraValueDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.supplier.BaseGoodsVo;
import com.chauncy.data.vo.supplier.PmGoodsAttributeValueVo;
import com.chauncy.data.vo.supplier.PmGoodsParamVo;
import com.chauncy.product.service.IPmGoodsRelAttributeGoodService;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
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
        List<PmGoodsRelAttributeGoodPo> relAttributeGoodPoList = Lists.newArrayList();

        PmGoodsRelAttributeGoodPo attributeGoodPo1 = new PmGoodsRelAttributeGoodPo();
        attributeGoodPo1.setGoodsAttributeId(addGoodBaseDto.getBrandId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
        relAttributeGoodPoList.add(attributeGoodPo1);

        PmGoodsRelAttributeGoodPo attributeGoodPo2 = new PmGoodsRelAttributeGoodPo();
        attributeGoodPo2.setGoodsAttributeId(addGoodBaseDto.getLabelId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
        relAttributeGoodPoList.add(attributeGoodPo2);

        PmGoodsRelAttributeGoodPo attributeGoodPo3 = new PmGoodsRelAttributeGoodPo();
        attributeGoodPo3.setGoodsAttributeId(addGoodBaseDto.getServiceId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
        relAttributeGoodPoList.add(attributeGoodPo3);

        //批量插入除商品参数之外的商品属性
        saveBatch.saveBatch(relAttributeGoodPoList);

        //处理商品和运费的绑定，多对一
//        PmGoodsRelAttributeGoodPo attributeGoodPo4 = new PmGoodsRelAttributeGoodPo();
//        attributeGoodPo4.setGoodsAttributeId(addGoodBaseDto.getShippingId()).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
//        relAttributeGoodPoList.add(attributeGoodPo4);



        //处理商品参数属性
        addGoodBaseDto.getGoodsParamDtoList().forEach(x -> {
            //商品参数属性值和商品参数属性绑定
            PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
            goodsAttributeValuePo.setCreateBy(user).setProductAttributeId(x.getId()).setValue(x.getName());
            //存入数据库
            goodsAttributeValueMapper.insert(goodsAttributeValuePo);

            //商品参数属性值和商品绑定
            PmGoodsRelAttributeValueGoodPo relAttributeValueGoodPo = new PmGoodsRelAttributeValueGoodPo();
            relAttributeValueGoodPo.setCreateBy(user).setGoodsAttributeValueId(goodsAttributeValuePo.getId())
                    .setGoodsId(goodsPo.getId());
            //存入数据库
            goodsRelAttributeValueGoodMapper.insert(relAttributeValueGoodPo);
        });


//        return new JsonViewData(ResultCode.SUCCESS, "添加成功", addGoodBaseDto);
    }

    /**
     * 根据商品ID获取商品的基本信息
     *
     * 关联表：参数与参数值 一对多
     *        商品和属性  多对多
     *        商品和参数值 一对多
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

        List<PmGoodsParamVo> paramVoList = Lists.newArrayList();
        //根据商品ID查找关联参数值
        Map<String,Object> map = new HashMap<>();
        map.put("goods_id",id);
        List<PmGoodsRelAttributeValueGoodPo> relAttributeValueGoodPos = goodsRelAttributeValueGoodMapper.selectByMap(map);
        List<Long> attributeValueIds = relAttributeValueGoodPos.stream().map(b->b.getGoodsAttributeValueId()).collect(Collectors.toList());
        //遍历商品对应的参数值通过属性值表获取属性对应的名称
        attributeValueIds.forEach(x->{
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

        });


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
            switch (goodsAttributeById){
                case BRAND:
                    BaseVo brandVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo,brandVo);
                    baseGoodsVo.setBrandVo(brandVo);
                    break;
                case LABEL:
                    BaseVo labelVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo,labelVo);
                    baseGoodsVo.setLabelVo(labelVo);
                    break;
                case PLATFORM_SERVICE:
                    BaseVo platformServiceVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo,platformServiceVo);
                    baseGoodsVo.setPlatformServiceVo(platformServiceVo);
                    break;
                case MERCHANT_SERVICE:
                    BaseVo merchantServiceVo = new BaseVo();
                    BeanUtils.copyProperties(goodsAttributePo,merchantServiceVo);
                    baseGoodsVo.setMerchantServiceVo(merchantServiceVo);
                    break;
            }
        });

        //根据不同类型运费模版保存不同运费信息
        PmShippingTemplatePo shippingTemplatePo = shippingTemplateMapper.selectById(goodsPo.getShippingTemplateId());

        GoodsShipTemplateEnum goodsShipTemplateById = GoodsShipTemplateEnum.getGoodsShipTemplateById(shippingTemplatePo.getType());
        switch (goodsShipTemplateById){
            case MERCHANT_SHIP:
                BaseVo merchantShipVo = new BaseVo();
                BeanUtils.copyProperties(shippingTemplatePo,merchantShipVo);
                baseGoodsVo.setMerchantShipVo(merchantShipVo);
                break;
            case PLATFORM_SHIP:
                BaseVo platformShipVo = new BaseVo();
                BeanUtils.copyProperties(shippingTemplatePo,platformShipVo);
                baseGoodsVo.setPlatformShipVo(platformShipVo);
                break;

        }

        return baseGoodsVo;
    }

    /**
     * 修改商品基本信息
     *
     * @param updateGoodBaseDto
     */
    @Override
    public void updateBase(AddGoodBaseDto updateGoodBaseDto) {

        PmGoodsPo goodsPo = new PmGoodsPo();
        //保存非关联信息
        BeanUtils.copyProperties(updateGoodBaseDto, goodsPo);


    }

    /**
     * 供应商添加商品时需要的默认的规格值
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<PmGoodsAttributeValueVo> searchStandard(Long categoryId) {

        List<PmGoodsAttributeValueVo> list = mapper.searchStandard(categoryId);

        return list;
    }

    /**
     * 添加商品额外的属性值
     *
     * @param addExtraValueDto
     * @return
     */
    @Override
    public void addExtraValue(AddExtraValueDto addExtraValueDto) {

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        //判断该属性下是否已经存在属性值
        List<PmGoodsAttributeValuePo> valuePoList = goodsAttributeValueMapper.findByAttributeId(addExtraValueDto.getGoodsAttributeId());

        List<String> valueList = valuePoList.stream().map(s -> s.getValue()).collect(Collectors.toList());
        if (valueList.contains(addExtraValueDto.getValue())) {
            throw new ServiceException(ResultCode.DUPLICATION, "添加失败，属性值不能重复");
        }
        PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
        goodsAttributeValuePo.setProductAttributeId(addExtraValueDto.getGoodsAttributeId());
        goodsAttributeValuePo.setValue(addExtraValueDto.getValue());
        goodsAttributeValuePo.setCreateBy(user);
        //设置类型为自定义属性值
        goodsAttributeValuePo.setIsCustom(true);
        goodsAttributeValueMapper.insert(goodsAttributeValuePo);

//        return new JsonViewData(ResultCode.SUCCESS);
    }

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
            PmGoodsSkuPo goodsSkuPo = new PmGoodsSkuPo();
            BeanUtils.copyProperties(addSkuAttributeDto, goodsSkuPo);
            goodsSkuPo.setCreateBy(user);
            goodsSkuMapper.insert(goodsSkuPo);
            //保存关联表
            PmGoodsRelAttributeValueSkuPo goodsRelAttributeValueSkuPo = new PmGoodsRelAttributeValueSkuPo();
            goodsRelAttributeValueSkuPo.setCreateBy(user);
            goodsRelAttributeValueSkuPo.setGoodsAttributeValueId(addSkuAttributeDto.getStandardValueId());
            goodsRelAttributeValueSkuPo.setGoodsSkuId(goodsSkuPo.getId());
            goodsRelAttributeValueSkuMapper.insert(goodsRelAttributeValueSkuPo);

        }

//        return new JsonViewData(ResultCode.SUCCESS);
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

//        return new JsonViewData(ResultCode.SUCCESS);
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
//        return new JsonViewData(ResultCode.SUCCESS);
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

//        return new JsonViewData(ResultCode.SUCCESS);
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

//        return new JsonViewData(ResultCode.SUCCESS);
    }


}
