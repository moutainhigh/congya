package com.chauncy.message.information.comment.service;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.InformationViceCommentDto;
import com.chauncy.data.vo.manage.message.information.comment.InformationMainCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo;
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
     * 后台分页查询评论
     * @param informationCommentDto
     * @return
     */
    PageInfo<InformationViceCommentVo> searchPaging(InformationCommentDto informationCommentDto);
    /**
     * 根据主评论id查询副评论
     *
     * @param informationViceCommentDto
     * @return
     */
    PageInfo<InformationViceCommentVo> searchViceCommentByMainId(InformationViceCommentDto informationViceCommentDto);
    /**
     * app分页查询评论
     * @param informationCommentDto
     * @return
     */
    PageInfo<InformationMainCommentVo> searchInfoCommentById(InformationCommentDto informationCommentDto);

    /**
     * 隐藏显示评论
     *
     * @param baseUpdateStatusDto
     */
    void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 删除评论
     * @param id
     */
    void delInfoCommentById(Long id);
}
