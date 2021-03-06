package com.chauncy.web.api.manage.message.information.label;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationLabelDto;
import com.chauncy.data.dto.manage.message.information.select.InformationLabelSearchDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.label.InformationLabelVo;
import com.chauncy.message.information.label.service.IMmInformationLabelService;
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
 * @since 2019/6/25 17:58
 */
@Api(tags = "平台_资讯_标签管理接口")
@RestController
@RequestMapping("/manage/information/label")
@Slf4j
public class MmInformationLabelApi extends BaseApi {

    @Autowired
    private IMmInformationLabelService mmInformationLabelService;

    /**
     * 保存资讯标签信息
     * @param informationLabelDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存资讯标签信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "informationLabelDto", value = "资讯标签信息")
                                     InformationLabelDto informationLabelDto) {

        mmInformationLabelService.saveInformationLabel(informationLabelDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }

    /**
     * 编辑资讯标签信息
     * @param informationLabelDto
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑资讯标签信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData edit(@Validated(IUpdateGroup.class) @RequestBody @ApiParam(required = true, name = "informationLabelDto", value = "资讯标签信息")
                                         InformationLabelDto informationLabelDto) {

        mmInformationLabelService.editInformationLabel(informationLabelDto);
        return new JsonViewData(ResultCode.SUCCESS, "编辑成功");
    }


    /**
     * 根据ID查找资讯标签
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找资讯标签", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData<InformationLabelVo> findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationLabelService.findById(id));

    }


    /**
     * 条件查询
     * @param informationLabelSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据标签ID、标签名称查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationLabelVo>> searchPaging(@Valid @RequestBody InformationLabelSearchDto informationLabelSearchDto) {

        PageInfo<InformationLabelVo> informationLabelVoPageInfo = mmInformationLabelService.searchPaging(informationLabelSearchDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationLabelVoPageInfo);

    }

    /**
     * 查询所有的资讯标签
     * @return
     */
    @ApiOperation(value = "查询所有的资讯标签", notes = "查询所有的资讯标签")
    @GetMapping("/selectAll")
    public JsonViewData<InformationLabelVo> searchAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationLabelService.selectAll());
    }

    @PostMapping("/editStatusBatch")
    @ApiOperation(value = "批量禁用启用")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editStatusBatch(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "id、修改的状态值")
                                                BaseUpdateStatusDto baseUpdateStatusDto) {

        mmInformationLabelService.editStatusBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改状态成功");
    }


    /**
     * 批量删除标签
     *
     * @param ids
     */
    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                 @PathVariable Long[] ids) {

        mmInformationLabelService.delInformationLabelByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS, "删除标签成功");
    }


}
