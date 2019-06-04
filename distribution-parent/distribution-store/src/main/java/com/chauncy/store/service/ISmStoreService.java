package com.chauncy.store.service;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.vo.JsonViewData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
public interface ISmStoreService extends Service<SmStorePo> {

    /**
     * 保存店铺信息
     * @param smStorePo
     * @return
     */
    JsonViewData saveStore(SmStorePo smStorePo);
}
