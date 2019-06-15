package com.chauncy.store.service;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.add.StoreAccountInfoDto;
import com.chauncy.data.dto.manage.store.add.StoreBaseInfoDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.store.SmStoreBaseVo;
import com.github.pagehelper.PageInfo;

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

    /**
     * 修改店铺经营状态
     * @param baseUpdateStatusDto
     * ids 店铺ID
     * enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    JsonViewData editStoreStatus(BaseUpdateStatusDto baseUpdateStatusDto);



    /**
     * 条件查询
     * @param storeSearchDto
     * @return
     */
    PageInfo<SmStoreBaseVo> searchBaseInfo(StoreSearchDto storeSearchDto);
}
