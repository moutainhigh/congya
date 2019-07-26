package com.chauncy.data.temp.controller.order;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.data.temp.order.service.IOmUserWithdrawalService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;;

/**
 * <p>
 * 'distribution.activity_view' is not BASE TABLE 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@RestController
@RequestMapping("om-user-withdrawal-po")
@Api(tags = "'distribution.activity_view' is not BASE TABLE")
public class OmUserWithdrawalApi extends BaseApi; {

 @Autowired
 private IOmUserWithdrawalService service;


}
