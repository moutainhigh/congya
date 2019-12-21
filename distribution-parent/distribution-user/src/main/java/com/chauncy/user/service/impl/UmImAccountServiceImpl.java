package com.chauncy.user.service.impl;

import com.chauncy.data.domain.po.user.UmImAccountPo;
import com.chauncy.data.mapper.user.UmImAccountMapper;
import com.chauncy.user.service.IUmImAccountService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户IM账号 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-12-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UmImAccountServiceImpl extends AbstractService<UmImAccountMapper,UmImAccountPo> implements IUmImAccountService {

 @Autowired
 private UmImAccountMapper mapper;

}
