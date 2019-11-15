package com.chauncy.message.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.message.ArticleLocationEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.content.MmArticlePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.content.add.AddArticleDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.mapper.message.content.MmArticleMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo;
import com.chauncy.message.content.service.IMmArticleService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
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
        /*if (!addArticleDto.getArticleLocation().equals(ArticleLocationEnum.HELP_CENTER.getName())){
            if (contentDtos!=null && contentDtos.size()!=0){
                throw new ServiceException(ResultCode.DUPLICATION,addArticleDto.getArticleLocation()+"位置下已有文章,请检查");
            }
        }*/
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
        articlePo.setUpdateBy(securityUtil.getCurrUser().getUsername());
        mapper.updateById(articlePo);
    }

    /**
     * 条件查询文章信息
     *
     * @param searchContentDto
     * @return
     */
    @Override
    public PageInfo<ArticleVo> searchArticle(SearchContentDto searchContentDto) {

        Integer pageNo = searchContentDto.getPageNo() == null ? defaultPageNo : searchContentDto.getPageNo();
        Integer pageSize = searchContentDto.getPageSize() == null ? defaultPageSize : searchContentDto.getPageSize();
        PageInfo<ArticleVo> articleVo = PageHelper.startPage(pageNo,pageSize).
                doSelectPageInfo(()->mapper.searchArticle(searchContentDto));

        return articleVo;
    }
    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    @Override
    public void delArticleByIds(Long[] ids) {
        Arrays.asList(ids).forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.FAIL,"数据库不存在该文章"+a);
            }
        });
       mapper.deleteBatchIds(Arrays.asList(ids));
    }

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
    @Override
    public void editEnabled(BaseUpdateStatusDto baseUpdateStatusDto) {

        SysUserPo userPo = securityUtil.getCurrUser();
        Long id = Arrays.asList(baseUpdateStatusDto.getId()).get(0);
        //获取该文章位置下(除帮助中心外)的所有文章的启用状态，若有启用状态的置为禁用--0/false
        String location = mapper.selectById(id).getArticleLocation();
        if (!location.equals(ArticleLocationEnum.HELP_CENTER.getName())) {
            if (baseUpdateStatusDto.getEnabled()) {
                //获取该广告下的所有启用广告并禁用
                List<MmArticlePo> articlePos = mapper.selectList(new QueryWrapper<MmArticlePo>().lambda().and(obj ->
                        obj.eq(MmArticlePo::getEnabled, true).eq(MmArticlePo::getArticleLocation, location)));
                articlePos.forEach(b -> {
                    b.setEnabled(false);
                    mapper.updateById(b);
                });
            }
        }
        MmArticlePo articlePo = mapper.selectById(id);
        articlePo.setEnabled(baseUpdateStatusDto.getEnabled()).setUpdateBy(userPo.getUsername());
        mapper.updateById(articlePo);
    }

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
    @Override
    public Map<String, String> findArticleLocations() {

        //存储文章位置
        Map<String, String> locations = Maps.newHashMap();
        List<ArticleLocationEnum> articleLocationEnums = Arrays.stream(ArticleLocationEnum.values()).collect(Collectors.toList());
        articleLocationEnums.forEach(a -> {
            locations.put(String.valueOf(a.getId()), a.getName());
        });
        return locations;
    }

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
    @Override
    public List<FindArticleContentVo> findArticleContent(Integer type) {

        ArticleLocationEnum locationEnum = ArticleLocationEnum.getArticalLocationById(type);

        if (locationEnum == null){
            throw new ServiceException(ResultCode.NO_EXISTS,String.format("所传的值(type:%s)在枚举类中不存在！",type));
        }

        List<FindArticleContentVo> findArticleContentVos = mapper.findArticleContent(locationEnum.getName());

        return findArticleContentVos;
    }

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
    @Override
    public String getInvitationArticle() {

        return mapper.getInvitationArticle();
    }

}
