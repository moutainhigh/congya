package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelShufflingPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandShufflingVo;

import java.util.List;

/**
 * <p>
 * 广告与无关联轮播图关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface MmAdviceRelShufflingMapper extends IBaseMapper<MmAdviceRelShufflingPo> {

    /**
     *
     * 根据某个广告下的某个选项卡与品牌关联的表的ID查找关联的轮播图广告
     * @param relTabBrandId
     * @return
     */
    List<BrandShufflingVo> findShufflingList(Long relTabBrandId);
}
