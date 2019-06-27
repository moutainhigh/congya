package com.chauncy.web.api.manage.message.information.category;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.information.add.InformationCategoryDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.message.information.category.service.IInformationCategoryService;
import com.chauncy.web.base.BaseApi;
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
 * @since 2019/6/25 17:59
 */
@Api(tags = "平台_店铺资讯分类管理接口")
@RestController
@RequestMapping("/manage/information/category")
@Slf4j
public class InformationCategoryApi extends BaseApi {
    
    @Autowired
    private IInformationCategoryService smInformationCategoryService;


    /**
     * 保存店铺资讯分类信息
     * @param informationCategoryDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存店铺资讯分类信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "informationCategoryDto", value = "店铺资讯分类信息")
                                     InformationCategoryDto informationCategoryDto) {

        smInformationCategoryService.saveInformationCategory(informationCategoryDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }

    /**
     * 编辑店铺资讯分类信息
     * @param informationLabelDto
     * @return
     */
   /* @PostMapping("/edit")
    @ApiOperation(value = "编辑店铺资讯分类信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData edit(@Validated(IUpdateGroup.class) @RequestBody @ApiParam(required = true, name = "informationLabelDto", value = "店铺资讯分类信息")
                                     InformationLabelDto informationLabelDto) {

        smInformationLabelService.editInformationLabel(informationLabelDto);
        return new JsonViewData(ResultCode.SUCCESS, "编辑成功");
    }


    *//**
     * 根据ID查找店铺资讯分类
     *
     * @param value
     * @return
     *//*
    @ApiOperation(value = "查找店铺资讯分类", notes = "根据ID查找")
    @GetMapping("/findById/{value}")
    public JsonViewData findById(@ApiParam(required = true, value = "value")
                                 @PathVariable Long value) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smInformationLabelService.findById(value));

    }


    *//**
     * 条件查询
     * @param informationLabelSearchDto
     * @return
     *//*
    @ApiOperation(value = "条件查询", notes = "根据分类ID、分类名称查询")
    @PostMapping("/searchPaging")
    public JsonViewData searchPaging(@RequestBody InformationLabelSearchDto informationLabelSearchDto) {

        PageInfo<InformationLabelVo> informationLabelVoPageInfo = smInformationLabelService.searchPaging(informationLabelSearchDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationLabelVoPageInfo);

    }

    *//**
     * 查询所有的店铺资讯分类
     * @return
     *//*
    @ApiOperation(value = "查询所有的店铺资讯分类", notes = "查询所有的店铺资讯分类")
    @GetMapping("/selectAll")
    public JsonViewData searchAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smInformationLabelService.selectAll());
    }

    *//**
     * 批量删除分类
     *
     * @param ids
     *//*
    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                 @PathVariable Long[] ids) {

        smInformationLabelService.delInformationLabelByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS, "批量删除分类成功");
    }
*/
}
