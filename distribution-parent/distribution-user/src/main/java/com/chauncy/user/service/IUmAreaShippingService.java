package com.chauncy.user.service;

import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddAreaDto;
import com.chauncy.data.vo.app.user.ShipAreaVo;

import java.util.List;

/**
 * <p>
 * 收货地址表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface IUmAreaShippingService extends Service<UmAreaShippingPo> {

    /**
     * 用户添加收货地址
     *
     * @param addAreaDto
     * @return
     */
    void addArea(AddAreaDto addAreaDto, UmUserPo userPo);

    /**
     * 用户修改收货地址
     *
     * @param updateAreaDto
     * @return
     */
    void updateArea(AddAreaDto updateAreaDto,UmUserPo userPo);

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    void delArea(Long id);

    /**
     * 查找用户收货地址
     *
     * @param userId
     * @return
     */
    List<ShipAreaVo> findShipArea(Long userId);
}
