package com.chauncy.message.content.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.content.add.AddArticleDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

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
     * @param searchContentDto
     * @return
     */
    PageInfo<ArticleVo> searchArticle(SearchContentDto searchContentDto);

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    void delArticleByIds(Long[] ids);

    /**
     * @Author chauncy
     * @Date 2019-09-18 11:17
     * @Description //批量启用或禁用,同一个文章位(除帮助中心外)只能有一个是启用状态
     *
     * @Update chauncy
     *
     * @Param [baseUpdateStatusDto]
     * @return void
     **/
    void editEnabled(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * @Author chauncy
     * @Date 2019-09-18 16:11
     * @Description //查找所有的文章位置
     *
     * @Update chauncy
     *
     * @Param []
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.BaseVo>>
     **/
    Map<String, String> findArticleLocations();

    /**
     * @Author chauncy
     * @Date 2019-09-18 21:14
     * @Description //根据文章位置类型获取文章信息
     *
     * @Update chauncy
     *
     * @Param [type]
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo>>
     **/
    List<FindArticleContentVo> findArticleContent(Integer type);

    /**
     * @Author chauncy
     * @Date 2019-11-15 18:11
     * @Description //查找邀请有礼图文详情
     *
     * @Update chauncy
     *
     * @param
     * @return java.lang.String
     **/
    String getInvitationArticle();
}
