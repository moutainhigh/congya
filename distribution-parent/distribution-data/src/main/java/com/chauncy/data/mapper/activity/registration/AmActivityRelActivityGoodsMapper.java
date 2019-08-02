package com.chauncy.data.mapper.activity.registration;

import com.chauncy.data.bo.supplier.activity.StoreActivityBo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelActivityGoodsPo;
import com.chauncy.data.dto.supplier.activity.select.SearchSupplierActivityDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.supplier.activity.SearchSupplierActivityVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 平台活动与商品关联表 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
public interface AmActivityRelActivityGoodsMapper extends IBaseMapper<AmActivityRelActivityGoodsPo> {

    /**
     * 获取店铺参与的活动
     * @param storeId
     * @return
     */
    @Select("select activity_id,activity_type,goods_id from am_activity_rel_activity_goods a where store_id = #{storeId} and goods_id = #{goodsId} and del_flag = 0 ")
    List<StoreActivityBo> findStoreActivity(@Param("storeId") Long storeId,@Param("goodsId") Long goodsId);

    /**
     * 商家端查找参与的活动
     *
     * @param searchSupplierActivityDto
     * @return
     */
    List<SearchSupplierActivityVo> searchSupplierActivity(@Param("t") SearchSupplierActivityDto searchSupplierActivityDto,@Param("tableName") String tableName,@Param("storeId") Long storeId);
}
