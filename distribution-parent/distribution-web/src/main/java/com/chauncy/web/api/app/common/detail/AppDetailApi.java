package com.chauncy.web.api.app.common.detail;

import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.order.service.IOmRealUserService;
import com.chauncy.order.service.IOmShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-08-31 13:36
 *
 * App端获取的详情
 */

@RestController
@RequestMapping("/app/common/detail")
@Api(tags = "app_通用_详情")
public class AppDetailApi {

    @Autowired
    private IOmShoppingCartService service;

    /**
     * 查看商品详情
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/selectSpecifiedGoods/{goodsId}")
    @ApiOperation("查看具体商品详情")
    public JsonViewData<SpecifiedGoodsVo> selectSpecifiedGoods(@ApiParam(required = true, name = "goodsId", value = "商品ID")
                                                               @PathVariable Long goodsId) {
        return new JsonViewData<SpecifiedGoodsVo>(service.selectSpecifiedGoods(goodsId));
    }
}
