package com.chauncy.data.mapper.message.content;

import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo;
import org.apache.ibatis.annotations.Select;

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

    /**
     * @Author chauncy
     * @Date 2019-09-18 21:42
     * @Description //根据文章位置类型获取文章信息
     *
     * @Update chauncy
     *
     * @Param [name]
     * @return java.util.List<com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo>
     **/
    @Select("select id,name,detail_html from mm_article where del_flag =0 and enabled = 1 and article_location = #{location}  ")
    List<FindArticleContentVo> findArticleContent(String location);

    /**
     * @Author chauncy
     * @Date 2019-11-15 18:12
     * @Description //查找邀请有礼图文详情
     *
     * @Update chauncy
     *
     * @param
     * @return java.lang.String
     **/
    @Select("select detail_html from mm_article where del_flag = 0 and enabled = 1 and article_location = '邀请好友' ")
    String getInvitationArticle();
}
