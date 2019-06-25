package com.chauncy.web.controller.store.information.label;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.store.information.label.service.ISmInformationLabelService;

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
@RequestMapping("sm-information-label-po")
public class SmInformationLabelController {

 @Autowired
 private ISmInformationLabelService service;


}
