package com.chauncy.order.evaluate.service.impl;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.evaluate.add.AddValuateDto;
import com.chauncy.data.dto.app.order.evaluate.add.SearchEvaluateDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetPersonalEvaluateDto;
import com.chauncy.data.dto.supplier.evaluate.SaveStoreReplyDto;
import com.chauncy.data.dto.supplier.good.select.SearchEvaluatesDto;
import com.chauncy.data.mapper.order.OmEvaluateMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.supplier.evaluate.EvaluateVo;
import com.chauncy.data.vo.supplier.evaluate.SearchEvaluateVo;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private OmOrderMapper orderMapper;

    /**
     * 用户进行商品评价
     *
     * @param addValuateDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addEvaluate(AddValuateDto addValuateDto) {

        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        List<OmEvaluatePo> saveOmEvaluatePos= Lists.newArrayList();
        addValuateDto.getAddCommentSkus().forEach(x->{
            OmEvaluatePo omEvaluatePo=new OmEvaluatePo();
            BeanUtils.copyProperties(addValuateDto, omEvaluatePo);
            BeanUtils.copyProperties(x, omEvaluatePo);
            omEvaluatePo.setCreateBy(appCurrUser.getPhone());
            saveOmEvaluatePos.add(omEvaluatePo);
        });
        this.saveBatch(saveOmEvaluatePos);

        OmOrderPo updateOrder=new OmOrderPo();
        updateOrder.setId(addValuateDto.getOrderId()).setStatus(OrderStatusEnum.ALREADY_EVALUATE);
        orderMapper.updateById(updateOrder);


        /*//判断是否是第一次评论
        if (addValuateDto.getId() == 0) {
            SysUserPo user= securityUtil.getCurrUser ();
            OmEvaluatePo omEvaluatePo = new OmEvaluatePo();
            BeanUtils.copyProperties(addValuateDto, omEvaluatePo);
            omEvaluatePo.setId(Long.valueOf (user.getId ()));
            omEvaluatePo.setParentId(null);
            omEvaluatePo.setCreateBy(user.getUsername ());

            mapper.insert(omEvaluatePo);
            //商家回复
        } else {
            Long storeId = securityUtil.getCurrUser().getStoreId();
            String name = storeMapper.selectById(storeId).getName();
            OmEvaluatePo omEvaluatePo = new OmEvaluatePo();
            BeanUtils.copyProperties(addValuateDto, omEvaluatePo);
            omEvaluatePo.setId(null);
            omEvaluatePo.setParentId(addValuateDto.getId());
            omEvaluatePo.setCreateBy(name);
            mapper.insert(omEvaluatePo);
        }*/
    }

    /**
     * 获取商品对应的所有评价信息
     *
     * @return
     */
    @Override
    public PageInfo<GoodsEvaluateVo> getGoodsEvaluate(SearchEvaluateDto searchEvaluateDto) {

        Integer pageNo = searchEvaluateDto.getPageNo() == null ? defaultPageNo : searchEvaluateDto.getPageNo();
        Integer pageSize = searchEvaluateDto.getPageSize() == null ? defaultPageSize : searchEvaluateDto.getPageSize();
        PageInfo<GoodsEvaluateVo> goodsEvaluateVo = new PageInfo<>();

        goodsEvaluateVo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.getGoodsEvaluate(searchEvaluateDto));

        if (goodsEvaluateVo.getList().size() != 0 && goodsEvaluateVo.getList() != null) {
            goodsEvaluateVo.getList ().forEach (a -> {
                Map<String, Object> map1 = new HashMap<> ();
                map1.put ("parent_id", a.getId ());
                List<OmEvaluatePo> evaluatePo = mapper.selectByMap (map1);
                if (evaluatePo != null && evaluatePo.size () != 0) {
                    a.setReply (evaluatePo.get (0).getContent ());
                }
            });
        }
        return goodsEvaluateVo;

    }

    /**
     * 用户获取已经评价的商品评价信息
     * @param getPersonalEvaluateDto
     * @return
     */
    @Override
    public PageInfo<GoodsEvaluateVo> getPersonalEvaluate(GetPersonalEvaluateDto getPersonalEvaluateDto) {

        //获取当前用户id
        UmUserPo userPo = securityUtil.getAppCurrUser ();
        Long userId = userPo.getId ();
        Integer pageNo = getPersonalEvaluateDto.getPageNo() == null ? defaultPageNo : getPersonalEvaluateDto.getPageNo();
        Integer pageSize = getPersonalEvaluateDto.getPageSize() == null ? defaultPageSize : getPersonalEvaluateDto.getPageSize();
        PageInfo<GoodsEvaluateVo> goodsEvaluateVo = new PageInfo<>();

        goodsEvaluateVo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.getPersonalEvaluate(getPersonalEvaluateDto,userId));

        return getReply(goodsEvaluateVo);

    }

    /**
     * 条件查询评价信息
     * @param searchEvaluateDto
     * @return
     */
    @Override
    public PageInfo<SearchEvaluateVo> searchEvaluate(SearchEvaluatesDto searchEvaluateDto) {

        //获取当前用户并判断属于哪种用户
        Long storeId = securityUtil.getCurrUser().getStoreId();
        if (storeId!=null){
            searchEvaluateDto.setStoreId(storeId);
        }
        Integer pageNo = searchEvaluateDto.getPageNo() == null ? defaultPageNo : searchEvaluateDto.getPageNo();
        Integer pageSize = searchEvaluateDto.getPageSize() == null ? defaultPageSize : searchEvaluateDto.getPageSize();

        PageInfo<SearchEvaluateVo> searchEvaluateVo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->mapper.searchEvaluate(searchEvaluateDto));
        //获取评价信息
        if (searchEvaluateVo.getList().size() != 0 && searchEvaluateVo.getList() != null) {
            searchEvaluateVo.getList().forEach(a -> {
                //用户的评价
                EvaluateVo evaluateVo1 = mapper.getEvaluate(a.getOrderId(),a.getSkuId());
                //获取回复信息
                Map<String, Object> map1 = new HashMap<>();
                map1.put("parent_id", evaluateVo1.getId());
                if (mapper.selectByMap(map1) != null && mapper.selectByMap(map1).size() != 0) {
                    evaluateVo1.setReply(mapper.selectByMap(map1).get(0).getContent());
                }
                a.setEvaluateVo(evaluateVo1);
            });
        }
        return searchEvaluateVo;
    }

    @Override
    public void reply(SaveStoreReplyDto saveStoreReplyDto) {
        SysUserPo sysUserPo = securityUtil.getCurrUser();
        OmEvaluatePo saveEvaluate=new OmEvaluatePo();
        saveEvaluate.setContent(saveStoreReplyDto.getContent()).setParentId(saveStoreReplyDto.getEvaluateId())
        .setCreateBy(sysUserPo.getUsername()).setOrderId(saveStoreReplyDto.getOrderId());
        mapper.insert(saveEvaluate);
    }

    /**
     * 获取回复
     *
     * @param goodsEvaluateVo
     * @return
     */
    private PageInfo<GoodsEvaluateVo> getReply(PageInfo<GoodsEvaluateVo> goodsEvaluateVo) {
        if (goodsEvaluateVo.getList().size() != 0 && goodsEvaluateVo.getList() != null) {
            goodsEvaluateVo.getList().forEach(a -> {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("parent_id", a.getId());
                List<OmEvaluatePo> evaluatePo = mapper.selectByMap(map1);
                if (evaluatePo != null && evaluatePo.size() != 0) {
                    a.setReply(evaluatePo.get(0).getContent());
                }
            });
        }
        return goodsEvaluateVo;
    }
}
