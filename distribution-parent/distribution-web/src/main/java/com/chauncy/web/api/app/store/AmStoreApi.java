package com.chauncy.web.api.app.store;

import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @since 2019/7/3 23:26
 */
@Api(tags = "APP_店铺管理接口")
@RestController
@RequestMapping("/app/store")
@Slf4j
public class AmStoreApi  extends BaseApi {

}
