package com.chauncy.common.third.easemob;

import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.third.easemob.service.impl.EasemobIMUsers;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author cheng
 * @create 2019-08-04 20:19
 *
 * 注册环信IM账号
 */
@Slf4j

public class RegistIM {

    private final static EasemobIMUsers easemobIMUsers= new EasemobIMUsers();

    /**
     * 获取单个用户信息
     *
     * @param name
     */
    public static Object getUser(final String name){

        Object result = easemobIMUsers.getIMUserByUserName(name);
        System.out.println(JSONUtils.toJSONObject(result));

        return result;
    }

    /**
     * 删除单个IM账号
     *
     * @param name
     */
    public static Object deleteUser(final String name){

        Object result = easemobIMUsers.deleteIMUserByUserName(name);

//        System.out.println(JSONUtils.toJSONObject(result));

        log.info(String.valueOf(result));

        return result;
    }

    /**
     *
     * 注册单个用户
     * @param regUser
     */
    public static Object reg(RegUserBo regUser){
        RegisterUsers registerUsers = new RegisterUsers();
        User p = new User().username(regUser.getUsername()).password(regUser.getPassword());
        Nickname nickName = new Nickname().nickname(regUser.getNickname());

        registerUsers.add(p);
        Object result = easemobIMUsers.createNewIMUserSingle(registerUsers);

        Object nickNameResult = easemobIMUsers.modifyIMUserNickNameWithAdminToken(regUser.getUsername(),nickName);

        System.out.println(JSONUtils.toJSONObject(result));
        System.out.println(JSONUtils.toJSONObject(nickNameResult));

        return result;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-08 14:03
     * @Description //更新用户昵称
     *
     * @Update chauncy
     *
     * @param  nickName 昵称
     * @param  IMAccount IM账号
     * @return
     **/
    public static Object modifyIMUserNickName(String IMAccount,String nickName){

        Nickname alias = new Nickname().nickname(nickName);
        Object nickNameResult = easemobIMUsers.modifyIMUserNickNameWithAdminToken(IMAccount,alias);

        System.out.println(JSONUtils.toJSONObject(nickNameResult));

        return nickNameResult;
    }
}
