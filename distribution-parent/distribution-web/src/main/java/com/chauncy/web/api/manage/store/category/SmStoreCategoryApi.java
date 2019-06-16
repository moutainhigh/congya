package com.chauncy.web.api.manage.store.category;

import com.chauncy.data.dto.manage.store.add.StoreCategoryDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.store.category.service.ISmStoreCategoryService;
import com.chauncy.store.label.service.ISmStoreLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/6/16 14:50
 */
@Api(tags = "店铺分类管理接口")
@RestController
@RequestMapping("/common/store/category")
@Slf4j
public class SmStoreCategoryApi {


    @Autowired
    private ISmStoreCategoryService smStoreCategoryService;


    /**
     * 保存店铺分类信息
     * @param storeCategoryDto
     * @param result
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存店铺分类信息")
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "storeLabelDto", value = "店铺标签信息")
                                     StoreCategoryDto storeCategoryDto, BindingResult result) {


        return smStoreCategoryService.saveStoreCategory(storeCategoryDto);
    }
}
