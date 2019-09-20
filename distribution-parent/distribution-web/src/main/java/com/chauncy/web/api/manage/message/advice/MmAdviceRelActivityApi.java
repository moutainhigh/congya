package com.chauncy.web.api.manage.message.advice;

import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchActivityGroupDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.message.advice.IMmAdviceRelTabAssociationService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-09-20 09:03
 *
 * 活动广告配置
 */
@RestController
@Api(tags = "平台_广告运营管理_活动管理")
@RequestMapping("/manage/message/advice/activity")
public class MmAdviceRelActivityApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabAssociationService service;

    /**
     * @Author chauncy
     * @Date 2019-09-20 09:28
     * @Description //条件分页查询活动分组信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGroupDto]
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.BaseVo>>
     **/
    @PostMapping("/searchActivityGroup")
    @ApiOperation("条件分页查询活动分组信息")
    private JsonViewData<PageInfo<BaseVo>> searchActivityGroup(@RequestBody @ApiParam(required = true,value = "searchActivityGroupDto",name = "分页查询活动分组条件")
                                                                @Validated SearchActivityGroupDto searchActivityGroupDto){

        return setJsonViewData(service.searchActivityGroup(searchActivityGroupDto));
    }


}
