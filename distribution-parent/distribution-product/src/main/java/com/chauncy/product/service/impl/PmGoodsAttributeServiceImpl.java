package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chauncy.common.enums.ResultCode;
import com.chauncy.common.enums.goods.GoodsAttribute;
import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.domain.po.product.PmGoodsSkuCategoryAttributeRelationPo;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.mapper.product.PmGoodsAttributeValueMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuCategoryAttributeRelationMapper;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.product.PmGoodsAttributeVo;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品属性业务处理
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmGoodsAttributeServiceImpl extends ServiceImpl<PmGoodsAttributeMapper, PmGoodsAttributePo> implements IPmGoodsAttributeService {

    @Autowired
    private PmGoodsAttributeMapper mapper;

    @Autowired
    private PmGoodsAttributeValueMapper valueMapper;

    @Autowired
    private PmGoodsSkuCategoryAttributeRelationMapper relationMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 保存商品属性
     *
     * @param goodsAttributePo
     * @return
     */
    @Override
    public JsonViewData saveAttribute(PmGoodsAttributePo goodsAttributePo) {
        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        goodsAttributePo.setCreateBy(user);
        goodsAttributePo.setCreateTime(date);

        //判断必要字段
        if (goodsAttributePo.getType() == null || goodsAttributePo.getType() == ' ' || goodsAttributePo.getName() == null || goodsAttributePo.getName().equals(' ')) {
            return new JsonViewData(ResultCode.FAIL, "添加失败,缺少必需字段name或type");
        }

        //判断不同类型对应的属性名称是否已经存在
        if (mapper.findByTypeAndName(goodsAttributePo.getType(), goodsAttributePo.getName()) != null) {
            return new JsonViewData(ResultCode.FAIL, "添加失败,该属性名称已存在");
        }

        //处理属性类型为规格类型的
        if (GoodsAttribute.STANDARD.getId() == goodsAttributePo.getType()) {
            if (goodsAttributePo.getValues() != null) {
                String temp = goodsAttributePo.getValues()[0];
                for (int i = 1; i < goodsAttributePo.getValues().length; i++) {
                    //判断值不能相同
                    boolean s = temp.equals(goodsAttributePo.getValues()[i]);
                    if (s == true) {
                        return new JsonViewData(ResultCode.FAIL, "添加失败,属性值不能重复");
                    }
                }

                //先保存基本信息，在保存对应的值
                mapper.insert(goodsAttributePo);
                System.out.println(goodsAttributePo.getId());
                for (String value : goodsAttributePo.getValues()) {
                    PmGoodsAttributeValuePo po = new PmGoodsAttributeValuePo();
                    po.setProductAttributeId(goodsAttributePo.getId());
                    po.setValue(value);
                    po.setCreateBy(user);
                    po.setCreateTime(date);
                    valueMapper.insert(po);
                }
            }
        } else
            mapper.insert(goodsAttributePo);
        return new JsonViewData(ResultCode.SUCCESS, "保存成功", goodsAttributePo);
    }

    /**
     * 批量删除属性以及关联的值
     * @param ids
     * @return
     */
    @Override
    public JsonViewData deleteAttributeByIds(Long[] ids) {

        for (Long id : ids) {
            List<PmGoodsSkuCategoryAttributeRelationPo> list = relationMapper.findByAttributeId(id);
            if (list != null && list.size() > 0) {
                return new JsonViewData(ResultCode.FAIL, "删除失败，包含正被商品或类目使用关联的属性");
            }
        }
        //遍历ID
        for (Long id : ids) {
            PmGoodsAttributePo po = new PmGoodsAttributePo();
            po = mapper.selectById(id);
            //处理规格和商品参数
            if (po.getType() == GoodsAttribute.STANDARD.getId() || po.getType() == GoodsAttribute.GOODS_PARAM.getId()) {
                valueMapper.deleteByAttributeId(id);
                mapper.deleteById(id);
            } else
                mapper.deleteById(id);
        }

        return new JsonViewData(ResultCode.SUCCESS, "批量通过id删除数据成功");
    }

    /**
     * 更新数据
     *
     * @param goodsAttributePo
     * @return
     */
    @Override
    public JsonViewData edit(PmGoodsAttributePo goodsAttributePo) {

        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();

        goodsAttributePo.setUpdateTime(date);
        goodsAttributePo.setUpdateBy(user);

        //判断必要字段
        if (goodsAttributePo.getType() == null || goodsAttributePo.getType() == ' ' || goodsAttributePo.getName() == null || goodsAttributePo.getName().equals(' ')) {
            return new JsonViewData(ResultCode.FAIL, "修改失败,缺少必需字段name或type");
        }

        PmGoodsAttributePo po1 = mapper.selectById(goodsAttributePo.getId());
        //判断不同类型对应的属性名称是否已经存在
        PmGoodsAttributePo po = mapper.findByTypeAndName(goodsAttributePo.getType(), goodsAttributePo.getName());
        if (!po1.getName().equals(goodsAttributePo.getName()) && po != null) {
            return new JsonViewData(ResultCode.FAIL, "修改失败,该属性名称已存在");
        }

        //根据属性id查找是否正被使用
        List<PmGoodsSkuCategoryAttributeRelationPo> list = relationMapper.findByAttributeId(goodsAttributePo.getId());
        if (list != null && list.size() > 0) {
            return new JsonViewData(ResultCode.FAIL, "修改失败，包含正被类目或商品使用的关联的属性名称");
        }

        mapper.updateById(goodsAttributePo);

        if (goodsAttributePo==null)
        {
            return new JsonViewData(ResultCode.FAIL,"修改失败");
        }

        return new JsonViewData(ResultCode.SUCCESS, "修改成功", goodsAttributePo);
    }

    /**
     * 根据ID查找属性以及关联的属性值
     * @param id
     * @return
     */
    @Override
    public JsonViewData findById(Long id) {
        //属性信息表
        PmGoodsAttributePo goodsAttributePo = mapper.selectById(id);
        List<PmGoodsAttributeValuePo> goodsAttributeValueList = new ArrayList<>();
        if (goodsAttributePo.getType() == GoodsAttribute.STANDARD.getId() || goodsAttributePo.getType() == GoodsAttribute.GOODS_PARAM.getId()) {
            //查询属性值表
            List<Long> idList = new ArrayList<>();
            List<PmGoodsAttributeValuePo> valuePoList = valueMapper.findByAttributeId(id);
            for(PmGoodsAttributeValuePo po : valuePoList) {
                idList.add(po.getId());
            }
            goodsAttributeValueList = valueMapper.selectBatchIds(idList);
        }
        PmGoodsAttributeVo pmGoodsAttributeVo = new PmGoodsAttributeVo();
        BeanUtils.copyProperties(goodsAttributePo,pmGoodsAttributeVo);
        pmGoodsAttributeVo.setValueList(goodsAttributeValueList);


        return new JsonViewData(ResultCode.SUCCESS,"查询成功",pmGoodsAttributeVo);
    }

    @Override
    public JsonViewData search(Integer type, String name, boolean enable) {
        List<PmGoodsAttributePo> goodsAttributePos = mapper.search(type,name,enable);
        List<PmGoodsAttributeValuePo> goodsAttributeValueList = new ArrayList<>();
        List<PmGoodsAttributeVo> goodsAttributeVos = new ArrayList<>();
        for (PmGoodsAttributePo goodsAttributePo : goodsAttributePos) {
            if (goodsAttributePo.getType() == GoodsAttribute.STANDARD.getId() || goodsAttributePo.getType() == GoodsAttribute.GOODS_PARAM.getId()) {
                //查询属性值表
                List<Long> idList = new ArrayList<>();
                List<PmGoodsAttributeValuePo> valuePoList = valueMapper.findByAttributeId(goodsAttributePo.getId());
                for (PmGoodsAttributeValuePo po : valuePoList) {
                    idList.add(po.getId());
                }
                goodsAttributeValueList = valueMapper.selectBatchIds(idList);
            }
            PmGoodsAttributeVo pmGoodsAttributeVo = new PmGoodsAttributeVo();
            BeanUtils.copyProperties(goodsAttributePo,pmGoodsAttributeVo);
            pmGoodsAttributeVo.setValueList(goodsAttributeValueList);
            goodsAttributeVos.add(pmGoodsAttributeVo);
        }

        return new JsonViewData(ResultCode.SUCCESS,"查询成功",goodsAttributeVos);
    }

}
