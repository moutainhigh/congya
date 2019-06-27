package com.chauncy.message.content.service;

import com.chauncy.data.domain.po.message.content.MmKeywordsSearchPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.content.add.AddKeyWordsDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.vo.manage.message.content.KeyWordVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 热搜关键字管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface IMmKeywordsSearchService extends Service<MmKeywordsSearchPo> {

    /**
     * 获取关键字类型
     *
     * @return
     */
    List<String> selectKeyWordType();

    /**
     * 添加关键字
     *
     * @param addKeyWordsDto
     * @return
     */
    void addKeyWords(AddKeyWordsDto addKeyWordsDto);

    /**
     * 修改关键字
     *
     * @param updateKeyWordsDto
     * @return
     */
    void updateKeyWords(AddKeyWordsDto updateKeyWordsDto);

    /**
     * 批量删除启动页
     *
     * @param ids
     * @return
     */
    void delKeyWordsByIds(Long[] ids);

    /**
     * 条件查询关键字信息
     *
     * @param searchContentDto
     * @return
     */
    PageInfo<KeyWordVo> searchKeyWords(SearchContentDto searchContentDto);
}
