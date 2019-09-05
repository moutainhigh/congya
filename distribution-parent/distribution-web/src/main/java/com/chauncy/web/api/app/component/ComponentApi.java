package com.chauncy.web.api.app.component;

import com.chauncy.activity.gift.IAmGiftService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.message.content.service.IMmBootPageService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-05 10:03
 *
 * app端处理单一业务的接口
 */
@RestController
@RequestMapping("/app/component")
@Api(tags = "app_组件")
public class ComponentApi extends BaseApi {

    @Autowired
    private IMmBootPageService bootPageService;

    @Autowired
    private IAmGiftService giftService;

    /**
     * 判断用户是否领取过新人礼包
     *
     * @return
     */
    @ApiOperation("判断用户是否领取过新人礼包")
    @GetMapping("/isReceive")
    public JsonViewData isReceive(){
        return setJsonViewData(giftService.isReceive());
    }

    /**
     * 获取提供给用户领取的新人礼包
     *
     * @return
     */
    @ApiOperation("获取提供给用户领取的新人礼包")
    @GetMapping("/getGift")
    public JsonViewData<BaseVo> getGift(){

        return setJsonViewData(giftService.getGift());
    }

    /**
     * 新人领取礼包
     * @param giftId
     * @return
     */
    @ApiOperation("新人领取礼包")
    @GetMapping("/receiveGift/{giftId}")
    public JsonViewData receiveGift(@ApiParam(required = true,name = "giftId",value = "礼包ID")
                                    @PathVariable Long giftId){
        giftService.receiveGift(giftId);
        return setJsonViewData(ResultCode.SUCCESS,"恭喜你,领取成功!");
    }

    /**
     * 获取引导页图片
     *
     * @return
     */
    @GetMapping("/findBootPage")
    @ApiOperation("获取引导页图片")
    public JsonViewData<List<String>> findBootPage(){

        return setJsonViewData(bootPageService.findBootPage());
    }
}
