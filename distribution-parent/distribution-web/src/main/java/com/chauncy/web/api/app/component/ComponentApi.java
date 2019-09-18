package com.chauncy.web.api.app.component;

import com.chauncy.activity.gift.IAmGiftService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.component.ShareDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.user.GetMembersCenterVo;
import com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo;
import com.chauncy.message.content.service.IMmArticleService;
import com.chauncy.message.content.service.IMmBootPageService;
import com.chauncy.message.content.service.IMmKeywordsSearchService;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IUmUserService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private IMmKeywordsSearchService mmKeywordsSearchService;

    @Autowired
    private IPmGoodsService goodsService;

    @Autowired
    private IUmUserService userService;

    @Autowired
    private IMmArticleService articleService;

    @Autowired
    private SecurityUtil securityUtil;

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
     * 获取搜索界面-热门搜索词语
     *
     * @return
     */
    @ApiOperation("获取搜索界面-热门搜索词语")
    @GetMapping("/getKeyWordByType/{type}")
    public JsonViewData<List<String>> isReceive(@ApiParam(required = true, value = "1：商品；2：店铺；3：资讯") @PathVariable Integer type){
        return setJsonViewData(mmKeywordsSearchService.getKeyWordByType(type));
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

    /**
     * 分享商品
     *
     * @param
     * @return
     */
    @PostMapping("/share")
    @ApiOperation("分享商品/资讯")
    public JsonViewData shareGoods(@RequestBody @ApiParam(required = true,name = "shareDto",value = "分享商品/资讯Dto")
                                   @Validated ShareDto shareDto){

        goodsService.share(shareDto);

        return setJsonViewData(ResultCode.SUCCESS,"转发成功");
    }

    /**
     * 会员中心
     *
     * @return
     */
    @PostMapping("/getMembersCenter")
    @ApiOperation("会员中心")
    public JsonViewData<GetMembersCenterVo> getMembersCenter(){

        UmUserPo userPo = securityUtil.getAppCurrUser();

        return setJsonViewData(userService.getMembersCenter(userPo));
    }

    /**
     * @Author chauncy
     * @Date 2019-09-18 16:11
     * @Description //查找所有的文章位置
     *
     * @Update chauncy
     *
     * @Param []
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.BaseVo>>
     **/
    @GetMapping("/selectLocations")
    @ApiOperation("查找所有的文章位置")
    public JsonViewData<Map<Integer,String>> selectLocations(){

//        System.out.println(articleService.findArticleLocations());

        return setJsonViewData(articleService.findArticleLocations());
    }

    /**
     * @Author chauncy
     * @Date 2019-09-18 21:14
     * @Description //根据文章位置类型获取文章信息
     *
     * @Update chauncy
     *
     * @Param [type]
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.manage.message.content.app.FindArticleContentVo>>
     **/
    @GetMapping("/findArticleContent/{type}")
    @ApiOperation("根据文章位置类型获取文章信息")
    public JsonViewData<List<FindArticleContentVo>> findArticleContent(@PathVariable Integer type){

        return setJsonViewData(articleService.findArticleContent(type));
    }

}
