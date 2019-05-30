package com.chauncy.web.api.manage.product;


import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(description = "商品属性页面控制器")
@RestController
@RequestMapping("/pm-goods-attribute-po")
@Slf4j
public class PmGoodsAttributeApi {

    @Autowired
    private IPmGoodsAttributeService goodsAttributeService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存属性
     *
     * @param goodsAttributePo
     */
    @PostMapping("/saveAttribute")
    @ApiOperation(value = "保存商品属性（品牌、规格、服务等）")
    public void saveAttribute(@ModelAttribute PmGoodsAttributePo goodsAttributePo) {

        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        System.out.println(securityUtil.getCurrUser().getUsername());
        goodsAttributePo.setUpdateTime(date);
        goodsAttributeService.save(goodsAttributePo);
    }

    /**
     * 根据id删除属性
     *
     * @param ids
     */
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
