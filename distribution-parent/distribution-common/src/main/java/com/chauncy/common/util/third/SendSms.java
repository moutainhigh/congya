package com.chauncy.common.util.third;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.LoggerUtil;

public class SendSms {

    /*用户登录名称 congya@1514302434338046.onaliyun.com
    AccessKey ID LTAIrOQ5daZ6ArrP
    AccessKeySecret UkTtE9cNYIbThMA5QMdwZPCCIoSM3O*/

    public static void sendCode(String phoneNumbers, String validCode, String templateCode) {
        String templateParam = String.format("{\"code\":\"%s\"}", validCode);
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIrOQ5daZ6ArrP", "UkTtE9cNYIbThMA5QMdwZPCCIoSM3O");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", "葱鸭百货");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            LoggerUtil.info(response.getData());
        } catch (ClientException e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.THIRD_FAIL, e.getLocalizedMessage());
        }
    }

    /**
     * 验证用户的短信模板是否审核通过
     *
     * @param templateCode 阿里云短信模板编码
     */
    public static void validTemplateCode(String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIrOQ5daZ6ArrP", "UkTtE9cNYIbThMA5QMdwZPCCIoSM3O");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("QuerySmsTemplate");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("TemplateCode", "SMS_174029582");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data=response.getData();
            JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
            String code = jsonObject.getString("Code");
            if ("isv.SMS_TEMPLATE_ILLEGAL".equalsIgnoreCase(code)){
                throw new ServiceException(ResultCode.THIRD_FAIL,
                        String.format("短信模板CODE不存在:【%s】",templateCode));
            }
            else if ("OK".equalsIgnoreCase(code)){
                Integer templateStatus=jsonObject.getInteger("TemplateStatus");
                if (templateStatus==0){
                    throw new ServiceException(ResultCode.THIRD_FAIL,
                            String.format("短信模板CODE【%s】正在审核，请等待审核通过后再添加",templateCode));
                }
                else if (templateStatus==2){
                    throw new ServiceException(ResultCode.THIRD_FAIL,
                            String.format("短信模板CODE【%s】审核不通过，请重新编辑待审核通过后再添加",templateCode));
                }
            }
            else {
                throw new ServiceException(ResultCode.THIRD_FAIL,
                        String.format("短信模板CODE【%s】不合法，请检查是否存在以及审核状态",templateCode));
            }
            LoggerUtil.info("阿里云短信模板验证【%s】:%s",templateCode,response.getData());
        } catch (ClientException e) {
            throw new ServiceException(ResultCode.THIRD_FAIL, e.getLocalizedMessage());
        }
    }

    /**
     *
     * @param phones 手机号码 用字符串隔开
     */
    public static void sendContent(String phones,String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phones);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("SignName", "葱鸭百货");
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ClientException e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.THIRD_FAIL, e.getLocalizedMessage());
        }
    }
}