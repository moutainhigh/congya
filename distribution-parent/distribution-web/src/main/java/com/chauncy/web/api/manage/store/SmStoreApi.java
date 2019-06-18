package com.chauncy.web.api.manage.store;

import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.store.SmStoreBaseVo;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @Author: xiaoye
 * @Date: 2019/6/3 18:37
 */
@Api(tags = "店铺管理接口")
@RestController
@RequestMapping("/manage/store")
@Slf4j
public class SmStoreApi extends BaseApi {

    @Autowired
    private ISmStoreService smStoreService;

    @PostMapping("/save/storeBaseInfo")
    @ApiOperation(value = "保存店铺信息（基本信息）")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData saveStoreBaseInfo(@Valid @ModelAttribute  @ApiParam(required = true, name = "storeBaseInfoDto", value = "店铺基本信息")
                                                  StoreBaseInfoDto storeBaseInfoDto) {

        return smStoreService.saveStore(storeBaseInfoDto);
    }

    @PostMapping("/save/storeAccountInfo")
    @ApiOperation(value = "保存店铺信息（账户信息）")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData saveStoreAccountInfo(@Valid @RequestBody  @ApiParam(required = true, name = "storeAccountInfoDto", value = "店铺账户信息")
                                                     StoreAccountInfoDto storeAccountInfoDto) {

        return smStoreService.saveStore(storeAccountInfoDto);
    }

    @PostMapping("/editStoreStatus")
    @ApiOperation(value = "批量修改店铺经营状态")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData saveStoreAccountInfo(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "店铺id、修改的状态值")
                                                     BaseUpdateStatusDto baseUpdateStatusDto) {

        return smStoreService.editStoreStatus(baseUpdateStatusDto);
    }

    /**
     * 条件查询
     * @param storeSearchDto
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "根据店铺ID、手机号、店铺类型、店铺名称、店铺状态查询")
    @PostMapping("/searchBaseInfo")
    public JsonViewData searchBaseInfo(@RequestBody StoreSearchDto storeSearchDto) {

        PageInfo<SmStoreBaseVo> smStoreBaseVoPageInfo = smStoreService.searchBaseInfo(storeSearchDto);
        return setJsonViewData(smStoreBaseVoPageInfo);
    }

}
