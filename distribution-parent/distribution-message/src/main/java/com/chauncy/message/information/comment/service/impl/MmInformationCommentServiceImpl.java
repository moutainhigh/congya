package com.chauncy.message.information.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.mapper.message.information.comment.MmInformationCommentMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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
     * 分页查询评论
     *
     * @param informationCommentDto
     * @return
     */
    @Override
    public PageInfo<InformationCommentVo> searchPaging(InformationCommentDto informationCommentDto) {

        Integer pageNo = informationCommentDto.getPageNo()==null ? defaultPageNo : informationCommentDto.getPageNo();
        Integer pageSize = informationCommentDto.getPageSize()==null ? defaultPageSize : informationCommentDto.getPageSize();

        System.out.println(informationCommentDto.getId());
        PageInfo<InformationCommentVo> informationCategoryVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchPaging(informationCommentDto.getId()));
        return informationCategoryVoPageInfo;
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
