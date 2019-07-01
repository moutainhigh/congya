package com.chauncy.order.evaluate.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.dto.app.order.evaluate.AddValuateDto;
import com.chauncy.data.mapper.order.OmEvaluateMapper;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class OmEvaluateServiceImpl extends AbstractService<OmEvaluateMapper, OmEvaluatePo> implements IOmEvaluateService {

    @Autowired
    private OmEvaluateMapper mapper;

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    @Override
    public void addEvaluate(AddValuateDto addValuateDto) {

        //判断是否是第一次评论
        if (addValuateDto.getId() == 0) {
            OmEvaluatePo omEvaluatePo = new OmEvaluatePo();
            omEvaluatePo.setId(null);
            omEvaluatePo.setParentId(null);
            omEvaluatePo.setCreateBy("获取的当前用户");
            BeanUtils.copyProperties(addValuateDto, omEvaluatePo);
            mapper.insert(omEvaluatePo);
        } else {
            OmEvaluatePo omEvaluatePo = new OmEvaluatePo();
            omEvaluatePo.setId(null);
            omEvaluatePo.setParentId(addValuateDto.getId());
            omEvaluatePo.setCreateBy("获取的当前用户");
            BeanUtils.copyProperties(addValuateDto, omEvaluatePo);
            mapper.insert(omEvaluatePo);
        }
    }
}
