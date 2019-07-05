package com.chauncy.web.api.app.user;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddAreaDto;
import com.chauncy.data.dto.app.user.add.AddIdCardDto;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.dto.app.user.add.BindUserDto;
import com.chauncy.data.dto.app.user.update.UpdatePhoneDto;
import com.chauncy.data.dto.app.user.update.UpdateUserDataDto;
import com.chauncy.data.valid.group.ISaveGroup;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.user.ShipAreaVo;
import com.chauncy.data.vo.app.user.UserDataVo;
import com.chauncy.user.service.IUmAreaShippingService;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端用户 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@RestController
@RequestMapping("/app/user/data")
@Api(tags = "APP_前端用户个人资料")
public class UmUserDataApi extends BaseApi {

    @Autowired
    private IUmUserService service;

    @Autowired
    private IUmAreaShippingService shipService;

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
        shipService.addArea(addAreaDto);
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
        shipService.updateArea(updateAreaDto);
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
        shipService.delArea(id);
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

        return new JsonViewData(shipService.findShipArea(userId));
    }

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    public JsonViewData register(@Validated(ISaveGroup.class) @RequestBody AddUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptPass);

        return service.saveUser(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }

    @PostMapping("/reset")
    @ApiOperation("忘记密码")
    public JsonViewData reset(@Validated(IUpdateGroup.class) @RequestBody AddUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptPass);

        return service.reset(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);

    }

    @PostMapping("/binding")
    @ApiOperation("第三方登录绑定")
    public JsonViewData binding(@Validated @RequestBody BindUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptPass);
        return service.bindUser(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);
    }


    @PostMapping("/certification")
    @ApiOperation("实名认证")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData certification(@Validated @RequestBody AddIdCardDto addIdCardDto){
        UmUserPo appCurrUser = getAppCurrUser();
        UmUserPo updateUserPo = new UmUserPo();
        BeanUtils.copyProperties(addIdCardDto,updateUserPo);
        updateUserPo.setId(appCurrUser.getId()).setUpdateBy(appCurrUser.getPhone());
        umUserService.updateById(updateUserPo);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/get_phone")
    @ApiOperation("获得当前登录用户手机")
    public JsonViewData phone(){
        UmUserPo appCurrUser = getAppCurrUser();
        if (appCurrUser==null){
            return setJsonViewData(ResultCode.FAIL,"用户尚未登录！");
        }
        else {
            return setJsonViewData(appCurrUser.getPhone());
        }
    }


    @PostMapping("/old_phone_check")
    @ApiOperation("原有手机号码验证")
    public JsonViewData oldphone(@Validated @RequestBody UpdatePhoneDto updatePhoneDto){
        boolean isTrue = umUserService.validVerifyCode(updatePhoneDto.getVerifyCode(), updatePhoneDto.getPhone()
                , ValidCodeEnum.OLD_BIND_PHONE_CODE);
        return isTrue?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/new_phone_check")
    @ApiOperation("新手机号码验证")
    public JsonViewData newphone(@Validated @RequestBody UpdatePhoneDto updatePhoneDto){
        boolean isTrue = umUserService.validVerifyCode(updatePhoneDto.getVerifyCode(), updatePhoneDto.getPhone()
                , ValidCodeEnum.NEW_BIND_PHONE_CODE);
        if (!isTrue){
            return setJsonViewData(ResultCode.FAIL,"验证码错误！");
        }
        UmUserPo updateUser=new UmUserPo();
        updateUser.setPhone(updatePhoneDto.getPhone()).setId(getAppCurrUser().getId());

        umUserService.updateById(updateUser);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/update")
    @ApiOperation("个人头像 昵称 性别 邀请码更改")
    public JsonViewData update(@Validated @RequestBody UpdateUserDataDto updateUserDataDto){
        UmUserPo updateUser=new UmUserPo();
        BeanUtils.copyProperties(updateUserDataDto,updateUser);
        updateUser.setId(getAppCurrUser().getId());
        umUserService.updateById(updateUser);
        return setJsonViewData(ResultCode.SUCCESS);
    }


    @PostMapping("/get")
    @ApiOperation("获取个人基本信息")
    public JsonViewData<UserDataVo> get(){
        return setJsonViewData(service.getUserDataVo(getAppCurrUser().getPhone()));
    }





}
