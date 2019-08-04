package com.chauncy.web.api.manage.ship;

import com.chauncy.common.constant.logistics.LogisticsContants;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.ship.add.AddShipTemplateDto;
import com.chauncy.data.dto.manage.ship.delete.DelListDto;
import com.chauncy.data.dto.manage.ship.select.SearchPlatTempDto;
import com.chauncy.data.dto.manage.ship.update.EnableTemplateDto;
import com.chauncy.data.dto.manage.ship.update.VerifyTemplateDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.ship.PlatTemplateVo;
import com.chauncy.message.easemob.comm.OrgInfo;
import com.chauncy.product.service.IPmShippingTemplateService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cheng
 * @create 2019-06-24 10:42
 *
 * 平台运费模版管理
 */
@Api(tags = "商家端_运费模版管理")
@RestController
@RequestMapping("/manage/ship")
@Slf4j
public class ShipTemplateApi {

    @Autowired
    private IPmShippingTemplateService service;

    /**
     * 添加或修改运费模版
     *
     * @param addShipTemplateDto
     * @return
     */
    @PostMapping("/addOrUpdateShipTemplate")
    @ApiOperation("添加或修改运费模版")
    public JsonViewData addOrUpdateShipTemplate(@RequestBody @Validated @ApiParam(required = true,name = "添加或修改运费模版",
            value = "addShipTemplateDto") AddShipTemplateDto addShipTemplateDto){

        service.addShipTemplate(addShipTemplateDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 批量删除按金额计算运费列表
     *
     * @param delListDto
     * @return
     */
    @PostMapping("/delListByIds")
    @ApiOperation("批量删除指定地区运费计算列表")
    public JsonViewData delByIds(@RequestBody @ApiParam(required = true,name = "指定地区计算运费ID集合",value = "amountIds")
                                 @Validated DelListDto delListDto){

        service.delByIds(delListDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 批量删除运费模版
     *
     * @param templateIds
     * @return
     */
    @GetMapping("/delTemplateByIds/{templateIds}")
    @ApiOperation("批量删除运费模版")
    public JsonViewData delTemplateByIds(@ApiParam(required = true,name = "运费模版ID集合",value = "templateIds")
                                         @PathVariable Long[] templateIds){

        service.delTemplateByIds(templateIds);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 条件查询平台运费模版字段
     *
     * @param searchPlatTempDto
     * @return
     */
    @PostMapping("/searchTemplateByConditions")
    @ApiOperation("条件查询运费信息")
    public JsonViewData<PageInfo<PlatTemplateVo>> SearchPlatTempByConditions(@RequestBody @Validated @ApiParam(required = true,name = "添加运费模版",
            value = "searchPlatTempDto") SearchPlatTempDto searchPlatTempDto){

        return new JsonViewData(service.searchPlatTempByConditions(searchPlatTempDto));
    }

    /**
     * 批量修改模版的审核状态
     *
     * @param verifyTemplateDto
     * @return
     */
    @PostMapping("/verifyTemplate")
    @ApiOperation("批量修改模版的审核状态")
    public JsonViewData verifyTemplate(@RequestBody @Validated @ApiParam(required = true,name = "verifyTemplateDto",value ="批量修改模版的审核状态")
                                               VerifyTemplateDto verifyTemplateDto){
        service.verifyTemplate(verifyTemplateDto);

        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 批量启用或禁用模版
     *
     * @param enableTemplateDto
     * @return
     */
    @PostMapping("/enable")
    @ApiOperation("批量启用或禁用模版")
    public JsonViewData enableTemplate(@RequestBody @Validated @ApiParam(required = true,name = "verifyTemplateDto",value ="批量修改模版的审核状态")
                                               EnableTemplateDto enableTemplateDto){
        service.enableTemplate(enableTemplateDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }
}
