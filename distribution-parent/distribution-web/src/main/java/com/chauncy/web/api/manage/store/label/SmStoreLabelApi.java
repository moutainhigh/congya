package com.chauncy.web.api.manage.store.label;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.store.label.SmStoreLabelPo;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreLabelDto;
import com.chauncy.data.dto.manage.store.select.StoreLabelSearchDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.label.SmStoreLabelVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.label.service.ISmStoreLabelService;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/6/15 16:42
 */
@Api(tags = "店铺标签管理接口")
@RestController
@RequestMapping("/common/store/label")
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


        return smStoreLabelService.saveStoreLabe(storeBaseInfoDto);
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


        return smStoreLabelService.editStoreLabe(storeBaseInfoDto);
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


        return smStoreLabelService.findById(id);

    }


    /**
     * 条件查询
     * @param storeLabelSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据标签ID、标签名称、创建时间查询")
    @GetMapping("/searchAll")
    public JsonViewData searchAll(@ModelAttribute StoreLabelSearchDto storeLabelSearchDto) {

        PageInfo<SmStoreLabelVo> smStoreBaseVoPageInfo = smStoreLabelService.searchAll(storeLabelSearchDto);
        return setJsonViewData(smStoreBaseVoPageInfo);
    }


}
