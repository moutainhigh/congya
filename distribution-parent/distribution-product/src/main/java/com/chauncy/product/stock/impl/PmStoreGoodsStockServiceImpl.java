package com.chauncy.product.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockPo;
import com.chauncy.data.domain.po.product.stock.PmStoreGoodsStockPo;
import com.chauncy.data.domain.po.product.stock.PmStoreRelGoodsStockPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.dto.supplier.good.add.StoreRelGoodsStockBaseDto;
import com.chauncy.data.mapper.product.stock.PmGoodsVirtualStockMapper;
import com.chauncy.data.mapper.product.stock.PmStoreGoodsStockMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.chauncy.product.stock.IPmGoodsVirtualStockService;
import com.chauncy.product.stock.IPmStoreGoodsStockService;
import com.chauncy.product.stock.IPmStoreRelGoodsStockService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private PmStoreGoodsStockMapper pmStoreGoodsStockMapper;
    @Autowired
    private PmGoodsVirtualStockMapper pmGoodsVirtualStockMapper;
    @Autowired
    private IPmGoodsVirtualStockService pmGoodsVirtualStockService;
    @Autowired
    private IPmStoreRelGoodsStockService pmStoreRelGoodsStockService;
    @Autowired
    private SecurityUtil securityUtil;

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
        for(StoreRelGoodsStockBaseDto storeRelGoodsStockBaseDto : storeRelGoodsStockBaseDtoList) {
            //查询剩余库存
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("goods_sku_id", storeRelGoodsStockBaseDto.getGoodsSkuId());
            queryWrapper.eq("store_id", pmStoreGoodsStockPo.getStoreId());
            PmGoodsVirtualStockPo pmGoodsVirtualStockPo = pmGoodsVirtualStockService.getOne(queryWrapper);
            if(pmGoodsVirtualStockPo.getStockNum() >= storeRelGoodsStockBaseDto.getDistributeStockNum()) {
                //库存充足 分配库存详情插入
                PmStoreRelGoodsStockPo pmStoreRelGoodsStockPo = new PmStoreRelGoodsStockPo();
                pmStoreRelGoodsStockPo.setStoreStockId(pmStoreGoodsStockPo.getId());
                pmStoreRelGoodsStockPo.setGoodsSkuId(storeRelGoodsStockBaseDto.getGoodsSkuId());
                pmStoreRelGoodsStockPo.setGoodsId(storeRelGoodsStockBaseDto.getGoodsId());
                pmStoreRelGoodsStockPo.setCreateBy(pmStoreGoodsStockPo.getCreateBy());
                pmStoreRelGoodsStockPo.setDistributeStockNum(storeRelGoodsStockBaseDto.getDistributeStockNum());
                //todo  需要判断供货价是否满足条件
                pmStoreRelGoodsStockPo.setDistributePrice(storeRelGoodsStockBaseDto.getDistributePrice());
                pmStoreRelGoodsStockPoList.add(pmStoreRelGoodsStockPo);

                //分配库存成功 修改库存信息
                int result = pmGoodsVirtualStockMapper.updateGoodsVirtualStock(pmStoreGoodsStockPo.getStoreId(), pmStoreGoodsStockPo.getDistributeStoreId(),
                        storeRelGoodsStockBaseDto.getGoodsSkuId(), storeRelGoodsStockBaseDto.getDistributeStockNum());
                if(result < 2) {
                    //没有更新两条数据
                    throw new ServiceException(ResultCode.PARAM_ERROR, "库存不足");
                }
            } else {
                throw new ServiceException(ResultCode.PARAM_ERROR, "库存不足");
            }
        }
        pmStoreRelGoodsStockService.saveBatch(pmStoreRelGoodsStockPoList);
    }

}
