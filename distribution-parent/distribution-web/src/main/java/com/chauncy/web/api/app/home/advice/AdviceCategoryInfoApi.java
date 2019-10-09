package com.chauncy.web.api.app.home.advice;

import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.dto.app.advice.category.select.BaiHuoMiddleAdviceVo;
import com.chauncy.data.dto.app.advice.category.select.GoodsCategoryVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.message.advice.IMmAdviceRelAssociaitonService;
import com.chauncy.message.advice.IMmAdviceRelTabService;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-05 14:47
 *
 * app端首页葱鸭百货相关接口
 */
@Api(tags = "APP_首页_葱鸭百货")
@RestController
@RequestMapping("/app/home/category")
@Slf4j
public class AdviceCategoryInfoApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabService relTabService;

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    /**
     * 联动查询葱鸭百货广告位关联的商品分类
     *
     * @return
     */
    @GetMapping("/findCategory/{adviceId}")
    @ApiOperation(value = "联动查询葱鸭百货广告位关联的商品分类")
    public JsonViewData<List<GoodsCategoryVo>> findGoodsCategoryTreeVo(@PathVariable Long adviceId){

        List<GoodsCategoryVo> goodsCategoryVos = goodsCategoryService.findGoodsCategory(adviceId);

        return setJsonViewData(goodsCategoryVos);
    }

    /**
     * @Author chauncy
     * @Date 2019-10-08 22:35
     * @Description //查找百货中部广告信息
     *
     * @Update chauncy
     *
     * @param
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.dto.app.advice.category.select.BaiHuoMiddleAdviceVo>>
     **/
    @GetMapping("/findBaiHuoMiddleAdvice")
    @ApiOperation(value = "查找百货中部广告信息")
    public JsonViewData<List<BaiHuoMiddleAdviceVo>> findBaiHuoMiddleAdvice(){

        return setJsonViewData(relTabService.findBaiHuoMiddleAdvice());
    }
}
