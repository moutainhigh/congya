package com.chauncy.web.api.manage.message.content;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.content.add.AddBootPageDto;
import com.chauncy.data.dto.manage.message.content.select.search.SearchContentDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.content.BootPageVo;
import com.chauncy.message.content.service.IMmBootPageService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 启动页管理 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/manage/message/content")
@Api(tags = "平台_内容管理_引导页管理")
public class MmBootPageApi {

    @Autowired
    private IMmBootPageService service;

    /**
     * 添加启动页
     *
     * @param addBootPageDto
     * @return
     */
    @PostMapping("/addBootPage")
    @ApiOperation("添加引导页")
    public JsonViewData addBootPage(@RequestBody @ApiParam(required = true,name="添加启动页Dto",value = "addBootPage")
                                    @Validated AddBootPageDto addBootPageDto){
        service.addBootPage(addBootPageDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 修改启动页
     *
     * @param updateBootPage
     * @return
     */
    @PostMapping("/updateBootPage")
    @ApiOperation("添加引导页")
    public JsonViewData updateBootPage(@RequestBody @ApiParam(required = true,name="添加启动页Dto",value = "addBootPage")
                                    @Validated(IUpdateGroup.class) AddBootPageDto updateBootPage){
        service.updateBootPage(updateBootPage);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 批量删除启动页
     * @param ids
     * @return
     */
    @GetMapping("/delBootPageByIds/{ids}")
    @ApiOperation("批量删除引导页")
    public JsonViewData delBootPageByIds(@ApiParam(required = true,name = "ids",value = "启动页集合")
                                         @PathVariable Long[] ids){
        service.delBootPageByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 查找启动页
     *
     * @param searchContentDto
     * @return
     */
    @PostMapping("/searchPages")
    @ApiOperation("查找引导页")
    public JsonViewData<PageInfo<BootPageVo>> searchPages(@RequestBody @Validated @ApiParam(required = true, name = "searchContentDto", value = "查找文章")
                                                                  SearchContentDto searchContentDto) {
        return new JsonViewData(service.searchPages(searchContentDto));
    }

}
