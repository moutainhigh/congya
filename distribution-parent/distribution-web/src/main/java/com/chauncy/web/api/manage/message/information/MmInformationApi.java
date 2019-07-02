package com.chauncy.web.api.manage.message.information;

import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @since 2019/6/25 17:57
 */
@Api(tags = "平台_店铺资讯管理接口")
@RestController
@RequestMapping("/manage/information")
@Slf4j
public class MmInformationApi extends BaseApi {

    @Autowired
    private IMmInformationService mmInformationService;

    // todo 审核资讯

}
