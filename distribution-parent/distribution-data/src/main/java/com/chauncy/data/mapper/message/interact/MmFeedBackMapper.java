package com.chauncy.data.mapper.message.interact;

import com.chauncy.data.domain.po.message.interact.MmFeedBackPo;
import com.chauncy.data.dto.manage.message.interact.select.SearchFeedBackDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.interact.feedBack.SearchFeedBackVo;

import java.util.List;

/**
 * <p>
 * app用户反馈列表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-06
 */
public interface MmFeedBackMapper extends IBaseMapper<MmFeedBackPo> {

    /**
     * 条件查询意见反馈
     *
     * @param searchFeedBackDto
     * @return
     */
    List<SearchFeedBackVo> searchFeedBack(SearchFeedBackDto searchFeedBackDto);
}
