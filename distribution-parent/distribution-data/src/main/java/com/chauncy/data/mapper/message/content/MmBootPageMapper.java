package com.chauncy.data.mapper.message.content;

import com.chauncy.data.domain.po.message.content.MmBootPagePo;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.content.BootPageVo;

import java.util.List;

/**
 * <p>
 * 启动页管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface MmBootPageMapper extends IBaseMapper<MmBootPagePo> {

    /**
     * 查找启动页
     *
     * @param searchContentDto
     * @return
     */
    List<BootPageVo> searchPages(SearchContentDto searchContentDto);
}
