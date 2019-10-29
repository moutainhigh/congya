package com.chauncy.activity.registration.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.activity.registration.IAmActivityRelGoodsSkuService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.bo.app.activity.GroupStockBo;
import com.chauncy.data.bo.app.activity.IntegralPriceBo;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.domain.po.sys.BasicSettingPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.car.SettleAccountsDto;
import com.chauncy.data.mapper.activity.registration.AmActivityRelGoodsSkuMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.vo.app.activity.integral.JudgeIntegralBalanceVo;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 平台活动的商品与sku关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmActivityRelGoodsSkuServiceImpl extends AbstractService<AmActivityRelGoodsSkuMapper, AmActivityRelGoodsSkuPo> implements IAmActivityRelGoodsSkuService {

    @Autowired
    private AmActivityRelGoodsSkuMapper mapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public JudgeIntegralBalanceVo judgeIntegralBalance(SettleAccountsDto settleAccountsDto) {
        IntegralPriceBo integralPriceBo = mapper.getIntegralPriceBo(settleAccountsDto.getSkuId());
        if (integralPriceBo==null){
            throw new ServiceException(ResultCode.PARAM_ERROR,"该商品当前未处于积分活动中");
        }
        BasicSettingPo queryBasic = basicSettingMapper.selectOne(new QueryWrapper<>());
        //售价与活动价的差价
        BigDecimal price= BigDecimalUtil.safeSubtract(integralPriceBo.getSellPrice(),integralPriceBo.getActivityPrice());
        //需要多少积分
        BigDecimal integral = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(price, queryBasic.getMoneyToIntegrate()),settleAccountsDto.getNumber());
        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        JudgeIntegralBalanceVo judgeIntegralBalanceVo=new JudgeIntegralBalanceVo();
        if (appCurrUser.getCurrentIntegral().compareTo(integral)>=0){
            judgeIntegralBalanceVo.setIsEnough(true);
        }
        else {
            judgeIntegralBalanceVo.setIsEnough(false);
        }
        return judgeIntegralBalanceVo;
    }

    @Override
    public List<GroupStockBo> getGroupStockBo(Long mainId) {
        return mapper.getGroupStockBo(mainId);
    }

    @Override
    public int addStock(GroupStockBo groupStockBo) {
        return mapper.addStockInActivityStock(groupStockBo);
    }

    @Override
    public GroupStockBo getGroupStockBoByMemberId(Long memberId) {
        return mapper.getGroupStockBoByMemberId(memberId);
    }
}
