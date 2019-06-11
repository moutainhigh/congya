package com.chauncy.data.mapper.store;

import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param ids 店铺id
     * @param enabled 店铺经营状态修改 true 启用 false 禁用
     * @return
     */
    int editStoreStatus(@Param("id") Long[] ids, @Param("enabled") Boolean enabled);
}
