package com.chauncy.product.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.goods.StoreGoodsTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.domain.po.product.stock.PmGoodsRelStockTemplatePo;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockTemplatePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.dto.supplier.good.add.StockTemplateBaseDto;
import com.chauncy.data.mapper.product.stock.PmGoodsRelStockTemplateMapper;
import com.chauncy.data.mapper.product.stock.PmGoodsVirtualStockTemplateMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.supplier.good.stock.GoodsStockTemplateVo;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateSkuInfoVo;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.product.stock.IPmGoodsRelStockTemplateService;
import com.chauncy.product.stock.IPmGoodsVirtualStockService;
import com.chauncy.product.stock.IPmGoodsVirtualStockTemplateService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品虚拟库存模板信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmGoodsVirtualStockTemplateServiceImpl extends AbstractService<PmGoodsVirtualStockTemplateMapper, PmGoodsVirtualStockTemplatePo> implements IPmGoodsVirtualStockTemplateService {

    @Autowired
    private PmGoodsVirtualStockTemplateMapper pmGoodsVirtualStockTemplateMapper;
    @Autowired
    private PmGoodsRelStockTemplateMapper pmGoodsRelStockTemplateMapper;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private IPmGoodsService pmGoodsService;
    @Autowired
    private IPmGoodsRelStockTemplateService pmGoodsRelStockTemplateService;
    @Autowired
    private IPmGoodsVirtualStockService pmGoodsVirtualStockService;

    /**
     * 保存库存模板信息
     *
     * @param stockTemplateBaseDto
     * @return
     */
    @Override
    public Long saveStockTemplate(StockTemplateBaseDto stockTemplateBaseDto) {
        PmGoodsVirtualStockTemplatePo pmGoodsVirtualStockTemplatePo = this.getById(stockTemplateBaseDto.getId());
        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }

        QueryWrapper<PmGoodsVirtualStockTemplatePo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", stockTemplateBaseDto.getName());
        /*if(this.count(queryWrapper) > 0) {
            throw  new ServiceException(ResultCode.DUPLICATION, "库存模板名称重复");
        }*/
        if(null != stockTemplateBaseDto.getId()) {
            //编辑时
            PmGoodsVirtualStockTemplatePo oldTemplatePo = this.getOne(queryWrapper);
            if(null != oldTemplatePo && !pmGoodsVirtualStockTemplatePo.getName().equals(oldTemplatePo.getName())) {
                throw  new ServiceException(ResultCode.DUPLICATION, "库存模板名称重复");
            }
        } else {
            //新增时
            if(this.count(queryWrapper) > 0) {
                throw  new ServiceException(ResultCode.DUPLICATION, "库存模板名称重复");
            }
            pmGoodsVirtualStockTemplatePo = new PmGoodsVirtualStockTemplatePo();
            pmGoodsVirtualStockTemplatePo.setType(stockTemplateBaseDto.getType());
            pmGoodsVirtualStockTemplatePo.setCreateBy(sysUserPo.getUsername());
        }
        pmGoodsVirtualStockTemplatePo.setName(stockTemplateBaseDto.getName());
        pmGoodsVirtualStockTemplatePo.setStoreId(sysUserPo.getStoreId());
        pmGoodsVirtualStockTemplatePo.setUpdateBy(sysUserPo.getUsername());
        pmGoodsVirtualStockTemplatePo.setUpdateTime(LocalDateTime.now());
        if(null != stockTemplateBaseDto.getId()) {
            pmGoodsVirtualStockTemplateMapper.updateById(pmGoodsVirtualStockTemplatePo);
        } else {
            pmGoodsVirtualStockTemplateMapper.insert(pmGoodsVirtualStockTemplatePo);
        }

        //库存模板商品关联插入
        insertGoodsRelStockTemplate(pmGoodsVirtualStockTemplatePo, stockTemplateBaseDto.getGoodsIds());

        return pmGoodsVirtualStockTemplatePo.getId();
    }

    /**
     * 编辑库存模板信息
     *
     * @param stockTemplateBaseDto
     * @return
     */
    @Override
    public Long editStockTemplate(StockTemplateBaseDto stockTemplateBaseDto) {
        PmGoodsVirtualStockTemplatePo pmGoodsVirtualStockTemplatePo = this.getById(stockTemplateBaseDto.getId());

        QueryWrapper<PmGoodsVirtualStockTemplatePo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", stockTemplateBaseDto.getName());
        PmGoodsVirtualStockTemplatePo oldTemplatePo = this.getOne(queryWrapper);
        if(null != oldTemplatePo && !pmGoodsVirtualStockTemplatePo.getName().equals(oldTemplatePo.getName())) {
            throw  new ServiceException(ResultCode.DUPLICATION, "库存模板名称重复");
        }

        pmGoodsVirtualStockTemplatePo.setName(stockTemplateBaseDto.getName());

        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        pmGoodsVirtualStockTemplatePo.setUpdateBy(sysUserPo.getUsername());
        pmGoodsVirtualStockTemplateMapper.updateById(pmGoodsVirtualStockTemplatePo);

        //库存模板商品关联插入
        insertGoodsRelStockTemplate(pmGoodsVirtualStockTemplatePo, stockTemplateBaseDto.getGoodsIds());

        return pmGoodsVirtualStockTemplatePo.getId();
    }

    /**
     * 库存模板商品关联插入
     * @param pmGoodsVirtualStockTemplatePo
     * @param goodsIds
     */
    private void insertGoodsRelStockTemplate(PmGoodsVirtualStockTemplatePo pmGoodsVirtualStockTemplatePo, Long[] goodsIds) {
        List<PmGoodsRelStockTemplatePo> pmGoodsRelStockTemplatePoList = new ArrayList<>();
        for(Long goodsId : goodsIds) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", goodsId);
            queryWrapper.select("store_id");
            Map<String, Long> resultMap = pmGoodsService.getMap(queryWrapper);
            //判断商品是否来自模板类型对应的商品
            if(null != resultMap && resultMap.get("store_id").equals(pmGoodsVirtualStockTemplatePo.getStoreId())) {
                //商品来自自有类型
                if(pmGoodsVirtualStockTemplatePo.getType().equals(StoreGoodsTypeEnum.DISTRIBUTION_GOODS.getId())) {
                    throw  new ServiceException(ResultCode.PARAM_ERROR, "模板商品类型跟商品不对应");
                }
            } else {
                QueryWrapper goodsVirtualStockQuery = new QueryWrapper();
                goodsVirtualStockQuery.eq("store_id", pmGoodsVirtualStockTemplatePo.getStoreId());
                goodsVirtualStockQuery.eq("goods_id", goodsId);
                if (pmGoodsVirtualStockService.count(goodsVirtualStockQuery) > 0) {
                    //商品来自分配类型
                    if (pmGoodsVirtualStockTemplatePo.getType().equals(StoreGoodsTypeEnum.OWN_GOODS.getId())) {
                        throw new ServiceException(ResultCode.PARAM_ERROR, "模板商品类型跟商品不对应");
                    }
                } else {
                    throw new ServiceException(ResultCode.PARAM_ERROR, "商品不属于店铺");
                }
            }
            QueryWrapper templateQueryWrapper = new QueryWrapper();
            templateQueryWrapper.eq("goods_id", goodsId);
            templateQueryWrapper.eq("stock_template_id", pmGoodsVirtualStockTemplatePo.getId());
            if(pmGoodsRelStockTemplateService.count(templateQueryWrapper) == 0) {
                //关联不存在则插入
                PmGoodsRelStockTemplatePo pmGoodsRelStockTemplatePo = new PmGoodsRelStockTemplatePo();
                pmGoodsRelStockTemplatePo.setCreateBy(pmGoodsVirtualStockTemplatePo.getCreateBy());
                pmGoodsRelStockTemplatePo.setGoodsId(goodsId);
                pmGoodsRelStockTemplatePo.setStockTemplateId(pmGoodsVirtualStockTemplatePo.getId());
                pmGoodsRelStockTemplatePoList.add(pmGoodsRelStockTemplatePo);
            }
        }
        pmGoodsRelStockTemplateService.saveBatch(pmGoodsRelStockTemplatePoList);
    }


    /**
     * 根据Id删除库存模板
     *
     * @param ids
     */
    @Override
    public void delTemplateById(Long[] ids) {

        if(null != ids && ids.length > 0) {
            QueryWrapper<PmGoodsVirtualStockTemplatePo> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().in(PmGoodsVirtualStockTemplatePo::getId, Arrays.asList(ids));
            pmGoodsVirtualStockTemplateMapper.delete(queryWrapper);
        }
    }


    /**
     * 删除商品与库存模板的关联
     *
     * @param id
     */
    @Override
    public void delRelById(Long id) {
        PmGoodsRelStockTemplatePo pmGoodsRelStockTemplatePo = pmGoodsRelStockTemplateMapper.selectById(id);
        if(null == pmGoodsRelStockTemplatePo) {
            throw  new ServiceException(ResultCode.NO_EXISTS, "商品与库存模板关联不存在");
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", id);
            pmGoodsRelStockTemplateMapper.delete(queryWrapper);
        }
    }

    /**
     * 根据模板名称以及创建时间查询
     *
     * @param baseSearchByTimeDto
     * @return
     */
    @Override
    public PageInfo<GoodsStockTemplateVo> searchPaging(BaseSearchByTimeDto baseSearchByTimeDto) {

        Long storeId = securityUtil.getCurrUser().getStoreId();
        baseSearchByTimeDto.setId(storeId);

        Integer pageNo = baseSearchByTimeDto.getPageNo()==null ? defaultPageNo : baseSearchByTimeDto.getPageNo();
        Integer pageSize = baseSearchByTimeDto.getPageSize()==null ? defaultPageSize : baseSearchByTimeDto.getPageSize();

        PageInfo<GoodsStockTemplateVo> informationLabelVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> pmGoodsVirtualStockTemplateMapper.searchPaging(baseSearchByTimeDto));
        return informationLabelVoPageInfo;
    }

    /**
     * 查询当前店铺的库存模板信息
     *
     * @param
     * @return
     */
    @Override
    public List<BaseBo> selectStockTemplate() {
        //获取当前用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null == storeId) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }
        return pmGoodsVirtualStockTemplateMapper.selectStockTemplate(storeId);
    }

    /**
     * 根据商品库存模板Id获取商品规格信息（选择规格分配库存）
     *
     * @param templateId
     * @return
     */
    public List<StockTemplateSkuInfoVo> searchSkuInfoByTemplateId(Long templateId) {
        PmGoodsVirtualStockTemplatePo pmGoodsVirtualStockTemplate = pmGoodsVirtualStockTemplateMapper.selectById(templateId);
        if(null == pmGoodsVirtualStockTemplate) {
            throw new ServiceException(ResultCode.NO_EXISTS, "库存模板不存在") ;
        }
        //获取当前店铺用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null == storeId) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }

        List<StockTemplateSkuInfoVo> stockTemplateSkuInfoVoList = new ArrayList<>();
        if(pmGoodsVirtualStockTemplate.getType().equals(StoreGoodsTypeEnum.OWN_GOODS.getId())) {
            //自有商品类型
            stockTemplateSkuInfoVoList = pmGoodsVirtualStockTemplateMapper.searchSkuInfoByOwnType(templateId, storeId);
        } else if(pmGoodsVirtualStockTemplate.getType().equals(StoreGoodsTypeEnum.DISTRIBUTION_GOODS.getId())) {
            //分配商品
            stockTemplateSkuInfoVoList = pmGoodsVirtualStockTemplateMapper.searchSkuInfoByDistributionType(templateId, storeId);
        }
        return stockTemplateSkuInfoVoList ;
    }

}
