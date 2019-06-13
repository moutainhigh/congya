package com.chauncy.product.service.impl;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueSkuPo;
import com.chauncy.data.mapper.product.PmGoodsAttributeValueMapper;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeValueSkuMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsAttributeValueService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import com.chauncy.data.domain.po.product.PmGoodsSkuCategoryAttributeRelationPo;

/**
 * <p>
 * 存储产品参数信息的表
 * <p>
 * 规格值
 * 参数值 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
@Transactional
public class PmGoodsAttributeValueServiceImpl extends AbstractService<PmGoodsAttributeValueMapper, PmGoodsAttributeValuePo> implements IPmGoodsAttributeValueService {

    @Autowired
    private PmGoodsAttributeValueMapper mapper;

    @Autowired
    private PmGoodsRelAttributeValueSkuMapper valueSkuMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 根据属性ID添加属性值
     * 需判断是否已存在
     * @param goodsAttributeValuePo
     *
     * @return
     */
    @Override
    public JsonViewData saveAttValue(PmGoodsAttributeValuePo goodsAttributeValuePo) {
        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        goodsAttributeValuePo.setCreateBy(user);
        goodsAttributeValuePo.setCreateTime(date);
        //判断该属性下是否已经存在属性值
        List<PmGoodsAttributeValuePo> valuePoList = mapper.findByAttributeId(goodsAttributeValuePo.getProductAttributeId());
        List<String> valueList = new ArrayList<>();
        for (PmGoodsAttributeValuePo valuePo : valuePoList) {
            valueList.add(valuePo.getValue());
        }
        for (int i = 0; i < valueList.size(); i++) {
            //判断值不能相同
            boolean s = goodsAttributeValuePo.getValue().equals(valueList.get(i));
            if (s == true) {
                return new JsonViewData(ResultCode.FAIL, "添加失败，属性值不能重复");
            }
        }

            mapper.insert(goodsAttributeValuePo);

            return new JsonViewData(ResultCode.SUCCESS, "添加成功", goodsAttributeValuePo);
        }


    /**
     * 更新属性值
     * 需判断是否被用&&已存在
     * @param pmGoodsAttributeValuePo
     * @return
     */
    @Override
    public JsonViewData editValue(PmGoodsAttributeValuePo pmGoodsAttributeValuePo) {
        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        pmGoodsAttributeValuePo.setUpdateTime(date);
        pmGoodsAttributeValuePo.setUpdateBy(user);
        //根据属性值id查找是否正被使用
        List<PmGoodsRelAttributeValueSkuPo> list = valueSkuMapper.findByAttributeValueId(pmGoodsAttributeValuePo.getId());
        if (list != null && list.size() > 0) {
            return new JsonViewData(ResultCode.FAIL, "修改失败，包含正被sku或商品使用的关联的属性值");
        }

        //判断该属性下是否已经存在属性值
        List<PmGoodsAttributeValuePo> valuePoList = mapper.findByAttributeId(pmGoodsAttributeValuePo.getProductAttributeId());
        List<String> valueList = new ArrayList<>();
        for (PmGoodsAttributeValuePo valuePo : valuePoList) {
            valueList.add(valuePo.getValue());
        }
        for (int i = 0; i < valueList.size(); i++) {
            //判断值不能相同
            boolean s = pmGoodsAttributeValuePo.getValue().equals(valueList.get(i));
            if (s == true) {
                return new JsonViewData(ResultCode.FAIL, "修改失败，属性值不能重复");
            }
        }

        mapper.updateById(pmGoodsAttributeValuePo);
        if (pmGoodsAttributeValuePo == null) {
            return new JsonViewData(ResultCode.FAIL, "修改失败");
        }
        return new JsonViewData(ResultCode.SUCCESS, "修改成功", pmGoodsAttributeValuePo);
    }

    /**
     * 批量删除属性值
     * 需判断是否正被用
     * @param ids
     * @return
     */
    @Override
    public JsonViewData delAttValueByIds(Long[] ids) {

        for (Long id : ids) {
            //根据属性值id查找是否正被使用
            List<PmGoodsRelAttributeValueSkuPo> list = valueSkuMapper.findByAttributeValueId(id);

            if (list != null && list.size() > 0) {
                return new JsonViewData(ResultCode.FAIL, "删除失败，包含正被sku或商品使用的关联的属性值");
            }
        }

        List<Long> idList = new ArrayList<>();
        idList = Arrays.asList(ids);
        mapper.deleteBatchIds(idList);

        return new JsonViewData(ResultCode.SUCCESS,"删除成功");
    }

    /**
     * 根据查找属性值信息
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewData findValueById(Long id) {
        PmGoodsAttributeValuePo valuePo = mapper.selectById(id);
        return new JsonViewData(ResultCode.SUCCESS,"查询成功",valuePo);
    }
}
