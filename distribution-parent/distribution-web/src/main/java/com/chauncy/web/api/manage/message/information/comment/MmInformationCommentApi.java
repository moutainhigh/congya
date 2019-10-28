package com.chauncy.web.api.manage.message.information.comment;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.AddInformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.SearchInfoCommentDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.InformationContentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/6/30 16:26
 */
@Api(tags = "平台_资讯_评论管理接口")
@RestController
@RequestMapping("/manage/information/comment")
@Slf4j
public class MmInformationCommentApi extends BaseApi {

    @Autowired
    private IMmInformationCommentService mmInformationCommentService;

    @Autowired
    private IMmInformationService mmInformationService;

    @Autowired
    private SecurityUtil securityUtil;

   /**
    * @Author yeJH
    * @Date 2019/10/21 21:27
    * @Description 查询所有资讯评论
    *
    * @Update yeJH
    *
    * @param  searchInfoCommentDto
    * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo>>
    **/
    @ApiOperation(value = "查询所有资讯评论列表", notes = "根据评论用户id、评论时间、评论状态查询")
    @PostMapping("/searchInfoComment")
    public JsonViewData<PageInfo<InformationCommentVo>> searchInfoComment(
            @ApiParam(required = true, name = "searchInfoCommentDto", value = "查询条件")
            @Validated @RequestBody SearchInfoCommentDto searchInfoCommentDto) {

        PageInfo<InformationCommentVo> informationCommentVoPageInfo =
                mmInformationCommentService.searchInfoComment(searchInfoCommentDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationCommentVoPageInfo);

    }

    /**
     * @Author yeJH
     * @Date 2019/10/21 23:44
     * @Description 根据资讯id查询资讯信息
     *
     * @Update yeJH
     *
     * @param  infoId 资讯id
     * @return com.chauncy.data.vo.JsonViewData<com.chauncy.data.vo.manage.message.information.InformationContentVo>
     **/
    @ApiOperation(value = "评论管理-平台查看资讯", notes = "根据评资讯id查询")
    @GetMapping("/getBaseInformation/{infoId}")
    public JsonViewData<InformationContentVo> getBaseInformation(@PathVariable(value = "infoId") Long infoId) {

        InformationContentVo informationContentVo = mmInformationService.getBaseInformation(infoId);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功", informationContentVo);

    }

    /**
     * 条件查询
     * @param informationCommentDto
     * @return
     */
    @ApiOperation(value = "根据资讯id查询评论", notes = "根据ID查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationViceCommentVo>> searchPaging( @Validated @RequestBody InformationCommentDto informationCommentDto) {

        PageInfo<InformationViceCommentVo> informationViceCommentVoPageInfo = mmInformationCommentService.searchPaging(informationCommentDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationViceCommentVoPageInfo);

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

    /**
     * 保存后台用户资讯评论
     *
     * @param addInformationCommentDto
     * @return
     */
    @ApiOperation(value = "保存后台用户资讯评论", notes = "保存后台用户资讯评论")
    @PostMapping("/saveInfoComment")
    public JsonViewData saveInfoComment(@Valid @RequestBody AddInformationCommentDto addInformationCommentDto) {

        mmInformationCommentService.saveInfoComment(addInformationCommentDto, Long.parseLong(securityUtil.getCurrUser().getId()));
        return new JsonViewData(ResultCode.SUCCESS, "保存成功");
    }

}
