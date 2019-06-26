package com.chauncy.data.mapper.message.content;

import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.content.ArticleVo;

import java.util.List;

/**
 * <p>
 * 文章管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface MmArticleMapper extends IBaseMapper<MmArticlePo> {

    /**
     * 条件查询文章信息
     *
     * @param searchContentDto
     * @return
     */
    List<ArticleVo> searchArticle(SearchContentDto searchContentDto);
}
