package com.chauncy.data.mapper.message.information.comment;

import com.chauncy.data.domain.po.message.information.comment.MmCommentLikedPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户资讯评论点赞表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-03
 */
public interface MmCommentLikedMapper extends IBaseMapper<MmCommentLikedPo> {

    /**
     * 查询用户评论点赞记录
     * @param commentId
     * @param userId
     * @return
     */
    MmCommentLikedPo selectForUpdate(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
