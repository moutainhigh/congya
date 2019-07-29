package com.chauncy.web.api.manage.order.log;


import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.web.base.BaseApi;
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
 * 账目流水表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("/manage/finance")
@Api(tags = "平台_财务管理接口")
public class OmFinanceLogApi extends BaseApi {

    @Autowired
    private IOmAccountLogService omAccountLogService;


    /**
     * 平台流水
     * @param searchPlatformLogDto
     * @return
     */
    @ApiOperation(value = "查询平台流水", notes = "条件查询平台流水")
    @PostMapping("/searchPlatformLogPaging")
    public JsonViewData<PageInfo<SearchPlatformLogVo>> searchPlatformLogPaging(@RequestBody @ApiParam(required = true, name = "searchPlatformLogDto", value = "查询平台流水") @Validated
                                                                                           SearchPlatformLogDto searchPlatformLogDto) {

        PageInfo<SearchPlatformLogVo> searchPlatformLogVoPageInfo = omAccountLogService.searchPlatformLogPaging(searchPlatformLogDto);
        return setJsonViewData(searchPlatformLogVoPageInfo);
    }


}
