package com.chauncy.web.api.manage.product;


import com.chauncy.data.dto.manage.good.add.GoodAttributeDto;
import com.chauncy.data.dto.manage.good.add.GoodAttributeValueDto;
import com.chauncy.data.dto.manage.good.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.good.select.FindAttributeInfoByConditionDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.product.service.IPmGoodsAttributeValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品属性页面控制器
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Api(description = "商品属性管理接口")
@RestController
@RequestMapping("/pm-goods-attribute-po")
@Slf4j
/*@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}
)*/
public class PmGoodsAttributeApi {

    @Autowired
    private IPmGoodsAttributeService goodsAttributeService;

    @Autowired
    private IPmGoodsAttributeValueService valueService;

    //TODO 属性操作

    /**
     * 添加属性以及属性值
     *
     * @param goodsAttributeDto
     */
    @PostMapping("/saveAttribute")
    @ApiOperation(value = "保存商品属性（品牌、规格、服务等）")
    public JsonViewData saveAttribute(@RequestBody @Valid @ApiParam(required = true, name = "goodsAttributeDto", value = "属性信息") GoodAttributeDto goodsAttributeDto, BindingResult result) {

        return goodsAttributeService.saveAttribute(goodsAttributeDto);
    }

    /**
     * 根据id删除属性以及关联的值
     *
     * @param ids
     */
    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @DeleteMapping("/delAllByIds/{ids}")
    public JsonViewData deleteAttributeByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                             @PathVariable Long[] ids) {

        return goodsAttributeService.deleteAttributeByIds(ids);
    }


    /**
     * 更新属性基本信息
     *
     * @param goodsAttributeDto
     * @return
     */
    @ApiOperation(value = "更新属性", notes = "根据ID更新属性")
    @PostMapping("/editAttribute")
    public JsonViewData editAttribute(@RequestBody @Validated(IUpdateGroup.class) @ApiParam(required = true, name = "goodsAttributeDto", value = "属性信息")
                                              GoodAttributeDto goodsAttributeDto, BindingResult result) {
        return goodsAttributeService.edit(goodsAttributeDto);
    }

    /**
     * 根据ID查找属性以及关联属性值
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找属性信息", notes = "根据ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {

        return goodsAttributeService.findById(id);
    }

    /**
     * 根据条件查找属性信息
     *
     * @param findAttributeInfoByConditionDto
     * @param result
     * @return
     */
    @ApiOperation(value = "根据条件查找属性信息", notes = "1、根据type查询：\n" +
            "类型 1->平台服务说明管理类型 2->商家服务说明管理类型 3->平台活动说明管理类型  4->商品参数管理类型 5->标签管理类型 6->购买须知管理类型 7->规格管理类型 8->品牌管理\n" +
            "2、搜索查询：name、type、enabled")
    @PostMapping("/findByCondition")
    public JsonViewData findByCondition(@RequestBody @Valid @ApiParam(required = true, name = "findAttributeInfoByConditionDto", value = "属性列表查询条件") FindAttributeInfoByConditionDto findAttributeInfoByConditionDto,
                                        BindingResult result) {

        return goodsAttributeService.findByCondition(findAttributeInfoByConditionDto);
    }

    /**
     * 启用/禁用属性
     *
     * @param baseUpdateStatusDto
     * @return
     */
    @ApiOperation(value="启用或禁用属性")
    @PostMapping("/updateStatus")
    public JsonViewData updateStatus(@RequestBody @ApiParam(required = true,name ="baseUpdateStatusDto",value = "启用或禁用") BaseUpdateStatusDto baseUpdateStatusDto){
        return goodsAttributeService.updateStatus(baseUpdateStatusDto);
    }

//TODO 属性值操作

    /**
     * 根据属性ID添加属性值
     *
     * @param goodAttributeValueDto
     * @return
     */
    @ApiOperation(value = "添加属性值", notes = "根据属性ID添加属性值")
    @PostMapping("/saveAttValue")
    public JsonViewData saveAttValue(@RequestBody @Valid @ApiParam(required = true, name = "goodAttributeValueDto", value = "属性值")
                                             GoodAttributeValueDto goodAttributeValueDto,
                                     BindingResult result) {

        return valueService.saveAttValue(goodAttributeValueDto);
    }

    /**
     * 删除属性值
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除属性值", notes = "有用到属性值的不能删除")
    @DeleteMapping("/delAttValueByIds/{ids}")
    public JsonViewData delAttValueByIds(@ApiParam(required = true, name = "ids", value = "属性值id集合") @PathVariable Long[] ids) {
        return valueService.delAttValueByIds(ids);
    }


    /**
     * 根据ID查找属性值
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找属性值信息", notes = "根据ID查找")
    @GetMapping("/findValueById/{id}")
    public JsonViewData findValueById(@ApiParam(required = true, name = "id", value = "属性值ID")
                                      @PathVariable Long id) {

        return valueService.findValueById(id);
    }

    /**
     * 更新属性值
     *
     * @param goodAttributeValueDto
     * @return
     */
    @ApiOperation(value = "更新属性值", notes = "根据ID和属性ID更新属性值")
    @PostMapping("/editAttValue")
    public JsonViewData editAttValue(@RequestBody @Validated(IUpdateGroup.class) @ApiParam(required = true, name = "goodAttributeValueDto", value = "属性值")
                                                 GoodAttributeValueDto goodAttributeValueDto,
                                     BindingResult result) {

        return valueService.editValue(goodAttributeValueDto);
    }

}
