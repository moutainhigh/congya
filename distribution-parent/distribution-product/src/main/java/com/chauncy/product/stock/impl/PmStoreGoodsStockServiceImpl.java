package com.chauncy.product.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.goods.StoreGoodsTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockPo;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockTemplatePo;
import com.chauncy.data.domain.po.product.stock.PmStoreGoodsStockPo;
import com.chauncy.data.domain.po.product.stock.PmStoreRelGoodsStockPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.dto.supplier.good.add.StoreRelGoodsStockBaseDto;
import com.chauncy.data.dto.supplier.good.select.SearchStoreGoodsStockDto;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.mapper.product.stock.PmGoodsVirtualStockMapper;
import com.chauncy.data.mapper.product.stock.PmGoodsVirtualStockTemplateMapper;
import com.chauncy.data.mapper.product.stock.PmStoreGoodsStockMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateSkuInfoVo;
import com.chauncy.data.vo.supplier.good.stock.StoreGoodsStockVo;
import com.chauncy.product.stock.IPmGoodsVirtualStockService;
import com.chauncy.product.stock.IPmStoreGoodsStockService;
import com.chauncy.product.stock.IPmStoreRelGoodsStockService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 店铺-商品虚拟库存模板关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmStoreGoodsStockServiceImpl extends AbstractService<PmStoreGoodsStockMapper, PmStoreGoodsStockPo> implements IPmStoreGoodsStockService {

    @Autowired
    private SmStoreMapper smStoreMapper;
    @Autowired
    private PmGoodsVirtualStockTemplateMapper pmGoodsVirtualStockTemplateMapper;
    @Autowired
    private PmStoreGoodsStockMapper pmStoreGoodsStockMapper;
    @Autowired
    private PmGoodsVirtualStockMapper pmGoodsVirtualStockMapper;
    @Autowired
    private PmGoodsSkuMapper pmGoodsSkuMapper;
    @Autowired
    private IPmGoodsVirtualStockService pmGoodsVirtualStockService;
    @Autowired
    private IPmStoreRelGoodsStockService pmStoreRelGoodsStockService;
    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 删除编辑修改记录前判断数据是否正确
     * @param pmStoreGoodsStockPo
     */
    private void verifyData(PmStoreGoodsStockPo pmStoreGoodsStockPo) {
        if(null == pmStoreGoodsStockPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
        //获取当前店铺用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if(null == storeId || !storeId.equals(pmStoreGoodsStockPo.getStoreId())) {
            //当前登录用户跟操作不匹配
            throw  new ServiceException(ResultCode.FAIL, "操作失败");
        }

    }

    /**
     * 保存分店商品库存库存信息
     *
     * @param storeGoodsStockBaseDto
     * @return
     */
    @Override
    public Long saveStoreGoodsStock(StoreGoodsStockBaseDto storeGoodsStockBaseDto) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", storeGoodsStockBaseDto.getName());
        if(this.count(queryWrapper) > 0) {
            throw  new ServiceException(ResultCode.DUPLICATION, "分店库存名称重复");
        }

        PmStoreGoodsStockPo pmStoreGoodsStockPo = new PmStoreGoodsStockPo();
        BeanUtils.copyProperties(storeGoodsStockBaseDto, pmStoreGoodsStockPo);
        //获取当前店铺用户
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        if(null == sysUserPo.getStoreId()) {
            throw  new ServiceException(ResultCode.FAIL, "当前登录用户不是商家用户");
        }
        pmStoreGoodsStockPo.setStoreId(sysUserPo.getStoreId());
        pmStoreGoodsStockPo.setCreateBy(sysUserPo.getUsername());
        pmStoreGoodsStockPo.setId(null);
        pmStoreGoodsStockMapper.insert(pmStoreGoodsStockPo);

        //店铺分配商品库存
        insertStoreRelGoodsStock(pmStoreGoodsStockPo, storeGoodsStockBaseDto.getStoreRelGoodsStockBaseDtoList());

        return pmStoreGoodsStockPo.getId();
    }


    /**
     * 店铺分配商品库存
     * @param pmStoreGoodsStockPo
     * @param storeRelGoodsStockBaseDtoList
     */
    private void insertStoreRelGoodsStock(PmStoreGoodsStockPo pmStoreGoodsStockPo, List<StoreRelGoodsStockBaseDto> storeRelGoodsStockBaseDtoList) {
        List<PmStoreRelGoodsStockPo> pmStoreRelGoodsStockPoList = new ArrayList<>();
        PmGoodsVirtualStockTemplatePo pmGoodsVirtualStockTemplatePo = pmGoodsVirtualStockTemplateMapper.selectById(pmStoreGoodsStockPo.getStockTemplateId());
        for(StoreRelGoodsStockBaseDto storeRelGoodsStockBaseDto : storeRelGoodsStockBaseDtoList) {
            PmGoodsSkuPo pmGoodsSkuPo = pmGoodsSkuMapper.selectById(storeRelGoodsStockBaseDto.getGoodsSkuId());
            //商品销售价
            BigDecimal sellPrice = pmGoodsSkuPo.getSellPrice();
            //原始供货价
            BigDecimal supplierPrice ;
            Integer stockNum ;
            if(pmGoodsVirtualStockTemplatePo.getType().equals(StoreGoodsTypeEnum.OWN_GOODS.getId())) {
                //自有商品 查询剩余库存 从PmGoodsVirtualStockPo查找
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("goods_sku_id", storeRelGoodsStockBaseDto.getGoodsSkuId());
                queryWrapper.eq("store_id", pmStoreGoodsStockPo.getStoreId());
                PmGoodsVirtualStockPo pmGoodsVirtualStockPo = pmGoodsVirtualStockService.getOne(queryWrapper);
                stockNum = pmGoodsVirtualStockPo.getStockNum();
                //自有商品  原始供货价为PmGoodsSkuPo的供货价
                supplierPrice = pmGoodsSkuPo.getSupplierPrice();
            } else if(pmGoodsVirtualStockTemplatePo.getType().equals(StoreGoodsTypeEnum.DISTRIBUTION_GOODS.getId())) {
                //分配商品 查询剩余库存 从PmStoreRelGoodsStockPo查找
                PmStoreRelGoodsStockPo pmStoreRelGoodsStockPo = pmStoreRelGoodsStockService.getById(storeRelGoodsStockBaseDto.getParentId());
                stockNum = pmStoreRelGoodsStockPo.getRemainingStockNum();
                //分配商品 原始供货价为分配库存的上级店铺分配对应库存批次的分配供货价
                supplierPrice = pmStoreRelGoodsStockPo.getDistributePrice();
            } else {
                throw new ServiceException(ResultCode.FAIL, "操作失败");
            }
            if(stockNum >= storeRelGoodsStockBaseDto.getDistributeStockNum()) {
                if(storeRelGoodsStockBaseDto.getDistributePrice().compareTo(sellPrice) == 1 || storeRelGoodsStockBaseDto.getDistributePrice().compareTo(supplierPrice) == -1) {
                    //分配的供货价 > 销售价  或者  分配的供货价 < 原始供货价
                    throw new ServiceException(ResultCode.PARAM_ERROR, "供货价出错");
                }
                //库存充足 分配库存详情插入
                PmStoreRelGoodsStockPo pmStoreRelGoodsStockPo = new PmStoreRelGoodsStockPo();
                pmStoreRelGoodsStockPo.setStoreStockId(pmStoreGoodsStockPo.getId());
                pmStoreRelGoodsStockPo.setGoodsSkuId(storeRelGoodsStockBaseDto.getGoodsSkuId());
                pmStoreRelGoodsStockPo.setGoodsId(storeRelGoodsStockBaseDto.getGoodsId());
                pmStoreRelGoodsStockPo.setCreateBy(pmStoreGoodsStockPo.getCreateBy());
                pmStoreRelGoodsStockPo.setDistributeStockNum(storeRelGoodsStockBaseDto.getDistributeStockNum());
                pmStoreRelGoodsStockPo.setRemainingStockNum(storeRelGoodsStockBaseDto.getDistributeStockNum());
                pmStoreRelGoodsStockPo.setParentId(storeRelGoodsStockBaseDto.getParentId());
                //分配商家的id
                pmStoreRelGoodsStockPo.setStoreId(pmStoreGoodsStockPo.getDistributeStoreId());
                //需要判断供货价是否满足条件
                pmStoreRelGoodsStockPo.setDistributePrice(storeRelGoodsStockBaseDto.getDistributePrice());
                pmStoreRelGoodsStockPoList.add(pmStoreRelGoodsStockPo);

                //分配库存成功 修改库存信息
                int result = pmGoodsVirtualStockMapper.updateGoodsVirtualStock(pmStoreGoodsStockPo.getStoreId(), pmStoreGoodsStockPo.getDistributeStoreId(),
                        storeRelGoodsStockBaseDto.getGoodsSkuId(), storeRelGoodsStockBaseDto.getDistributeStockNum());
                if(result < 1) {
                    //没有更新两条数据
                    throw new ServiceException(ResultCode.PARAM_ERROR, "库存不足");
                }
            } else {
                throw new ServiceException(ResultCode.PARAM_ERROR, "库存不足");
            }
        }
        pmStoreRelGoodsStockService.saveBatch(pmStoreRelGoodsStockPoList);
    }

    /**
     *
     * 根据ID查找店铺库存信息
     *
     * @param id 库存id
     * @return
     */
    @Override
    public StoreGoodsStockVo findById(Long id) {
        PmStoreGoodsStockPo pmStoreGoodsStockPo = pmStoreGoodsStockMapper.selectById(id);
        if(null == pmStoreGoodsStockPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }

        //店铺库存基本信息
        StoreGoodsStockVo storeGoodsStockVo = new StoreGoodsStockVo();
        storeGoodsStockVo = pmStoreGoodsStockMapper.findById(id);

        //店铺库存对应的分配库存详情
        List<StockTemplateSkuInfoVo> stockTemplateSkuInfoVoList = new ArrayList<>();
        stockTemplateSkuInfoVoList = pmStoreGoodsStockMapper.searchSkuInfoByStockId(id);
        storeGoodsStockVo.setStockTemplateSkuInfoVoList(stockTemplateSkuInfoVoList);

        return storeGoodsStockVo;
    }

    /**
     * 根据reld删除库存关联 退回库存
     *
     * @param id
     */
    @Override
    public void delRelById(Long id) {
        PmStoreRelGoodsStockPo pmStoreRelGoodsStockPo = pmStoreRelGoodsStockService.getById(id);
        if(null == pmStoreRelGoodsStockPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "记录不存在");
        }
        //删除分配的库存
        updateGoodsStock (pmStoreRelGoodsStockPo);
    }

    /**
     * 删除分配的库存
     * 1.如果上级店铺的库存来自分配的库存  需要将库存退回到上级店铺原批次的库存中
     * 2.上级店铺以及当前店铺的商品虚拟库存各自增减
     * 3.逻辑删除分配的库存记录
     * @param pmStoreRelGoodsStockPo
     */
    private void updateGoodsStock (PmStoreRelGoodsStockPo pmStoreRelGoodsStockPo) {

        //修改库存
        PmStoreRelGoodsStockPo parentRelpo = pmStoreRelGoodsStockService.getById(pmStoreRelGoodsStockPo.getParentId());
        if(null != parentRelpo) {
            //上级店铺的库存商品也是分配的商品 不是自有商品 库存退回上级原批次的库存
            UpdateWrapper<PmStoreRelGoodsStockPo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(PmStoreRelGoodsStockPo::getRemainingStockNum,
                    parentRelpo.getRemainingStockNum() + pmStoreRelGoodsStockPo.getDistributeStockNum());
            pmStoreRelGoodsStockService.update(updateWrapper);
        }
        PmStoreGoodsStockPo pmStoreGoodsStockPo = pmStoreGoodsStockMapper.selectById(pmStoreRelGoodsStockPo.getStoreStockId());
        //用户对应的商品规格库存修改
        Integer result = pmGoodsVirtualStockMapper.updateGoodsVirtualStock(pmStoreRelGoodsStockPo.getStoreId(), pmStoreGoodsStockPo.getStoreId(),
                pmStoreRelGoodsStockPo.getGoodsSkuId(), pmStoreRelGoodsStockPo.getDistributeStockNum());
        if(result < 1) {
            //没有更新两条数据
            throw new ServiceException(ResultCode.PARAM_ERROR, "库存不足");
        }
        //删除关联
        pmStoreRelGoodsStockService.removeById(pmStoreRelGoodsStockPo.getId());
    }

    /**
     * 分页条件查询
     * 根据库存名称，创建时间，状态，分配商家，库存数量查询
     *
     * @param searchStoreGoodsStockDto
     * @return
     */
    @Override
    public PageInfo<StoreGoodsStockVo> searchPaging(SearchStoreGoodsStockDto searchStoreGoodsStockDto) {
        Long storeId = securityUtil.getCurrUser().getStoreId();
        searchStoreGoodsStockDto.setStoreId(storeId);

        Integer pageNo = searchStoreGoodsStockDto.getPageNo()==null ? defaultPageNo : searchStoreGoodsStockDto.getPageNo();
        Integer pageSize = searchStoreGoodsStockDto.getPageSize()==null ? defaultPageSize : searchStoreGoodsStockDto.getPageSize();

        PageInfo<StoreGoodsStockVo> informationLabelVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> pmStoreGoodsStockMapper.searchPaging(searchStoreGoodsStockDto));
        return informationLabelVoPageInfo;
    }


    /**
     * 店铺库存禁用启用
     *
     * @return
     */
    @Override
    public void editStoreStockStatus(BaseUpdateStatusDto baseUpdateStatusDto) {
        this.editEnabledBatch(baseUpdateStatusDto);
    }


    /**
     * 店铺库存删除 ，相应的库存增减
     *
     * @param id
     */
    @Override
    public void delById(Long id) {
        PmStoreGoodsStockPo pmStoreGoodsStockPo = pmStoreGoodsStockMapper.selectById(id);
        verifyData(pmStoreGoodsStockPo);

        QueryWrapper<PmStoreRelGoodsStockPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmStoreRelGoodsStockPo::getStoreStockId, pmStoreGoodsStockPo.getId());
        List<PmStoreRelGoodsStockPo> pmStoreRelGoodsStockPoList = pmStoreRelGoodsStockService.list(queryWrapper);
        //删除分配的库存
        pmStoreRelGoodsStockPoList.forEach(x -> updateGoodsStock (x));

        //删除店铺库存记录
        pmStoreGoodsStockMapper.deleteById(pmStoreGoodsStockPo.getId());
    }


}
