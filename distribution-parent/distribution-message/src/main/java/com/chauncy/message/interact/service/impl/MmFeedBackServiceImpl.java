package com.chauncy.message.interact.service.impl;

import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.dto.manage.message.interact.select.SearchFeedBackDto;
import com.chauncy.data.mapper.message.interact.MmFeedBackMapper;
import com.chauncy.data.vo.manage.message.interact.feedBack.SearchFeedBackVo;
import com.chauncy.message.interact.service.IMmFeedBackService;
import com.chauncy.data.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * app用户反馈列表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmFeedBackServiceImpl extends AbstractService<MmFeedBackMapper, MmFeedBackPo> implements IMmFeedBackService {

    @Autowired
    private MmFeedBackMapper mapper;

    /**
     * 条件查询意见反馈
     *
     * @param searchFeedBackDto
     * @return
     */
    @Override
    public PageInfo<SearchFeedBackVo> searchFeedBack(SearchFeedBackDto searchFeedBackDto) {

//        Integer pageNo = searchFeedBackDto.getPageNo() == null ? defaultPageNo : searchFeedBackDto.getPageNo();
//        Integer pageSize = searchFeedBackDto.getPageSize() == null ? defaultPageSize : searchFeedBackDto.getPageSize();
//        PageInfo<SearchFeedBackVo> searchFeedBackVoPageInfo = PageHelper.startPage(pageNo, pageSize)
//                .doSelectPageInfo(() -> mapper.searchFeedBack(searchFeedBackDto));

        return null;
    }
}
