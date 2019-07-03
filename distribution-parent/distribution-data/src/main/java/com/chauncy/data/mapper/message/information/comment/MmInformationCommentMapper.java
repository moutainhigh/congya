package com.chauncy.data.mapper.message.information.comment;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.information.comment.InformationMainCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资讯评论信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface MmInformationCommentMapper extends IBaseMapper<MmInformationCommentPo> {
    /**
     * 后台分页查询评论
     *
     * @param id  资讯ID
     * @return
     */
    List<InformationViceCommentVo> searchInfoViceComment(@Param("id") Long id);

    /**
     * app分页查询评论
     *
     * @param id  资讯ID
     * @return
     */
    List<InformationMainCommentVo> searchInfoMainComment(@Param("id") Long id);

    /**
     * 根据主评论id查询副评论
     *
     * @param id  评论ID
     * @param type  类型
     *              为subStep 表示为searchInfoMainComment方法的分步查询方法 查询第一页
     *              为空表示根据主评论id分页查询副评论
     * @return
     */
    List<InformationViceCommentVo> searchViceCommentByMainId(@Param("id") Long id, @Param("type") String type);
}
