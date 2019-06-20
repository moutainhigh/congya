package com.chauncy.web.api.manage.store.category;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreCategoryDto;
import com.chauncy.data.dto.manage.store.select.StoreCategorySearchDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.category.SmStoreCategoryVo;
import com.chauncy.store.category.service.ISmStoreCategoryService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
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
 * @since 2019/6/16 14:50
 */
@Api(tags = "平台_店铺分类管理接口")
@RestController
@RequestMapping("/manage/store/category")
@Slf4j
public class SmStoreCategoryApi extends BaseApi {


    @Autowired
    private ISmStoreCategoryService smStoreCategoryService;


    /**g
     * 保存店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存店铺分类信息")
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "storeCategoryDto", value = "店铺分类信息")
                                     StoreCategoryDto storeCategoryDto) {

        return new JsonViewData(ResultCode.SUCCESS, "添加成功",
                smStoreCategoryService.saveStoreCategory(storeCategoryDto));
    }
    /**
     * 编辑店铺分类信息
     * @param storeCategoryDto
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑店铺分类信息")
    public JsonViewData edit(@Valid @RequestBody @ApiParam(required = true, name = "storeCategoryDto", value = "店铺分类信息")
                                     StoreCategoryDto storeCategoryDto) {


        return new JsonViewData(ResultCode.SUCCESS, "编辑成功",
                smStoreCategoryService.editStoreCategory(storeCategoryDto));
    }


    @PostMapping("/editStoreStatus")
    @ApiOperation(value = "批量修改店铺分类状态")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData editStoreStatus(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "店铺分类id、修改的状态值")
                                                BaseUpdateStatusDto baseUpdateStatusDto) {

        smStoreCategoryService.editCategoryStatus(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改店铺分类状态成功");
    }



    /**
     * 根据ID查找店铺分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找店铺分类", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreCategoryService.findById(id));

    }


    /**
     * 条件查询
     * @param storeCategorySearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据分类ID、分类名称、创建时间查询")
    @PostMapping("/searchPaging")
    public JsonViewData searchPaging(@RequestBody StoreCategorySearchDto storeCategorySearchDto) {

        PageInfo<SmStoreCategoryVo> smStoreBaseVoPageInfo = smStoreCategoryService.searchPaging(storeCategorySearchDto);
        return setJsonViewData(smStoreBaseVoPageInfo);
    }

    /**
     * 查询所有的店铺分类
     * @return
     */
    @ApiOperation(value = "查询所有的店铺分类", notes = "查询所有的店铺分类")
    @GetMapping("/searchAll")
    public JsonViewData selectAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功", smStoreCategoryService.selectAll());
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

        smStoreCategoryService.delStoreCategoryByIds(ids);
        return new JsonViewData(ResultCode.SUCCESS, "批量删除分类成功");
    }
}
