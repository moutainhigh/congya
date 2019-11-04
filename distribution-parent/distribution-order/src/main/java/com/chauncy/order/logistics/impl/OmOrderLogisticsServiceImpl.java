package com.chauncy.order.logistics.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.constant.logistics.LogisticsContantsConfig;
import com.chauncy.common.enums.message.NoticeContentEnum;
import com.chauncy.common.enums.message.NoticeTitleEnum;
import com.chauncy.common.enums.message.NoticeTypeEnum;
import com.chauncy.common.enums.order.LogisticsStatusEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.MD5Utils;
import com.chauncy.common.util.rabbit.RabbitUtil;
import com.chauncy.data.bo.app.logistics.*;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.area.AreaShopLogisticsPo;
import com.chauncy.data.domain.po.message.interact.MmUserNoticePo;
import com.chauncy.data.domain.po.order.OmGoodsTempPo;
import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.OmOrderTempPo;
import com.chauncy.data.domain.po.user.UmAreaShippingPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.logistics.SynQueryLogisticsDto;
import com.chauncy.data.dto.app.order.logistics.SynQueryParamDto;
import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.mapper.area.AreaShopLogisticsMapper;
import com.chauncy.data.mapper.message.interact.MmUserNoticeMapper;
import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.mapper.order.OmOrderLogisticsMapper;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.mapper.sys.BasicSettingMapper;
import com.chauncy.data.mapper.user.UmAreaShippingMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.app.order.logistics.FindLogicDetailVo;
import com.chauncy.data.vo.app.order.logistics.LogisticsCodeNumVo;
import com.chauncy.data.vo.app.order.logistics.NoticeResponseVo;
import com.chauncy.data.vo.app.order.logistics.SynQueryLogisticsVo;
import com.chauncy.order.logistics.IOmOrderLogisticsService;
import com.chauncy.order.service.IOmOrderService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
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

    @Autowired
    private OmGoodsTempMapper omGoodsTempMapper;

    @Autowired
    private MmUserNoticeMapper mmUserNoticeMapper;

    @Resource
    private LogisticsContantsConfig logisticsProperties;

    @Autowired
    private OmOrderMapper orderMapper;

    @Autowired
    private AreaShopLogisticsMapper logisticsMapper;

    @Autowired
    private UmAreaShippingMapper areaShippingMapper;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private RabbitUtil rabbitUtil;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private IOmOrderService orderService;


//    /**
//     * 实时订阅查询请求地址
//     */
//    private static final String SUBSCRIBE_URL = "http://poll.kuaidi100.com/poll";
//
//    /**
//     * 实时查询请求地址
//     */
//    private static final String SYNQUERY_URL = "http://poll.kuaidi100.com/poll/query.do";

    /**
     * 订单订阅物流信息
     *
     * @param taskRequestDto
     * @return
     */
    @Override
    public TaskResponseBo subscribleLogistics(TaskRequestDto taskRequestDto) {

        Long orderId = taskRequestDto.getOrderId();
        //回调Url
        String url = logisticsProperties.getCallbackUrl().concat("/").concat(String.valueOf(orderId));
//        taskRequestDto.getParameters().put("callbackurl", url);
        LogisticsRequestParametersBo parametersBo = new LogisticsRequestParametersBo();
        parametersBo.setCallbackurl(url);
        parametersBo.setResultv2(logisticsProperties.getResultv2());
        parametersBo.setAutoCom(logisticsProperties.getAutoCom());
        parametersBo.setInterCom(logisticsProperties.getInterCom());
        taskRequestDto.setParameters(parametersBo);
        taskRequestDto.setKey(logisticsProperties.getKey());
//        taskRequestDto.setFrom(null);
//        taskRequestDto.setTo(null);
        Map<String, String> p = Maps.newHashMap();
        p.put("schema", "json");
        p.put("param", String.valueOf(JSONObject.fromObject(taskRequestDto)));
        log.info("物流信息订阅开始,订单号:【{}】,入参为：【{}】", orderId, p);
        System.out.println(JSONObject.fromObject(taskRequestDto));
        //发送 Post 请求
        return this.post(taskRequestDto, p, orderId);
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

        NoticeResponseVo resp = new NoticeResponseVo();
        try {
            NoticeRequestParamBo nReq = JSONUtils.toBean(param, NoticeRequestParamBo.class);
            if (!"abort".equals(nReq.getStatus())) {
                LastResultBo result = nReq.getLastResult();
                // 运单号
                String logisticsNo = result.getNu();

                // 快递公司编码
                String expressCompanyCode = result.getCom();

                // 快递单当前签收状态
                String status = result.getState();

                //	是否签收标记
                String isCheck = result.getIscheck();
                if("1".equals(isCheck)) {
                    //邮件签收  发送消息给到APP内消息列表
                    saveSignedNotice(orderId);
                }
                log.error(isCheck);

                String data = JSONUtils.toJSONString(result.getData());

                //根据物流单号获取原来的物流信息，首次记录是返回null
                OmOrderLogisticsPo oldOrderLogistics = mapper.selectOne(new QueryWrapper<OmOrderLogisticsPo>().eq("order_id", orderId));

                //根据物流单号删除物流信息，首次记录是返回0
                int changeRows = mapper.delete(new QueryWrapper<OmOrderLogisticsPo>().eq("logistics_no", logisticsNo));

                //根据物流单号和订单号查询订单
//                OmOrderPo order = orderMapper.findByOrderIdAndOrderLogisticsNo(Long.valueOf(orderId), logisticsNo);


                AreaShopLogisticsPo logisticsPo = logisticsMapper.selectOne(new QueryWrapper<AreaShopLogisticsPo>().eq("logi_code", expressCompanyCode));
                // 快递公司名称
                String expressCompanyName = logisticsPo.getLogiName();
                OmOrderLogisticsPo orderLogistics = new OmOrderLogisticsPo();
                orderLogistics.setOrderId(Long.valueOf(orderId));
                orderLogistics.setLogisticsNo(logisticsNo);
                orderLogistics.setStatus(status);
                orderLogistics.setLogiCode(expressCompanyCode);
                orderLogistics.setLogiName(expressCompanyName);
                orderLogistics.setData(data);
                orderLogistics.setIsCheck(isCheck);
                mapper.insert(orderLogistics);
                log.info("订单物流回调成功，订单物流信息为：【{}】", orderLogistics);
                if (changeRows != 0) {
                    log.info("订单物流回调，旧的物流信息为:【{}】,新的物流信息为:【{}】", oldOrderLogistics, orderLogistics);
                }

                resp.setResult(true);
                resp.setReturnCode("200");
                resp.setMessage("成功");
            }

        } catch (Exception e) {
            resp.setResult(false);
            resp.setReturnCode("500");
            resp.setMessage("保存失败" + e);
            log.error("订单物流回调失败，失败信息为:【{}】", resp);
        }
        return resp;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/22 11:45
     * @Description 订单签收 给用户发交易物流通知消息
     *
     * @Update yeJH
     *
     * @param  orderId
     * @return void
     **/
    public void saveSignedNotice(String orderId) {
        OmOrderPo omOrderPo = orderMapper.selectById(orderId);
        if(null != omOrderPo) {
            //找到订单中的一个商品
            QueryWrapper<OmGoodsTempPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(OmGoodsTempPo::getOrderId, orderId);
            queryWrapper.last(" LIMIT 1 ");
            OmGoodsTempPo omGoodsTempPo = omGoodsTempMapper.selectOne(queryWrapper);
            MmUserNoticePo mmUserNoticePo = new MmUserNoticePo();
            mmUserNoticePo.setUserId(omOrderPo.getUmUserId())
                    .setNoticeType(NoticeTypeEnum.EXPRESS_LOGISTICS.getId())
                    .setTitle(NoticeTitleEnum.ALREADY_SIGNED.getName())
                    .setContent(MessageFormat.format(NoticeContentEnum.ALREADY_SIGNED.getName(),
                            omGoodsTempPo.getName()));
            mmUserNoticeMapper.insert(mmUserNoticePo);
        }
    }

    /**
     * 根据订单号查询物流信息
     *
     * @param orderId
     * @return
     */
    @Override
    public FindLogicDetailVo getLogistics(long orderId) {
//        根据物流单号获取物流信息
        OmOrderLogisticsPo orderLogistics = mapper.selectOne(new QueryWrapper<OmOrderLogisticsPo>().eq("order_id", orderId));
        OmOrderPo order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "该订单不存在");
        }
        if (orderLogistics == null) {
            throw new ServiceException(ResultCode.NO_EXISTS, "该物流单号不存在");
        }
        //解析物流信息
//        JSONArray obj = JSONUtils.toJSONArray(orderLogistics.getData());
        // int index = 1;
        // for (Object o : obj) {
        //    Map<String, Object> m = (Map) o;
        //    m.put("index", index++);
        // }
//        Map<String, Object> map = new HashMap<>();
//        map.put("expressCompanyCode", orderLogistics.getLogiCode());
//        map.put("expressCompanyName", orderLogistics.getLogiName());
//        map.put("logisticsNo", orderLogistics.getLogisticsNo());
//        map.put("address", "详细地址");//待修改
//        map.put("logisticsData", obj);
//        map.put("statusCode", orderLogistics.getStatus());
//        map.put("statusName", LogisticsStatusEnum.fromName(orderLogistics.getStatus()));
//        map.put("isCheck", orderLogistics.getIsCheck());
//        map.put("receiveName", "收货人");
//        map.put("receiveTel", "手机号");

        //获取用户详细收货地址
        Long areaShippingId = order.getAreaShippingId();
        UmAreaShippingPo areaShippingPo = areaShippingMapper.selectById(areaShippingId);
        String areaName = areaShippingPo.getAreaName().replace(",", "");
        String detail = areaShippingPo.getDetailedAddress();
        String address = areaName.concat(detail);
        UmUserPo userPo = userMapper.selectById(areaShippingPo.getUmUserId());

        List<LogisticsDataBo> logisticsDataBos = JSONUtils.toJSONArray(orderLogistics.getData());
        FindLogicDetailVo findLogicDetailVo = new FindLogicDetailVo();
        log.error(orderLogistics.getStatus());
        findLogicDetailVo.setLogisticsData(logisticsDataBos)
                .setAddress(address)
                .setExpressCompanyCode(orderLogistics.getLogiCode())
                .setExpressCompanyName(orderLogistics.getLogiName())
                .setIsCheck(orderLogistics.getIsCheck())
                .setLogisticsNo(orderLogistics.getLogisticsNo())
                .setReceiveName(userPo.getTrueName())
                .setReceiveTel(userPo.getPhone())
                .setStatusCode(orderLogistics.getStatus())
                .setStatusName(LogisticsStatusEnum.getLogisticsStatusEnumById(orderLogistics.getStatus()).getName());

        return findLogicDetailVo;
    }


    /**
     * 实时查询物流信息
     *
     * @param synQueryLogisticsDto
     * @return
     */
    @Override
    public SynQueryLogisticsVo synQueryLogistics(SynQueryLogisticsDto synQueryLogisticsDto) {

        SynQueryParamDto param = synQueryLogisticsDto.getParam();
        param.setResultv2(logisticsProperties.getResultv2());

//        JSONObject jsonObject = JSONObject.fromObject(param);

        //fastjson序列化空属性
        String jsonObject = JSON.toJSONString(param, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        System.out.println(jsonObject);

        String sign = MD5Utils.encode(jsonObject + logisticsProperties.getKey() + logisticsProperties.getCustomer());

//        param.setKey(logisticsProperties.getKey());

        Map<String, String> params = Maps.newHashMap();
        params.put("customer", logisticsProperties.getCustomer());
        params.put("sign", sign);
        params.put("param", JSON.toJSONString(param, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));


        System.out.println(params);

        String result = this.postSynQuery(params);

        SynQueryLogisticsVo synQueryLogisticsVo = JSONUtils.toBean(result, SynQueryLogisticsVo.class);
        synQueryLogisticsVo.setData(JSONUtils.toJSONArray(synQueryLogisticsVo.getData()));

        return synQueryLogisticsVo;
    }

    /**
     * 根据客户提交的快递单号，判断该单号可能所属的快递公司编码
     *
     * @param num
     * @return
     */
    @Override
    public LogisticsCodeNumVo autoFind(String num) {

        String key = logisticsProperties.getKey();
        StringBuffer response = new StringBuffer("");

        List<AutoFindLogsticsBo> autoFindLogsticsBos = Lists.newArrayList();
        List<AutoFindLogsticsBo> autoFindLogsticsBos2 = Lists.newArrayList();
        LogisticsCodeNumVo logisticsCodeNumVo = new LogisticsCodeNumVo();

        BufferedReader reader = null;
        try {
            StringBuffer con = new StringBuffer(logisticsProperties.getAutoUrl());

            URL url = new URL((con.append("?num=").append(num).append("&key=").append(key)).toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            autoFindLogsticsBos = JSONUtils.toList(response.toString(), AutoFindLogsticsBo.class);

//            autoFindLogsticsBos2 = JSONUtils.toJSONArray(response.toString());

            System.out.println(autoFindLogsticsBos.get(0).getComCode());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.FAIL, "实时查询物流连接异常,请稍会再试！");
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!ListUtil.isListNullAndEmpty(autoFindLogsticsBos)) {
            AutoFindLogsticsBo autoFindLogsticsBo = autoFindLogsticsBos.get(0);
            //公司编码
            logisticsCodeNumVo.setValue(autoFindLogsticsBo.getComCode());
            String logicName = logisticsMapper.selectOne(new QueryWrapper<AreaShopLogisticsPo>().eq("logi_code", autoFindLogsticsBo.getComCode())).getLogiName();
            //公司名称
            logisticsCodeNumVo.setLabel(logicName.concat("(由快递100猜测,本结果仅供参考)"));
        }

        return logisticsCodeNumVo;
    }

    /**
     * 实时查询快递单号
     */
    public String postSynQuery(Map<String, String> params) {

        StringBuffer response = new StringBuffer("");

        BufferedReader reader = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (builder.length() > 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                builder.append('=');
                builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] bytes = builder.toString().getBytes("UTF-8");

            URL url = new URL(logisticsProperties.getSynqueryUrl());
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.FAIL, "实时查询物流连接异常,请稍会再试！");
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> map = JSONUtils.toHashMap(response.toString());
        if (map.get("result") != null) {
            TaskResponseBo taskResponseBo = JSONUtils.toBean(response.toString(), TaskResponseBo.class);
            if (!taskResponseBo.getResult()) {
                throw new ServiceException(ResultCode.FAIL, taskResponseBo.getMessage());
            }
        }

        return response.toString();
    }


    /**
     * 发送post请求订阅物流信息
     *
     * @param taskRequestDto
     * @param parameters
     * @param orderId
     * @return
     */
    private TaskResponseBo post(TaskRequestDto taskRequestDto, Map<String, String> parameters, Long orderId) {

        StringBuffer response = new StringBuffer("");

        BufferedReader reader = null;
        TaskResponseBo resp = new TaskResponseBo();
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

            URL url = new URL(logisticsProperties.getSubscribeUrl());
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.LOGISTICS_ERROR3, "快递100订单订阅物流信息连接错误");
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取结果后进行处理
        resp = JSONUtils.toBean(response.toString(), TaskResponseBo.class);
        if (resp.getResult() != true) {
            if (resp.getReturnCode().equals("600")) {
                throw new ServiceException(ResultCode.FAIL, "POLL:KEY已过期");
            } else if (resp.getReturnCode().equals("601")) {
                throw new ServiceException(ResultCode.FAIL, "您不是合法的订阅者（即授权Key出错）");
            } else if (resp.getReturnCode().equals("500")) {
                throw new ServiceException(ResultCode.FAIL, "服务器错误");
            } else if (resp.getReturnCode().equals("501")) {
                throw new ServiceException(ResultCode.FAIL, "重复订阅,运单号重复");
            } else if (resp.getReturnCode().equals("700")) {
                throw new ServiceException(ResultCode.FAIL, "订阅方的订阅数据存在错误（如不支持的快递公司、单号为空、单号超长等）或错误的回调地址）");
            } else if (resp.getReturnCode().equals("701")) {
                throw new ServiceException(ResultCode.FAIL, "拒绝订阅的快递公司 ");
            } else if (resp.getReturnCode().equals("702")) {
                throw new ServiceException(ResultCode.FAIL, "POLL:识别不到该单号对应的快递公司 ");
            }
        } else {
            orderService.storeSend(orderId);

            log.info("订阅物流信息成功，订单号为:【{}】,物流单号为:【{}】", orderId, taskRequestDto.getNumber());
            return resp;
        }
        System.out.println(response.toString());
        return resp;
    }



}
