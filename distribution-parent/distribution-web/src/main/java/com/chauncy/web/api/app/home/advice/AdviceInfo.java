package com.chauncy.web.api.app.home.advice;

import com.chauncy.data.vo.JsonViewData;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-08-27 11:14
 *
 * app端获取广告信息
 *
 */
@Api(tags = "APP_首页_广告")
@RestController
@RequestMapping("/app/home/advice")
@Slf4j
public class AdviceInfo extends BaseApi {

    @Autowired
    private IMmAdviceService adviceService;

    /**
     * 获取APP首页葱鸭优选、葱鸭百货等广告位信息
     * @return
     */
//    @GetMapping("/getAdviceInfo")
//    @ApiOperation("获取APP首页葱鸭优选、葱鸭百货等广告位信息")
//    public JsonViewData<GetAdviceInfoVo> getAdviceInfo(){
//
//        return setJsonViewData(adviceService.getAdviceInfo());
    }
//}
