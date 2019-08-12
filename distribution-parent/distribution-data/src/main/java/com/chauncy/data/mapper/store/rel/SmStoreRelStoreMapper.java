package com.chauncy.data.mapper.store.rel;

import com.chauncy.data.domain.po.store.rel.SmStoreRelStorePo;
import com.chauncy.data.dto.manage.store.add.StoreRelStoreDto;
import com.chauncy.data.mapper.IBaseMapper;

/**
 * <p>
 * 店铺与店铺关联信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-07
 */
public interface SmStoreRelStoreMapper extends IBaseMapper<SmStoreRelStorePo> {

    /**
     * 1.如果关系链是否已存在  storeId  parentId  type 都相同
     * 2.或者绑定的店铺关系是团队合作，该店铺只能绑定一个店铺，也只能被一个店铺绑定
     * @param storeRelStoreDto
     * @return
     */
    Integer selectStoreRelCount(StoreRelStoreDto storeRelStoreDto);
}
