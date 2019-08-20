package com.chauncy.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdvicePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.message.advice.add.SaveOtherAdviceDto;
import com.chauncy.data.dto.manage.message.advice.select.SearchAdvicesDto;
import com.chauncy.data.vo.manage.message.advice.SearchAdvicesVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 广告基本信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
public interface IMmAdviceService extends Service<MmAdvicePo> {

    /**
     * 获取广告位置
     * @return
     */
    Object findAdviceLocation();

    /**
     * 条件分页获取广告信息及其对应的详情
     *
     * @param searchAdvicesDto
     * @return
     */
    PageInfo<SearchAdvicesVo> searchAdvices(SearchAdvicesDto searchAdvicesDto);

    /**
     * 批量删除广告
     *
     * @param idList
     */
    void deleteAdvices(List<Long> idList);

    /**
     * 保存充值入口/拼团鸭广告
     *
     * @param saveOtherAdviceDto
     * @return
     */
    void saveOtherAdvice(SaveOtherAdviceDto saveOtherAdviceDto);
}
