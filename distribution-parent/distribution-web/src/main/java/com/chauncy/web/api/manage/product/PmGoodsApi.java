package com.chauncy.web.api.manage.product;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.good.update.RejectGoodsDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/manage/product")
@Api(description = "平台审核商品管理")
@Slf4j
public class PmGoodsApi extends BaseApi {

    @Autowired
    private IPmGoodsService service;

    /**
     * 驳回商品
     * @param rejectGoodsDto
     * @return
     */
    @PostMapping("/rejectGoods")
    @ApiOperation("平台驳回商品")
    public JsonViewData rejectGoods(@ApiParam(required = true, name = "goodsId", value = "goodsId") @Validated
                                    RejectGoodsDto rejectGoodsDto){

        service.rejectGoods(rejectGoodsDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }
}
