package com.chauncy.product.service.impl;

import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.*;
import com.chauncy.data.dto.manage.good.add.GoodAttributeDto;
import com.chauncy.data.dto.manage.good.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.good.select.FindAttributeInfoByConditionDto;
import com.chauncy.data.mapper.product.*;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.product.PmGoodsAttributeVo;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品属性业务处理
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
@Transactional
public class PmGoodsAttributeServiceImpl extends AbstractService<PmGoodsAttributeMapper, PmGoodsAttributePo> implements IPmGoodsAttributeService {

    @Autowired
    private PmGoodsAttributeMapper mapper;

    @Autowired
    private PmGoodsAttributeValueMapper valueMapper;

    @Autowired
    private PmGoodsRelAttributeCategoryMapper attributeCategoryMapper;

    @Autowired
    private PmGoodsRelAttributeGoodMapper attributeGoodMapper;

    @Autowired
    private PmGoodsRelAttributeSkuMapper attributeSkuMapper;

    @Autowired
    private SecurityUtil securityUtil;

    private static int defaultPageSize = 10;

    private static int defaultPageNo = 1;

    private static String defaultSoft = "sort desc";

    /**
     * 保存商品属性
     *
     * @param goodsAttributeDto
     * @return
     */
    @Override
    public JsonViewData saveAttribute(GoodAttributeDto goodsAttributeDto) {
        PmGoodsAttributePo goodsAttributePo = new PmGoodsAttributePo();
        BeanUtils.copyProperties(goodsAttributeDto, goodsAttributePo);
        LocalDateTime date = LocalDateTime.now();
        //获取当前用户
        String user = securityUtil.getCurrUser().getUsername();
        goodsAttributePo.setCreateBy(user);
        goodsAttributePo.setCreateTime(date);
        goodsAttributePo.setId(null);
        //判断必要字段
        if (goodsAttributePo.getType() == null || goodsAttributePo.getType() == ' ' || goodsAttributePo.getName() == null || goodsAttributePo.getName().equals(' ')) {
            return new JsonViewData(ResultCode.FAIL, "添加失败,缺少必需字段name或type");
        }

        //判断不同类型对应的属性名称是否已经存在
        if (mapper.findByTypeAndName(goodsAttributePo.getType(), goodsAttributePo.getName()) != null) {
            return new JsonViewData(ResultCode.FAIL, "添加失败,该属性名称已存在");
        }

        //处理属性类型为规格类型的
        if (GoodsAttributeTypeEnum.STANDARD.getId() == goodsAttributePo.getType()) {
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
     *
     * @param ids
     * @return
     */
    @Override
    public JsonViewData deleteAttributeByIds(Long[] ids) {
        //判断是否存在引用
        for (Long id : ids) {
            List<PmGoodsRelAttributeCategoryPo> list1 = attributeCategoryMapper.findByAttributeId(id);
            List<PmGoodsRelAttributeGoodPo> list2 = attributeGoodMapper.findByAttributeId(id);
            List<PmGoodsRelAttributeSkuPo> list3 = attributeSkuMapper.findByAttributeId(id);
            boolean a = list1 != null && list1.size() > 0;
            boolean b = list2 != null && list2.size() > 0;
            boolean c = list3 != null && list3.size() > 0;
            if (a == true || b == true || c == true) {
                return new JsonViewData(ResultCode.FAIL, "删除失败，包含正被商品或类目或sku使用关联的属性");
            }
        }
        //遍历ID
        for (Long id : ids) {
            PmGoodsAttributePo po = new PmGoodsAttributePo();
            po = mapper.selectById(id);
            //处理规格和商品参数
            if (po.getType() == GoodsAttributeTypeEnum.STANDARD.getId() || po.getType() == GoodsAttributeTypeEnum.GOODS_PARAM.getId()) {
                Map<String, Object> map = new HashMap<>();
                map.put("product_attribute_id", id);
                valueMapper.deleteByMap(map);
                mapper.deleteById(id);
            } else
                mapper.deleteById(id);
        }

        return new JsonViewData(ResultCode.SUCCESS, "批量通过id删除数据成功");
    }

    /**
     * 更新数据
     *
     * @param goodAttributeDto
     * @return
     */
    @Override
    public JsonViewData edit(GoodAttributeDto goodAttributeDto) {

        PmGoodsAttributePo goodsAttributePo = new PmGoodsAttributePo();
        BeanUtils.copyProperties(goodAttributeDto, goodsAttributePo);
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
        if (po1 == null) {
            return new JsonViewData(ResultCode.FAIL, "该属性不存在");
        }
        //判断不同类型对应的属性名称是否已经存在
        PmGoodsAttributePo po = mapper.findByTypeAndName(goodsAttributePo.getType(), goodsAttributePo.getName());
        if (!po1.getName().equals(goodsAttributePo.getName()) && po != null) {
            return new JsonViewData(ResultCode.FAIL, "修改失败,该属性名称已存在");
        }

        //根据属性id查找是否正被使用
        List<PmGoodsRelAttributeCategoryPo> list1 = attributeCategoryMapper.findByAttributeId(goodsAttributePo.getId());
        List<PmGoodsRelAttributeGoodPo> list2 = attributeGoodMapper.findByAttributeId(goodsAttributePo.getId());
        List<PmGoodsRelAttributeSkuPo> list3 = attributeSkuMapper.findByAttributeId(goodsAttributePo.getId());
        boolean a = list1 != null && list1.size() > 0;
        boolean b = list2 != null && list2.size() > 0;
        boolean c = list3 != null && list3.size() > 0;
        if (a == true || b == true || c == true) {
            return new JsonViewData(ResultCode.FAIL, "修改失败，包含正被类目或商品使用的关联的属性名称");
        }

        mapper.updateById(goodsAttributePo);

        if (goodsAttributePo == null) {
            return new JsonViewData(ResultCode.FAIL, "修改失败");
        }

        return new JsonViewData(ResultCode.SUCCESS, "修改成功", goodsAttributePo);
    }

    /**
     * 根据ID查找属性以及关联的属性值
     *
     * @param id
     * @return
     */
    @Override
    public JsonViewData findById(Long id) {
        //属性信息表
        PmGoodsAttributePo goodsAttributePo = mapper.selectById(id);
        List<String> valueList = new ArrayList<>();
        //需要判断是否为空
        if (goodsAttributePo==null){
            return new JsonViewData(ResultCode.FAIL,"不存在该属性！");
        }
        if (goodsAttributePo.getType() == GoodsAttributeTypeEnum.STANDARD.getId() || goodsAttributePo.getType() == GoodsAttributeTypeEnum.GOODS_PARAM.getId()) {
            //查询属性值表
            List<PmGoodsAttributeValuePo> valuePoList = valueMapper.findByAttributeId(id);
            for (PmGoodsAttributeValuePo po : valuePoList) {
                valueList.add(po.getValue());
            }
        }
        PmGoodsAttributeVo pmGoodsAttributeVo = new PmGoodsAttributeVo();
        BeanUtils.copyProperties(goodsAttributePo, pmGoodsAttributeVo);
        pmGoodsAttributeVo.setValueList(valueList);


        return new JsonViewData(ResultCode.SUCCESS, "查询成功", pmGoodsAttributeVo);
    }


    /**
     * 条件分页查询
     *
     * @param findAttributeInfoByConditionDto
     * @return
     */
    @Override
    public JsonViewData findByCondition(FindAttributeInfoByConditionDto findAttributeInfoByConditionDto) {
        Integer pageNo = findAttributeInfoByConditionDto.getPageNo() == null ? defaultPageNo : findAttributeInfoByConditionDto.getPageNo();
        Integer pageSize = findAttributeInfoByConditionDto.getPageSize() == null ? defaultPageSize : findAttributeInfoByConditionDto.getPageSize();
        PageInfo<PmGoodsAttributeVo> goodsAttributeVo = new PageInfo<>();
        //判断Type是否为规格或参数
        if (findAttributeInfoByConditionDto.getType() == GoodsAttributeTypeEnum.STANDARD.getId() || findAttributeInfoByConditionDto.getType() == GoodsAttributeTypeEnum.GOODS_PARAM.getId()) {
            goodsAttributeVo = PageHelper.startPage(pageNo, pageSize/*, "id desc"*/)
                    .doSelectPageInfo(() -> valueMapper.findByCondition(findAttributeInfoByConditionDto.getType(), findAttributeInfoByConditionDto.getName(), findAttributeInfoByConditionDto.getEnabled()));
        } else {
            goodsAttributeVo = PageHelper.startPage(pageNo, pageSize, defaultSoft)
                    .doSelectPageInfo(() -> mapper.findByCondition(findAttributeInfoByConditionDto.getType(), findAttributeInfoByConditionDto.getName(), findAttributeInfoByConditionDto.getEnabled()));
        }

        return new JsonViewData(ResultCode.SUCCESS, "查询成功", goodsAttributeVo);
    }

    /**
     * 启用或禁用
     *
     * @param baseUpdateStatusDto
     * @return
     */
    @Override
    public JsonViewData updateStatus(BaseUpdateStatusDto baseUpdateStatusDto) {
        PmGoodsAttributePo goodsAttributePo = new PmGoodsAttributePo();
        BeanUtils.copyProperties(baseUpdateStatusDto,goodsAttributePo);
        mapper.updateById(goodsAttributePo);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功！");
    }

}
