package com.chauncy.message.easemob;

import com.chauncy.common.util.JSONUtils;
import com.chauncy.data.bo.message.RegUserBo;
import com.chauncy.message.easemob.service.impl.EasemobIMUsers;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

/**
 * @Author cheng
 * @create 2019-08-04 20:19
 *
 * 注册环信IM账号
 */
public class RegistIM {

    private final static EasemobIMUsers easemobIMUsers= new EasemobIMUsers();

    public static void reg(String username){

        RegisterUsers registerUsers = new RegisterUsers();
        User p = new User().username(username).password("123456");
        registerUsers.add(p);
        Object result = easemobIMUsers.createNewIMUserSingle(registerUsers);

        System.out.println(JSONUtils.toJSONObject(result));
    }
}
