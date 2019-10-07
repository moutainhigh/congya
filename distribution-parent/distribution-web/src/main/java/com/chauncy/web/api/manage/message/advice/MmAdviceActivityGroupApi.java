package com.chauncy.web.api.manage.message.advice;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.advice.tab.association.add.SaveActivityGroupAdviceDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchActivityGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.association.search.SearchActivityGroupDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo;
import com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGroupsVo;
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
 * @create 2019-09-22 21:10
 *
 * 广告之满减、积分活动管理
 */
@RestController
@Api(tags = "平台_广告运营管理_满减、积分活动管理")
@RequestMapping("/manage/message/advice/activity")
public class MmAdviceActivityGroupApi extends BaseApi {

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
    public JsonViewData<PageInfo<SearchActivityGroupsVo>> searchActivityGroup(@RequestBody @ApiParam(required = true,value = "searchActivityGroupDto",name = "分页查询活动分组条件")
                                                               @Validated SearchActivityGroupDto searchActivityGroupDto){

        return setJsonViewData(service.searchActivityGroup(searchActivityGroupDto));
    }

    /**
     * @Author chauncy
     * @Date 2019-09-24 10:33
     * @Description //条件分页查询参与对应活动的商品信息
     *
     * @Update chauncy
     *
     * @Param [searchActivityGoodsDto]
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.association.acticity.SearchActivityGoodsVo>>
     **/
    @PostMapping("/searchActivityGoods")
    @ApiOperation("条件分页查询参与对应活动的商品信息")
    public JsonViewData<PageInfo<SearchActivityGoodsVo>> searchActivityGoods(@RequestBody @ApiParam(required = true,value = "searchActivityGoodsDto",name="分页获取对应的活动商品信息")
                                                                             @Validated SearchActivityGoodsDto searchActivityGoodsDto){

        return setJsonViewData(service.searchActivityGoods(searchActivityGoodsDto));
    }

    /**
     * @Author chauncy
     * @Date 2019-09-22 21:26
     * @Description //保存积分、满减活动广告
     *
     * @Update chauncy
     *
     * @Param [saveActivityGroupAdviceDto]
     * @return com.chauncy.data.vo.JsonViewData
     **/
    @PostMapping("/saveActivityGroupAdvice")
    @ApiOperation("保存积分、满减活动广告")
    public JsonViewData saveActivityGroupAdvice(@RequestBody @ApiParam(required = true,value = "saveActivityGroupAdviceDto",name = "保存积分、满减活动广告")
                                                @Validated SaveActivityGroupAdviceDto saveActivityGroupAdviceDto){
        service.saveActivityGroupAdvice(saveActivityGroupAdviceDto);

        return setJsonViewData(ResultCode.SUCCESS);
    }


}
