//package com.chauncy.data.haiguan;
//
//
//import com.google.common.collect.Maps;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//
///**
// * 接收海关发来的消息
// *
// * @author 你的爷爷 bug
// */
//public class HaiGuanApi {
//    private String orderNo;
//    private String sessionID;
//    private String serviceTime;
//
//    public static Map<String, String> MAP_HAI = new ConcurrentHashMap();
//
//    public static Map<String, JSONObject> MAP_VALUE = new ConcurrentHashMap();
//
//    public synchronized void removeMapOrderNo(String orderNo) {
//        MAP_HAI.remove(orderNo);
//        MAP_VALUE.remove(orderNo);
//    }
//
//    public synchronized void setMapOrderNo(String key, String haiValue, JSONObject asin) {
//        MAP_HAI.put(key, haiValue);
//        MAP_VALUE.put(key, asin);
//    }
//
//    public HaiGuanApi() {
//
//
//    }
//
//    public Map<String, Object> responseServiceSuccess() {
//
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("code", "10000");
//        map.put("message", "");
//        map.put("serviceTime", System.currentTimeMillis());
//
//        return map;
//
//    }
//
//    public Map<String, Object> responseServiceError() {
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("code", "20000");
//        map.put("message", "parms is null.");
//        map.put("serviceTime", System.currentTimeMillis());
//
//        return map;
//    }
//
//
//    /**
//     * 海关加签方法
//     * <p>
//     * sessionID，
//     * payExchangeInfoHead，
//     * payExchangeInfoLists，
//     * serviceTime
//     *
//     * @return
//     */
//    public String apptenBufferUtils(JSONObject parmar) {
//
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("\"sessionID\":\"" + parmar.get("sessionID") + "\"");
//        buffer.append("||");
//        buffer.append("\"payExchangeInfoHead\":\"" + parmar.get("payExchangeInfoHead") + "\"");
//        buffer.append("||");
//        buffer.append("\"payExchangeInfoLists\":\"" + parmar.get("payExchangeInfoLists") + "\"");
//        buffer.append("||");
//        buffer.append("\"serviceTime\":\"" + parmar.get("serviceTime") + "\"");
//
//        return buffer.toString();
//    }
//
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public String getSessionID() {
//        return sessionID;
//    }
//
//    public void setSessionID(String sessionID) {
//        this.sessionID = sessionID;
//    }
//
//    public String getServiceTime() {
//        return serviceTime;
//    }
//
//    public void setServiceTime(String serviceTime) {
//        this.serviceTime = serviceTime;
//    }
//
//    //推荐传个 对象 order 方便一点  直接处理
//    public boolean putPostMsg(String orderNo) {
//        try {
//
//            HaiGuanApi api = new HaiGuanApi();
//            //自己获取订单信息 组装 提交的bean  papa
//            Order orderPay = new getOrder(orderNo)；
//
//            JSONObject formBean = new JSONObject();
//            List<String> arrList = new ArrayList<String>();
//
//            payExchangeInfoHead payhead = new payExchangeInfoHead();
//
//
//            AlipaySearchOrder pay = new AlipaySearchOrder();
//            Map<String, String> resposneRequesHeader = pay.resposneRequesHeader(orderNo, orderPay.getPayNo());
//            List<Orderdetail> details = orderPay.getDetails();
//
//            //组装商品信息 随意
//            for (int i = 0; i < details.size(); i++) {
//                payExchangeInfoLists payLists = new payExchangeInfoLists();
//                Product product = details.get(i).getProduct();
//
//                String url = details.get(i).getProduct().getImgUrl();
//                JSONArray jsonarr = new JSONArray();
//                JSONObject o1 = new JSONObject();
//                o1.put("gname", product.getProductName());
//                o1.put("itemLink", product.getRemark());
//                jsonarr.add(o1);
//                payLists.setGoodsInfo(jsonarr);
//                payLists.setOrderNo(orderPay.getOrderCode());
//                arrList.add(payLists.toString());
//            }
//            payhead.setInitalRequest(resposneRequesHeader.get("request").toString());
//            payhead.setInitalResponse(resposneRequesHeader.get("response").toString());
//            payhead.setCurrency("142");
//            payhead.setPayTransactionId(orderPay.getPayNo());
//            payhead.setNote("");
//            payhead.setVerDept("3");
//            payhead.setPayType("1");
//            BigDecimal totalMoey = new BigDecimal(orderPay.getTotalPrice());
//            payhead.setTotalAmount(totalMoey);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//            String formatdate = sdf.format(orderPay.getOrderedTime());
//            payhead.setTradingTime(formatdate);
//            //等待替换
//            formBean.put("sessionID", this.sessionID);//no.getHaiguanSessionId().toLowerCase());
//            formBean.put("payExchangeInfoHead", payhead.toString());
//
//            formBean.put("payExchangeInfoLists", arrList);
//		      /*  pa.put("payExchangeInfoHead", payhead.toString());
//		        pa.put("payExchangeInfoLists", payLists.toString());*/
//            formBean.put("serviceTime", this.serviceTime);
//            // pa.put("serviceTime", serviceTime) ;
//            formBean.put("certNo", "01251197");
//
//            formBean.put("signValue", "");
//
//            String apptenBufferUtils = api.apptenBufferUtils(formBean);
//
//            api.MAP_HAI.put(orderPay.getPayNo(), apptenBufferUtils);
//            api.MAP_VALUE.put(orderPay.getPayNo(), formBean);
//            System.out.println("待加签数据：" + apptenBufferUtils);
//            System.out.println("待提交数据：" + formBean.toString());
//            return true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public static void main(String[] args) {
//        String no = "SC123564DEPT1021";
//
//        String dept = no.substring(no.indexOf("DEPT"));
//        String orderCode = no.substring(0, no.indexOf("DEPT"));
//        Long id = Long.parseLong(dept.replaceAll("DEPT", ""));
//        System.out.println(orderCode);
//        System.out.println(dept);
//        System.out.println(id);
//    }
//}
