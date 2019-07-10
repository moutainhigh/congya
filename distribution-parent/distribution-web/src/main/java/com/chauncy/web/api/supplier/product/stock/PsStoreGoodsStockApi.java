package com.chauncy.web.api.supplier.product.stock;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.product.stock.IPmGoodsVirtualStockTemplateService;
import com.chauncy.product.stock.IPmStoreGoodsStockService;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/7/10 10:03
 */
@RestController
@RequestMapping("/supplier/store/stock")
@Api(tags = "商家_商品_库存管理接口")
public class PsStoreGoodsStockApi extends BaseApi {

    @Autowired
    private IPmStoreGoodsStockService pmStoreGoodsStockService;
    @Autowired
    private ISmStoreService smStoreService;
    @Autowired
    private IPmGoodsVirtualStockTemplateService pmGoodsVirtualStockTemplateService;


    /**
     * 保存分店库存信息
     * @param storeGoodsStockBaseDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存分店库存信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "storeGoodsStockBaseDto", value = "分店库存信息")
                                     StoreGoodsStockBaseDto storeGoodsStockBaseDto) {


        return new JsonViewData(ResultCode.SUCCESS, "添加成功",
                pmStoreGoodsStockService.saveStoreGoodsStock(storeGoodsStockBaseDto));
    }

    /**
     * 获取当前店铺的下级店铺(分店)（模糊搜索）
     * @param storeName
     * @return
     */
    @PostMapping("/searchBranchByName")
    @ApiOperation(value = "获取当前店铺的下级店铺(分店)（模糊搜索）")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData searchBranchByName(@Valid @RequestBody @ApiParam(required = true, name = "storeName", value = "店铺名称")
                                     String storeName) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.searchBranchByName(storeName));
    }


    /**
     * 查询当前店铺的库存模板信息
     *
     * @param
     * @return
     */
    @PostMapping("/findGoodsByType")
    @ApiOperation(value = "查询当前店铺的库存模板信息 ")
    public JsonViewData<BaseBo> selectStockTemplate(){

        return new JsonViewData(ResultCode.SUCCESS,"操作成功",
                pmGoodsVirtualStockTemplateService.selectStockTemplate());
    }


}
