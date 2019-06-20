package com.chauncy.data.mapper.store;

import com.chauncy.data.domain.po.store.rel.SmRelStoreAttributePo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-19
 */
public interface SmRelStoreAttributeMapper extends IBaseMapper<SmRelStoreAttributePo> {


    /**
     * 批量插入店铺品牌关联记录
     * @param ids  品牌id数组
     * @param ids  店铺id
     * @param userName  添加记录用户
     */
    void insertByBatch(@Param("ids") Long[] ids, @Param("storeId") Long storeId,  @Param("userName") String userName);
}
