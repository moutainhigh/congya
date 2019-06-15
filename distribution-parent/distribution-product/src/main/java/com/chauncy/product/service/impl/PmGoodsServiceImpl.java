package com.chauncy.product.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeGoodPo;
import com.chauncy.data.dto.manage.good.add.GoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddExtraValueDto;
import com.chauncy.data.mapper.product.PmGoodsAttributeValueMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeGoodMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.PmGoodsAttributeValueVo;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 添加商品基础信息
     *
     * @param goodBaseDto
     * @return
     */
    @Override
    public JsonViewData addBase(GoodBaseDto goodBaseDto) {

        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        PmGoodsPo goodsPo = new PmGoodsPo();
        goodsPo.setCreateBy(user);
        goodsPo.setShippingTemplateId(goodBaseDto.getShippingId());
//        goodsPo.setCreateTime(date);
        //复制Dto对象到po
        BeanUtils.copyProperties(goodBaseDto, goodsPo);
        //先保存商品不关联信息
        mapper.insert(goodsPo);
        //处理商品属性
        for (Long attId : goodBaseDto.getAttributeIds()) {
            PmGoodsRelAttributeGoodPo attributeGoodPo = new PmGoodsRelAttributeGoodPo();
            attributeGoodPo.setGoodsAttributeId(attId).setGoodsGoodId(goodsPo.getId()).setCreateBy(user);
            attributeGoodMapper.insert(attributeGoodPo);
        }


        return new JsonViewData(ResultCode.SUCCESS, "添加成功", goodBaseDto);
    }

    /**
     * 供应商添加商品时需要的默认的规格值
     *
     * @param categoryId
     * @return
     */
    @Override
    public JsonViewData searchStandard(Long categoryId) {

        List<PmGoodsAttributeValueVo> list = mapper.searchStandard(categoryId);

        return new JsonViewData(list);
    }

    /**
     * 添加商品额外的属性值
     *
     * @param addExtraValueDto
     * @return
     */
    @Override
    public JsonViewData addExtraValue(AddExtraValueDto addExtraValueDto) {

        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        //判断该属性下是否已经存在属性值
        List<PmGoodsAttributeValuePo> valuePoList = goodsAttributeValueMapper.findByAttributeId(addExtraValueDto.getGoodsAttributeId());
        for (PmGoodsAttributeValuePo value : valuePoList) {
            //判断值不能相同
            boolean s = addExtraValueDto.getValue().equals(value.getValue());
            if (s == true) {
                return new JsonViewData(ResultCode.FAIL, "添加失败，属性值不能重复");

            }
        }
        PmGoodsAttributeValuePo goodsAttributeValuePo = new PmGoodsAttributeValuePo();
        goodsAttributeValuePo.setProductAttributeId(addExtraValueDto.getGoodsAttributeId());
        goodsAttributeValuePo.setValue(addExtraValueDto.getValue());
        goodsAttributeValuePo.setCreateBy(user);
        //设置类型为自定义属性值
        goodsAttributeValuePo.setIsCustom(true);
        goodsAttributeValueMapper.insert(goodsAttributeValuePo);

        return new JsonViewData(ResultCode.SUCCESS);
    }

}
