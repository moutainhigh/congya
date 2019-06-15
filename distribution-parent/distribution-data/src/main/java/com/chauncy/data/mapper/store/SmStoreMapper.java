package com.chauncy.data.mapper.store;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.store.select.StoreSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.store.SmStoreBaseVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-03
 */
public interface SmStoreMapper extends IBaseMapper<SmStorePo> {

    /**
     * 修改店铺经营状态
     * @param baseUpdateStatusDto
     * ids 店铺ID
     * enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    int editStoreStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 条件查询
     *
     * @param storeSearchDto
     * @return
     */
    List<SmStoreBaseVo> searchBaseInfo(StoreSearchDto storeSearchDto);
}
