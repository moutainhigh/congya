package com.chauncy.order.customs;

import com.chauncy.data.domain.po.order.CustomsDataPo;
import com.chauncy.data.mapper.order.CustomsDataMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 海关字段 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomsDataServiceImpl extends AbstractService<CustomsDataMapper,CustomsDataPo> implements ICustomsDataService {

 @Autowired
 private CustomsDataMapper mapper;

}
