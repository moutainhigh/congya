package com.chauncy.data.mapper.activity.seckill;

import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;

import java.util.List;

/**
 * <p>
 * 秒杀活动管理 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
public interface AmSeckillMapper extends IBaseMapper<AmSeckillPo> {

    /**
     * 条件查询秒杀活动信息
     *
     * @param searchActivityListDto
     * @return
     */
    List<SearchActivityListVo> searchSeckillList(SearchActivityListDto searchActivityListDto);

    /**
     * 查询秒杀详情
     *
     * @param id
     * @return
     */
    SearchActivityListVo findSeckillById(Long id);
}
