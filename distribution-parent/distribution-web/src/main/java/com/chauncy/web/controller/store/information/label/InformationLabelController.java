package com.chauncy.web.controller.store.information.label;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.message.information.label.service.IMmInformationLabelService;

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
public class InformationLabelController {

 @Autowired
 private IMmInformationLabelService service;


}
