package com.chauncy.message.interact.service;

import com.chauncy.data.bo.app.message.SaveUserNoticeBo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.vo.app.message.information.interact.UnreadNoticeNumVo;
import com.chauncy.data.vo.app.message.information.interact.UserNoticeListVo;
import com.github.pagehelper.PageInfo;

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
     * @Author yeJH
     * @Date 2019/10/22 16:38
     * @Description 保存APP内消息
     *
     * @Update yeJH
     *
     * @param  name
     * @param  saveUserNoticeBo
     * @return void
     **/
    void saveUserNotice(String name, SaveUserNoticeBo saveUserNoticeBo);
    
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
