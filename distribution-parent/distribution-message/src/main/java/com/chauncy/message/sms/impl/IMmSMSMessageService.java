package com.chauncy.message.sms.impl;

import com.chauncy.data.domain.po.message.MmSMSMessagePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.interact.add.AddSmsMessageDto;

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

}
