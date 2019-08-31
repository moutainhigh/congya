package com.chauncy.message.information.rel.service.impl;

import com.chauncy.data.domain.po.message.information.rel.MmInformationLikedPo;
import com.chauncy.data.mapper.message.information.rel.MmInformationLikedMapper;
import com.chauncy.message.information.rel.service.IMmInformationLikedService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户资讯点赞表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInformationLikedServiceImpl extends AbstractService<MmInformationLikedMapper,MmInformationLikedPo> implements IMmInformationLikedService {

 @Autowired
 private MmInformationLikedMapper mapper;

}
