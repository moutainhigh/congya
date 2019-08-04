package com.chauncy.web.controller.pay;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.order.pay.impl.WXserviceImpl;
import com.chauncy.security.util.IpInfoUtil;
import com.chauncy.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/7/5 16:14
 */
@Api(tags = "APP_支付管理接口")
@RestController
@RequestMapping("/app/wxPay")
@Slf4j
public class WXController {

    private final static Logger logger = LoggerFactory.getLogger(WXController.class);

    @Autowired
    private WXserviceImpl wxPayService;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 统一下单
     * 官方文档:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @return
     * @throws Exception
     */
    @PostMapping("/wxPay/{payOrderId}")
    @ApiOperation("统一下单")
    public JsonViewData wxPay(HttpServletRequest request, @PathVariable(value = "payOrderId") Long payOrderId) {

        //获取当前店铺用户
        UmUserPo umUserPo = securityUtil.getAppCurrUser();
        if(null == umUserPo) {
            throw  new ServiceException(ResultCode.NO_LOGIN, "未登陆或登陆已超时");
        }
        try {
            //请求预支付订单
            Map<String, String> returnMap = wxPayService.doUnifiedOrder(ipInfoUtil.getIpAddr(request), payOrderId);
            return new JsonViewData(ResultCode.SUCCESS, "操作成功", returnMap);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.SYSTEM_ERROR, "系统错误");
        }

    }

    /**
     *   支付异步结果通知，我们在请求预支付订单时传入的地址
     *   官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3
     */
    @RequestMapping(value = "/wxPay/notify", method = {RequestMethod.GET, RequestMethod.POST}, produces={"application/xml;"})
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = wxPayService.payBack(resXml);
            return result;
        } catch (Exception e) {
            logger.error("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }
}
