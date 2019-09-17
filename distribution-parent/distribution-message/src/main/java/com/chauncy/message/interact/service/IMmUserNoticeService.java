package com.chauncy.message.interact.service;

import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo;
import com.chauncy.data.vo.app.message.information.interact.UserNoticeListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 用户消息列表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-14
 */
public interface IMmUserNoticeService extends Service<MmUserNoticePo> {

    /**
     * 获取用户消息列表未读消息数目
     * @return
     */
    UnreadNoticeNumVo getUnreadNoticeNum();

    /**
     * 用户消息列表
     * @param noticeType
     * @return
     */
    PageInfo<UserNoticeListVo> searchPaging(Integer noticeType, BaseSearchPagingDto baseSearchPagingDto);
}
