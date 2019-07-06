package com.chauncy.web.api.manage.message.interact;


import com.chauncy.data.dto.manage.message.interact.select.SearchFeedBackDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.interact.feedBack.SearchFeedBackVo;
import com.chauncy.message.interact.service.IMmFeedBackService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * app用户反馈列表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-06
 */
@RestController
@RequestMapping("/manage/message/interact")
@Api(tags = "平台—互动管理-意见反馈")
public class MmFeedBackApi {

    @Autowired
    private IMmFeedBackService service;

    /**
     * 条件查询意见反馈
     * @param searchFeedBackDto
     * @return
     */
    @PostMapping("/searchFeedBack")
    @ApiOperation("条件查询意见反馈")
    public JsonViewData<PageInfo<SearchFeedBackVo>> searchFeedBack(@RequestBody @ApiParam(required = true,name = "searchFeedBackDto",value = "条件查询意见反馈")
                                       @Validated SearchFeedBackDto searchFeedBackDto){

        return new JsonViewData(service.searchFeedBack(searchFeedBackDto));
    }

}
