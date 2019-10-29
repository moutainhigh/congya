package com.chauncy.product.service;

import com.chauncy.data.bo.app.activity.GroupStockBo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;

/**
 * <p>
 * 商品sku信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsSkuService extends Service<PmGoodsSkuPo> {

    /**
     * @Author zhangrt
     * @Date 2019/10/26 0:20
     * @Description  拼团失败返回库存
     *
     * @Update
     *
     * @Param [groupStockBo]
     * @return int
     **/

    int addStockInGroup(GroupStockBo groupStockBo);


}
