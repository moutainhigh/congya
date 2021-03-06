package com.chauncy.data.mapper.order;

import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.dto.app.order.evaluate.add.SearchEvaluateDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetPersonalEvaluateDto;
import com.chauncy.data.dto.supplier.good.select.SearchEvaluatesDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.evaluate.EvaluateLevelNumVo;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.supplier.evaluate.EvaluateVo;
import com.chauncy.data.vo.supplier.evaluate.SearchEvaluateVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 商品评价表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface OmEvaluateMapper extends IBaseMapper<OmEvaluatePo> {

    /**
     * 获取商品对应的所有评价信息
     *
     * @param searchEvaluateDto
     * @return
     */
    List<GoodsEvaluateVo> getGoodsEvaluate(@Param("t") SearchEvaluateDto searchEvaluateDto);

    /**
     * 用户获取已经评价的商品评价信息
     *
     * @param getPersonalEvaluateDto
     * @return
     */
    List<GoodsEvaluateVo> getPersonalEvaluate(@Param("a") GetPersonalEvaluateDto getPersonalEvaluateDto,
                                              @Param("userId") Long userId);
    /**
     * 条件查询评价信息
     * @param searchEvaluateDto
     * @return
     */
    List<SearchEvaluateVo> searchEvaluate(@Param("evaluate") SearchEvaluatesDto searchEvaluateDto);

    /**
     * 获取单个评价信息
     *
     * @param orderId
     * @param skuId
     * @return
     */
    EvaluateVo getEvaluate(@Param("orderId") Long orderId, @Param("skuId") Long skuId);

    /**
     * 获取商品不同评价级别的对应的评价数量
     *
     * @param goodsId
     * @return
     */
    EvaluateLevelNumVo findEvaluateLevelNum(Long goodsId);

    /**
     * @Author chauncy
     * @Date 2019-09-21 13:14
     * @Description //点赞的评价数量加1
     *
     * @Update chauncy
     *
     * @Param [evaluateId]
     * @return void
     **/
    @Update("update om_evaluate set liked_num = liked_num + 1 where id = #{evaluateId}")
    void addLikedNum(Long evaluateId);

    /**
     * @Author chauncy
     * @Date 2019-09-21 13:31
     * @Description //取消点赞，评价点赞数量-1
     *
     * @Update chauncy
     *
     * @Param [evaluateId]
     * @return void
     **/
    @Update("update om_evaluate set liked_num = liked_num - 1 where id = #{evaluateId} and liked_num > 0")
    void delLikedNum(Long evaluateId);

    /**
     * @Author chauncy
     * @Date 2019-09-22 00:31
     * @Description //获取最新的五星好评的一条记录
     *
     * @Update chauncy
     *
     * @Param [goodsId]
     * @return com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo
     **/
    GoodsEvaluateVo getGoodsEvaluateOne(Long goodsId);
}

