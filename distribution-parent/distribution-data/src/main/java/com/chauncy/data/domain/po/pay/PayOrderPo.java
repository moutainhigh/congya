package com.chauncy.data.domain.po.pay;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author huangwancheng
* @since 2019-07-09
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("pay_order")
    @ApiModel(value="PayOrderPo对象", description="")
    public class PayOrderPo implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String payTypeCode;

    private String payOrderNo;

    private String tradePayNo;

    private String prePayId;

    private String payId;

    private String userIp;

    private Integer payAmount;

    private LocalDateTime payTime;

    private Integer status;

    private String errorCode;

    private String errorMsg;

    private LocalDateTime startTime;

    private LocalDateTime expireTime;

    private String openId;

    private String buyerLogonId;

    private String notifyUrl;

    private String extra;

    private String subject;

    private String detail;

    private String codeUrl;

    private String merchantId;

    private String tradeType;

    private String returnUrl;

    private Integer refundAmount;


}
