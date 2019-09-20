//package com.chauncy.web.api.customs;
//
//import com.alibaba.fastjson.JSON;
//import com.chauncy.data.haiguan.HaiGuanApi;
//import net.sf.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import test.haiguan.payExchangeInfoHead;
//import test.haiguan.payExchangeInfoLists;
//import test.http.HttpsPostUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * 海关验证
// *
// * @version 1.0
// **/
//@RestController("/custom")
//public class CustomsApi {
//
//    Logger log = LoggerFactory.getLogger(CustomsApi.class);
//
//    /**
//     * 海关请求传递值
//     *
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST, value = "/platDataOpen", headers = "content-type=application/x-www-form-urlencoded")
//    public Map<String, Object> platDataOpen(@RequestParam(value = "openReq") String openReq) {
//
//        openReq = openReq.replaceAll("&quot;", "\"");
//
//        HaiGuanApi haiGuanApi = JSON.parseObject(openReq, HaiGuanApi.class);
//        if (haiGuanApi.getOrderNo()==null||haiGuanApi.getServiceTime()==null||haiGuanApi.getSessionID()==null){
//            return haiGuanApi.responseServiceError();
//        }
//        haiGuanApi.putPostMsg(haiGuanApi.getOrderNo());
//
//        return haiGuanApi.responseServiceSuccess();
//    }
//
//    /**
//     * 海关请求传递值
//     *
//     * @param request
//     * @param filter
//     * @param map
//     * @return
//     */
//    @RequestMapping(value = "custom/lunxun")
//    synchronized protected void lunxun(HttpServletRequest request, HttpServletResponse response, OrderFilter filter, ModelMap map) {
//        PrintWriter writer = null;
//        HaiGuanApi api = new HaiGuanApi();
//        JSONObject json = new JSONObject();
//        response.setContentType("text/html;charset=utf-8");
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Cache-Control", "no-cache");
//        try {
//
//            writer = response.getWriter();
//            if (null != api.MAP_HAI && api.MAP_HAI.size() > 0) {
//                System.out.println(api.MAP_HAI.size());
//                writer.write(JSONObject.fromObject(api.MAP_HAI).toString());
//
//            } else {
//                writer.write("{\"error\":20000}");
//
//            }
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            writer.print(api.responseServiceError());
//        } finally {
//
//            writer.close();
//        }
//
//    }
//
//    /**
//     * 海关请求传递值
//     *
//     * @param request
//     * @param filter
//     * @param map
//     * @return
//     */
//    @RequestMapping(value = "custom/callback")
//    synchronized protected void callbacks(HttpServletRequest request, HttpServletResponse response, OrderFilter filter, ModelMap map) {
//        PrintWriter writer = null;
//        HaiGuanApi api = new HaiGuanApi();
//        String asin = request.getParameter("asin");
//        String orderNo = request.getParameter("orderNo");
//        System.out.println("orderNo" + orderNo);
//        System.out.println("asin:" + asin);
//
//        response.setContentType("text/html;charset=utf-8");
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Cache-Control", "no-cache");
//        HttpsPostUtil post = new HttpsPostUtil();
//        HaiGuanApi.MAP_VALUE.get(orderNo).put("signValue", asin);
//
//        try {
//
//            if (null != api.MAP_HAI.get(orderNo)) {
//                String sendDate = post.sendDate(HaiGuanApi.MAP_VALUE.get(orderNo));
//                response.setContentType("text/html;charset=utf-8");
//
//                writer = response.getWriter();
//                writer.print(sendDate);
//
//
//                System.out.println("发送海关处理结果：" + sendDate);
//                writer.write(sendDate);
//            } else {
//                writer.write("{\"state\":20000}");
//            }
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            writer.print(api.responseServiceError());
//        } finally {
//            api.removeMapOrderNo(orderNo);
//            writer.close();
//        }
//
//    }
//
//
//    /**
//     * 订单查询
//     *
//     * @param request demo
//     * @param filter
//     * @param map
//     * @return
//     */
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
//}