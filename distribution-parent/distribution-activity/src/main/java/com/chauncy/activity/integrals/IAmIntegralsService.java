package com.chauncy.activity.integrals;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.integrals.AmIntegralsPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.SearchCategoryByActivityIdDto;
import com.chauncy.data.dto.manage.activity.integrals.add.SaveIntegralsDto;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分活动管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmIntegralsService extends Service<AmIntegralsPo> {

    /**
     * 获取分类信息
     *
     * @param searchCategoryByActivityIdDto
     * @return
     */
    Map<String,Object> searchCategory(SearchCategoryByActivityIdDto searchCategoryByActivityIdDto) throws Exception;

    /**
     * 保存积分信息
     * @param saveIntegralsDto
     */
    void saveIntegrals(SaveIntegralsDto saveIntegralsDto);

    /**
     * 条件查询积分活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    PageInfo<SearchActivityListVo> searchIntegralsList(SearchActivityListDto searchActivityListDto);

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    void delByIds(List<Long> ids);
}
