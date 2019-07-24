package com.chauncy.activity.spell.impl;

import com.chauncy.activity.spell.IAmSpellGroupService;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupPo;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 秒杀活动管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmSpellGroupServiceImpl extends AbstractService<AmSpellGroupMapper,AmSpellGroupPo> implements IAmSpellGroupService {

 @Autowired
 private AmSpellGroupMapper mapper;

}
