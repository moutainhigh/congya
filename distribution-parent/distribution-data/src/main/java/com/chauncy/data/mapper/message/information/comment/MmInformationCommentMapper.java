package com.chauncy.data.mapper.message.information.comment;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
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
     * 分页查询评论
     *
     * @param id  资讯ID
     * @return
     */
    List<InformationCommentVo> searchPaging(@Param("id") Long id);
}
