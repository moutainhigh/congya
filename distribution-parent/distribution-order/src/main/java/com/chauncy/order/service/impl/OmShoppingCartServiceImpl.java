package com.chauncy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.mapper.order.OmShoppingCartMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.mapper.product.PmGoodsSkuMapper;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.cart.StoreGoodsVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车列表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmShoppingCartServiceImpl extends AbstractService<OmShoppingCartMapper, OmShoppingCartPo> implements IOmShoppingCartService {

    @Autowired
    private OmShoppingCartMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PmGoodsSkuMapper skuMapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    /**
     * 添加商品到购物车
     *
     * @param addCartDto
     * @return
     */
    @Override
    public void addToCart(AddCartDto addCartDto) {
        //获取当前app用户信息
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        //判断购物车是否存在该商品
        Map<String,Object> map = new HashMap<>();
        map.put("sku_id",addCartDto.getSkuId());
        List<OmShoppingCartPo> shoppingCartPos = mapper.selectByMap(map);
        boolean exit = shoppingCartPos!=null && shoppingCartPos.size()!=0;
        //判断当前库存是否足够
        Integer originStock = skuMapper.selectById(addCartDto.getSkuId()).getStock();

        //查找购物车是否已经存在该商品
        if (exit){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("sku_id",addCartDto.getSkuId());
            OmShoppingCartPo shoppingCartPo = mapper.selectOne(queryWrapper);
            shoppingCartPo.setNum(shoppingCartPo.getNum()+addCartDto.getNum());
            if (originStock<shoppingCartPo.getNum()){
                throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY,"库存不足!");
            }
            mapper.updateById(shoppingCartPo);
        }
        //不存在
        else {
            if (originStock<addCartDto.getNum()){
                throw new ServiceException(ResultCode.NSUFFICIENT_INVENTORY,"库存不足!");
            }
            OmShoppingCartPo omShoppingCartPo = new OmShoppingCartPo();
            BeanUtils.copyProperties(addCartDto,omShoppingCartPo);
            omShoppingCartPo.setUserId(umUserPo.getId());
            omShoppingCartPo.setId(null);
            omShoppingCartPo.setCreateBy(umUserPo.getName());
            mapper.insert(omShoppingCartPo);
        }
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @Override
    public PageInfo<CartVo> SearchCart(SearchCartDto searchCartDto) {
        //获取当前用户
         UmUserPo userPo = securityUtil.getAppCurrUser();
        Integer pageNo = searchCartDto.getPageNo() == null ? defaultPageNo : searchCartDto.getPageNo();
        Integer pageSize = searchCartDto.getPageSize() == null ? defaultPageSize : searchCartDto.getPageSize();
        PageInfo<CartVo> cartVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchCart(userPo.getId()));
        //对购物车库存处理
        cartVoPageInfo.getList().forEach(a->{
            List<StoreGoodsVo> storeGoodsVos = Lists.newArrayList();
            a.getStoreGoodsVoList().forEach(b->{
                Integer sum = skuMapper.selectById(b.getSkuId()).getStock();
                b.setSum(sum);
                //库存不足处理
                if (sum==0){
                    b.setIsSoldOut(true);
                    b.setNum(0);
                }else if(b.getNum()>=sum){
                    b.setNum(sum);
                }
                //下架处理,宝贝失效处理
                if (goodsMapper.selectById(skuMapper.selectById(b.getSkuId()).getGoodsId()).getPublishStatus()!=null) {
                    boolean publish = goodsMapper.selectById(skuMapper.selectById(b.getSkuId()).getGoodsId()).getPublishStatus();
                    if (!publish) {
                        b.setIsObtained(true);
                    }
                }
                storeGoodsVos.add(b);
            });
            a.setStoreGoodsVoList(storeGoodsVos);
        });

        return cartVoPageInfo;
    }

    /**
     * 批量删除购物车
     *
     * @return
     */
    @Override
    public void delCart(Long[] ids) {
        //判断商品是否存在
        Arrays.asList(ids).forEach(a->{
            if (mapper.selectById(a)==null){
                throw new ServiceException(ResultCode.NO_EXISTS,"数据不存在");
            }
        });
        mapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 修改购物车商品
     *
     * @param updateCartDto
     * @return
     */
    @Override
    public void updateCart(AddCartDto updateCartDto) {
        OmShoppingCartPo cartPo = new OmShoppingCartPo();
        BeanUtils.copyProperties(updateCartDto,cartPo);
        mapper.updateById(cartPo);
    }
}
