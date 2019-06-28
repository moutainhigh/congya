package com.chauncy.web.api.app.order.evalute;


import com.chauncy.order.evaluate.service.IOmEvaluateService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

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
@RequestMapping("om-evaluate-po")
@Api(tags = "商品评价表")
public class OmEvaluateApi {

 @Autowired
 private IOmEvaluateService service;


}
