package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsSkuCategoryAttributeRelationPo;
import com.chauncy.data.dto.product.GoodBaseDto;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuCategoryAttributeRelationMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsServiceImpl extends ServiceImpl<PmGoodsMapper, PmGoodsPo> implements IPmGoodsService {

    @Autowired
    private PmGoodsMapper mapper;

    @Autowired
    private PmGoodsSkuCategoryAttributeRelationMapper relationMapper;

    @Autowired
    private SecurityUtil securityUtil;
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
        BeanUtils.copyProperties(goodBaseDto,goodsPo);
        //先保存商品不关联信息
        mapper.insert(goodsPo);
        //处理商品属性
        for (Long attId : goodBaseDto.getAttributeIds()){
            PmGoodsSkuCategoryAttributeRelationPo relationPo = new PmGoodsSkuCategoryAttributeRelationPo();
            relationPo.setGoodsAttributeId(attId).setGoodsId(goodsPo.getId()).setCreateBy(user);
            relationMapper.insert(relationPo);
        }


        return new JsonViewData(ResultCode.SUCCESS,"添加成功",goodBaseDto);
    }
}
