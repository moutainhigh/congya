package com.chauncy.web.api.app.user;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.user.add.AddUserDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端用户 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@RestController
@RequestMapping("/api/app/user")
@Api(tags = "前端用户")
public class UmUserApi extends BaseApi {

    @Autowired
    private IUmUserService service;

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    public JsonViewData register(@Validated @RequestBody AddUserDto userDto){
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptPass);

        return service.saveUser(userDto)?setJsonViewData(ResultCode.SUCCESS):setJsonViewData(ResultCode.FAIL);

    }


}
