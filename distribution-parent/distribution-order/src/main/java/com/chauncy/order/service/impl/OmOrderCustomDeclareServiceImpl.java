package com.chauncy.order.service.impl;

import com.chauncy.data.domain.po.order.OmOrderCustomDeclarePo;
import com.chauncy.data.mapper.order.OmOrderCustomDeclareMapper;
import com.chauncy.order.service.IOmOrderCustomDeclareService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 海关申报表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderCustomDeclareServiceImpl extends AbstractService<OmOrderCustomDeclareMapper,OmOrderCustomDeclarePo> implements IOmOrderCustomDeclareService {

 @Autowired
 private OmOrderCustomDeclareMapper mapper;

}
