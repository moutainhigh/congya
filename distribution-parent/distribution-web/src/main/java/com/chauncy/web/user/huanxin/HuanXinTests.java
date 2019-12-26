package com.chauncy.web.user.huanxin;//package com.chauncy.mq;

import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.third.easemob.comm.RegUserBo;
import com.chauncy.common.third.easemob.service.impl.EasemobIMUsers;
import com.google.common.collect.Lists;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-19 16:06
 */
@Slf4j
public class HuanXinTests {

    private final static EasemobIMUsers easemobIMUsers= new EasemobIMUsers();

    public static void main(String[] args){

        //查找单个IM账号
//        getUser("1145671767662256129d");
        //添加单个IM人员
        RegUserBo regUserBo = new RegUserBo();
        regUserBo.setUsername("114567176");
        regUserBo.setPassword("123456");
        reg(regUserBo);

        //添加多个IM账号
//        List<RegUserBo> regUserBos = Lists.newArrayList();
//        regs(regUserBos);

        //查找多个IM人员
//        getUsers((long) 5,"");
    }

    /**
     *
     * 注册单个用户
     * @param regUser
     */
    public static void reg(RegUserBo regUser){
        RegisterUsers registerUsers = new RegisterUsers();
        User p = new User().username(regUser.getUsername()).password(regUser.getPassword());
        registerUsers.add(p);
        Object result = easemobIMUsers.createNewIMUserSingle(registerUsers);

        System.out.println(JSONUtils.toJSONObject(result));
    }

    /**
     * 注册多个用户
     *
     * @param regUser
     */
    public static void regs(List<RegUserBo> regUser){
        RegisterUsers registerUsers = new RegisterUsers();
        regUser.forEach(a->{
            User p = new User().username(a.getUsername()).password(a.getPassword());
            registerUsers.add(p);
        });
        Object result = easemobIMUsers.createNewIMUserBatch(registerUsers);

        System.out.println(JSONUtils.toJSONObject(result));
    }

    /**
     * 获取单个用户信息
     *
     * @param name
     */
    public static void getUser(final String name){

        Object result = easemobIMUsers.getIMUserByUserName(name);

        System.out.println(JSONUtils.toJSONObject(result));
    }

    /**
     *
     * 获取多个用户信息
     *
     * @param limit
     * @param cursor
     */
    public static void getUsers(final Long limit, final String cursor){

        Object result = easemobIMUsers.getIMUsersBatch(limit, cursor);
        log.info(result.toString());
    }

    /**
     * 删除单个IM账号
     *
     * @param name
     */
    public static void deleteUser(final String name){

        Object result = easemobIMUsers.deleteIMUserByUserName(name);

        log.info(result.toString());
    }
}
