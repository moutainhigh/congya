package com.chauncy.web.controller.information.comment;


import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资讯评论信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@RestController
@RequestMapping("mm-information-comment-po")
public class MmInformationCommentController {

    @Autowired
    private IMmInformationCommentService service;


}
