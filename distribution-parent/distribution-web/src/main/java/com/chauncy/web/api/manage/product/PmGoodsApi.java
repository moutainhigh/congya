package com.chauncy.web.api.manage.product;


import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/pm-goods-po")
@Api(description = "商品管理接口")
@Slf4j
public class PmGoodsApi extends BaseApi {

}
