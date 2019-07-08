package com.chauncy.message.interact.service;

import com.chauncy.data.domain.po.message.interact.MmInteractPushPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.interact.add.AddPushMessageDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.vo.manage.message.interact.push.UmUsersVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 平台信息管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
public interface IMmInteractPushService extends Service<MmInteractPushPo> {

    /**
     * 用户列表
     *
     * @param searchUserListDto
     * @return
     */
    PageInfo<UmUsersVo> searchUserList(SearchUserListDto searchUserListDto);

    /**
     * 添加推送信息
     * @param addPushMessageDto
     * @return
     */
    void addPushMessage(AddPushMessageDto addPushMessageDto);
}
