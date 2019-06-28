package com.chauncy.user.service.impl;

import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.mapper.user.UmAreaShippingMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.user.service.IUmAreaShippingService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class UmAreaShippingServiceImpl extends AbstractService<UmAreaShippingMapper,UmAreaShippingPo> implements IUmAreaShippingService {

 @Autowired
 private UmAreaShippingMapper mapper;

}
