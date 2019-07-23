package com.chauncy.data.mapper.store.rel;

import com.chauncy.data.domain.po.store.rel.SmStoreBankCardPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.store.rel.StoreBankCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 店铺银行卡表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
public interface SmStoreBankCardMapper extends IBaseMapper<SmStoreBankCardPo> {

    /**
     * 店铺银行卡列表
     * @param storeId
     * @return
     */
    List<StoreBankCardVo> selectBankCard(@Param("storeId") Long storeId);
}
