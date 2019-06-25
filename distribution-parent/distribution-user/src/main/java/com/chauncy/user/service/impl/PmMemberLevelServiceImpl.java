package com.chauncy.user.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chauncy.user.service.IPmMemberLevelService;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmMemberLevelServiceImpl extends AbstractService<PmMemberLevelMapper, PmMemberLevelPo> implements IPmMemberLevelService {

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Override
    public PmMemberLevelPo findByMaxLevel() {
        return memberLevelMapper.loadMaxLevel();
    }
}
