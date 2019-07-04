package com.chauncy.message.information.comment.service.impl;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.InformationViceCommentDto;
import com.chauncy.data.mapper.message.information.comment.MmInformationCommentMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.message.information.comment.InformationMainCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 资讯评论信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInformationCommentServiceImpl extends AbstractService<MmInformationCommentMapper, MmInformationCommentPo> implements IMmInformationCommentService {

    @Autowired
    private MmInformationCommentMapper mmInformationCommentMapper;

    /**
     * 后台分条件页查询评论
     *
     * @param informationCommentDto
     * @return
     */
    @Override
    public PageInfo<InformationViceCommentVo> searchPaging(InformationCommentDto informationCommentDto) {

        Integer pageNo = informationCommentDto.getPageNo()==null ? defaultPageNo : informationCommentDto.getPageNo();
        Integer pageSize = informationCommentDto.getPageSize()==null ? defaultPageSize : informationCommentDto.getPageSize();

        PageInfo<InformationViceCommentVo> informationViceCommentVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchInfoViceComment(informationCommentDto.getId()));
        return informationViceCommentVoPageInfo;
    }

    /**
     * 根据主评论id查询副评论
     *
     * @param informationViceCommentDto
     * @return
     */
    @Override
    public PageInfo<InformationViceCommentVo> searchViceCommentByMainId(InformationViceCommentDto informationViceCommentDto) {

        Integer pageNo = informationViceCommentDto.getPageNo()==null ? defaultPageNo : informationViceCommentDto.getPageNo();
        Integer pageSize = informationViceCommentDto.getPageSize()==null ? defaultPageSize : informationViceCommentDto.getPageSize();

        PageInfo<InformationViceCommentVo> informationViceCommentVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchViceCommentByMainId(informationViceCommentDto.getId(), null));
        return informationViceCommentVoPageInfo;
    }

    /**
     * app分页查询评论
     *
     * @param informationCommentDto
     * @return
     */
    @Override
    public PageInfo<InformationMainCommentVo> searchInfoCommentById(InformationCommentDto informationCommentDto) {

        Integer pageNo = informationCommentDto.getPageNo()==null ? defaultPageNo : informationCommentDto.getPageNo();
        Integer pageSize = informationCommentDto.getPageSize()==null ? defaultPageSize : informationCommentDto.getPageSize();

        PageInfo<InformationMainCommentVo> informationMainCommentVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchInfoMainComment(informationCommentDto.getId()));
        return informationMainCommentVoPageInfo;
    }

    /**
     * 隐藏显示评论
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto) {
        this.editEnabledBatch(baseUpdateStatusDto);
    }


    /**
     * 删除评论
     * @param id
     */
    @Override
    public void delInfoCommentById(Long id) {
        //批量删除
        mmInformationCommentMapper.deleteById(id);
    }

}
