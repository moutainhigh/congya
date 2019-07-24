package com.chauncy.activity.reduced.impl;

import com.chauncy.activity.reduced.IAmReducedService;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.dto.manage.activity.reduced.add.SaveReducedDto;
import com.chauncy.data.mapper.activity.reduced.AmReducedMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 满减活动管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmReducedServiceImpl extends AbstractService<AmReducedMapper, AmReducedPo> implements IAmReducedService {

    @Autowired
    private AmReducedMapper mapper;

    /**
     * 保存满减活动信息
     * @param saveReducedDto
     * @return
     */
    @Override
    public void saveReduced(SaveReducedDto saveReducedDto) {


    }
}
