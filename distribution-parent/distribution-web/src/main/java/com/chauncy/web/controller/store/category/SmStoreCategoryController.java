package com.chauncy.web.controller.store.category;


import com.chauncy.store.category.service.ISmStoreCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-16
 */
@RestController
@RequestMapping("sm-store-category-po")
public class SmStoreCategoryController {

 @Autowired
 private ISmStoreCategoryService service;


}
