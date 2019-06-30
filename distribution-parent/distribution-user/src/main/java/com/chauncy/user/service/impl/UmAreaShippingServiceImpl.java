package com.chauncy.user.service.impl;

import com.chauncy.data.domain.po.area.AreaRegionPo;
import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.dto.app.user.AddAreaDto;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.mapper.user.UmAreaShippingMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.user.ShipAreaVo;
import com.chauncy.user.service.IUmAreaShippingService;
import io.swagger.annotations.ApiModel;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class UmAreaShippingServiceImpl extends AbstractService<UmAreaShippingMapper, UmAreaShippingPo> implements IUmAreaShippingService {

    @Autowired
    private UmAreaShippingMapper mapper;

    @Autowired
    private AreaRegionMapper areaRegionMapper;

    /**
     * 用户添加收货地址
     *
     * @param addAreaDto
     * @return
     */
    @Override
    public void addArea(AddAreaDto addAreaDto) {

        UmAreaShippingPo areaShippingPo = updateDefault(addAreaDto);
        //获取当前登陆的用户ID
        areaShippingPo.setUmUserId((long) 1);
        areaShippingPo.setCreateBy("当前登陆的用户");
        areaShippingPo.setId(null);
        mapper.insert(areaShippingPo);
    }

    /**
     * 用户修改收货地址
     *
     * @param updateAreaDto
     * @return
     */
    @Override
    public void updateArea(AddAreaDto updateAreaDto) {

        UmAreaShippingPo areaShippingPo = updateDefault(updateAreaDto);
        areaShippingPo.setUpdateBy("当前用户");
        mapper.updateById(areaShippingPo);
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @Override
    public void delArea(Long id) {
        mapper.deleteById(id);
    }

    /**
     * 查找用户收货地址
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShipAreaVo> findShipArea(Long userId) {

        List<ShipAreaVo> shipAreaVos = Lists.newArrayList();

        Map<String, Object> map = new HashMap<>();
        map.put("um_user_id", userId);
        List<UmAreaShippingPo> areaShippingPos = mapper.selectByMap(map);
        areaShippingPos.stream().forEach(x -> {
            ShipAreaVo shipAreaVo = new ShipAreaVo();
            BeanUtils.copyProperties(x, shipAreaVo);
            shipAreaVo.setMergerName(areaRegionMapper.selectById(x.getAreaId()).getMergerName());
            shipAreaVos.add(shipAreaVo);
        });
        return shipAreaVos;
    }

    private UmAreaShippingPo updateDefault(AddAreaDto updateAreaDto) {
        if (updateAreaDto.getIsDefault()) {
            //判断当前用户是否已有默认收货地址，若有则更新置为0
            Map<String, Object> map = new HashMap<>();
            map.put("is_default", true);
            if (mapper.selectByMap(map) != null && mapper.selectByMap(map).size() != 0) {
                UmAreaShippingPo po = mapper.selectByMap(map).get(0);
                po.setIsDefault(false);
                mapper.updateById(po);
            }
        }
        UmAreaShippingPo areaShippingPo = new UmAreaShippingPo();
        BeanUtils.copyProperties(updateAreaDto, areaShippingPo);
        return areaShippingPo;
    }
}
