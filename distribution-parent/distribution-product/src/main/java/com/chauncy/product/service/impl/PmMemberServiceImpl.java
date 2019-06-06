package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmMemberPo;
import com.chauncy.data.mapper.product.PmMemberMapper;
import com.chauncy.product.service.IPmMemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmMemberServiceImpl extends AbstractService<PmMemberMapper, PmMemberPo> implements IPmMemberService {

}
