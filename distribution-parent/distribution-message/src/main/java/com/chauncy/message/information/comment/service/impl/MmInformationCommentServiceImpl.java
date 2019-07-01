package com.chauncy.message.information.comment.service.impl;

import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.message.information.comment.MmInformationCommentMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
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
     * 分页查询评论
     *
     * @param baseSearchDto
     * @return
     */
    @Override
    public PageInfo<InformationCommentVo> searchPaging(BaseSearchDto baseSearchDto) {

        Integer pageNo = baseSearchDto.getPageNo()==null ? defaultPageNo : baseSearchDto.getPageNo();
        Integer pageSize = baseSearchDto.getPageSize()==null ? defaultPageSize : baseSearchDto.getPageSize();


        PageInfo<InformationCommentVo> informationCategoryVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchPaging(baseSearchDto));
        return informationCategoryVoPageInfo;    }
}
