package com.chauncy.order.logistics.impl;

import com.chauncy.common.constant.logistics.LogisticsContantsConfig;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.data.bo.app.logistics.LogisticsDataBo;
import com.chauncy.data.bo.app.logistics.LastResultBo;
import com.chauncy.data.bo.app.logistics.TaskResponseBo;
import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.mapper.order.OmOrderLogisticsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.vo.app.order.logistics.NoticeResponseVo;
import com.chauncy.order.logistics.IOmOrderLogisticsService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <p>
 * 物流信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmOrderLogisticsServiceImpl extends AbstractService<OmOrderLogisticsMapper, OmOrderLogisticsPo> implements IOmOrderLogisticsService {

    @Autowired
    private OmOrderLogisticsMapper mapper;

    @Resource
    private LogisticsContantsConfig logisticsProperties;

    @Resource
    private OmOrderMapper orderMapper;

    /**
     * 实时查询请求地址
     */
    private static final String SUBSCRIBE_URL = "http://poll.kuaidi100.com/poll";

    /**
     * 订单订阅物流信息
     *
     * @param taskRequestDto
     * @param orderId
     * @return
     */
    @Override
    public String subscribleLogistics(TaskRequestDto taskRequestDto, long orderId) {

        //回调Url
        String url = logisticsProperties.getCallbackUrl().concat(String.valueOf(orderId));
        taskRequestDto.getParameters().put("callbackurl", url);
        taskRequestDto.setKey(logisticsProperties.getKey());
        taskRequestDto.setFrom(null);
        taskRequestDto.setTo(null);
        Map<String, String> parameters = Maps.newHashMap();
        parameters.put("schema", "json");
        log.error(JSONUtils.toJSONString(parameters));
        parameters.put("param", JSONUtils.toJSONString(parameters));
        log.info("物流信息订阅开始,订单号:【{}】,入参为：【{}】", orderId, parameters);
        //发送 Post 请求
        return this.post(taskRequestDto, parameters, orderId);
    }

    /**
     * 快递100订阅推送数据
     * 快递结果回调接口
     *
     * @param param
     * @param orderId
     * @return
     */
    @Override
    public NoticeResponseVo updateExpressInfo(String param, String orderId) {

//        NoticeResponseVo resp = new NoticeResponseVo();
//        try {
//            LastResultBo nReq = JSONUtils.toBean(param, LastResultBo.class);
//            if (!"abort".equals(nReq.getStatus())) {
//                LogisticsDataBo result = nReq.getLastResult();
//                // 运单号
//                String logisticsNo = result.getNu();
//
//                // 快递公司编码
//                String expressCompanyCode = result.getCom();
//
//                // 快递单当前签收状态
//                String status = result.getState();
//
//                //	是否签收标记
//                String isCheck = result.getIscheck();
//
//                String data = JacksonHelper.toJSON(result.getData());
//
//                //根据物流单号获取原来的物流信息，首次记录是返回null
//                OrderLogistics oldOrderLogistics = orderLogisticsRepository.findAllByOrderId(Long.valueOf(orderId));
//
//                //根据物流单号删除物流信息，首次记录是返回0
//                int changeRows = orderLogisticsRepository.deleteByLogisticsNo(logisticsNo);
//
//                //根据物流单号和订单号查询订单
//                Order order = orderRepository.findByOrderIdAndOrderLogisticsNo(Long.valueOf(orderId), logisticsNo);
//
//
//                ExpressCompanyDict expressCompanyDict = expressCompanyDictRepository.findByExpressCompanyCode(expressCompanyCode);
//                // 快递公司名称
//                String expressCompanyName = expressCompanyDict.getExpressCompanyName();
//                OrderLogistics orderLogistics = new OrderLogistics();
//                orderLogistics.setOrderId(Long.valueOf(orderId));
//                orderLogistics.setLogisticsNo(logisticsNo);
//                orderLogistics.setStatus(status);
//                orderLogistics.setExpressCompanyCode(expressCompanyCode);
//                orderLogistics.setExpressCompanyName(expressCompanyName);
//                orderLogistics.setData(data);
//                orderLogistics.setIsCheck(isCheck);
//                orderLogisticsRepository.save(orderLogistics);
//                log.info("订单物流回调成功，订单物流信息为：【{}】", orderLogistics);
//                if (changeRows != 0) {
//                    log.info("订单物流回调，旧的物流信息为:【{}】,新的物流信息为:【{}】", oldOrderLogistics, orderLogistics);
//                }
//
//                resp.setResult(true);
//                resp.setReturnCode("200");
//                resp.setMessage("成功");
//            }
//
//        } catch (Exception e) {
//            resp.setResult(false);
//            resp.setReturnCode("500");
//            resp.setMessage("保存失败" + e);
//            log.error("订单物流回调失败，失败信息为:【{}】", resp);
//        }
//        return resp;
        return null;
    }


    private String post(TaskRequestDto taskRequestDto, Map<String, String> parameters, Long orderId) {

        StringBuffer response = new StringBuffer("");

        BufferedReader reader = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                if (builder.length() > 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                builder.append('=');
                builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] bytes = builder.toString().getBytes("UTF-8");

            URL url = new URL(SUBSCRIBE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(bytes);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            //获取结果后进行处理
            TaskResponseBo resp = JSONUtils.toBean(response.toString(), TaskResponseBo.class);
            if (resp.getResult() != true) {
                if (resp.getReturnCode().equals("600")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR1, "POLL:KEY已过期");
                } else if (resp.getReturnCode().equals("601")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR2, "您不是合法的订阅者（即授权Key出错）");
                } else if (resp.getReturnCode().equals("500")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR3, "服务器错误");
                } else if (resp.getReturnCode().equals("501")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR4, "重复订阅");
                } else if (resp.getReturnCode().equals("700")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR5, "订阅方的订阅数据存在错误（如不支持的快递公司、单号为空、单号超长等）或错误的回调地址）");
                } else if (resp.getReturnCode().equals("701")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR7, "拒绝订阅的快递公司 ");
                } else if (resp.getReturnCode().equals("702")) {
                    throw new ServiceException(ResultCode.LOGISTICS_ERROR6, "POLL:识别不到该单号对应的快递公司 ");
                }
            } else {
                //同步快递单号，修改订单状态为已发货
                OmOrderPo order = orderMapper.selectById(orderId);
                if (order != null) {
                    order.setStatus(OrderStatusEnum.NEED_RECEIVE_GOODS);
                    orderMapper.updateById(order);
                }
                log.info("订阅物流信息成功，订单号为:【{}】,物流单号为:【{}】", orderId, taskRequestDto.getNumber());
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
