package com.chauncy.message.content.service.impl;

import com.chauncy.common.enums.message.ArticleLocationEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.dto.manage.message.content.add.AddArticleDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchArticleDto;
import com.chauncy.data.mapper.message.content.MmArticleMapper;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.chauncy.message.content.service.IMmArticleService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Service
public class MmArticleServiceImpl extends AbstractService<MmArticleMapper, MmArticlePo> implements IMmArticleService {

    @Autowired
    private MmArticleMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 添加文章
     *
     * @param addArticleDto
     * @return
     */
    @Override
    public void addArticle(AddArticleDto addArticleDto) {

        Map<String,Object> map = new HashMap<>();
        map.put("article_location",addArticleDto.getArticleLocation());
        List<MmArticlePo> contentDtos = mapper.selectByMap(map);
        //只有帮助中心位置才能有多个文章
        if (!addArticleDto.getArticleLocation().equals(ArticleLocationEnum.HELP_CENTER.getName())){
            if (contentDtos!=null && contentDtos.size()!=0){
                throw new ServiceException(ResultCode.DUPLICATION,addArticleDto.getArticleLocation()+"位置下已有文章,请检查");
            }
        }
        MmArticlePo articlePo = new MmArticlePo();
        BeanUtils.copyProperties(addArticleDto,articlePo);
        articlePo.setId(null);
        articlePo.setCreateBy(securityUtil.getCurrUser().getUsername());
        articlePo.setUpdateTime(LocalDateTime.now());
        mapper.insert(articlePo);
    }

    /**
     * 查找所有的文章位置
     *
     * @return
     */
    @Override
    public List<String> findLocations() {

        List<String> locations = Arrays.asList(ArticleLocationEnum.values()).stream().map(a->a.getName()).collect(Collectors.toList());
        return locations;
    }


    /**
     * 更新文章
     *
     * @param updateArticleDto
     * @return
     */
    @Override
    public void updateArticle(AddArticleDto updateArticleDto) {
        //不能修改位置
        if (!mapper.selectById(updateArticleDto.getId()).getArticleLocation().equals(updateArticleDto.getArticleLocation())){
            throw new ServiceException(ResultCode.FAIL,"不能改变文章位置"+mapper.selectById(updateArticleDto.getId()).getArticleLocation());
        }
        MmArticlePo articlePo = new MmArticlePo();
        BeanUtils.copyProperties(updateArticleDto,articlePo);
        mapper.updateById(articlePo);
    }

    /**
     * 条件查询文章信息
     *
     * @param searchArticleDto
     * @return
     */
    @Override
    public PageInfo<ArticleVo> searchArticle(SearchArticleDto searchArticleDto) {

        Integer pageNo = searchArticleDto.getPageNo() == null ? defaultPageNo : searchArticleDto.getPageNo();
        Integer pageSize = searchArticleDto.getPageSize() == null ? defaultPageSize : searchArticleDto.getPageSize();
        PageInfo<ArticleVo> articleVo = PageHelper.startPage(pageNo,pageSize).
                doSelectPageInfo(()->mapper.searchArticle(searchArticleDto));

        return articleVo;
    }
    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    @Override
    public void delArticleByIds(Long[] ids) {
       mapper.deleteBatchIds(Arrays.asList(ids));
    }
}
