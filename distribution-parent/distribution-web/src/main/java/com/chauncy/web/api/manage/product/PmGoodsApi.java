package com.chauncy.web.api.manage.product;


import com.chauncy.data.dto.product.GoodBaseDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/pm-goods-po")
@Api(description = "添加商品基本信息流程")
@Slf4j
public class PmGoodsApi extends BaseApi {

    @Autowired
    private IPmGoodsService service;

    /**
     * 添加商品基本信息
     *
     * @param goodBaseDto
     * @return
     */
    @PostMapping("/addBase")
    @ApiOperation(value = "添加基本信息")
    public JsonViewData addBase(@Valid @ModelAttribute GoodBaseDto goodBaseDto,
    BindingResult result) {

        return service.addBase(goodBaseDto);
    }

    @GetMapping("findBaseById")
    @ApiOperation(value="查找商品基本信息")
    public JsonViewData findBaseById(@ApiParam(required = true,name="id",value = "商品ID") Long id){

        return service.findBaseById(id);
    }

}
