package com.chauncy.order.customs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.order.CustomsStatusEnum;
import com.chauncy.data.domain.po.order.CustomsDataPo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderCustomDeclarePo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.pay.PayParamPo;
import com.chauncy.data.haiguan.vo.*;
import com.chauncy.data.mapper.order.CustomsDataMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderCustomDeclareMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.mapper.pay.PayParamMapper;
import com.google.common.collect.Lists;
import org.assertj.core.util.Maps;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 海关字段 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomsDataServiceImpl extends AbstractService<CustomsDataMapper, CustomsDataPo> implements ICustomsDataService {

    @Autowired
    private CustomsDataMapper mapper;

    @Autowired
    private OmGoodsTempMapper goodsTempMapper;

    @Autowired
    private OmOrderMapper orderMapper;

    @Autowired
    private IPayOrderMapper payOrderMapper;

    @Autowired
    private PayParamMapper payParamMapper;

    @Autowired
    private OmOrderCustomDeclareMapper orderCustomDeclareMapper;

    @Override
    public CustomsDataWithMyId getHgCheckVo(String customsDataId) {
        //查出orderid和sessionid
        QueryWrapper<CustomsDataPo> customsDataPoQueryWrapper = new QueryWrapper<>();
        if (customsDataId==null){
            customsDataPoQueryWrapper.lambda().eq(CustomsDataPo::getCustomsStatus, CustomsStatusEnum.NEED_SEND);
            customsDataPoQueryWrapper.last(" limit 1");
        }
        else {
            customsDataPoQueryWrapper.lambda().eq(CustomsDataPo::getId, customsDataId);

        }
        CustomsDataPo queryCustom = mapper.selectOne(customsDataPoQueryWrapper);

        if (queryCustom==null){
            return null;
        }

        //查出商品快照表
        QueryWrapper<OmGoodsTempPo> goodsTempPoQueryWrapper=new QueryWrapper<>();
        goodsTempPoQueryWrapper.lambda().eq(OmGoodsTempPo::getOrderId,queryCustom.getOrderId());
        List<OmGoodsTempPo> queryGoodsTemps = goodsTempMapper.selectList(goodsTempPoQueryWrapper);


        //查出订单信息
        OmOrderPo queryOrder=orderMapper.selectById(queryCustom.getOrderId());

        //查出支付单信息
        PayOrderPo queryPayOrder = payOrderMapper.selectById(queryOrder.getPayOrderId());

        //查出支付请求和请求响应
        PayParamPo queryPayParam=payParamMapper.selectOne(new QueryWrapper<PayParamPo>().lambda().eq(PayParamPo::getPayOrderId,queryOrder.getPayOrderId()));

      /*  //查出拆单后海关数据
        OmOrderCustomDeclarePo orderCustomDeclarePo=orderCustomDeclareMapper.selectOne(new QueryWrapper<OmOrderCustomDeclarePo>().
                lambda().eq(OmOrderCustomDeclarePo::getOrderId,queryCustom.getOrderId()));*/


        HgCheckVO hgCheckVO = new HgCheckVO();
        List<Body179> payExchangeInfoLists = Lists.newArrayList();
        Head179 payExchangeInfoHead=new Head179();
        List<GoodsInfo> goodsInfos = Lists.newArrayList();

        queryGoodsTemps.forEach(x->{
            GoodsInfo goodsInfo=new GoodsInfo();
            // TODO: 2019/9/22 商品链接拼接上id
            goodsInfo.setGname(x.getName()).setItemLink(x.getId()+"");
            goodsInfos.add(goodsInfo);
        });
        payExchangeInfoHead.setInitalRequest("uy").setInitalResponse("iu").setPayTransactionId(queryPayOrder.getPayOrderNo())
                .setTradingTime(queryPayOrder.getPayTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()+"")
                .setTotalAmount(queryOrder.getTotalMoney()).setGuid(queryCustom.getId()+"");


        Body179 body179=new Body179();
        body179.setOrderNo(queryCustom.getOrderId()).setGoodsInfo(goodsInfos);
        payExchangeInfoLists.add(body179);

        hgCheckVO.setServiceTime(queryCustom.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()+"").setSessionID(queryCustom.getSessionId())
        .setPayExchangeInfoHead(payExchangeInfoHead).setPayExchangeInfoLists(payExchangeInfoLists);

        return new CustomsDataWithMyId(queryCustom.getId()+"",hgCheckVO);
    }
}
