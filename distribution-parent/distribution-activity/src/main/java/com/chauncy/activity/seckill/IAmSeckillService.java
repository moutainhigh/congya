package com.chauncy.activity.seckill;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.seckill.SaveSeckillDto;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 秒杀活动管理 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface IAmSeckillService extends Service<AmSeckillPo> {

    /**
     * 保存秒杀活动信息
     * @param saveSeckillDto
     * @return
     */
    void saveReduced(SaveSeckillDto saveSeckillDto);


    /**
     * 条件查询秒杀活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    PageInfo<SearchActivityListVo> searchSeckillList(SearchActivityListDto searchActivityListDto);

    /**
     * 批量删除活动
     *
     * @param ids
     * @return
     */
    void delByIds(List<Long> ids);
}
