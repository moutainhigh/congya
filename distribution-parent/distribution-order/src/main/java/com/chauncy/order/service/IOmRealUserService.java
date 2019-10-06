package com.chauncy.order.service;

import com.chauncy.data.domain.po.order.OmRealUserPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.vo.app.order.cart.RealUserVo;

/**
 * <p>
 * 订单实名认证用户 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-08
 */
public interface IOmRealUserService extends Service<OmRealUserPo> {

    RealUserVo getVoById(Long id);



}
