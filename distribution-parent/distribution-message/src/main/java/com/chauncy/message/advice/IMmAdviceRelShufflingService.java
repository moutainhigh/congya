package com.chauncy.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelShufflingPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.advice.shuffling.add.SaveShufflingDto;
import com.chauncy.data.dto.manage.message.advice.shuffling.select.SearchShufflingAssociatedDetailDto;
import com.chauncy.data.vo.manage.message.advice.shuffling.SearchShufflingAssociatedDetailVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 广告与无关联轮播图关联表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface IMmAdviceRelShufflingService extends Service<MmAdviceRelShufflingPo> {

    /**
     * 条件分页查询轮播图广告需要绑定的资讯、商品、店铺
     *
     * @param searchShufflingAssociatedDetailDto
     * @return
     */
    PageInfo<SearchShufflingAssociatedDetailVo> searchShufflingAssociatedDetail(SearchShufflingAssociatedDetailDto searchShufflingAssociatedDetailDto);

    /**
     *
     * 广告位置：首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式
     * 保存无关联广告轮播图
     *
     * @param saveShufflingDto
     * @return
     */
    void saveShuffling(SaveShufflingDto saveShufflingDto);
}
