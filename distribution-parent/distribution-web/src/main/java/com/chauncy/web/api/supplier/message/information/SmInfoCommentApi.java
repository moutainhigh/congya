package com.chauncy.web.api.supplier.message.information;

import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @since 2019/7/2 11:33
 */
@Api(tags = "APP_店铺资讯_评论管理接口")
@RestController
@RequestMapping("/app/information/comment")
@Slf4j
public class SmInfoCommentApi extends BaseApi {

    @Autowired
    private IMmInformationCommentService mmInformationCommentService;


}
