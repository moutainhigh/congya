package com.chauncy.web.api.app.store;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.StoreBaseInfoVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @Autowired
    private ISmStoreService smStoreService;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 用户关注店铺
     * @param storeId  店铺id
     * @return
     */
    @ApiOperation(value = "用户关注店铺", notes = "用户关注店铺")
    @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/findBaseById/{storeId}")
    public JsonViewData<StoreBaseInfoVo> userFocusStore(@PathVariable(value = "storeId")Long storeId) {

        smStoreService.userFocusStore(storeId, securityUtil.getAppCurrUser().getId());
        return new JsonViewData(ResultCode.SUCCESS, "关注成功");

    }

}
