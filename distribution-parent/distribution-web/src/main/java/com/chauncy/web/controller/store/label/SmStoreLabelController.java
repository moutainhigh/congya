package com.chauncy.web.controller.store.label;


import com.chauncy.store.label.service.ISmStoreLabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-15
 */
@RestController
@RequestMapping("sm-store-label-po")
public class SmStoreLabelController {

 @Autowired
 private ISmStoreLabelService service;


}
