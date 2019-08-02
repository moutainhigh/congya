package com.chauncy.data.areaService;

import com.chauncy.data.domain.po.area.AreaRegionPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.vo.area.AreaCityVo;
import com.chauncy.data.vo.area.AreaVo;

import java.util.List;

/**
 * <p>
 * 中国行政地址信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface IAreaRegionService extends Service<AreaRegionPo> {

    /**
     * 获取省市区
     *
     * @return
     */
    List<AreaVo> searchList();

    /**
     * 获取省市
     *
     * @return
     */
    List<AreaCityVo> search();

    /**
     * 根据区县编号获取街道信息
     *
     * @param parentCode
     * @return
     */
    List<AreaVo> findStreet(String parentCode);

}
