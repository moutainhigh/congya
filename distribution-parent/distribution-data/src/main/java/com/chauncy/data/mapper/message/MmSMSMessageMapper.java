package com.chauncy.data.mapper.message;

import com.chauncy.data.domain.po.message.MmSMSMessagePo;
import com.chauncy.data.dto.manage.message.interact.select.SearchSmsDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.interact.push.SmsPushVo;

import java.util.List;

/**
 * <p>
 * 平台短信信息 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
public interface MmSMSMessageMapper extends IBaseMapper<MmSMSMessagePo> {

    /**
     * 条件查询短信推送信息
     *
     * @param searchSmsDto
     * @return
     */
    List<SmsPushVo> searchSmsPush(SearchSmsDto searchSmsDto);

}
