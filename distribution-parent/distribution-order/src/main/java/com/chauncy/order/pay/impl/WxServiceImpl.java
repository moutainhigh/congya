package com.chauncy.order.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.activity.gift.IAmGiftOrderService;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import com.chauncy.common.enums.log.PaymentWayEnum;
import com.chauncy.common.enums.order.OrderPayTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.BigDecimalUtil;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.common.util.wechat.WXConfigUtil;
import com.chauncy.common.util.wechat.WxMD5Util;
import com.chauncy.data.bo.manage.order.OrderRefundInfoBo;
import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.app.order.pay.PayParamDto;
import com.chauncy.data.mapper.activity.gift.AmGiftOrderMapper;
import com.chauncy.data.mapper.afterSale.OmAfterSaleOrderMapper;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.data.vo.app.order.pay.UnifiedOrderVo;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
import com.chauncy.order.pay.IWxService;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.security.util.SecurityUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/7/5 16:23
 */

@Service
public class WxServiceImpl implements IWxService {

    private static final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

    //支付结果通知地址
    public static final String PAY_NOTIFY_URL = "http://112.126.96.226/distribution/app/wxPay/wxPay/notify";
    //退款结果通知地址
    public static final String REFUND_NOTIFY_URL = "http://112.126.96.226/distribution/app/wxPay/wxPay/notify";
    //交易类型
    public static final String TRADE_TYPE_APP = "APP";


    @Autowired
    private IPayOrderMapper payOrderMapper;

    @Autowired
    private AmGiftOrderMapper amGiftOrderMapper;

    @Autowired
    private IAmGiftOrderService amGiftOrderService;

    @Autowired
    private OmAfterSaleOrderMapper omAfterSaleOrderMapper;

    @Autowired
    private IOmOrderService omOrderService;

    @Autowired
    private WXConfigUtil wxConfigUtil;

    @Autowired
    private WxMD5Util wxMD5Util;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IOmAfterSaleOrderService omAfterSaleOrderService;

    /**
     * 调用官方SDK统一下单 获取前端调起支付接口的参数
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnifiedOrderVo unifiedOrder(PayParamDto payParamDto) throws Exception {
        UnifiedOrderVo unifiedOrderVo = new UnifiedOrderVo();
        OrderPayTypeEnum orderPayTypeEnum = OrderPayTypeEnum.getById(payParamDto.getOrderPayType());
        switch (orderPayTypeEnum) {
            case GOODS_PAYMENT:
                unifiedOrderVo = goodsPayment(payParamDto.getIpAddr(), payParamDto.getPayOrderId());
                break;
            case GIFT_RECHARGE:
                unifiedOrderVo = gifiRecharge(payParamDto.getIpAddr(), payParamDto.getPayOrderId());
                break;
        }
        return unifiedOrderVo;
    }

    /**
     * 商品支付
     * @param ipAddr
     * @param payOrderId
     * @return
     * @throws Exception
     */
    private UnifiedOrderVo goodsPayment(String ipAddr, Long payOrderId) throws Exception {
        //商品支付
        QueryWrapper<PayOrderPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayOrderPo::getId, payOrderId)
                .eq(PayOrderPo::getEnabled, true);
        PayOrderPo payOrderPo = payOrderMapper.selectOne(queryWrapper);
        if (null == payOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }
        WxMD5Util md5Util = wxMD5Util;
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);
        //app调起支付接口所需参数
        UnifiedOrderVo unifiedOrderVo = new UnifiedOrderVo();
        //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
        unifiedOrderVo.setAppId(config.getAppID());
        unifiedOrderVo.setPartnerId(config.getMchID());
        unifiedOrderVo.setPackageStr("Sign=WXPay");
        unifiedOrderVo.setNonceStr(WXPayUtil.generateNonceStr());
        //时间戳 单位为秒
        unifiedOrderVo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));

        if(Strings.isNotBlank(payOrderPo.getPrePayId()) && payOrderPo.getPayTypeCode().equals(PaymentWayEnum.WECHAT.getName())) {
            //预支付交易会话标识 说明预支付单已生成
            unifiedOrderVo.setPrepayId(payOrderPo.getPrePayId());
            //returnMap.put("sign", response.get("sign"));
            //returnMap.put("trade_type", response.get("trade_type"));
            //调起支付参数重新签名  不要使用请求预支付订单时返回的签名
            Map<String, String> returnMap = BeanUtils.describe(unifiedOrderVo);
            unifiedOrderVo.setSign(md5Util.getSign(returnMap));
            return unifiedOrderVo;
        }

        //总金额  单位分
        Integer totalFee = BigDecimalUtil.safeMultiply(payOrderPo.getTotalRealPayMoney(), new BigDecimal(100)).intValue();
        //统一下单接口微信签名参数
        Map<String, String> data = getSignMap(wxConfigUtil, wxMD5Util, ipAddr, payOrderId, totalFee, OrderPayTypeEnum.GOODS_PAYMENT);

        //支付单信息
        payOrderPo.setPayTypeCode(PaymentWayEnum.WECHAT.getName());
        payOrderPo.setStartTime(LocalDateTime.now());
        payOrderPo.setUserIp(ipAddr);
        payOrderPo.setNotifyUrl(PAY_NOTIFY_URL);
        //payOrderPo.setExtra("");
        payOrderPo.setSubject(config.getBody());
        //payOrderPo.setDetail("");
        payOrderPo.setMerchantId(config.getMchID());
        payOrderPo.setTradeType(TRADE_TYPE_APP);

        //使用微信统一下单API请求预付订单
        Map<String, String> response = wxpay.unifiedOrder(data);
        //获取返回码
        String returnCode = response.get("return_code");
        //若返回码return_code为SUCCESS，则会返回一个result_code,再对该result_code进行判断
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                //resultCode 为SUCCESS，才会返回prepay_id和trade_type
                unifiedOrderVo.setPrepayId(response.get("prepay_id"));
                //调起支付参数重新签名  不要使用请求预支付订单时返回的签名
                Map<String, String> returnMap = BeanUtils.describe(unifiedOrderVo);
                unifiedOrderVo.setSign(md5Util.getSign(returnMap));
                //更新支付订单
                payOrderPo.setPrePayId(response.get("prepay_id"));
                payOrderMapper.updateById(payOrderPo);
                return unifiedOrderVo;
            } else {
                //调用微信统一下单接口返回失败
                String errCodeDes = response.get("err_code_des");
                //更新支付订单
                payOrderPo.setErrorCode(response.get("err_code"));
                payOrderPo.setErrorMsg(errCodeDes);
                payOrderMapper.updateById(payOrderPo);
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信统一下单接口返回失败
            String returnMsg = response.get("return_msg");
            //更新支付订单
            payOrderPo.setErrorCode(response.get("return_code"));
            payOrderPo.setErrorMsg(returnMsg);
            payOrderMapper.updateById(payOrderPo);
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }

    }

    /**
     * 礼包充值
      * @param ipAddr
     * @param payOrderId
     * @return
     * @throws Exception
     */
    private UnifiedOrderVo gifiRecharge(String ipAddr, Long payOrderId) throws Exception {

        //礼包充值
        AmGiftOrderPo amGiftOrderPo = amGiftOrderMapper.selectById(payOrderId);
        if(null == amGiftOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }

        WxMD5Util md5Util = wxMD5Util;
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);
        //app调起支付接口所需参数
        UnifiedOrderVo unifiedOrderVo = new UnifiedOrderVo();
        //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
        unifiedOrderVo.setAppId(config.getAppID());
        unifiedOrderVo.setPartnerId(config.getMchID());
        unifiedOrderVo.setPackageStr("Sign=WXPay");
        unifiedOrderVo.setNonceStr(WXPayUtil.generateNonceStr());
        //时间戳 单位为秒
        unifiedOrderVo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));

        //总金额  单位分
        Integer totalFee = BigDecimalUtil.safeMultiply(amGiftOrderPo.getPurchasePrice(), new BigDecimal(100)).intValue();
        //统一下单接口微信签名参数
        Map<String, String> data = getSignMap(wxConfigUtil, wxMD5Util, ipAddr, payOrderId, totalFee, OrderPayTypeEnum.GIFT_RECHARGE);

        //使用微信统一下单API请求预付订单
        Map<String, String> response = wxpay.unifiedOrder(data);
        //获取返回码
        String returnCode = response.get("return_code");
        //若返回码return_code为SUCCESS，则会返回一个result_code,再对该result_code进行判断
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                //resultCode 为SUCCESS，才会返回prepay_id和trade_type
                unifiedOrderVo.setPrepayId(response.get("prepay_id"));
                //调起支付参数重新签名  不要使用请求预支付订单时返回的签名
                Map<String, String> returnMap = BeanUtils.describe(unifiedOrderVo);
                unifiedOrderVo.setSign(md5Util.getSign(returnMap));
                return unifiedOrderVo;
            } else {
                //调用微信统一下单接口返回失败
                String errCodeDes = response.get("err_code_des");
                //更新支付订单
                amGiftOrderPo.setErrorCode(response.get("err_code"));
                amGiftOrderPo.setErrorMsg(errCodeDes);
                amGiftOrderMapper.updateById(amGiftOrderPo);
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信统一下单接口返回失败
            String returnMsg = response.get("return_msg");
            //更新支付订单
            amGiftOrderPo.setErrorCode(response.get("return_code"));
            amGiftOrderPo.setErrorMsg(returnMsg);
            amGiftOrderMapper.updateById(amGiftOrderPo);
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }

    }

    /**
     * 统一下单接口微信签名参数
     * @param config
     * @param md5Util
     * @param ipAddr
     * @param payOrderId
     * @param totalFee
     * @param orderPayTypeEnum
     * @return
     * @throws Exception
     */
    private Map<String, String> getSignMap(WXConfigUtil config, WxMD5Util md5Util, String ipAddr,
                                           Long payOrderId, Integer totalFee, OrderPayTypeEnum orderPayTypeEnum) throws Exception {
        Map<String, String> data = new HashMap<>();
        //应用id APPID
        data.put("appid", config.getAppID());
        //商户号
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述  APP——需传入应用市场上的APP名字-实际商品名称
        data.put("body", config.getBody());
        //附加数据  订单支付类型（商品支付/礼包充值）
        data.put("attach", orderPayTypeEnum.name());
        //商户订单号
        data.put("out_trade_no", String.valueOf(payOrderId));
        data.put("total_fee", String.valueOf(totalFee));
        //调用微信支付API的机器IP
        data.put("spbill_create_ip", ipAddr);
        //异步通知地址
        data.put("notify_url", PAY_NOTIFY_URL);
        //交易类型
        data.put("trade_type", TRADE_TYPE_APP);
        //统一下单签名
        String orderSign = md5Util.getSign(data);
        data.put("sign", orderSign);
        return data;
    }

    /**
     * 微信支付结果通知
     * @param notifyData 异步通知后的XML数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String payBack(String notifyData) throws Exception{
        String failXmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        String successXmlBack = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
        WXConfigUtil config = null;
        try {
            config = new WXConfigUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
        WXPay wxpay = new WXPay(config);
        //异步通知返回微信的参数 xml格式
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            //异步通知微信返回的参数 调用官方SDK转换成map类型数据
            notifyMap = WXPayUtil.xmlToMap(notifyData);
            String outTradeNo = notifyMap.get("out_trade_no");
            String attach = notifyMap.get("attach");
            OrderPayTypeEnum orderPayTypeEnum = OrderPayTypeEnum.valueOf(attach);
            //验证签名是否有效，有效则进一步处理
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                //状态
                String returnCode = notifyMap.get("return_code");
                if (returnCode.equals("SUCCESS")) {
                    if(orderPayTypeEnum.equals(OrderPayTypeEnum.GOODS_PAYMENT)) {
                        //商品支付订单
                        PayOrderPo payOrderPo =  payOrderMapper.selectById(outTradeNo);
                        if (null != payOrderPo) {
                            if(payOrderPo.getStatus().equals(PayOrderStatusEnum.NEED_PAY.getId())) {
                                //回调支付金额
                                Integer cashFee = Integer.parseInt(notifyMap.get("cash_fee"));
                                //支付订单计算应支付总金额
                                Integer totalMoney = BigDecimalUtil.safeMultiply(payOrderPo.getTotalRealPayMoney(), new BigDecimal(100)).intValue();
                                if(cashFee.equals(totalMoney)) {
                                    //业务数据持久化
                                    omOrderService.wxPayNotify(payOrderPo, notifyMap);
                                } else {
                                    logger.info("微信手机支付回调成功，订单号:{}，但是金额不对应，回调支付金额{}，支付订单计算应支付总金额", outTradeNo, cashFee, totalMoney);
                                }
                            } else if(payOrderPo.getStatus().equals(PayOrderStatusEnum.ALREADY_PAY.getId()) ||
                                payOrderPo.getStatus().equals(PayOrderStatusEnum.ALREADY_CANCEL.getId())) {
                            //订单状态为已支付或者已取消
                            // 注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                            }
                            logger.info("微信手机支付回调成功订单号:{}", outTradeNo);
                            xmlBack = successXmlBack;
                        } else {
                            logger.info("微信手机支付回调失败订单号:{}", outTradeNo);
                            xmlBack = failXmlBack;
                        }
                    } else if(orderPayTypeEnum.equals(OrderPayTypeEnum.GIFT_RECHARGE)) {
                        //礼包充值订单
                        AmGiftOrderPo amGiftOrderPo = amGiftOrderMapper.selectById(outTradeNo);
                        if (null != amGiftOrderPo) {
                            if(amGiftOrderPo.getPayStatus().equals(PayOrderStatusEnum.NEED_PAY.getId())) {
                                //回调支付金额
                                Integer cashFee = Integer.parseInt(notifyMap.get("cash_fee"));
                                //支付订单计算应支付总金额
                                Integer totalMoney = BigDecimalUtil.safeMultiply(amGiftOrderPo.getPurchasePrice(), new BigDecimal(100)).intValue();
                                if(cashFee.equals(totalMoney)) {
                                    //业务数据持久化
                                    amGiftOrderService.wxPayNotify(amGiftOrderPo, notifyMap);
                                } else {
                                    logger.info("微信手机支付回调成功，订单号:{}，但是金额不对应，回调支付金额{}，支付订单计算应支付总金额", outTradeNo, cashFee, totalMoney);
                                }
                            } else if(amGiftOrderPo.getPayStatus().equals(PayOrderStatusEnum.ALREADY_PAY.getId()) ||
                                    amGiftOrderPo.getPayStatus().equals(PayOrderStatusEnum.ALREADY_CANCEL.getId())) {
                                //订单状态为已支付或者已取消
                                // 注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                            }
                            logger.info("微信手机支付回调成功订单号:{}", outTradeNo);
                            xmlBack = successXmlBack;
                        } else {
                            logger.info("微信手机支付回调失败订单号:{}", outTradeNo);
                            xmlBack = failXmlBack;
                        }
                    }
                }
                return xmlBack;
            } else {
                //签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
                logger.error("手机支付回调通知签名错误,返回参数：" + notifyMap);
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            logger.error("手机支付回调通知失败", e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }



    /**
     *   微信查询订单接口  订单未操作的做业务更新
     *   官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_2&index=4
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderQuery(Long payOrderId) throws Exception {
        PayOrderPo payOrderPo = payOrderMapper.selectById(payOrderId);
        if(null == payOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }

        WxMD5Util md5Util = new WxMD5Util();
        WXConfigUtil config = new WXConfigUtil();
        WXPay wxpay = new WXPay(config);
        Map<String, String> returnMap = new HashMap<>();

        //查询订单接口微信签名参数
        Map<String, String> data = new HashMap<>();
        //应用id APPID
        data.put("appid", config.getAppID());
        //商户号
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        //微信订单号(优先选择)  商户订单号
        if(Strings.isNotBlank(payOrderPo.getPayOrderNo())) {
            data.put("transaction_id", payOrderPo.getPayOrderNo());
        } else {
            data.put("out_trade_no", String.valueOf(payOrderPo.getId()));
        }
        //签名
        String sign = md5Util.getSign(data);
        data.put("sign", sign);

        //使用微信查询订单API请求预付订单
        Map<String, String> response = wxpay.orderQuery(data);
        //获取返回码
        String returnCode = response.get("return_code");
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                //resultCode 为SUCCESS，才会返回prepay_id和trade_type
                if("SUCCESS".equals(response.get("trade_state")) && payOrderPo.getStatus().equals(PayOrderStatusEnum.NEED_PAY.getId())) {
                    //回调支付金额
                    Integer cashFee = Integer.parseInt(response.get("cash_fee"));
                    //支付订单计算应支付总金额
                    Integer totalMoney = BigDecimalUtil.safeMultiply(payOrderPo.getTotalRealPayMoney(), new BigDecimal(100)).intValue();
                    if(cashFee.equals(totalMoney)) {
                        //业务数据持久化
                        omOrderService.wxPayNotify(payOrderPo, response);
                    } else {
                        logger.info("微信查询订单成功，订单号:{}，但是金额不对应，回调支付金额{}，支付订单计算应支付总金额", payOrderPo, cashFee, totalMoney);
                    }
                }
            } else {
                //调用微信统一下单接口返回失败
                String errCodeDes = response.get("err_code_des");
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信查询订单接口返回失败
            String returnMsg = response.get("return_msg");
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }

    }


    /**
     * 调用官方SDK申请退款
     * @param afterSaleOrderId   售后订单id
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(Long afterSaleOrderId,boolean isAuto)  {

        OmAfterSaleOrderPo omAfterSaleOrderPo = omAfterSaleOrderMapper.selectById(afterSaleOrderId);
        if(null == omAfterSaleOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "退款订单记录不存在");
        }
        //如果不是延时任务，需要判断登录用户是否有权限
        if (!isAuto) {
            //获取当前店铺用户
            SysUserPo sysUserPo = securityUtil.getCurrUser();
            if (null == sysUserPo.getStoreId() || !sysUserPo.getStoreId().equals(omAfterSaleOrderPo.getStoreId())) {
                //当前登录用户跟操作不匹配
                throw new ServiceException(ResultCode.FAIL, "当前登录用户跟操作不匹配");
            }
        }
        //售后订单仅退款且状态为待商家处理  或者  售后订单退货退款且状态为待商家退款 才能退款
        if(!((omAfterSaleOrderPo.getAfterSaleType() == AfterSaleTypeEnum.ONLY_REFUND &&
                omAfterSaleOrderPo.getStatus() == AfterSaleStatusEnum.NEED_STORE_DO) ||
                (omAfterSaleOrderPo.getAfterSaleType() == AfterSaleTypeEnum.RETURN_GOODS &&
                        omAfterSaleOrderPo.getStatus() == AfterSaleStatusEnum.NEED_STORE_REFUND))) {
            throw new ServiceException(ResultCode.FAIL, "退款操作跟当前售后订单状态不符");
        }
        WxMD5Util md5Util = wxMD5Util;
        WXConfigUtil config = wxConfigUtil;
        WXPay wxpay = new WXPay(config);

        //申请退款接口微信签名参数
        Map<String, String> data = new HashMap<>();
        //应用id APPID
        data.put("appid", config.getAppID());
        //商户号
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        OrderRefundInfoBo orderRefundInfoBo = payOrderMapper.findOrderRefundInfo(afterSaleOrderId);
        if(null != orderRefundInfoBo.getPayOrderNo()) {
            //微信订单号
            data.put("transaction_id", orderRefundInfoBo.getPayOrderNo());
        } else {
            //商户订单号
            data.put("out_trade_no", String.valueOf(orderRefundInfoBo.getPayOrderId()));
        }
        //商户退款单号
        data.put("out_refund_no", String.valueOf(omAfterSaleOrderPo.getId()));
        //退款金额
        String refundFee = Integer.toString(
                BigDecimalUtil.safeMultiply(omAfterSaleOrderPo.getRefundMoney(), new BigDecimal(100)).intValue());
        data.put("refund_fee", refundFee);
        //订单金额
        String totalFee = Integer.toString(
                BigDecimalUtil.safeMultiply(orderRefundInfoBo.getTotalRealPayMoney(), new BigDecimal(100)).intValue());
        data.put("total_fee", totalFee);
        //退款结果通知url
        data.put("notify_url", REFUND_NOTIFY_URL);
        //申请退款签名
        String orderSign = null;
        try {
            orderSign = md5Util.getSign(data);
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.FAIL,"微信退款出错！");
        }
        data.put("sign", orderSign);

        //使用微信申请退款API请求预付订单
        Map<String, String> response = null;
        try {
            response = wxpay.refund(data);
        } catch (Exception e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.FAIL,"微信退款出错！");
        }
        //获取返回码
        String returnCode = response.get("return_code");
        //若返回码return_code为SUCCESS，则会返回一个result_code,再对该result_code进行判断
        if (returnCode.equals("SUCCESS")) {
            String resultCode = response.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                //resultCode 为SUCCESS
                //更新售后订单订单
                UpdateWrapper<OmAfterSaleOrderPo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().eq(OmAfterSaleOrderPo::getId, omAfterSaleOrderPo.getId())
                        .set(OmAfterSaleOrderPo::getRefundId, response.get("refund_id"));
                omAfterSaleOrderService.update(updateWrapper);
                omAfterSaleOrderService.permitRefund(afterSaleOrderId,isAuto);
            } else {
                //调用微信申请退款接口返回失败
                String errCodeDes = response.get("err_code_des");
                throw new ServiceException(ResultCode.FAIL, errCodeDes);
            }
        } else {
            //调用微信微信申请接口返回失败
            String returnMsg = response.get("return_msg");
            throw new ServiceException(ResultCode.FAIL, returnMsg);
        }

    }



}

