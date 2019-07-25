package com.chauncy.activity.view;

import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.supplier.activity.add.SearchAllActivitiesDto;
import com.chauncy.data.dto.supplier.activity.select.FindByIdAndTypeDto;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.chauncy.data.vo.supplier.activity.ActivityVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-24
 */
public interface IActivityViewService extends Service<ActivityViewPo> {

    /**
     *
     * 查询全部活动列表信息
     * @param searchAllActivitiesDto
     * @return
     */
    PageInfo<ActivityViewPo> searchAllActivitiesVo(SearchAllActivitiesDto searchAllActivitiesDto);

    /**
     * 查询活动详情
     * @param findByIdAndTypeDto
     * @return
     */
    SearchActivityListVo findActivityDetailByIdAndType(FindByIdAndTypeDto findByIdAndTypeDto);
}
