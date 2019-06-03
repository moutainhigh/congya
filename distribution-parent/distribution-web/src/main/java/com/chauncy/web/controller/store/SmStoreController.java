package com.chauncy.web.controller.store;


import com.chauncy.data.temp.store.service.ISmStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
@RestController
@RequestMapping("sm-store-po")
public class SmStoreController {

 @Autowired
 private ISmStoreService service;


}
