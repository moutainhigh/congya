package com.chauncy.web.api.common;


import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.area.ShopLogisticsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.data.areaService.IAreaShopLogisticsService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

import java.util.List;

/**
 * <p>
 * 物流公司表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/common/logistics")
@Api(tags = "通用_物流管理")
public class AreaShopLogisticsApi extends BaseApi {

    @Autowired
    private IAreaShopLogisticsService service;

    /**
     * 生成快递100所有物流Json
     *
     * @return
     */
    @ApiOperation("生成快递100所有物流Json")
    @GetMapping("/generateLogistics")
    public JsonViewData<List<ShopLogisticsVo>> generateLogistics(){

        return setJsonViewData(service.generateLogistics());
    }
}
