package com.chauncy.activity.gift.impl;

import com.chauncy.data.domain.po.activity.gift.AmGiftPo;
import com.chauncy.data.mapper.activity.gift.AmGiftMapper;
import com.chauncy.activity.gift.IAmGiftService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 礼包表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmGiftServiceImpl extends AbstractService<AmGiftMapper, AmGiftPo> implements IAmGiftService {

    @Autowired
    private AmGiftMapper mapper;

}
