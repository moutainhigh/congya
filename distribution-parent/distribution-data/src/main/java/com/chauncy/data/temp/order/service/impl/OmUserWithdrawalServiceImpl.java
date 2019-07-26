package com.chauncy.data.temp.order.service.impl;

import com.chauncy.data.domain.po.order.OmUserWithdrawalPo;
import com.chauncy.data.mapper.order.OmUserWithdrawalMapper;
import com.chauncy.data.temp.order.service.IOmUserWithdrawalService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 'distribution.activity_view' is not BASE TABLE 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmUserWithdrawalServiceImpl extends AbstractService<OmUserWithdrawalMapper,OmUserWithdrawalPo> implements IOmUserWithdrawalService {

 @Autowired
 private OmUserWithdrawalMapper mapper;

}
