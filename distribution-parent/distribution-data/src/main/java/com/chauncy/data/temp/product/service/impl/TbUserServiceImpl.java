package com.chauncy.data.temp.product.service.impl;

import com.chauncy.data.domain.po.product.TbUserPo;
import com.chauncy.data.mapper.product.TbUserMapper;
import com.chauncy.data.temp.product.service.ITbUserService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-22
 *
 * 用来测试代码生成器以及测试相关的基类
 */
@Service
public class TbUserServiceImpl extends AbstractService<TbUserMapper,TbUserPo> implements ITbUserService {

 @Autowired
 private TbUserMapper mapper;

}
