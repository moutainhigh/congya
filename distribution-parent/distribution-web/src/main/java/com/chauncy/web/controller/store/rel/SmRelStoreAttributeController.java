package com.chauncy.web.controller.store.rel;


import com.chauncy.store.rel.service.ISmRelStoreAttributeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-19
 */
@RestController
@RequestMapping("sm-rel-store-attribute-po")
public class SmRelStoreAttributeController {

 @Autowired
 private ISmRelStoreAttributeService service;


}
