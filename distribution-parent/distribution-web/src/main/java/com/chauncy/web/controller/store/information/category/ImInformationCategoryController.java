package com.chauncy.web.controller.store.information.category;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.store.information.category.service.IImInformationCategoryService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-25
 */
@RestController
@RequestMapping("im-information-category-po")
public class ImInformationCategoryController {

 @Autowired
 private IImInformationCategoryService service;


}
