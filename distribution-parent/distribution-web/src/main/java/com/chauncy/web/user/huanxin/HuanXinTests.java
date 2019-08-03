package com.chauncy.web.user.huanxin;//package com.chauncy.mq;

import com.chauncy.common.util.JSONUtils;
import com.chauncy.data.bo.message.RegUserBo;
import com.chauncy.message.easemob.service.impl.EasemobIMUsers;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author cheng
 * @create 2019-07-19 16:06
 */
@Slf4j
public class HuanXinTests {

    private final static EasemobIMUsers easemobIMUsers= new EasemobIMUsers();

    public static void main(String[] args){
        //添加IM人员
        RegUserBo regUserBo = new RegUserBo();
        regUserBo.setUsername("chuancy3");
        regUserBo.setPassword("123456");
        reg(regUserBo);
    }

    public static void reg(RegUserBo regUser){
        RegisterUsers registerUsers = new RegisterUsers();
        User p = new User().username(regUser.getUsername()).password(regUser.getPassword());
        registerUsers.add(p);
        Object result = easemobIMUsers.createNewIMUserSingle(registerUsers);

        System.out.println(JSONUtils.toJSONObject(result));
    }
}
