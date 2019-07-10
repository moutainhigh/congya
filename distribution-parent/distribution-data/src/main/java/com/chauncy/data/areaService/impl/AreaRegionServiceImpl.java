package com.chauncy.data.areaService.impl;

import com.chauncy.common.util.TreeUtil;
import com.chauncy.data.domain.po.area.AreaRegionPo;
import com.chauncy.data.mapper.area.AreaRegionMapper;
import com.chauncy.data.areaService.IAreaRegionService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.area.AreaCityVo;
import com.chauncy.data.vo.area.AreaVo;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中国行政地址信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class AreaRegionServiceImpl extends AbstractService<AreaRegionMapper, AreaRegionPo> implements IAreaRegionService {

    @Autowired
    private AreaRegionMapper mapper;

    /**
     * 获取省市区
     *
     * @return
     */
    @Override
    public List<AreaVo> searchList() {

        List<AreaVo> city = mapper.searchList();
        List<AreaVo> areaVoList = Lists.newArrayList();
        try {
            areaVoList = TreeUtil.getTree(city,"cityCode","parentCode","children");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaVoList;
    }

    /**
     * 获取省市
     *
     * @return
     */
    @Override
    public List<AreaCityVo> search() {

        List<AreaCityVo> city = mapper.search();
        List<AreaCityVo> areaCityVos = Lists.newArrayList();
        try {
            areaCityVos = TreeUtil.getTree(city,"cityCode","parentCode","children");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaCityVos;
    }

    /**
     * 根据区县编号获取街道信息
     *
     * @param parentCode
     * @return
     */
    @Override
    public List<AreaVo> findStreet(String parentCode) {

        return mapper.findStreet(parentCode);
    }
}
