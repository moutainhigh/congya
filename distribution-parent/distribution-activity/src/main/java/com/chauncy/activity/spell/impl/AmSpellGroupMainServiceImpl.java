package com.chauncy.activity.spell.impl;

import com.chauncy.activity.spell.IAmSpellGroupMainService;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupMainPo;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMainMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 拼团单号表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmSpellGroupMainServiceImpl extends AbstractService<AmSpellGroupMainMapper, AmSpellGroupMainPo> implements IAmSpellGroupMainService {

    @Autowired
    private AmSpellGroupMainMapper mapper;

}
