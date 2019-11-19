package com.chauncy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.domain.po.area.AreaRegionPo;
import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.user.add.AddAreaDto;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.mapper.user.UmAreaShippingMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.app.user.ShipAreaVo;
import com.chauncy.user.service.IUmAreaShippingService;
import org.apache.catalina.security.SecurityUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.Query;
import javax.swing.plaf.ListUI;
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
    public Long addArea(AddAreaDto addAreaDto, UmUserPo userPo) {

        UmAreaShippingPo areaShippingPo = updateDefault(addAreaDto,userPo);

        //第一个地址无论用户有没有选择默认，都是默认地址
        QueryWrapper<UmAreaShippingPo> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(UmAreaShippingPo::getUmUserId,userPo.getId());
        Integer count = mapper.selectCount(queryWrapper);
        if (count==0){
            areaShippingPo.setIsDefault(true);
        }

        //获取当前登陆的用户ID
        areaShippingPo.setUmUserId(userPo.getId());
        areaShippingPo.setCreateBy(userPo.getName());
        areaShippingPo.setId(null);
        mapper.insert(areaShippingPo);
        return areaShippingPo.getId();
    }

    /**
     * 用户修改收货地址
     *
     * @param updateAreaDto
     * @return
     */
    @Override
    public void updateArea(AddAreaDto updateAreaDto,UmUserPo userPo) {

        UmAreaShippingPo areaShippingPo = updateDefault(updateAreaDto,userPo);
        areaShippingPo.setUpdateBy(userPo.getName());
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
        UmAreaShippingPo areaShippingPo = mapper.selectById(id);
        if (areaShippingPo == null){
            throw new ServiceException(ResultCode.FAIL,"收货地址不存在!");
        }
        Long userId = mapper.selectById(id).getUmUserId();
        mapper.deleteById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("um_user_id", userId);
        List<UmAreaShippingPo> areaShippingPos = mapper.selectByMap(map);
        //如果有地址但是没有默认地址，设置第一个地址为默认地址
        long count = areaShippingPos.stream().filter(UmAreaShippingPo::getIsDefault).count();
        if (count==0 && !ListUtil.isListNullAndEmpty(areaShippingPos)){
            UmAreaShippingPo umAreaShippingPo = areaShippingPos.get(0);
            umAreaShippingPo.setIsDefault(true);
            mapper.updateById(umAreaShippingPo);
        }
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
            //shipAreaVo.setMergerName(areaRegionMapper.selectById(x.getAreaId()).getMergerName());
            shipAreaVos.add(shipAreaVo);
        });
        //如果有地址但是没有默认地址，设置第一个地址为默认地址
        long count = shipAreaVos.stream().filter(ShipAreaVo::getIsDefault).count();
        if (count==0 && !ListUtil.isListNullAndEmpty(shipAreaVos)){
            shipAreaVos.get(0).setIsDefault(true);
        }
        shipAreaVos.forEach(x->{
            x.setAreaName(x.getAreaName().replace(","," "));
        });
        return shipAreaVos;
    }

    @Override
    public ShipAreaVo findDefaultShipArea(Long userId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("um_user_id", userId);
        queryWrapper.eq("is_default", true);
        UmAreaShippingPo areaShippingPo = mapper.selectOne(queryWrapper);
        ShipAreaVo shipAreaVo = new ShipAreaVo();
        if (areaShippingPo != null) {
            BeanUtils.copyProperties(areaShippingPo, shipAreaVo);
            return shipAreaVo;
        }else {
            QueryWrapper queryWrapper2=new QueryWrapper();
            queryWrapper2.eq("um_user_id", userId);
            List<UmAreaShippingPo> areaShippingPos = mapper.selectList(queryWrapper2);
            if (ListUtil.isListNullAndEmpty(areaShippingPos)){
                return null;
            }
            else {
                UmAreaShippingPo areaShippingPo2 = areaShippingPos.get(0);
                ShipAreaVo shipAreaVo2 = new ShipAreaVo();
                BeanUtils.copyProperties(areaShippingPo2, shipAreaVo2);
                return shipAreaVo2;

            }
        }
    }

    private UmAreaShippingPo updateDefault(AddAreaDto updateAreaDto,UmUserPo userPo) {
        if (updateAreaDto.getIsDefault()) {
            //判断当前用户是否已有默认收货地址，若有则把之前的置为0
            UmAreaShippingPo umAreaShippingPo = mapper.selectOne(new QueryWrapper<UmAreaShippingPo>().lambda()
                    .eq(UmAreaShippingPo::getIsDefault,true).eq(UmAreaShippingPo::getUmUserId,userPo.getId()));
            if (umAreaShippingPo != null) {
                umAreaShippingPo.setIsDefault(false);
                mapper.updateById(umAreaShippingPo);
            }
        }
        AreaRegionPo queryAreaRegion = areaRegionMapper.selectById(updateAreaDto.getAreaId());
        UmAreaShippingPo areaShippingPo = new UmAreaShippingPo();
        BeanUtils.copyProperties(updateAreaDto, areaShippingPo);
        areaShippingPo.setAreaName(queryAreaRegion.getMergerName());
        return areaShippingPo;
    }
}
