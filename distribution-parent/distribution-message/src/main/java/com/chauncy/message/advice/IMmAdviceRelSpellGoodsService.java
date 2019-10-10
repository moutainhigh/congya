package com.chauncy.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelSpellGoodsPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.advice.add.SaveSpellAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchSpellAdviceBindGoodsDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchSpellAdviceGoodsDto;
import com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 今日必拼广告绑定参加拼团活动商品关联表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-09
 */
public interface IMmAdviceRelSpellGoodsService extends Service<MmAdviceRelSpellGoodsPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-09 20:20
     * @Description //条件分页查询今日必拼广告需要绑定的参加拼团的商品信息
     *
     * @Update chauncy
     *
     * @param  searchSpellAdviceGoodsDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.advice.tab.tab.SearchAdviceGoodsVo>
     **/
    PageInfo<SearchAdviceGoodsVo> searchSpellAdviceGoods(SearchSpellAdviceGoodsDto searchSpellAdviceGoodsDto);

    /**
     * @Author chauncy
     * @Date 2019-10-09 21:45
     * @Description //条件分页查询今日必拼广告已经绑定的参加拼团的商品信息
     *
     * @Update chauncy
     *
     * @param  searchSpellAdviceGoodsDto
     * @return com.chauncy.common.enums.system.ResultCode
     **/
    PageInfo<SearchAdviceGoodsVo> searchSpellAdviceBindGoods(SearchSpellAdviceBindGoodsDto searchSpellAdviceGoodsDto);

    /**
     * @Author chauncy
     * @Date 2019-10-09 22:26
     * @Description //保存今日必拼广告信息
     *
     * @Update chauncy
     *
     * @param  saveSpellAdviceDto
     * @return void
     **/
    void saveSpellAdvice(SaveSpellAdviceDto saveSpellAdviceDto);
}
