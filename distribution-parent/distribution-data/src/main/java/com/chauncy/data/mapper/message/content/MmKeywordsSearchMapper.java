package com.chauncy.data.mapper.message.content;

import com.chauncy.data.domain.po.message.content.MmKeywordsSearchPo;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.content.KeyWordVo;

import java.util.List;

/**
 * <p>
 * 热搜关键字管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface MmKeywordsSearchMapper extends IBaseMapper<MmKeywordsSearchPo> {

    /**
     * 条件查询关键字信息
     *
     * @param searchContentDto
     * @return
     */
    List<KeyWordVo> searchKeyWords(SearchContentDto searchContentDto);
}
