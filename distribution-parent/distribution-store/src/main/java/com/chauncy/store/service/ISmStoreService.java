package com.chauncy.store.service;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.store.StoreAccountInfoDto;
import com.chauncy.data.dto.store.StoreBaseInfoDto;
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
     * 保存店铺基本信息
     * @param storeBaseInfoDto
     * @return
     */
    JsonViewData saveStore(StoreBaseInfoDto storeBaseInfoDto);

    /**
     * 保存店铺账户信息
     * @param storeAccountInfoDto
     * @return
     */
    JsonViewData saveStore(StoreAccountInfoDto storeAccountInfoDto);
}
