package com.chauncy.data.mapper.activity.view;

import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import com.chauncy.data.dto.supplier.activity.add.SearchAllActivitiesDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.supplier.activity.ActivityVo;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-24
 */
public interface ActivityViewMapper extends IBaseMapper<ActivityViewPo> {

    /**
     *
     * 查询全部活动列表信息
     * @param searchAllActivitiesDto
     * @return
     */
    List<ActivityViewPo> searchAllActivitiesVo(SearchAllActivitiesDto searchAllActivitiesDto);

}
