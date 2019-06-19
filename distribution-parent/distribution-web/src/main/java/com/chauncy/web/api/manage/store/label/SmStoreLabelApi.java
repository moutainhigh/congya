package com.chauncy.web.api.manage.store.label;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.store.add.StoreLabelDto;
import com.chauncy.data.dto.manage.store.select.StoreLabelSearchDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;
import com.chauncy.store.label.service.ISmStoreLabelService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/6/15 16:42
 */
@Api(tags = "店铺标签管理接口")
@RestController
@RequestMapping("/manage/store/label")
@Slf4j
public class SmStoreLabelApi extends BaseApi {


    @Autowired
    private ISmStoreLabelService smStoreLabelService;

    /**
     * 保存店铺标签信息
     * @param storeBaseInfoDto
     * @param result
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存店铺标签信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "storeLabelDto", value = "店铺标签信息")
                                     StoreLabelDto storeBaseInfoDto, BindingResult result) {


        return new JsonViewData(ResultCode.SUCCESS, "添加成功",
                smStoreLabelService.saveStoreLabel(storeBaseInfoDto));
    }

    /**
     * 编辑店铺标签信息
     * @param storeBaseInfoDto
     * @param result
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑店铺标签信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData edit(@Validated(IUpdateGroup.class) @RequestBody @ApiParam(required = true, name = "storeLabelDto", value = "店铺标签信息")
                                     StoreLabelDto storeBaseInfoDto, BindingResult result) {


        return new JsonViewData(ResultCode.SUCCESS, "编辑成功",
                smStoreLabelService.editStoreLabel(storeBaseInfoDto));
    }


    /**
     * 根据ID查找店铺标签
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找店铺标签", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreLabelService.findById(id));

    }


    /**
     * 条件查询
     * @param storeLabelSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据标签ID、标签名称、创建时间查询")
    @PostMapping("/searchPaging")
    public JsonViewData searchPaging(@RequestBody StoreLabelSearchDto storeLabelSearchDto) {

        PageInfo<SmStoreLabelVo> smStoreBaseVoPageInfo = smStoreLabelService.searchPaging(storeLabelSearchDto);
        return setJsonViewData(smStoreBaseVoPageInfo);

    }

    /**
     * 查询所有的店铺标签
     * @return
     */
    @ApiOperation(value = "查询所有的店铺标签", notes = "查询所有的店铺标签")
    @GetMapping("/selectAll")
    public JsonViewData searchAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreLabelService.selectAll());
    }


}
