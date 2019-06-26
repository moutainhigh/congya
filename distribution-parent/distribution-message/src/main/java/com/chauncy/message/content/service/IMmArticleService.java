package com.chauncy.message.content.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.dto.manage.message.content.add.AddArticleDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchArticleDto;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 文章管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
public interface IMmArticleService extends Service<MmArticlePo> {

    /**
     * 添加文章
     *
     * @param addArticleDto
     * @return
     */
    void addArticle(AddArticleDto addArticleDto);

    /**
     * 查找所有的文章位置
     *
     * @return
     */
    List<String> findLocations();

    /**
     * 更新文章
     *
     * @param updateArticleDto
     * @return
     */
    void updateArticle(AddArticleDto updateArticleDto);

    /**
     * 条件查询文章信息
     *
     * @param searchArticleDto
     * @return
     */
    PageInfo<ArticleVo> searchArticle(SearchArticleDto searchArticleDto);

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    void delArticleByIds(Long[] ids);
}
