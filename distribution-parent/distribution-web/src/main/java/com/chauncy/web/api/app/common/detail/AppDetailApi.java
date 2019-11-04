package com.chauncy.web.api.app.common.detail;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.order.service.IOmRealUserService;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2019-08-31 13:36
 *
 * App端获取的详情
 */

@RestController
@RequestMapping("/app/common/detail")
@Api(tags = "app_通用_详情")
public class AppDetailApi {

    @Autowired
    private IOmShoppingCartService service;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 查看商品详情
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/selectSpecifiedGoods/{goodsId}")
    @ApiOperation("查看具体商品详情")
    public JsonViewData<SpecifiedGoodsVo> selectSpecifiedGoods(@ApiParam(required = true, name = "goodsId", value = "商品ID")
                                                               @PathVariable Long goodsId) {
        UmUserPo userPo = securityUtil.getAppCurrUser();
        if (userPo == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "您不是APP用户！");
        }
        return new JsonViewData<SpecifiedGoodsVo>(service.selectSpecifiedGoods(goodsId, userPo));
    }

    /**
     * @Author yeJH
     * @Date 2019/11/4 9:38
     * @Description 非登录用户查看商品详情
     *
     * @Update yeJH
     *
     * @param  goodsId
     * @return com.chauncy.data.vo.JsonViewData<com.chauncy.data.vo.app.goods.SpecifiedGoodsVo>
     **/
    @GetMapping("/share/selectSpecifiedGoods/{goodsId}")
    @ApiOperation("非登录用户查看具体商品详情")
    public JsonViewData<SpecifiedGoodsVo> selectShareSpecifiedGoods(@ApiParam(required = true, name = "goodsId", value = "商品ID")
                                                               @PathVariable Long goodsId) {

        return new JsonViewData<SpecifiedGoodsVo>(service.selectSpecifiedGoods(goodsId, null));
    }
}
