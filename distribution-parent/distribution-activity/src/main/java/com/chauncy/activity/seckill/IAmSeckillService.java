package com.chauncy.activity.seckill;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.seckill.AmSeckillPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.product.SearchSeckillGoodsDto;
import com.chauncy.data.dto.manage.activity.SearchActivityListDto;
import com.chauncy.data.dto.manage.activity.seckill.SaveSeckillDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo;
import com.chauncy.data.vo.app.goods.SeckillGoodsVo;
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
     * @Author yeJH
     * @Date 2019/10/9 10:36
     * @Description 获取秒杀活动商品一级分类
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return java.lang.Object
     **/
    List<BaseVo> findGoodsCategory(SearchSeckillGoodsDto searchSeckillGoodsDto);

    /**
     * @Author yeJH
     * @Date 2019/10/8 20:57
     * @Description 获取秒杀活动商品列表
     *
     * @Update yeJH
     *
     * @param  searchSeckillGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.goods.SeckillGoodsVo>
     **/
    PageInfo<SeckillGoodsVo> searchActivityGoodsList(SearchSeckillGoodsDto searchSeckillGoodsDto);

    /**
     * @Author yeJH
     * @Date 2019/10/8 11:08
     * @Description 获取秒杀时间段
     *
     * @Update yeJH
     *
     * @param
     * @return java.util.List<com.chauncy.data.vo.app.activity.seckill.SeckillTimeQuantumVo>
     **/
    List<SeckillTimeQuantumVo> getSeckillTimeQuantum();

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
