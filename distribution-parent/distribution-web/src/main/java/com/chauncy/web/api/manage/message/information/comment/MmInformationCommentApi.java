package com.chauncy.web.api.manage.message.information.comment;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @since 2019/6/30 16:26
 */
@Api(tags = "平台_店铺资讯评论管理接口")
@RestController
@RequestMapping("/manage/information/comment")
@Slf4j
public class MmInformationCommentApi extends BaseApi {

    @Autowired
    private IMmInformationCommentService mmInformationCommentService;

    /**
     * 条件查询
     * @param baseSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据分类ID、分类名称查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationCommentVo>> searchPaging(@RequestBody BaseSearchDto baseSearchDto) {

        PageInfo<InformationCommentVo> informationCategoryVoPageInfo = mmInformationCommentService.searchPaging(baseSearchDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationCategoryVoPageInfo);

    }

}
