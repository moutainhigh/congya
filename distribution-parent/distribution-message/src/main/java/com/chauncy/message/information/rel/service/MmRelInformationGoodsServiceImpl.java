package com.chauncy.message.information.rel.service;

import com.chauncy.data.domain.po.message.information.rel.MmRelInformationGoodsPo;
import com.chauncy.data.mapper.message.information.rel.MmRelInformationGoodsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.information.rel.IMmRelInformationGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品与资讯关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-27
 */
@Service
public class MmRelInformationGoodsServiceImpl extends AbstractService<MmRelInformationGoodsMapper,MmRelInformationGoodsPo> implements IMmRelInformationGoodsService {

 @Autowired
 private MmRelInformationGoodsMapper mapper;

}
