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

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
public class SendSms {

    /*用户登录名称 congya@1514302434338046.onaliyun.com
    AccessKey ID LTAIrOQ5daZ6ArrP
    AccessKeySecret UkTtE9cNYIbThMA5QMdwZPCCIoSM3O*/
    public static   void send(String phoneNumbers,String validCode,String templateCode) {
        String templateParam=String.format("{\"code\":\"%s\"}",validCode);
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIrOQ5daZ6ArrP", "UkTtE9cNYIbThMA5QMdwZPCCIoSM3O");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", "葱鸭百货");
        request.putQueryParameter("TemplateCode", "SMS_144205067");
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            LoggerUtil.info(response.getData());
        }  catch (ClientException e) {
            LoggerUtil.error(e);
            throw new ServiceException(ResultCode.FAIL,"服务器繁忙，请稍后重试！");
        }
    }
}