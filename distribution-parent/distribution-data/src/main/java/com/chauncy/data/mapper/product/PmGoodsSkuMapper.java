package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.app.activity.GroupStockBo;
import com.chauncy.data.bo.app.order.reward.RewardShopTicketBo;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.dto.app.car.ShopTicketSoWithCarGoodDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.advice.goods.BrandGoodsVo;
import com.chauncy.data.vo.app.brand.GoodsVo;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品sku信息表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsSkuMapper extends IBaseMapper<PmGoodsSkuPo> {

    /**
     * 獲取最低價格
     * @param goodsId
     * @return
     */
    @Select ("select min(sell_price) from pm_goods_sku where goods_id = #{goodsId} and del_flag=0")
    BigDecimal getLowestPrice (Long goodsId);

    /**
     * 獲取最高價格
     * @param goodsId
     * @return
     */
    @Select ("select max(sell_price) from pm_goods_sku where goods_id = #{goodsId} and del_flag=0")
    BigDecimal getHighestPrice (Long goodsId);

    /**
     * 获取最低价格对应的相关信息
     *
     * @param goodsId
     * @return
     */
    @Select("select a.id,a.sell_price as sale_price,a.line_price from pm_goods_sku as a where sell_price =\n" +
            "(select min(sell_price) from pm_goods_sku where goods_id = #{goodsId} and del_flag=0)")
    List<GoodsVo> getPrice(Long goodsId);

    int updateStock(@Param("list") List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos);

    int updateStock2(@Param("list") List<ShopTicketSoWithCarGoodDto> shopTicketSoWithCarGoodDtos);

    /**
     * 获取具体的sku信息
     *
     * @param brandId
     * @return
     */
    List<BrandGoodsVo> findBrandGoodsVos(Long brandId);

    /**
     * 增加销量
     * @param id
     * @param salesVolume
     */
    void addASalesVolume(@Param("id") Long id,@Param("salesVolume") Integer salesVolume);

    /**
     * 获取计算返购物券的参数
     *
     * @param goodsId
     * @return
     */
    @Select("select * from pm_goods_sku where goods_id = #{goodsId}")
    List<RewardShopTicketBo> findRewardShopTicketInfos(Long goodsId);

    /**
     * 根据skuId获取计算返券值需要的信息
     * 、
     * @param skuId
     * @return
     */
    @Select("select * from pm_goods_sku where id = #{skuId}")
    RewardShopTicketBo getRewardShopTicket(Long skuId);

    /**
     * 根据skuId获取商品名称
     * 、
     * @param skuId
     * @return
     */
    @Select("select g.name from pm_goods_sku s,pm_goods g where s.goods_id=g.id  and s.id = #{skuId}")
    String getGoodsName(Long skuId);

    /**
     * @Author zhangrt
     * @Date 2019/10/26 0:21
     * @Description  拼团失败加回真实库存
     *
     * @Update
     *
     * @Param [groupStockBo]
     * @return int
     **/

    int addStockInGroup(GroupStockBo groupStockBo);
}
