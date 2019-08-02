package com.chauncy.activity.reduced;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.reduced.AmReducedPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.reduced.add.SaveReducedDto;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 满减活动管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmReducedService extends Service<AmReducedPo> {

    /**
     * 保存满减活动信息
     * @param saveReducedDto
     * @return
     */
    void saveReduced(SaveReducedDto saveReducedDto);

    /**
     * 条件查询满减活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    PageInfo<SearchActivityListVo> searchReduceList(SearchActivityListDto searchActivityListDto);

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    void delByIds(List<Long> ids);
}
