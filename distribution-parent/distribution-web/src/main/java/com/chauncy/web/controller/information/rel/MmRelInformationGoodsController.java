package com.chauncy.web.controller.information.rel;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.message.information.rel.IMmRelInformationGoodsService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品与资讯关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-27
 */
@RestController
@RequestMapping("mm-rel-information-goods-po")
public class MmRelInformationGoodsController {

 @Autowired
 private IMmRelInformationGoodsService service;


}
