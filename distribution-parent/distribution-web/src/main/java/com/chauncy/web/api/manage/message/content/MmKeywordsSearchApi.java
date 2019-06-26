package com.chauncy.web.api.manage.message.content;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.content.add.AddKeyWordsDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.content.KeyWordVo;
import com.chauncy.message.content.service.IMmKeywordsSearchService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 热搜关键字管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/manage/message/content")
@Api(tags = "平台_内容管理_热搜关键字管理")
public class MmKeywordsSearchApi {

    @Autowired
    private IMmKeywordsSearchService service;

    /**
     * 获取关键字类型
     *
     * @return
     */
    @GetMapping("/selectKeyWordType")
    @ApiOperation("获取关键字类型")
    public JsonViewData selectKeyWordType(){

        return new JsonViewData(service.selectKeyWordType());
    }

    /**
     * 添加关键字
     *
     * @param addKeyWordsDto
     * @return
     */
    @PostMapping("/addKeyWords")
    @ApiOperation("添加关键字")
    public JsonViewData addKeyWords(@RequestBody @ApiParam(required = true,name="addKeyWordsDto",value="添加关键字")
                                    @Validated AddKeyWordsDto addKeyWordsDto){
        service.addKeyWords(addKeyWordsDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 修改关键字
     *
     * @param updateKeyWordsDto
     * @return
     */
    @PostMapping("/updateKeyWords")
    @ApiOperation("修改关键字")
    public JsonViewData updateKeyWords(@RequestBody @ApiParam(required = true,name="updateKeyWordsDto",value="修改关键字")
                                    @Validated AddKeyWordsDto updateKeyWordsDto){
        service.updateKeyWords(updateKeyWordsDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 批量删除启动页
     *
     * @param ids
     * @return
     */
    @GetMapping("/delKeyWordsByIds/{ids}")
    @ApiOperation("批量删除启动页")
    public JsonViewData delKeyWordsByIds(@ApiParam(required = true,name = "ids",value = "关键字ID集合")
                                         @PathVariable Long[] ids){
        service.delKeyWordsByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 条件查询关键字信息
     *
     * @param searchContentDto
     * @return
     */
    @PostMapping("/searchKeyWords")
    @ApiOperation("条件查询关键字信息")
    public JsonViewData<PageInfo<KeyWordVo>> searchKeyWords(@RequestBody @Validated @ApiParam(required = true, name = "searchContentDto", value = "查找关键字信息")
                                               SearchContentDto searchContentDto){

        return new JsonViewData<>(service.searchKeyWords(searchContentDto));
    }
}
