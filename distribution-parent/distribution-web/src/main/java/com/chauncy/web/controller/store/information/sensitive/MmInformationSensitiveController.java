package com.chauncy.web.controller.store.information.sensitive;


import com.chauncy.message.information.sensitive.service.IMmInformationSensitiveService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@RestController
@RequestMapping("mm-information-sensitive-po")
public class MmInformationSensitiveController {

 @Autowired
 private IMmInformationSensitiveService iMmInformationSensitiveService;


}
