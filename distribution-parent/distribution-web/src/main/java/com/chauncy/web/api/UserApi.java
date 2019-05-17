package com.chauncy.web.api;

import com.chauncy.test.repository.UserPository;
import com.chauncy.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:41
 * @Version 1.0
 */
@RestController
@Api("查询用户信息")
@RequestMapping("/api/users")
@Slf4j
public class UserApi {

    private final static Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    private UserService userService;

    @Autowired
    private UserPository userPository;

    @ApiOperation(value="获取用户详细信息", notes="根据url的name来获取用户详细信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "path")
    @GetMapping("/findByUserUame/{username}")
    public Map<String, Object> findByUserUame(@PathVariable(value = "username")String username) {
        Map<String, Object> result = userService.findByUserUame(username);
        return result;
    }

    /*@GetMapping("/findByAll")
    public Object getUserAll() {

        List<UserPO> userList = userService.findByAll();

        log.info(userList.toString());
        log.error("!!!!");
        log.debug("DEBUG!");
        logger.info("写到日志！！");
        logger.error("错误信息写入");
        return userList;
    }

    @GetMapping("/findAll")
    public Object getAll() {
        List<GlobalDO> list = userPository.findAll();
        return list;
    }
    @GetMapping("/spel")
    public String spel(@Value("#{quartz.texts}") String text) {
        log.info(applicationContext.getBean("quartzTest").toString());
        log.info("使用SpEL应用Bean：",text);
        return text;
    }*/


}
