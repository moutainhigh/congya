package com.chauncy.message.sms.impl;

import com.chauncy.data.domain.po.message.MmSMSMessagePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.interact.add.AddSmsMessageDto;
import com.chauncy.data.dto.manage.message.interact.select.SearchSmsDto;
import com.chauncy.data.vo.manage.message.interact.push.SmsPushVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 平台短信信息 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
public interface IMmSMSMessageService extends Service<MmSMSMessagePo> {

    /**
     * 添加并发送消息
     *
     * @param addSmsMessageDto
     */
    void saveSmsMessage(AddSmsMessageDto addSmsMessageDto);

    /**
     * 条件查询短信推送信息
     *
     * @param searchSmsDto
     * @return
     */
    PageInfo<SmsPushVo> searchSmsPushVo(SearchSmsDto searchSmsDto);

}
