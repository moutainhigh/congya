package com.chauncy.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.TbUserPo;
import com.chauncy.data.mapper.product.TbUserMapper;
import com.chauncy.test.service.ITbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-22
 * <p>
 * 用来测试代码生成器以及测试相关的基类
 */
@Service
public class TbUserServiceImpl extends AbstractService<TbUserMapper, TbUserPo> implements ITbUserService {

    @Autowired
    private TbUserMapper mapper;

    @Override
    public void test(String username) {
        TbUserPo conditionSysUserPo = new TbUserPo();
        conditionSysUserPo.setName(username);
        QueryWrapper<TbUserPo> queryWrapper=new QueryWrapper<>(conditionSysUserPo);
        TbUserPo querySysUserPo = mapper.selectOne(queryWrapper);
        System.out.println(querySysUserPo);
    }

}
