package com.chauncy.web.api.supplier.product;

import com.chauncy.data.dto.manage.good.add.GoodBaseDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author cheng
 * @create 2019-06-15 11:13
 *
 * 商家端商品管理
 */
@RestController
@Api(value="商家端商品管理")
@RequestMapping("/manage/supplier/product")
@Slf4j
public class SmGoodsApi {

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
    public JsonViewData addBase(@RequestBody @Valid @ApiParam(required = true,name="goodBaseDto",value="商品基本信息")
                                        GoodBaseDto goodBaseDto, BindingResult result) {

        return service.addBase(goodBaseDto);
    }

    public JsonViewData searchStandard(){
        return null;
    }
}
