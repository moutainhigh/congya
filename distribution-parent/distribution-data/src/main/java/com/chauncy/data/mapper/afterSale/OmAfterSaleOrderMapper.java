package com.chauncy.data.mapper.afterSale;

import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.dto.manage.order.afterSale.SearchAfterSaleOrderDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.order.my.afterSale.AfterSaleDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
import com.chauncy.data.vo.app.order.my.afterSale.MyAfterSaleOrderListVo;
import com.chauncy.data.vo.manage.order.afterSale.AfterSaleLogVo;
import com.chauncy.data.vo.manage.order.afterSale.AfterSaleListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 售后订单表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-21
 */
public interface OmAfterSaleOrderMapper extends IBaseMapper<OmAfterSaleOrderPo> {

    /**
     * @Author yeJH
     * @Date 2019/11/12 22:08
     * @Description 根据售后订单获取店铺logo
     *
     * @Update yeJH
     *
     * @param  afterSaleOrderId
     * @return java.lang.String
     **/
    String getStoreLogoByOrder(Long afterSaleOrderId);

    /**
     * 通过订单id查找商品快照
     * @param id
     * @return
     */
    List<ApplyAfterSaleVo> searchBrothersById(Long id);

    /**
     * app我的售后订单列表
     * @param userId
     * @return
     */
    List<MyAfterSaleOrderListVo> searchAfterSaleOrderList(Long userId);


    /**
     * 总后台售后订单列表
     * @return
     */
    List<AfterSaleListVo> searchAfterList(@Param("t") SearchAfterSaleOrderDto searchAfterSaleOrderDto,@Param("storeId") Long storeId);

    /**
     * 根据售后订单id查找售后进度列表
     * @param afterSaleOrderId
     * @return
     */
    List<AfterSaleLogVo> searchCheckList(Long afterSaleOrderId);

    /**
     * 获取售后详情
     * @param afterSaleOrderId
     * @return
     */
    AfterSaleDetailVo getAfterSaleDetail( Long afterSaleOrderId);

    /**
     * 获取售后申请详情
     * @param afterSaleOrderId
     * @return
     */
    ApplyAfterDetailVo getApplyAfterDetail(Long afterSaleOrderId);
}
