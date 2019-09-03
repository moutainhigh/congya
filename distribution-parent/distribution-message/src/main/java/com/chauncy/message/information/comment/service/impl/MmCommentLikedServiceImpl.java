package com.chauncy.message.information.comment.service.impl;

import com.chauncy.data.domain.po.message.information.comment.MmCommentLikedPo;
import com.chauncy.data.mapper.message.information.comment.MmCommentLikedMapper;
import com.chauncy.message.information.comment.service.IMmCommentLikedService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户资讯评论点赞表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmCommentLikedServiceImpl extends AbstractService<MmCommentLikedMapper,MmCommentLikedPo> implements IMmCommentLikedService {

 @Autowired
 private MmCommentLikedMapper mapper;

}
