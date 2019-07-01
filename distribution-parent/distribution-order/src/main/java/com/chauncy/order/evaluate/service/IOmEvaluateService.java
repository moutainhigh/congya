package com.chauncy.order.evaluate.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.dto.app.order.evaluate.add.AddValuateDto;
import com.chauncy.data.dto.app.order.evaluate.add.SearchEvaluateDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetPersonalEvaluateDto;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 商品评价表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface IOmEvaluateService extends Service<OmEvaluatePo> {

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    void addEvaluate(AddValuateDto addValuateDto);

    /**
     * 获取商品对应的所有评价信息
     *
     * @return
     */
    PageInfo<GoodsEvaluateVo> getGoodsEvaluate(SearchEvaluateDto searchEvaluateDtod);

    /**
     * 用户获取已经评价的商品评价信息
     * @param getPersonalEvaluateDto
     * @return
     */
    PageInfo<GoodsEvaluateVo> getPersonalEvaluate(GetPersonalEvaluateDto getPersonalEvaluateDto);
}
