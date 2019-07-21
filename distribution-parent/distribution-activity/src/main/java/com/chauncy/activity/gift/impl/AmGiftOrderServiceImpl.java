package com.chauncy.activity.gift.impl;

import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.mapper.activity.gift.AmGiftOrderMapper;
import com.chauncy.activity.gift.IAmGiftOrderService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 礼包订单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmGiftOrderServiceImpl extends AbstractService<AmGiftOrderMapper, AmGiftOrderPo> implements IAmGiftOrderService {

    @Autowired
    private AmGiftOrderMapper mapper;

}
