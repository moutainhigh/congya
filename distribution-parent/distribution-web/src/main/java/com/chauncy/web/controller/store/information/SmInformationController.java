package com.chauncy.web.controller.store.information;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.store.information.service.ISmInformationService;

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
@RequestMapping("sm-information-po")
public class SmInformationController {

 @Autowired
 private ISmInformationService service;


}
