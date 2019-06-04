package com.chauncy.web.api.manage.store;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author: xiaoye
 * @Date: 2019/6/3 18:37
 */
@Api(description = "店铺管理接口")
@RestController
@RequestMapping("/sm-store-po")
@Slf4j
public class SmStoreApi extends BaseApi {

    @Autowired
    private ISmStoreService smStoreService;

    @PostMapping("/saveStore")
    @ApiOperation(value = "保存店铺信息（基本信息、账号信息）")
    public JsonViewData save(@ModelAttribute SmStorePo smStorePo) {

        return smStoreService.saveStore(smStorePo);
    }
}
