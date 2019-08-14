package com.chauncy.message.advice.impl;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelTabThingsPo;
import com.chauncy.data.mapper.message.advice.MmAdviceRelTabThingsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.message.advice.IMmAdviceRelTabThingsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告选项卡与商品/品牌关联表，广告位置为具体店铺分类、特卖、有品、主题和优选，非多重关联选项卡 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmAdviceRelTabThingsServiceImpl extends AbstractService<MmAdviceRelTabThingsMapper, MmAdviceRelTabThingsPo> implements IMmAdviceRelTabThingsService {

    @Autowired
    private MmAdviceRelTabThingsMapper mapper;

}
