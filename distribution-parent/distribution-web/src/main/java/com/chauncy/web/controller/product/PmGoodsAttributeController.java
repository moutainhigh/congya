package com.chauncy.web.controller.product;


import com.chauncy.common.util.JSONUtils;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.product.service.IPmGoodsAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品属性页面控制器
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Api(tags = "商品属性页面控制器")
@RestController
@RequestMapping("/goods")
@Slf4j
public class PmGoodsAttributeController {

    @Autowired
    private IPmGoodsAttributeService goodsAttributeService;

    /**
     * 保存属性
     *
     * @param goodsAttributePo
     */
    @PostMapping("/saveAttribute")
    public void saveAttribute(PmGoodsAttributePo goodsAttributePo) {

        LocalDateTime date = LocalDateTime.now();
        goodsAttributePo.setUpdateTime(date);
        goodsAttributeService.save(goodsAttributePo);
    }

    /**
     * 根据id删除属性
     *
     * @param id
     */
    @PostMapping("/deleteAttributeById")
    public void deleteAttributeById(Long id) {
        goodsAttributeService.removeById(id);
    }

    @ApiOperation(value = "删除属性", notes = "根据id批量删除")
    @PostMapping("/deleteAttributeByIds")
    public void deleteAttributeByIds(@ApiParam(required = true, name = "ids", value = "id集合")
                                     @RequestParam(value = "ids") Integer[] ids)
    {
        List<Integer> idList = new ArrayList<>();
//        idList = JSONUtils.toList(ids, Integer.class);
        idList = Arrays.asList(ids);
        goodsAttributeService.removeByIds(idList);
    }
}
