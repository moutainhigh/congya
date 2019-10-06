package com.chauncy.data.mapper.order;

import com.chauncy.data.domain.po.order.OmRealUserPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.order.cart.RealUserVo;

/**
 * <p>
 * 订单实名认证用户 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-08
 */
public interface OmRealUserMapper extends IBaseMapper<OmRealUserPo> {

    /**获取实名认证状态
     * @Author zhangrt
     * @Date 2019/10/5 16:26
     * @Description
     *
     * @Update
     *
     * @Param [id]
     * @return com.chauncy.data.vo.app.order.cart.RealUserVo
     **/

    RealUserVo getVoById(Long id);


}
