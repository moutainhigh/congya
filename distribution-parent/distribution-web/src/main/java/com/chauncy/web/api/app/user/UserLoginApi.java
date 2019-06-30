package com.chauncy.web.api.app.user;

import com.chauncy.data.dto.app.user.AddUserDto;
import com.chauncy.data.vo.JsonViewData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhangrt
 * @Date 2019/6/28 18:59
 **/
@RestController
@RequestMapping("/api/app/user")
public class UserLoginApi {


    public JsonViewData register(@Validated @RequestBody AddUserDto userDto){
        return null;

    }



}
