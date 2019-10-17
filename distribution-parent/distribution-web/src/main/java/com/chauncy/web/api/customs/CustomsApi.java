package com.chauncy.web.api.customs;

import com.alibaba.fastjson.JSON;
import com.chauncy.common.enums.order.CustomsStatusEnum;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.domain.po.order.CustomsDataPo;
import com.chauncy.data.haiguan.HaiGuanApi;
import com.chauncy.data.haiguan.vo.CustomsDataWithMyId;
import com.chauncy.data.haiguan.vo.HgCheckVO;
import com.chauncy.data.haiguan.vo.ReturnCustomsVo;
import com.chauncy.order.customs.ICustomsDataService;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 海关验证
 *
 * @version 1.0
 **/
@Controller
@RequestMapping("/custom")
public class CustomsApi {

    Logger log = LoggerFactory.getLogger(CustomsApi.class);

    @Autowired
    private ICustomsDataService customsDataService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 海关请求传递值
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/platDataOpen")
    @ResponseBody
    @ApiOperation("海关请求")
    public ReturnCustomsVo platDataOpen(@RequestParam(value = "openReq") String openReq) {

        openReq = openReq.replaceAll("&quot;", "\"");

        HaiGuanApi haiGuanApi = JSON.parseObject(openReq, HaiGuanApi.class);
        if (haiGuanApi.getOrderNo() == null || haiGuanApi.getServiceTime() == null || haiGuanApi.getSessionID() == null) {
            return new ReturnCustomsVo("20000","参数错误",System.currentTimeMillis());
        }
        //保存海关数据
        CustomsDataPo saveCustoms = new CustomsDataPo();
        saveCustoms.setOrderId(haiGuanApi.getOrderNo()).setSessionId(haiGuanApi.getSessionID())
                .setCustomsStatus(CustomsStatusEnum.NEED_SEND);
        customsDataService.save(saveCustoms);
        //haiGuanApi.putPostMsg(haiGuanApi.getOrderNo());

        return new ReturnCustomsVo("10000","",Long.valueOf(System.currentTimeMillis()));
    }

    /**
     * 海关请求传递值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/lunxun")
    synchronized protected void lunxun(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        HaiGuanApi api = new HaiGuanApi();
        response.setContentType("text/html;charset=utf-8");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        CustomsDataWithMyId customsDataWithMyId = customsDataService.getHgCheckVo(null);

        try {

            writer = response.getWriter();
            if (customsDataWithMyId != null) {
                HgCheckVO hgCheckVO = customsDataWithMyId.getHgCheckVO();
                Map<String, String> map1 = Maps.newHashMap();
                map1.put(customsDataWithMyId.getCustomsDataId(), hgCheckVO.apptenBufferUtils());
                LoggerUtil.info("海关加签参数：" + hgCheckVO.apptenBufferUtils());
                //设置已轮询过
                CustomsDataPo updateCustomsData=new CustomsDataPo();
                updateCustomsData.setId(Long.parseLong(customsDataWithMyId.getCustomsDataId())).setCustomsStatus(CustomsStatusEnum.SUCCESS);
                customsDataService.updateById(updateCustomsData);
                writer.write(JSONObject.fromObject(map1).toString());

            } else {
                writer.write("{\"error\":20000}");

            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            writer.print(api.responseServiceError());
        } finally {

            writer.close();
        }


    }

    /**
     * 海关请求传递值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/callback")
    synchronized protected void callbacks(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        HaiGuanApi api = new HaiGuanApi();
        String asin = request.getParameter("asin");
        String orderNo = request.getParameter("orderNo");
        System.out.println("orderNo:" + orderNo);
        System.out.println("asin:" + asin);

        response.setContentType("text/html;charset=utf-8");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        CustomsDataWithMyId customsDataWithMyId = customsDataService.getHgCheckVo(orderNo);
        HgCheckVO hgCheckVO = customsDataWithMyId.getHgCheckVO();

       String url = "https://swapptest.singlewindow.cn/ceb2grab/grab/realTimeDataUpload";
        //String url = "https://customs.chinaport.gov.cn/ceb2grab/grab/realTimeDataUpload";


        try {

            if (null != hgCheckVO) {
                response.setContentType("text/html;charset=utf-8");

                writer = response.getWriter();
                hgCheckVO.setSignValue(asin);
                LoggerUtil.info("发送海关请求参数："+JSON.toJSONString(hgCheckVO));
                HttpHeaders headers = new HttpHeaders();
                //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
                MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
                //  也支持中文
                params.add("payExInfoStr", JSON.toJSONString(hgCheckVO));
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity , String.class );

                //保存上传结果
                CustomsDataPo updateCustom=new CustomsDataPo();
                updateCustom.setId(Long.parseLong(orderNo)).setResponseBody(responseEntity.getBody());
                customsDataService.updateById(updateCustom);

                System.out.println("发送海关处理结果：" + responseEntity.getBody());
                writer.write(responseEntity.getBody());
            } else {
                writer.write("{\"state\":20000}");
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            LoggerUtil.error(e);
            writer.print(api.responseServiceError());
        } finally {
            if (writer!=null){
                writer.close();
            }
        }

    }


    /**
     * 订单查询
     *
     * @param request demo
     * @param filter
     * @param map
     * @return
     */
//    @RequestMapping(value = "/test")
//    public void realTimeDataUpload(HttpServletRequest request, HttpServletResponse response, OrderFilter filter, ModelMap map) {
//
//
//        Long serverTime = 1551921363030L;//System.currentTimeMillis() ;
//        HaiGuanApi aip = new HaiGuanApi();
//
//        payExchangeInfoHead payHead = new payExchangeInfoHead();
//
//        JSONObject pa = new JSONObject();
//        List<String> arrList = new ArrayList<String>();
//
//        payExchangeInfoHead payhead = new payExchangeInfoHead();
//        payExchangeInfoLists payLists = new payExchangeInfoLists();
//        //等待替换
//        String asin = "B1jWEU9jI2Q1jtiiGA9xdVaZQ/mkA4ooljDRnzbnbSHRZljp/n5WP+I4u0n1N3wFNLx9ttEzyLwg9OXOyXCWUKukm7dLSeEAFZ3AeJbUNgQV1iONpq9GN9mEP4LVQn3hhO7bzmHXsWwBVWseEx4Jfwvpqy9/fWgJlDIDJCrXlwo=";
//        pa.put("sessionID", "sdfdgh-asfsgdf-2ss");//no.getHaiguanSessionId().toLowerCase());
//        pa.put("payExchangeInfoHead", payhead.demo().toString());
//        arrList.add(payLists.demo().toString());
//        pa.put("payExchangeInfoLists", arrList);
// 	      /*  pa.put("payExchangeInfoHead", payhead.toString());
// 	        pa.put("payExchangeInfoLists", payLists.toString());*/
//        pa.put("serviceTime", "" + serverTime);
//        // pa.put("serviceTime", serviceTime) ;
//        pa.put("certNo", "01251197");
//        pa.put("signValue", asin);
//        //   pa.put("signValue","");
//
//        String apptenBufferUtils = aip.apptenBufferUtils(pa);
//        try {
//
//            System.out.println("待加签文本：" + apptenBufferUtils);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//        }
//        System.out.println("panram:" + pa.toString());
//        HttpsPostUtil post = new HttpsPostUtil();
//        PrintWriter writer = null;
//
//        try {
//            String sendDate = post.sendDate(pa);
//            response.setContentType("text/html;charset=utf-8");
//
//            writer = response.getWriter();
//            writer.print(sendDate);
//        } catch (Exception e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } finally {
//            writer.close();
//
//        }
//
//
//    }
//    //*****test  demo  ****//
//
//    public static void main(String[] args) throws Exception {
//        String asin = "B1jWEU9jI2Q1jtiiGA9xdVaZQ/mkA4ooljDRnzbnbSHRZljp/n5WP+I4u0n1N3wFNLx9ttEzyLwg9OXOyXCWUKukm7dLSeEAFZ3AeJbUNgQV1iONpq9GN9mEP4LVQn3hhO7bzmHXsWwBVWseEx4Jfwvpqy9/fWgJlDIDJCrXlwo=";
//        String postContent = "{\"sessionID\":\"sdfdgh-asfsgdf-24575\",\"payExchangeInfoHead\":{\"guid\":\"D690AA0E-CD34-48CB-ACC1-BF485168D2B0\",\"initalRequest\":\"test\",\"initalResponse\":\"test\",\"ebpCode\":\"xxxxx\",\"payCode\":\"3xxxxxx\"payTransactionId\":\"2019022622001444361020658513\",\"totalAmount\":348,\"currency\":\"142\",\"verDept\":\"3\",\"payType\":\"1\",\"tradingTime\":\"20190226145248\",\"note\":\"test\"},\"payExchangeInfoLists\":[{\"orderNo\":\"SC201902260001\",\"goodsInfo\":[{\"gname\":\"test\",\"itemLink\":\"test\"}],\"recpAccount\":\"9440xxxxxxx92\",\"recpCode\":\"914xxxxxxxxx2L\",\"recpName\":\"xxxxxxx电子商务有限公司\"}],\"serviceTime\":\"1921245687523\",\"certNo\":\"01251197\",\"signValue\":\"" + asin + "\"}";
//        HttpsPostUtil post = new HttpsPostUtil();
//        post.sendDate(postContent);
//
//
//    }
}