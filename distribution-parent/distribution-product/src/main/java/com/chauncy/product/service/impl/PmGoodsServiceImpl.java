package com.chauncy.product.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddExtraValueDto;
import com.chauncy.data.dto.supplier.good.add.AddSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.PmGoodsAttributeValueVo;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    private SecurityUtil securityUtil;

    @Autowired
    private PmGoodsAttributeValueMapper goodsAttributeValueMapper;

    @Autowired
    private PmGoodsSkuMapper goodsSkuMapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper goodsRelAttributeValueSkuMapper;

    @Autowired
    private PmGoodsRelGoodsMemberLevelMapper goodsRelGoodsMemberLevelMapper;

    @Autowired
    private PmAssociationGoodsMapper associationGoodsMapper;

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
        goodsPo.setShippingTemplateId(addGoodBaseDto.getShippingId());
//        goodsPo.setCreateTime(date);
        //复制Dto对象到po
        BeanUtils.copyProperties(addGoodBaseDto, goodsPo);
        //先保存商品不关联信息
        mapper.insert(goodsPo);
        //处理商品属性，关联信息
        for (Long attId : addGoodBaseDto.getAttributeIds()) {
            PmGoodsRelAttributeGoodPo attributeGoodPo = new PmGoodsRelAttributeGoodPo();
            attributeGoodPo.setGoodsAttributeId(attId).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
            attributeGoodMapper.insert(attributeGoodPo);
        }


//        return new JsonViewData(ResultCode.SUCCESS, "添加成功", addGoodBaseDto);
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

        List<String> valueList = valuePoList.stream().map(s->s.getValue()).collect(Collectors.toList());
        if(valueList.contains(addExtraValueDto.getValue())){
            throw new ServiceException(ResultCode.DUPLICATION,"添加失败，属性值不能重复");
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
