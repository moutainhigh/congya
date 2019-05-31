package com.chauncy.web.api.manage.product;


import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 保存属性
     *
     * @param goodsAttributePo
     */
    @PostMapping("/saveAttribute")
    @ApiOperation(value = "保存商品属性（品牌、规格、服务等）")
    public JsonViewData saveAttribute(@ModelAttribute PmGoodsAttributePo goodsAttributePo) {

        return goodsAttributeService.saveAttribute(goodsAttributePo);
    }

    /**
     * 根据id删除属性
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
     * @param goodsAttributeValuePo
     * @return
     */
    @ApiOperation(value = "更新属性", notes = "更新属性基本信息以及属性值")
    @PostMapping("/edit")
    public JsonViewData edit(@ModelAttribute PmGoodsAttributePo goodsAttributePo,
                             @ModelAttribute PmGoodsAttributeValuePo goodsAttributeValuePo) {

        return goodsAttributeService.edit(goodsAttributePo, goodsAttributeValuePo);
    }

    @ApiOperation(value = "查找属性信息", notes = "根据ID查找")
    @PostMapping("/findById/{id}")
    public JsonViewData findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {

        return goodsAttributeService.findById(id);
    }

}
