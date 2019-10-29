package com.chauncy.activity.registration;

import com.chauncy.data.bo.app.activity.GroupStockBo;
import com.chauncy.data.bo.app.activity.IntegralPriceBo;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.car.SettleAccountsDto;
import com.chauncy.data.vo.app.activity.integral.JudgeIntegralBalanceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台活动的商品与sku关联表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
public interface IAmActivityRelGoodsSkuService extends Service<AmActivityRelGoodsSkuPo> {

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

    JudgeIntegralBalanceVo judgeIntegralBalance(SettleAccountsDto settleAccountsDto);


    List<GroupStockBo> getGroupStockBo(Long mainId);

    int addStock(GroupStockBo groupStockBo);


    GroupStockBo getGroupStockBoByMemberId(Long memberId);



}
