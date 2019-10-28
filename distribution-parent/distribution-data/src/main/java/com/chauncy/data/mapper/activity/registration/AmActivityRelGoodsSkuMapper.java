package com.chauncy.data.mapper.activity.registration;

import com.chauncy.data.bo.app.activity.GroupStockBo;
import com.chauncy.data.bo.app.activity.IntegralPriceBo;
import com.chauncy.data.bo.app.car.ActivitySkuBo;
import com.chauncy.data.bo.app.car.FullDiscountSkuBo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台活动的商品与sku关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
public interface AmActivityRelGoodsSkuMapper extends IBaseMapper<AmActivityRelGoodsSkuPo> {

    /**
     * @Author zhangrt
     * @Date 2019/10/9 13:32
     * @Description  根据skuid查出活动一些信息，没有则不是活动商品
     *
     * @Update
     *
     * @Param [ids]
     * @return java.util.List<com.chauncy.data.bo.app.car.ActivitySkuBo>
     **/

    List<ActivitySkuBo> getBoByIds(@Param("ids") List<Long> ids);


    /**
     * @Author zhangrt
     * @Date 2019/10/9 13:32
     * @Description  根据skuid查出满减活动的一些信息
     *
     * @Update
     *
     * @Param [ids]
     * @return java.util.List<com.chauncy.data.bo.app.car.ActivitySkuBo>
     **/

    List<FullDiscountSkuBo> getFullDiscountSkuBo(@Param("ids") List<Long> ids);

    /**
     * @Author zhangrt
     * @Date 2019/10/12 11:03
     * @Description 根据skuid查出积分商品的销售价和活动价格
     *
     * @Update
     *
     * @Param [skuId]
     * @return com.chauncy.data.bo.app.activity.IntegralPriceBo
     **/

    IntegralPriceBo getIntegralPriceBo(Long skuId);


    int updateStock(@Param("list") List<ActivitySkuBo> activitySkuBos);

    List<GroupStockBo> getGroupStockBo(Long mainId);

    GroupStockBo getGroupStockBoByMemberId(Long memberId);


    int addStockInActivityStock(GroupStockBo groupStockBo);
    int addSaleVolumeInActivityStock(GroupStockBo groupStockBo);


    /**
     * @Author zhangrt
     * @Date 2019/10/26 0:54
     * @Description  根据评团成员id修改
     *
     * @Update
     *
     * @Param [groupStockBo]
     * @return int
     **/

    int addStockInActivityStockByMemberId(GroupStockBo groupStockBo);


}
