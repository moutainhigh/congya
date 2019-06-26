package com.chauncy.web.api.manage.message.content;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.content.add.AddArticleDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchArticleDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.chauncy.message.content.service.IMmArticleService;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/manage/message/content")
@Api(tags = "内容管理")
public class MmContentApi {

    @Autowired
    private IMmArticleService service;

    /**
     * 查找所有的文章位置
     *
     * @return
     */
    @GetMapping("/findLocations")
    @ApiOperation("查找所有的文章位置")
    public JsonViewData<List<String>> findLocations(){

        return new JsonViewData(service.findLocations());
    }
    /**
     * 添加文章
     *
     * @param addContentDto
     * @return
     */
    @PostMapping("/addArticle")
    @ApiOperation("添加文章")
    public JsonViewData addArticle(@RequestBody @Validated @ApiParam(required = true, name = "addContentDto", value = "添加文章管理Dto")
                                           AddArticleDto addContentDto) {
        service.addArticle(addContentDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    @GetMapping("/delArticleByIds/{ids}")
    @ApiOperation("批量删除文章")
    public JsonViewData delArticleByIds(@ApiParam(required = true,name = "ids",value = "文章ID集合") @PathVariable Long[] ids){
        service.delArticleByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS);
    }
    /**
     * 条件查询文章信息
     *
     * @param searchArticleDto
     * @return
     */
    @PostMapping("/searchArticle")
    @ApiOperation("条件查询文章信息")
    public JsonViewData<PageInfo<ArticleVo>> searchArticle(@RequestBody @Validated @ApiParam(required = true, name = "searchArticleDto", value = "查找文章")
                                                                   SearchArticleDto searchArticleDto){
        return new JsonViewData<PageInfo<ArticleVo>>(service.searchArticle(searchArticleDto));
    }
    /**
     * 更新文章
     *
     * @param updateArticleDto
     * @return
     */
    @PostMapping("/updateArticle")
    @ApiOperation("更新文章")
    public JsonViewData updateArticle(@RequestBody @Validated @ApiParam(required = true, name = "addContentDto", value = "修改文章管理Dto")
                                                  AddArticleDto updateArticleDto) {
        service.updateArticle(updateArticleDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }
}
