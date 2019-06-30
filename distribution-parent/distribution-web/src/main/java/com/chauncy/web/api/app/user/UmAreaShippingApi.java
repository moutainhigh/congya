package com.chauncy.web.api.app.user;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.user.AddAreaDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.user.ShipAreaVo;
import com.chauncy.user.service.IUmAreaShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@RestController
@RequestMapping("/app/user")
@Api(tags = "app_用户_收货地址")
public class UmAreaShippingApi {

    @Autowired
    private IUmAreaShippingService service;

    /**
     * 用户添加收货地址
     *
     * @param addAreaDto
     * @return
     */
    @PostMapping("/addArea")
    @ApiOperation("用户添加收货地址")
    public JsonViewData addArea(@RequestBody @ApiParam(required = true,name = "addAreaDto",value = "用户添加收货地址Dto")
                                @Validated AddAreaDto addAreaDto){
        service.addArea(addAreaDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 用户修改收货地址
     *
     * @param updateAreaDto
     * @return
     */
    @PostMapping("/updateArea")
    @ApiOperation("用户修改收货地址")
    public JsonViewData updateArea(@RequestBody @ApiParam(required = true,name = "updateAreaDto",value = "用户修改收货地址Dto")
                                @Validated(IUpdateGroup.class) AddAreaDto updateAreaDto){
        service.updateArea(updateAreaDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @GetMapping("/delArea/{id}")
    @ApiOperation("删除收货地址")
    public JsonViewData delArea(@ApiParam(required = true,name = "id",value = "收货地址ID")
                                @PathVariable Long id){
        service.delArea(id);
        return new JsonViewData(ResultCode.SUCCESS);

    }

    /**
     * 查找用户收货地址
     *
     * @param userId
     * @return
     */
    @GetMapping("/findShipArea/{userId}")
    @ApiOperation("查找用户收货地址")
    public JsonViewData<List<ShipAreaVo>> findShipArea(@ApiParam(required = true,name = "userId",value = "用户ID")
                                                       @PathVariable Long userId){

        return new JsonViewData(service.findShipArea(userId));
    }
}
