package com.chauncy.activity.spell.impl;

import com.chauncy.activity.spell.IAmSpellGroupMemberService;
import com.chauncy.data.domain.po.activity.spell.AmSpellGroupMemberPo;
import com.chauncy.data.mapper.activity.spell.AmSpellGroupMemberMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 拼团成员表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmSpellGroupMemberServiceImpl extends AbstractService<AmSpellGroupMemberMapper, AmSpellGroupMemberPo> implements IAmSpellGroupMemberService {

    @Autowired
    private AmSpellGroupMemberMapper mapper;

}
