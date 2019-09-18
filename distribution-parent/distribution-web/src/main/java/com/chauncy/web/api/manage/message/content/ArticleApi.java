package com.chauncy.web.api.manage.message.content;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.content.add.AddArticleDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.content.ArticleVo;
import com.chauncy.message.content.service.IMmArticleService;
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
@Api(tags = "平台_内容管理_文章管理")
public class ArticleApi {

    @Autowired
    private IMmArticleService service;

    /**
     * 查找所有的文章位置
     *
     * @return
     */
    @GetMapping("/selectLocations")
    @ApiOperation("查找所有的文章位置")
    public JsonViewData<List<String>> selectLocations(){

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
     * @param searchContentDto
     * @return
     */
    @PostMapping("/searchArticle")
    @ApiOperation("条件查询文章信息")
    public JsonViewData<PageInfo<ArticleVo>> searchArticle(@RequestBody @Validated @ApiParam(required = true, name = "searchContentDto", value = "查找文章")
                                                                   SearchContentDto searchContentDto){
        return new JsonViewData<PageInfo<ArticleVo>>(service.searchArticle(searchContentDto));
    }
    /**
     * 更新文章
     *
     * @param updateArticleDto
     * @return
     */
    @PostMapping("/updateArticle")
    @ApiOperation("更新文章")
    public JsonViewData updateArticle(@RequestBody @Validated(IUpdateGroup.class) @ApiParam(required = true, name = "addContentDto", value = "修改文章管理Dto")
                                                  AddArticleDto updateArticleDto) {
        service.updateArticle(updateArticleDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * @Author chauncy
     * @Date 2019-09-18 11:08
     * @Description //批量启用或禁用,同一个文章位置只能有一个是启用状态
     *
     * @Update chauncy
     *
     * @Param [baseUpdateStatusDto]
     * @return com.chauncy.data.vo.JsonViewData
     **/
    @PostMapping("/editEnable")
    @ApiOperation("启用或禁用,没有批量启用/禁用")
    public JsonViewData editEnable(@Validated @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "启用禁用广告")
                                           BaseUpdateStatusDto baseUpdateStatusDto){
        service.editEnabled(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

}
