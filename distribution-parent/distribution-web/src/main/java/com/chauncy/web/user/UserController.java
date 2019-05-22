package com.chauncy.web.user;

import com.chauncy.data.domain.po.test.UserPO;
import com.chauncy.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 17:41
 * @Version 1.0
 *
 * 用于测试，测试后删除
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    private UserService userService;

    @GetMapping("/findByUserUame")
    public Map<String, Object> findByUserUame(String username) {
        Map<String, Object> result = userService.findByUserUame(username);
        return result;
    }

    @GetMapping("/findByAll")
    public Object getUserAll() {

        List<UserPO> userList = userService.findByAll();

//        log.info(userList.toString());
        log.error("!!!!");
        log.debug("DEBUG!");
        logger.info("写到日志！！");
        logger.error("错误信息写入");
        return userList;
    }

    //templates测试
    @GetMapping("/hello")
    public ModelAndView testPage() {
        ModelAndView mv = new ModelAndView("hello");
        log.info("ceshi");
        return mv;
    }

}
