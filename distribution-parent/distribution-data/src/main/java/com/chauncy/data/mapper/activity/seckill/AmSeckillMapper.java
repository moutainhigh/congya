package com.chauncy.data.mapper.activity.seckill;

import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.dto.app.product.SearchSeckillGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo;
import com.chauncy.data.vo.app.goods.SeckillGoodsVo;
import com.chauncy.data.vo.manage.activity.SearchActivityListVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
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
     * @Author yeJH
     * @Date 2019/10/9 11:12
     * @Description 获取秒杀活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return java.util.List<com.chauncy.data.vo.BaseVo>
     **/
    List<BaseVo> findGoodsCategory(SearchSeckillGoodsDto searchSeckillGoodsDto);

    /**
     * @Author yeJH
     * @Date 2019/10/8 21:10
     * @Description 获取秒杀活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return java.util.List<SeckillGoodsVo>
     **/
    List<SeckillGoodsVo> searchActivityGoodsList(SearchSeckillGoodsDto searchSeckillGoodsDto);
    
    /**
     * @Author yeJH
     * @Date 2019/10/8 11:24
     * @Description 获取秒杀时间段  当前时间前24小时，之后24小时范围内的所有活动时间
     *
     * @Update yeJH
     *
     * @param  startDateTime
     * @param  endDateTime
     * @return java.util.List<com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo>
     **/
    List<SeckillTimeQuantumVo> getSeckillTimeQuantum(@Param("startDateTime") LocalDateTime startDateTime,
                                                     @Param("endDateTime")LocalDateTime endDateTime);

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
