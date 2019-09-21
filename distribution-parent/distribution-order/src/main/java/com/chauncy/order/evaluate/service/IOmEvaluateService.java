package com.chauncy.order.evaluate.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.dto.app.order.evaluate.add.AddValuateDto;
import com.chauncy.data.dto.app.order.evaluate.add.SearchEvaluateDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetPersonalEvaluateDto;
import com.chauncy.data.dto.supplier.evaluate.SaveStoreReplyDto;
import com.chauncy.data.dto.supplier.good.select.SearchEvaluatesDto;
import com.chauncy.data.vo.app.evaluate.EvaluateLevelNumVo;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.supplier.evaluate.EvaluateVo;
import com.chauncy.data.vo.supplier.evaluate.SearchEvaluateVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * <p>
 * 商品评价表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface IOmEvaluateService extends Service<OmEvaluatePo> {

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    void addEvaluate(AddValuateDto addValuateDto);

    /**
     * 获取商品对应的所有评价信息
     *
     * @return
     */
    PageInfo<GoodsEvaluateVo> getGoodsEvaluate(SearchEvaluateDto searchEvaluateDtod);

    /**
     * 用户获取已经评价的商品评价信息
     * @param getPersonalEvaluateDto
     * @return
     */
    PageInfo<GoodsEvaluateVo> getPersonalEvaluate(GetPersonalEvaluateDto getPersonalEvaluateDto);

    /**
     * 条件查询评价信息
     * @param searchEvaluateDto
     * @return
     */
    PageInfo<SearchEvaluateVo> searchEvaluate(SearchEvaluatesDto searchEvaluateDto);

    /**
     * 商家端回复评论
     * @param saveStoreReplyDto
     */
    void reply(SaveStoreReplyDto saveStoreReplyDto);

    /**
     * 获取商品不同评价级别的对应的评价数量
     *
     * @param goodsId
     * @return
     */
    EvaluateLevelNumVo findEvaluateLevelNum(Long goodsId);

    /**
     * @Author chauncy
     * @Date 2019-09-20 20:42
     * @Description //对商品评价点赞/取消点赞
     *
     * @Update chauncy
     *
     * @Param [evaluateId]
     * @return java.lang.Integer
     **/
    Integer updateEvaluateLiked(Long evaluateId);
}
