package com.chauncy.web.api.manage.user.label;


import com.chauncy.user.service.IUmUserLabelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户标签 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-26
 */
@Api(tags = "用户标签")
@RestController
@RequestMapping("/manage/user/label")
public class UmUserLabelApi {

    @Autowired
    private IUmUserLabelService service;




}
