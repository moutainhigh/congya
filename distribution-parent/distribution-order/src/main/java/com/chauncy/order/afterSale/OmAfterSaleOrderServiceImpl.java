package com.chauncy.order.afterSale;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleLogEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.data.bo.app.order.rabbit.RabbitAfterBo;
import com.chauncy.data.bo.manage.order.log.AddAccountLogBo;
import com.chauncy.data.domain.po.afterSale.OmAfterSaleLogPo;
import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.store.SmStorePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.my.afterSale.ApplyRefundDto;
import com.chauncy.data.dto.app.order.my.afterSale.SendDto;
import com.chauncy.data.dto.app.order.my.afterSale.UpdateRefundDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.dto.manage.order.afterSale.SearchAfterSaleOrderDto;
import com.chauncy.data.mapper.afterSale.OmAfterSaleLogMapper;
import com.chauncy.data.mapper.afterSale.OmAfterSaleOrderMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.store.SmStoreMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.order.my.afterSale.AfterSaleDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterDetailVo;
import com.chauncy.data.vo.manage.order.afterSale.AfterSaleListVo;
import com.chauncy.order.service.IOmAfterSaleLogService;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
import com.chauncy.data.vo.app.order.my.afterSale.MyAfterSaleOrderListVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 售后订单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmAfterSaleOrderServiceImpl extends AbstractService<OmAfterSaleOrderMapper, OmAfterSaleOrderPo> implements IOmAfterSaleOrderService {

    @Autowired
    private OmAfterSaleOrderMapper mapper;

    @Autowired
    private OmGoodsTempMapper goodsTempMapper;

    @Autowired
    private OmOrderMapper omOrderMapper;

    @Autowired
    private SmStoreMapper storeMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private IOmAfterSaleLogService afterSaleLogService;

    @Autowired
    private OmAfterSaleLogMapper afterSaleLogMapper;

    @Autowired
    private IOmOrderService omOrderService;


    @Autowired
    private UmUserMapper userMapper;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyAfterSaleVo validCanAfterSaleVo(Long goodsTempId) {

        OmGoodsTempPo queryGoodsTemp = goodsTempMapper.selectById(goodsTempId);

        //用户点击过一次售后就不能再点击了
        if (!queryGoodsTemp.getCanAfterSale()) {
            throw new ServiceException(ResultCode.FAIL, "该商品已售后过，不能再进行售后！");
        }

        OmOrderPo queryOrder = omOrderMapper.selectById(queryGoodsTemp.getOrderId());

        OrderStatusEnum orderStatus = queryOrder.getStatus();
        if (!orderStatus.canAfterSale()) {
            throw new ServiceException(ResultCode.FAIL, String.format("%s的订单状态没有售后资格！", orderStatus.getName()));
        }
        String goodsType = queryOrder.getGoodsType();
        if ("自取".equals(goodsType) || "服务类".equals(goodsType)) {
            throw new ServiceException(ResultCode.FAIL, "自取或服务类商品不能进行售后！");
        }

        //售后截止时间
        LocalDateTime afterSaleDeadline = queryOrder.getAfterSaleDeadline();

        if (afterSaleDeadline != null && LocalDateTime.now().isAfter(afterSaleDeadline)) {
            throw new ServiceException(ResultCode.FAIL, "已超过售后截止时间，该商品不能进行售后！");
        }

        ApplyAfterSaleVo applyAfterSaleVo = new ApplyAfterSaleVo();
        BeanUtils.copyProperties(queryGoodsTemp, applyAfterSaleVo);
        applyAfterSaleVo.setGoodsTempId(queryGoodsTemp.getId());

        return applyAfterSaleVo;
    }

    @Override
    public List<ApplyAfterSaleVo> searchBrotherById(Long orderId) {
        return mapper.searchBrothersById(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(ApplyRefundDto applyRefundDto) {
        UmUserPo currentUser = securityUtil.getAppCurrUser();

        QueryWrapper<OmGoodsTempPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(OmGoodsTempPo::getId, applyRefundDto.getGoodsTempIds());
        List<OmGoodsTempPo> queryGoodsTempPos = goodsTempMapper.selectList(queryWrapper);
        //该退款列表最大能退多少钱
        BigDecimal realPayMoney = queryGoodsTempPos.stream().map(x -> x.getRealPayMoney()).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        if (realPayMoney.compareTo(applyRefundDto.getRefundMoney()) < 0) {
            throw new ServiceException(ResultCode.FAIL, String.format("操作失败！退款金额大于实付金额:【%s】>【%s】", applyRefundDto.getRefundMoney()
                    , realPayMoney));
        }

        List<OmAfterSaleOrderPo> saveAfterSaleOrders = Lists.newArrayList();
        List<OmAfterSaleLogPo> saveAfterSaleLogs = Lists.newArrayList();
        applyRefundDto.getGoodsTempIds().forEach(x -> {
            //商品快照详情
            OmAfterSaleOrderPo saveAfterSaleOrder = new OmAfterSaleOrderPo();
            //复制reason、refundMoney、pictures
            BeanUtils.copyProperties(applyRefundDto, saveAfterSaleOrder, "goodsTempId");
            saveAfterSaleOrder.setGoodsTempId(x);
            //商品名称和数量
            OmGoodsTempPo queryGoodsTemp = queryGoodsTempPos.stream().filter(y -> y.getId().equals(x)).findFirst().orElse(null);
            saveAfterSaleOrder.setGoodsName(queryGoodsTemp.getName()).setNumber(queryGoodsTemp.getNumber());
            //订单号、店铺id、订单类型
            OmOrderPo queryOrder = omOrderMapper.selectById(queryGoodsTemp.getOrderId());
            saveAfterSaleOrder.setOrderId(queryOrder.getId()).setStoreId(queryOrder.getStoreId()).setGoodsType(queryOrder.getGoodsType());
            //店铺名称
            SmStorePo queryStore = storeMapper.selectById(queryOrder.getStoreId());
            saveAfterSaleOrder.setStoreName(queryStore.getName());
            //创建者、用户手机
            saveAfterSaleOrder.setCreateBy(currentUser.getId().toString()).setUpdateTime(LocalDateTime.now().plusDays(1)).setPhone(currentUser.getPhone()).setAfterSaleType(applyRefundDto.getType())
                    .setStatus(AfterSaleStatusEnum.NEED_STORE_DO).setId(SnowFlakeUtil.getFlowIdInstance().nextId());
            saveAfterSaleOrders.add(saveAfterSaleOrder);

            //售后进度日志
            OmAfterSaleLogPo saveAfterSaleLog = new OmAfterSaleLogPo();
            saveAfterSaleLog.setCreateBy(currentUser.getId().toString()).setAfterSaleOrderId(saveAfterSaleOrder.getId()).
                    setDescribes(applyRefundDto.getDescribe());
            if (applyRefundDto.getType() == AfterSaleTypeEnum.ONLY_REFUND) {
                saveAfterSaleLog.setNode(AfterSaleLogEnum.ONLY_REFUND_BUYER_START);
            } else {
                saveAfterSaleLog.setNode(AfterSaleLogEnum.BUYER_START);

            }
            saveAfterSaleLogs.add(saveAfterSaleLog);

        });
        saveBatch(saveAfterSaleOrders);
        afterSaleLogService.saveBatch(saveAfterSaleLogs);


        //该商品设置不可再进行售后
        OmGoodsTempPo updateGoodsTemp = new OmGoodsTempPo();
        updateGoodsTemp.setCanAfterSale(false);
        UpdateWrapper<OmGoodsTempPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(OmGoodsTempPo::getId, applyRefundDto.getGoodsTempIds());
        goodsTempMapper.update(updateGoodsTemp, updateWrapper);

        //发送延时队列
        saveAfterSaleOrders.forEach(x -> {
            RabbitAfterBo rabbitAfterBo = new RabbitAfterBo();
            rabbitAfterBo.setAfterSaleOrderId(x.getId());
            rabbitAfterBo.setAfterSaleStatusEnum(x.getStatus());
            rabbitAfterBo.setUpdateTime(x.getUpdateTime());
            sendMessage(rabbitAfterBo);
        });

    }

    /**
     * 售后状态改变后增加延迟队列
     *
     * @param rabbitAfterBo
     */
    private void sendMessage(RabbitAfterBo rabbitAfterBo) {
        // 添加延时队列
        this.rabbitTemplate.convertAndSend(RabbitConstants.AFTER_DEAD_EXCHANGE, RabbitConstants.AFTER_DEAD_ROUTING_KEY, rabbitAfterBo, message -> {

            message.getMessageProperties().setExpiration(RabbitConstants.AFTER_DELAY_TIME);
            return message;
        });
        LoggerUtil.info(String.format("售后订单id【%s】,状态【%s】发送消息队列时间：",rabbitAfterBo.getAfterSaleOrderId(),rabbitAfterBo.getAfterSaleStatusEnum().getName())
                + LocalDateTime.now());


    }


    @Override
    public PageInfo<MyAfterSaleOrderListVo> searchAfterSaleOrderList(BasePageDto basePageDto) {
        UmUserPo currentUser = securityUtil.getAppCurrUser();


        return PageHelper.startPage(basePageDto.getPageNo(), basePageDto.getPageSize()).
                doSelectPageInfo(() -> mapper.searchAfterSaleOrderList(currentUser.getId()));

    }

    @Override
    public PageInfo<AfterSaleListVo> searchAfterList(SearchAfterSaleOrderDto searchAfterSaleOrderDto) {
        SysUserPo currUser = securityUtil.getCurrUser();
        Long storeId = null;
        //如果是商家，只能查找自己店铺下的售后订单
        if (currUser.getSystemType().equals(SecurityConstant.SYS_TYPE_SUPPLIER)) {
            storeId = currUser.getStoreId();
        }
        Long finalStoreId = storeId;
        PageInfo<AfterSaleListVo> afterSaleListVoPageInfo = PageHelper.startPage(searchAfterSaleOrderDto.getPageNo(), searchAfterSaleOrderDto.getPageSize()).
                doSelectPageInfo(() -> mapper.searchAfterList(searchAfterSaleOrderDto, finalStoreId));
        afterSaleListVoPageInfo.getList().forEach(x -> {
            x.setAfterSaleLogVos(mapper.searchCheckList(x.getAfterSaleOrderId()));
        });
        return afterSaleListVoPageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void permitRefund(Long afterSaleOrderId,boolean isAuto) {

        String userId="auto";
        if (!isAuto){
            SysUserPo currUser = securityUtil.getCurrUser();
            userId=currUser.getId();
        }

        OmAfterSaleOrderPo queryAfterSaleOrder = mapper.selectById(afterSaleOrderId);

        //添加售后进度
        OmAfterSaleLogPo saveAfterSaleLog = new OmAfterSaleLogPo();
        //仅退款只有待商家处理状态才可以进行退款
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_DO) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许退款", queryAfterSaleOrder.getStatus().getName()));
            } else {
                if (isAuto){

                    saveAfterSaleLog.setNode(AfterSaleLogEnum.ONLY_REFUND_STORE_OVERTIME);
                }
                else {

                    saveAfterSaleLog.setNode(AfterSaleLogEnum.ONLY_REFUND_STORE_AGREE);
                }
            }
        }
        //退货退款只有待商家退款状态才可以进行退款
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_REFUND) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许退款", queryAfterSaleOrder.getStatus().getName()));
            } else {
                if (isAuto){

                    saveAfterSaleLog.setNode(AfterSaleLogEnum.STORE_OVERTIME_UNHANDLED_REFUND);
                }
                else {
                    saveAfterSaleLog.setNode(AfterSaleLogEnum.STORE_AGREE_REFUND);

                }
            }
        }
        saveAfterSaleLog.setAfterSaleOrderId(afterSaleOrderId).setCreateBy(userId);
        afterSaleLogService.save(saveAfterSaleLog);

        //改变售后订单状态
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setId(afterSaleOrderId).setUpdateBy(userId).setStatus(AfterSaleStatusEnum.SUCCESS);
        mapper.updateById(updateAfterOrder);

        //设置商品快照表售后成功
        OmGoodsTempPo updateGoodsTemp = new OmGoodsTempPo();
        updateGoodsTemp.setId(queryAfterSaleOrder.getGoodsTempId()).setIsAfterSale(true).setUpdateBy(userId);
        goodsTempMapper.updateById(updateGoodsTemp);

        //退还使用的红包、购物券
        OmGoodsTempPo queryGoodsTemp = goodsTempMapper.selectById(queryAfterSaleOrder.getGoodsTempId());
        OmOrderPo queryOrder=omOrderMapper.selectById(queryGoodsTemp.getOrderId());
        //根据商品销售价在订单中的比例算出退还的红包、购物券
        BigDecimal ratio=BigDecimalUtil.safeDivide(BigDecimalUtil.safeMultiply(queryGoodsTemp.getNumber(),queryGoodsTemp.getSellPrice()),
               BigDecimalUtil.safeSubtract(queryOrder.getTotalMoney(),queryOrder.getShipMoney(),queryOrder.getTaxMoney()) );
        UmUserPo updateUser=new UmUserPo();
        BigDecimal marginRedEnvelops = BigDecimalUtil.safeMultiply(ratio,queryOrder.getRedEnvelops());
        BigDecimal marginShopTicket = BigDecimalUtil.safeMultiply(ratio,queryOrder.getShopTicket());
        updateUser.setCurrentRedEnvelops(marginRedEnvelops)
                .setCurrentShopTicket(marginShopTicket)
                .setId(queryOrder.getUmUserId());
        userMapper.updateAdd(updateUser);


        //售后成功  购物券，红包退还 对应流水生成
        AddAccountLogBo addAccountLogBo = new AddAccountLogBo();
        addAccountLogBo.setLogTriggerEventEnum(LogTriggerEventEnum.ORDER_REFUND);
        addAccountLogBo.setRelId(queryAfterSaleOrder.getId());
        addAccountLogBo.setOperator(userId);
        addAccountLogBo.setMarginRedEnvelops(marginRedEnvelops);
        addAccountLogBo.setMarginShopTicket(marginShopTicket);
        addAccountLogBo.setUmUserId(queryOrder.getUmUserId());
        //listenerOrderLogQueue 消息队列
        this.rabbitTemplate.convertAndSend(
                RabbitConstants.ACCOUNT_LOG_EXCHANGE, RabbitConstants.ACCOUNT_LOG_ROUTING_KEY, addAccountLogBo);



    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseRefund(Long afterSaleOrderId) {
        SysUserPo currUser = securityUtil.getCurrUser();

        OmAfterSaleOrderPo queryAfterSaleOrder = mapper.selectById(afterSaleOrderId);

        //添加售后进度
        OmAfterSaleLogPo saveAfterSaleLog = new OmAfterSaleLogPo();
        //仅退款只有待商家处理状态才可以拒绝退款
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_DO) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许拒绝退款", queryAfterSaleOrder.getStatus().getName()));
            } else {
                saveAfterSaleLog.setNode(AfterSaleLogEnum.ONLY_REFUND_STORE_REFUSE);
            }
        }
        //退货退款只有待商家退款状态才可以拒绝退款
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_REFUND) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许拒绝退款", queryAfterSaleOrder.getStatus().getName()));
            } else {
                saveAfterSaleLog.setNode(AfterSaleLogEnum.STORE_REFUSE_REFUND);
            }
        }
        saveAfterSaleLog.setAfterSaleOrderId(afterSaleOrderId).setCreateBy(currUser.getId());
        afterSaleLogService.save(saveAfterSaleLog);
        //改变售后订单状态
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setId(afterSaleOrderId).setUpdateBy(currUser.getId())
        .setUpdateTime(LocalDateTime.now());
        //如果是退货退款，订单直接关闭
         if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS){
             updateAfterOrder.setStatus(AfterSaleStatusEnum.CLOSE);
             //售后订单关闭后进行返佣
             omOrderService.rakeBack(queryAfterSaleOrder.getGoodsTempId());
         }
         //如果是仅退款，订单变成待买家处理
        else {
             updateAfterOrder.setStatus(AfterSaleStatusEnum.NEED_BUYER_DO);
         }
        mapper.updateById(updateAfterOrder);

        //仅退款才需要发送延时队列
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND){

            //待买家处理---》（如果超时）退款关闭
            //延时队列需要的数据
            RabbitAfterBo rabbitAfterBo = new RabbitAfterBo();
            rabbitAfterBo.setUpdateTime(updateAfterOrder.getUpdateTime()).setAfterSaleOrderId(updateAfterOrder.getId())
                    .setAfterSaleStatusEnum(AfterSaleStatusEnum.NEED_BUYER_DO);
            //发送消息队列
            sendMessage(rabbitAfterBo);
        }

    }

    @Override
    public void permitReturnGoods(Long afterSaleOrderId,boolean isAuto) {
        String userId="auto";
        if (!isAuto){
            SysUserPo currUser = securityUtil.getCurrUser();
            userId=currUser.getId();
        }

        OmAfterSaleOrderPo queryAfterSaleOrder = mapper.selectById(afterSaleOrderId);

        //添加售后进度
        OmAfterSaleLogPo saveAfterSaleLog = new OmAfterSaleLogPo();
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND) {
            throw new ServiceException(ResultCode.FAIL, "仅退款订单不允许确认退货！");

        }
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_DO) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许确认退货", queryAfterSaleOrder.getStatus().getName()));
            } else {
                if (isAuto){
                    saveAfterSaleLog.setNode(AfterSaleLogEnum.STORE_OVERTIME_UNHANDLED_GOODS);

                }else {
                    saveAfterSaleLog.setNode(AfterSaleLogEnum.STORE_AGREE_GOODS);

                }
            }
        }
        saveAfterSaleLog.setAfterSaleOrderId(afterSaleOrderId).setCreateBy(userId);
        afterSaleLogService.save(saveAfterSaleLog);
        //改变售后订单状态
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setId(afterSaleOrderId).setUpdateBy(userId).setStatus(AfterSaleStatusEnum.NEED_BUYER_RETURN)
        .setUpdateTime(LocalDateTime.now());
        mapper.updateById(updateAfterOrder);

        //延时队列 待买家退货==》退款关闭
        RabbitAfterBo rabbitAfterBo=new RabbitAfterBo();
        rabbitAfterBo.setAfterSaleOrderId(updateAfterOrder.getId()).setAfterSaleStatusEnum(AfterSaleStatusEnum.NEED_BUYER_RETURN)
                .setUpdateTime(updateAfterOrder.getUpdateTime());
        sendMessage(rabbitAfterBo);


    }

    @Override
    public void refuseReturnGoods(Long afterSaleOrderId) {
        SysUserPo currUser = securityUtil.getCurrUser();

        OmAfterSaleOrderPo queryAfterSaleOrder = mapper.selectById(afterSaleOrderId);

        //添加售后进度
        OmAfterSaleLogPo saveAfterSaleLog = new OmAfterSaleLogPo();
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND) {
            throw new ServiceException(ResultCode.FAIL, "仅退款订单不允许拒绝退货！");

        }
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_DO) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许拒绝退货", queryAfterSaleOrder.getStatus().getName()));
            } else {
                saveAfterSaleLog.setNode(AfterSaleLogEnum.STORE_REFUSE_GOODS);
            }
        }
        saveAfterSaleLog.setAfterSaleOrderId(afterSaleOrderId).setCreateBy(currUser.getId());
        afterSaleLogService.save(saveAfterSaleLog);
        //改变售后订单状态
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setUpdateBy(currUser.getId()).setId(afterSaleOrderId).setStatus(AfterSaleStatusEnum.NEED_BUYER_DO)
        .setUpdateTime(LocalDateTime.now());
        mapper.updateById(updateAfterOrder);

        //延时队列 待买家处理==》退款关闭
        RabbitAfterBo rabbitAfterBo=new RabbitAfterBo();
        rabbitAfterBo.setAfterSaleOrderId(updateAfterOrder.getId()).setAfterSaleStatusEnum(AfterSaleStatusEnum.NEED_BUYER_DO)
                .setUpdateTime(updateAfterOrder.getUpdateTime());
        sendMessage(rabbitAfterBo);

    }

    @Override
    public void permitReceiveGoods(Long afterSaleOrderId) {
        SysUserPo currUser = securityUtil.getCurrUser();

        OmAfterSaleOrderPo queryAfterSaleOrder = mapper.selectById(afterSaleOrderId);

        //添加售后进度
        OmAfterSaleLogPo saveAfterSaleLog = new OmAfterSaleLogPo();
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND) {
            throw new ServiceException(ResultCode.FAIL, "仅退款订单不允许确认收货！");

        }
        if (queryAfterSaleOrder.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS) {
            if (queryAfterSaleOrder.getStatus() != AfterSaleStatusEnum.NEED_BUYER_RETURN) {
                throw new ServiceException(ResultCode.FAIL, String.format("当前售后订单状态为【%s】,不允许确认收货", queryAfterSaleOrder.getStatus().getName()));
            } else {
                saveAfterSaleLog.setNode(AfterSaleLogEnum.BUYER_RETURN_GOODS);
            }
        }
        saveAfterSaleLog.setAfterSaleOrderId(afterSaleOrderId).setCreateBy(currUser.getId());
        afterSaleLogService.save(saveAfterSaleLog);
        //改变售后订单状态
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setId(afterSaleOrderId).setUpdateBy(currUser.getId()).setStatus(AfterSaleStatusEnum.NEED_STORE_REFUND);
        mapper.updateById(updateAfterOrder);

    }

    @Override
    public AfterSaleDetailVo getAfterSaleDetail(Long afterSaleOrderId) {
        AfterSaleDetailVo afterSaleDetail = mapper.getAfterSaleDetail(afterSaleOrderId);
        //售后关闭和售后成功都不需要倒计时
        if (afterSaleDetail.getAfterSaleStatusEnum() != AfterSaleStatusEnum.CLOSE &&
                afterSaleDetail.getAfterSaleStatusEnum() != AfterSaleStatusEnum.SUCCESS) {
            LocalDateTime expireTime = afterSaleDetail.getOperatingTime().plusDays(3);
            Duration duration = Duration.between(LocalDateTime.now(), expireTime);
            afterSaleDetail.setRemainMinute(duration.toMinutes());
        }
        //售后说明和售后提示
        AfterSaleLogEnum node = afterSaleDetail.getNode();
        afterSaleDetail.setContentExplain(node.getContentExplain());
        afterSaleDetail.setContentTips(node.getContentTips());
        return afterSaleDetail;
    }

    @Override
    public ApplyAfterDetailVo getApplyAfterDetail(Long afterSaleOrderId) {
        return mapper.getApplyAfterDetail(afterSaleOrderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApply(UpdateRefundDto updateRefundDto) {
        OmAfterSaleOrderPo querySaleOrder = mapper.selectById(updateRefundDto.getId());

        QueryWrapper<OmGoodsTempPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OmGoodsTempPo::getId, querySaleOrder.getGoodsTempId());
        OmGoodsTempPo queryGoodsTempPo = goodsTempMapper.selectOne(queryWrapper);
        //该退款列表最大能退多少钱
        BigDecimal realPayMoney = queryGoodsTempPo.getRealPayMoney();
        if (realPayMoney.compareTo(updateRefundDto.getRefundMoney()) < 0) {
            throw new ServiceException(ResultCode.FAIL, String.format("操作失败！退款金额大于实付金额:【%s】>【%s】", updateRefundDto.getRefundMoney()
                    , realPayMoney));
        }
        //待商家处理和待买家处理状态才可以修改订单申请
        if (querySaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_DO &&
                querySaleOrder.getStatus() != AfterSaleStatusEnum.NEED_BUYER_DO) {
            throw new ServiceException(ResultCode.FAIL, "待商家处理和待买家处理状态才可以修改订单申请");
        } else {
            OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();

            LocalDateTime updateTime=LocalDateTime.now();
            //修改售后订单
            updateAfterOrder.setStatus(AfterSaleStatusEnum.NEED_STORE_DO).setPictures(updateRefundDto.getPictures())
                    .setId(updateRefundDto.getId()).setRefundMoney(updateRefundDto.getRefundMoney())
                    .setReason(updateRefundDto.getReason()).setUpdateTime(updateTime);
            mapper.updateById(updateAfterOrder);

            //找出用户第一个节点是什么（仅退款还是退货退款）
            //是否仅退款
            boolean isOnLyRefund;
            AfterSaleLogEnum firstLog;
            if (querySaleOrder.getAfterSaleType()==AfterSaleTypeEnum.ONLY_REFUND){
                firstLog=AfterSaleLogEnum.ONLY_REFUND_BUYER_START;
                isOnLyRefund=true;
            }
            else {
                firstLog=AfterSaleLogEnum.BUYER_START;
                isOnLyRefund=false;
            }

            //用户修改申请
            UpdateWrapper<OmAfterSaleLogPo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().and(a -> a.eq(OmAfterSaleLogPo::getAfterSaleOrderId, updateRefundDto.getId()).
                    eq(OmAfterSaleLogPo::getNode, firstLog)).
                    set(OmAfterSaleLogPo::getDescribes, updateRefundDto.getDescribe());
            afterSaleLogService.update(updateWrapper);

            //新增用户修改节点
            OmAfterSaleLogPo saveLog=new OmAfterSaleLogPo();
            saveLog.setNode(isOnLyRefund?AfterSaleLogEnum.ONLY_REFUND_BUYER_UPDATE:AfterSaleLogEnum.BUYER_UPDATE)
                    .setAfterSaleOrderId(updateRefundDto.getId()).setCreateBy(querySaleOrder.getCreateBy());
            afterSaleLogService.save(saveLog);

            RabbitAfterBo rabbitAfterBo = new RabbitAfterBo();
            rabbitAfterBo.setAfterSaleOrderId(updateRefundDto.getId());
            rabbitAfterBo.setAfterSaleStatusEnum(AfterSaleStatusEnum.NEED_STORE_DO);
            rabbitAfterBo.setUpdateTime(updateTime);
            sendMessage(rabbitAfterBo);

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long afterOrderId,boolean isAuto) {
        String userId="auto";
        if (!isAuto){
            UmUserPo appCurrUser = securityUtil.getAppCurrUser();
            userId=appCurrUser.getId()+"";
        }
        OmAfterSaleOrderPo querySaleOrder = mapper.selectById(afterOrderId);
        /*//如果不是超时处理的
        if (!isAuto) {
            if (querySaleOrder.getStatus() != AfterSaleStatusEnum.NEED_STORE_DO &&
                    querySaleOrder.getStatus() != AfterSaleStatusEnum.NEED_BUYER_RETURN) {
                throw new ServiceException(ResultCode.FAIL, "待商家处理和待买家退货状态才可以撤销售后！");
            }
        }*/

        if (querySaleOrder.getStatus() == AfterSaleStatusEnum.SUCCESS||querySaleOrder.getStatus() == AfterSaleStatusEnum.CLOSE ) {
            throw new ServiceException(ResultCode.FAIL, "退款关闭与退款成功的订单不可以进行撤销操作！");
        }

        //修改售后订单
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setStatus(AfterSaleStatusEnum.CLOSE).setId(afterOrderId);
        mapper.updateById(updateAfterOrder);

        //添加售后日志节点
        OmAfterSaleLogPo saveAfterLog = new OmAfterSaleLogPo();
        saveAfterLog.setCreateBy(userId).setAfterSaleOrderId(afterOrderId);
        //如果是仅付款
        if (querySaleOrder.getAfterSaleType()==AfterSaleTypeEnum.ONLY_REFUND){
            if (isAuto){
                saveAfterLog.setNode(AfterSaleLogEnum.ONLY_REFUND_BUYER_OVERTIME);
            }else {
                saveAfterLog.setNode(AfterSaleLogEnum.ONLY_REFUND_BUYER_CANCEL);
            }
        }
        //如果是退货退款
        else {
            if (isAuto){

                saveAfterLog.setNode(AfterSaleLogEnum.BUYER_OVERTIME);
            }
            else {

                saveAfterLog.setNode(AfterSaleLogEnum.BUYER_CANCEL);
            }
        }
        //售后订单关闭后进行返佣
        omOrderService.rakeBack(querySaleOrder.getGoodsTempId());
        afterSaleLogMapper.insert(saveAfterLog);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeCancel(Long afterOrderId) {
        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        OmAfterSaleOrderPo querySaleOrder = mapper.selectById(afterOrderId);
        //待买家处理状态才可以修改订单申请
        if (querySaleOrder.getStatus() != AfterSaleStatusEnum.NEED_BUYER_DO ) {
            throw new ServiceException(ResultCode.FAIL, "待买家处理状态才可以执行同意取消操作！");
        }

        //修改售后订单
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setId(afterOrderId).setStatus(AfterSaleStatusEnum.CLOSE);
        mapper.updateById(updateAfterOrder);

        //添加售后日志节点
        OmAfterSaleLogPo saveAfterLog = new OmAfterSaleLogPo();
        saveAfterLog.setAfterSaleOrderId(afterOrderId).setCreateBy(appCurrUser.getPhone());
        //如果是仅付款
        if (querySaleOrder.getAfterSaleType()==AfterSaleTypeEnum.ONLY_REFUND){
            saveAfterLog.setNode(AfterSaleLogEnum.ONLY_REFUND_BUYER_AGREE);
        }
        //如果是退货退款
        else {
            saveAfterLog.setNode(AfterSaleLogEnum.BUYER_AGREE_CANCEL);
        }
        //售后订单关闭后进行返佣
        omOrderService.rakeBack(querySaleOrder.getGoodsTempId());
        afterSaleLogMapper.insert(saveAfterLog);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(SendDto sendDto) {

        OmAfterSaleOrderPo querySaleOrder = mapper.selectById(sendDto.getId());
        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        if (querySaleOrder.getStatus() != AfterSaleStatusEnum.NEED_BUYER_RETURN ) {
            throw new ServiceException(ResultCode.FAIL, "待买家退货状态才可以执行我已寄出操作！");
        }

        //修改售后订单、并增加物流信息
        OmAfterSaleOrderPo updateAfterOrder = new OmAfterSaleOrderPo();
        updateAfterOrder.setId(sendDto.getId()).setUpdateTime(LocalDateTime.now()).setStatus(AfterSaleStatusEnum.NEED_STORE_REFUND)
        .setLogisticsCompany(sendDto.getLogisticsCompany()).setBillNo(sendDto.getBillNo()).setLogisticsCompany(sendDto.getLogisticsCompany());
        mapper.updateById(updateAfterOrder);

        //添加售后日志节点
        OmAfterSaleLogPo saveAfterLog = new OmAfterSaleLogPo();
        saveAfterLog.setAfterSaleOrderId(sendDto.getId()).setCreateBy(appCurrUser.getPhone());
        saveAfterLog.setNode(AfterSaleLogEnum.BUYER_RETURN_GOODS);
        afterSaleLogMapper.insert(saveAfterLog);




        //延时队列 买家发货后待商家退款==》退款成功
        RabbitAfterBo rabbitAfterBo=new RabbitAfterBo();
        rabbitAfterBo.setAfterSaleOrderId(updateAfterOrder.getId()).setAfterSaleStatusEnum(AfterSaleStatusEnum.NEED_STORE_REFUND)
                .setUpdateTime(updateAfterOrder.getUpdateTime());
        sendMessage(rabbitAfterBo);



    }


}
