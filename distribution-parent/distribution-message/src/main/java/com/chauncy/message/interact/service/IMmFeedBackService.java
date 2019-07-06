package com.chauncy.message.interact.service;

import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.interact.select.SearchFeedBackDto;
import com.chauncy.data.vo.manage.message.interact.feedBack.SearchFeedBackVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * app用户反馈列表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-06
 */
public interface IMmFeedBackService extends Service<MmFeedBackPo> {

    /**
     * 条件查询意见反馈
     *
     * @param searchFeedBackDto
     * @return
     */
    PageInfo<SearchFeedBackVo> searchFeedBack(SearchFeedBackDto searchFeedBackDto);
}
