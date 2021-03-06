package com.chauncy.web.api.manage.message.information.category;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.InformationCategoryDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.message.information.category.service.IMmInformationCategoryService;
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
 * @since 2019/6/25 17:59
 */
@Api(tags = "平台_资讯_分类管理接口")
@RestController
@RequestMapping("/manage/information/category")
@Slf4j
public class MmInformationCategoryApi extends BaseApi {
    
    @Autowired
    private IMmInformationCategoryService mmInformationCategoryService;


    /**
     * 保存资讯分类信息
     * @param informationCategoryDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存资讯分类信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "informationCategoryDto", value = "资讯分类信息")
                                     InformationCategoryDto informationCategoryDto) {

        mmInformationCategoryService.saveInformationCategory(informationCategoryDto);
        return new JsonViewData(ResultCode.SUCCESS, "添加成功");
    }

    /**
     * 编辑资讯分类信息
     * @param informationCategoryDto
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑资讯分类信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData edit(@Validated(IUpdateGroup.class) @RequestBody @ApiParam(required = true, name = "informationCategoryDto", value = "资讯分类信息")
                                     InformationCategoryDto informationCategoryDto) {

        mmInformationCategoryService.editInformationCategory(informationCategoryDto);
        return new JsonViewData(ResultCode.SUCCESS, "编辑成功");
    }


    /**
     * 根据ID查找资讯分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找资讯分类", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData<InformationCategoryVo> findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationCategoryService.findById(id));

    }


    /**
     * 条件查询
     * @param baseSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据分类ID、分类名称查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<InformationCategoryVo>> searchPaging(@RequestBody BaseSearchDto baseSearchDto) {

        PageInfo<InformationCategoryVo> informationCategoryVoPageInfo = mmInformationCategoryService.searchPaging(baseSearchDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                informationCategoryVoPageInfo);

    }

    /**
     * 查询所有的资讯分类
     * @return
     */
    @ApiOperation(value = "查询所有的资讯分类", notes = "查询所有的资讯分类")
    @GetMapping("/selectAll")
    public JsonViewData<InformationCategoryVo> searchAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                mmInformationCategoryService.selectAll());
    }

    @PostMapping("/editStatusBatch")
    @ApiOperation(value = "批量禁用启用")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editStatusBatch(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "id、修改的状态值")
                                                BaseUpdateStatusDto baseUpdateStatusDto) {

        mmInformationCategoryService.editStatusBatch(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改状态成功");
    }

    /**
     * 批量删除分类
     *
     * @param ids
     */
    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @GetMapping("/delByIds/{ids}")
    public JsonViewData delByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                 @PathVariable Long[] ids) {

        mmInformationCategoryService.delInformationCategoryByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS, "删除分类成功");
    }

}
