package com.chauncy.data.temp.controller.product;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.data.temp.product.service.IPmGoodsRelGoodsMemberLevelService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品和会员等级关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-16
 */
@RestController
@RequestMapping("pm-goods-rel-goods-member-level-po")
public class PmGoodsRelGoodsMemberLevelController {

 @Autowired
 private IPmGoodsRelGoodsMemberLevelService service;


}
