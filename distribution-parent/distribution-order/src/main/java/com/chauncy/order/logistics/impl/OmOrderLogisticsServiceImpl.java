package com.chauncy.order.logistics.impl;

import com.chauncy.common.constant.logistics.LogisticsContantsConfig;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ListUtil;
import com.chauncy.data.bo.app.logistics.TaskResponseBo;
import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.mapper.order.OmOrderLogisticsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.order.logistics.IOmOrderLogisticsService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
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
import java.util.Optional;

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

    /**
     * 实时查询请求地址
     */
    private static final String SUBSCRIBE_URL = "http://poll.kuaidi100.com/poll";

    /**
     * 订单订阅物流信息
     *
     * @param orderId
     * @param taskRequestDto
     * @return
     */
    @Override
    public JsonViewData<String> subscribleLogistics(TaskRequestDto taskRequestDto, long orderId) {

        //回调Url
        String url = logisticsProperties.getCallbackUrl().concat(String.valueOf(orderId));
        taskRequestDto.getParameters().put("callbackurl",url);
        taskRequestDto.setKey(logisticsProperties.getKey());
        taskRequestDto.setFrom(null);
        taskRequestDto.setTo(null);
        Map<String,String> parameters = Maps.newHashMap();
        parameters.put("schema","json");
        log.error(JSONUtils.toJSONString(parameters));
        parameters.put("param", JSONUtils.toJSONString(parameters));
        log.info("物流信息订阅开始,订单号:【{}】,入参为：【{}】", orderId, parameters);
        //发送 Post 请求
        return this.post(parameters);




//        try {
//
//
//
//            String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
//            TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
//            if (resp.getResult() != true) {
//                if (resp.getReturnCode().equals("600") || resp.getReturnCode().equals("601") || resp.getReturnCode().equals("500")) {
//                    CodeMsg codeMsg = CodeMsg.SUBSCRIBLE_FAIL;
//                    codeMsg = codeMsg.addData(resp.getMessage());
//                    log.error("订阅物流信息失败，订单号为:【{}】,失败原因为：【{}】", orderId, resp.getMessage());
//                    return RtnResult.error(codeMsg);
//                } else {
//                    log.error("订阅物流信息失败，订单号为:【{}】,失败原因为：【{}】", orderId, resp.getMessage());
//                    return RtnResult.success(resp.getMessage());
//                }
//            } else {
//                //同步订单的快递单号，快递公司code，修改订单状态为已发货
//                Optional<Order> orderOptional = orderRepository.findById(orderId);
//                if (orderOptional.isPresent()) {
//                    Order order = orderOptional.get();
//                    order.setOrderLogisticsNo(req.getNumber());
//                    order.setExpressCompanyCode(req.getCompany());
//                    order.setOrderStatus(Constants.OrderConstants.TradeState.SENDED);
//                    orderRepository.save(order);
//                }
//                log.info("订阅物流信息成功，订单号为:【{}】,物流单号为:【{}】", orderId, req.getNumber());
//                return RtnResult.success("success");
//            }
//        } catch (Exception e) {
//            throw new BusinessException("", e);
//        }
    }

    private JsonViewData<String> post(Map<String, String> parameters) {

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
            TaskResponseBo resp = JSONUtils.toBean(response.toString(),TaskResponseBo.class);
            if(resp.getResult() != true) {
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
            }
            else{
                //同步快递单号，修改订单状态为已发货
//                Optional
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

        return new JsonViewData(response.toString());
    }
}
