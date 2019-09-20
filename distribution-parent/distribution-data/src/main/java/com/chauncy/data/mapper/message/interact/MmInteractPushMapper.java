package com.chauncy.data.mapper.message.interact;

import com.chauncy.data.domain.po.message.interact.MmInteractPushPo;
import com.chauncy.data.dto.manage.message.interact.select.SearchPushDto;
import com.chauncy.data.dto.manage.message.interact.select.SearchSmsDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.interact.push.InteractPushVo;
import com.chauncy.data.vo.manage.message.interact.push.SmsPushVo;

import java.util.List;

/**
 * <p>
 * 平台信息管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
public interface MmInteractPushMapper extends IBaseMapper<MmInteractPushPo> {

    /**
     * 条件查询推送信息
     *
     * @param searchPushDto
     * @return
     */
    List<InteractPushVo> search(SearchPushDto searchPushDto);


}
