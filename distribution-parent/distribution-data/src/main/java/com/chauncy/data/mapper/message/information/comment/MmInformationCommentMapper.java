package com.chauncy.data.mapper.message.information.comment;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.SearchInfoCommentDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
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
     * @Author yeJH
     * @Date 2019/10/21 20:38
     * @Description 查询资讯所有评论
     *
     * @Update yeJH
     *
     * @param  searchInfoCommentDto 查询条件
     * @return java.util.List<InformationCommentVo>
     **/
    List<InformationCommentVo>  searchInfoComment(SearchInfoCommentDto searchInfoCommentDto);

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
     * @param informationCommentDto  资讯ID  用户id
     * @return
     */
    List<InformationMainCommentVo> searchInfoMainComment(InformationCommentDto informationCommentDto);

    /**
     * 根据主评论id查询副评论
     *
     * @param id  评论ID
     * @param userId  用户id判断是否点赞
     * @param type  类型
     *              为subStep 表示为searchInfoMainComment方法的分步查询方法 查询第一页
     *              为空表示根据主评论id分页查询副评论
     * @return
     */
    List<InformationViceCommentVo> searchViceCommentByMainId(@Param("id") Long id,
                                                             @Param("userId") Long userId,
                                                             @Param("type") String type);
}
