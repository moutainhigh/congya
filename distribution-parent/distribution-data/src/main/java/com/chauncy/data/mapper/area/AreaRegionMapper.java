package com.chauncy.data.mapper.area;

import com.chauncy.data.domain.po.area.AreaRegionPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.area.AreaCityVo;
import com.chauncy.data.vo.area.AreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 中国行政地址信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface AreaRegionMapper extends IBaseMapper<AreaRegionPo> {

    /**
     * 获取省市区
     *
     * @return
     */
    List<AreaVo> searchList();

    /**
     * 根据区县编号获取街道信息
     *
     * @param parentCode
     * @return
     */
    List<AreaVo> findStreet(@Param("parentCode") String parentCode);

    /**
     * 获取省市
     *
     * @return
     */
    List<AreaCityVo> search();
}
