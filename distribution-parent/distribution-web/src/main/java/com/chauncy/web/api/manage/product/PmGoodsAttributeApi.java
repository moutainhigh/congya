package com.chauncy.web.api.manage.product;


import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.product.service.IPmGoodsAttributeValueService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
public class PmGoodsAttributeApi {

    @Autowired
    private IPmGoodsAttributeService goodsAttributeService;

    @Autowired
    private IPmGoodsAttributeValueService valueService;

    //TODO 属性操作

    /**
     * 添加属性以及属性值
     *
     * @param goodsAttributePo
     */
    @PostMapping("/saveAttribute")
    @ApiOperation(value = "保存商品属性（品牌、规格、服务等）")
    public JsonViewData saveAttribute(@ModelAttribute PmGoodsAttributePo goodsAttributePo) {

        return goodsAttributeService.saveAttribute(goodsAttributePo);
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
     * @param goodsAttributePo
     * @return
     */
    @ApiOperation(value = "更新属性", notes = "根据ID更新属性")
    @PostMapping("/editAttribute")
    public JsonViewData editAttribute(@ModelAttribute PmGoodsAttributePo goodsAttributePo) {

        return goodsAttributeService.edit(goodsAttributePo);
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

    @ApiOperation(value = "条件查询", notes = "根据类型type、名称、启用状态查询")
    @GetMapping("/search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "属性类型type", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "属性名称name", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "是否启用enabled", required = false, dataType = "boolean", paramType = "query")}
    )

    public JsonViewData search(Integer type, String name, Boolean enabled) {

        return goodsAttributeService.search(type, name, enabled);
    }

//TODO 属性值操作

    /**
     * 根据属性ID添加属性值
     *
     * @param goodsAttributeValuePo
     * @return
     */
    @ApiOperation(value = "添加属性值", notes = "根据属性ID添加属性值")
    @PostMapping("/saveAttValue")
    public JsonViewData saveAttValue(/*@ApiParam(required = true,name = "attributeId",value = "属性值ID") @PathVariable Long attributeId,*/
            @ModelAttribute PmGoodsAttributeValuePo goodsAttributeValuePo) {

        return valueService.saveAttValue(goodsAttributeValuePo);
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
     * @param goodsAttributeValuePo
     * @return
     */
    @ApiOperation(value = "更新属性值", notes = "根据ID和属性ID更新属性值")
    @PostMapping("/editAttValue")
    public JsonViewData editAttValue(@ModelAttribute PmGoodsAttributeValuePo goodsAttributeValuePo) {

        return valueService.editValue(goodsAttributeValuePo);
    }

}
