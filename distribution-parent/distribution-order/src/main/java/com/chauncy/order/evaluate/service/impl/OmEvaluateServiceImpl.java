package com.chauncy.order.evaluate.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.order.OmEvaluatePo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.evaluate.add.AddValuateDto;
import com.chauncy.data.dto.app.order.evaluate.add.SearchEvaluateDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetPersonalEvaluateDto;
import com.chauncy.data.mapper.order.OmEvaluateMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import com.chauncy.order.evaluate.service.IOmEvaluateService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private OmOrderMapper orderMapper;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private SmStoreMapper storeMapper;

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
            BeanUtils.copyProperties(addValuateDto, omEvaluatePo);
            omEvaluatePo.setId(null);
            omEvaluatePo.setParentId(null);
            omEvaluatePo.setCreateBy("获取的当前用户");

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
        }
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

        return getReply(goodsEvaluateVo);

    }

    /**
     * 用户获取已经评价的商品评价信息
     * @param getPersonalEvaluateDto
     * @return
     */
    @Override
    public PageInfo<GoodsEvaluateVo> getPersonalEvaluate(GetPersonalEvaluateDto getPersonalEvaluateDto) {

        //获取当前用户id
        Long userId = 1L;
        Integer pageNo = getPersonalEvaluateDto.getPageNo() == null ? defaultPageNo : getPersonalEvaluateDto.getPageNo();
        Integer pageSize = getPersonalEvaluateDto.getPageSize() == null ? defaultPageSize : getPersonalEvaluateDto.getPageSize();
        PageInfo<GoodsEvaluateVo> goodsEvaluateVo = new PageInfo<>();

        goodsEvaluateVo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.getPersonalEvaluate(getPersonalEvaluateDto,userId));

        return getReply(goodsEvaluateVo);

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
