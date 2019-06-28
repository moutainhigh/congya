package com.chauncy.web.api.manage.message.information.sensitive;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationSensitiveDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.sensitive.InformationSensitiveVo;
import com.chauncy.message.information.sensitive.service.IMmInformationSensitiveService;
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
 * @since 2019/6/26 13:03
 */
@Api(tags = "平台_店铺资讯敏感词管理接口")
@RestController
@RequestMapping("/manage/information/sensitive")
@Slf4j
public class MmInformationSensitiveApi  extends BaseApi {

    @Autowired
    private IMmInformationSensitiveService mmInformationSensitiveService;


    /**
     * 保存店铺资讯敏感词信息
     * @param informationSensitiveDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存店铺资讯敏感词信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "informationSensitiveDto", value = "店铺资讯敏感词信息")
                                     InformationSensitiveDto informationSensitiveDto) {

        mmInformationSensitiveService.saveInformationSensitive(informationSensitiveDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }

    /**
     * 编辑店铺资讯敏感词信息
     * @param informationSensitiveDto
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑店铺资讯敏感词信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData edit(@Validated(IUpdateGroup.class) @RequestBody @ApiParam(required = true, name = "informationSensitiveDto", value = "店铺资讯敏感词信息")
                                         InformationSensitiveDto informationSensitiveDto) {

        mmInformationSensitiveService.editInformationSensitive(informationSensitiveDto);
        return new JsonViewData(ResultCode.SUCCESS, "编辑成功");
    }


    /**
     * 根据ID查找店铺资讯敏感词
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找店铺资讯敏感词", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData<InformationSensitiveVo> findById(@ApiParam(required = true, value = "id")
                                                        @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationSensitiveService.findById(id));

    }


    /**
     * 条件查询
     * @param baseSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据敏感词ID、敏感词名称查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationSensitiveVo>> searchPaging(@RequestBody BaseSearchDto baseSearchDto) {

        PageInfo<InformationSensitiveVo> informationSensitiveVoPageInfo = mmInformationSensitiveService.searchPaging(baseSearchDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationSensitiveVoPageInfo);

    }

    @PostMapping("/editStatusBatch")
    @ApiOperation(value = "批量禁用启用")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editStatusBatch(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "id、修改的状态值")
                                                BaseUpdateStatusDto baseUpdateStatusDto) {

        mmInformationSensitiveService.editStatusBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改状态成功");
    }


    /**
     * 批量删除敏感词
     *
     * @param ids
     */
    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                 @PathVariable Long[] ids) {

        mmInformationSensitiveService.delInformationSensitiveByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS, "删除敏感词成功");
    }

}
