package com.chauncy.store.rel.service;

import com.chauncy.data.domain.po.store.rel.SmStoreBankCardPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.store.add.SaveStoreBankCardDto;
import com.chauncy.data.vo.manage.store.rel.StoreBankCardVo;

import java.util.List;

/**
 * <p>
 * 店铺银行卡表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
public interface ISmStoreBankCardService extends Service<SmStoreBankCardPo> {


    /**
     * 商家店铺新增/编辑银行卡
     * @return
     */
    Long saveBankCard(SaveStoreBankCardDto saveStoreBankCardDto);

    /**
     * 店铺银行卡列表
     * @return
     */
    List<StoreBankCardVo> selectBankCard();
}
