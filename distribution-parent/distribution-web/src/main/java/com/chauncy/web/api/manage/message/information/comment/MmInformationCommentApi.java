package com.chauncy.web.api.manage.message.information.comment;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/6/30 16:26
 */
@Api(tags = "平台_店铺资讯_评论管理接口")
@RestController
@RequestMapping("/manage/information/comment")
@Slf4j
public class MmInformationCommentApi extends BaseApi {

    @Autowired
    private IMmInformationCommentService mmInformationCommentService;

    /**
     * 条件查询
     * @param informationCommentDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据ID、名称查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationCommentVo>> searchPaging(@RequestBody InformationCommentDto informationCommentDto) {

        PageInfo<InformationCommentVo> informationCategoryVoPageInfo = mmInformationCommentService.searchPaging(informationCommentDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationCategoryVoPageInfo);

    }

    /**
     * 隐藏显示评论
     *
     * @param baseUpdateStatusDto
     */
    @PostMapping("/editStatusBatch")
    @ApiOperation(value = "隐藏显示评论")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editStatusBatch(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "id、修改的状态值")
                                                BaseUpdateStatusDto baseUpdateStatusDto) {

        mmInformationCommentService.editStatusBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改状态成功");
    }

    /**
     * 删除评论
     *
     * @param id
     */
    @ApiOperation(value = "删除属性", notes = "根据id删除")
    @GetMapping("/delById/{id}")
    public JsonViewData delById(@ApiParam(required = true, name = "id", value = "评论id")
                                 @PathVariable Long id) {

        mmInformationCommentService.delInfoCommentById(id);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");
    }

}
