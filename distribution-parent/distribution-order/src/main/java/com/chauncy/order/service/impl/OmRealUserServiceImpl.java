package com.chauncy.order.service.impl;

import com.chauncy.data.domain.po.order.OmRealUserPo;
import com.chauncy.data.mapper.order.OmRealUserMapper;
import com.chauncy.data.vo.app.order.cart.RealUserVo;
import com.chauncy.order.service.IOmRealUserService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单实名认证用户 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmRealUserServiceImpl extends AbstractService<OmRealUserMapper, OmRealUserPo> implements IOmRealUserService {

    @Autowired
    private OmRealUserMapper mapper;


    public RealUserVo getVoById(Long id){
        RealUserVo realUserVo = mapper.getVoById(id);
        return realUserVo;
    }

}
