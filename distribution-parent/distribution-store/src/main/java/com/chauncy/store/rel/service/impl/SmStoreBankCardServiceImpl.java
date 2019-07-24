package com.chauncy.store.rel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.store.rel.SmStoreBankCardPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.store.add.SaveStoreBankCardDto;
import com.chauncy.data.mapper.store.rel.SmStoreBankCardMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.store.rel.StoreBankCardVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.rel.service.ISmStoreBankCardService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 店铺银行卡表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmStoreBankCardServiceImpl extends AbstractService<SmStoreBankCardMapper, SmStoreBankCardPo> implements ISmStoreBankCardService {

    @Autowired
    private SmStoreBankCardMapper smStoreBankCardMapper;

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 商家店铺新增/编辑银行卡
     *
     * @return
     */
    @Override
    public Long saveBankCard(SaveStoreBankCardDto saveStoreBankCardDto) {
        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            //当前登录用户跟操作不匹配
            throw  new ServiceException(ResultCode.FAIL, "操作失败");
        }
        SmStoreBankCardPo smStoreBankCardPo = new SmStoreBankCardPo();
        BeanUtils.copyProperties(saveStoreBankCardDto,smStoreBankCardPo);
        if(null == saveStoreBankCardDto.getId()) {
            smStoreBankCardPo.setStoreId(sysUserPo.getStoreId());
            smStoreBankCardPo.setCreateBy(sysUserPo.getUsername());
            smStoreBankCardPo.setUpdateTime(LocalDateTime.now());
            smStoreBankCardMapper.insert(smStoreBankCardPo);
        } else {
            smStoreBankCardPo.setUpdateBy(sysUserPo.getUsername());
            smStoreBankCardMapper.updateById(smStoreBankCardPo);
        }
        return smStoreBankCardPo.getId();
    }


    /**
     * 店铺银行卡列表
     *
     * @return
     */
    @Override
    public List<StoreBankCardVo> selectBankCard() {
        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            //当前登录用户跟操作不匹配
            throw  new ServiceException(ResultCode.FAIL, "操作失败");
        }
        return smStoreBankCardMapper.selectBankCard(sysUserPo.getStoreId());
    }
}
