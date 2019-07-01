package com.chauncy.message.information.comment.service;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 资讯评论信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
public interface IMmInformationCommentService extends Service<MmInformationCommentPo> {

    /**
     * 分页查询评论
     * @param baseSearchDto
     * @return
     */
    PageInfo<InformationCommentVo> searchPaging(BaseSearchDto baseSearchDto);
}
