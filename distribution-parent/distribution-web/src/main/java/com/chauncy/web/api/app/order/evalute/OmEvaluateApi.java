package com.chauncy.web.api.app.order.evalute;


import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.util.RedisUtil;
import com.chauncy.data.dto.app.order.evaluate.AddValuateDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品评价表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@RestController
@RequestMapping("/app/order/evaluate")
@Api(tags = "app_用户_商品评价")
public class OmEvaluateApi {

    @Autowired
    private IOmEvaluateService service;

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    @PostMapping("/addEvaluate")
    @ApiOperation("用户进行商品评价")
    public JsonViewData addEvaluate(@RequestBody @ApiParam(required = true,name = "addEvaluateDto",value = "用户评价商品")
                                    @Validated AddValuateDto addValuateDto){


        return new JsonViewData(ResultCode.SUCCESS);
    }
}
