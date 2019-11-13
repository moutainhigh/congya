package com.chauncy.data.bo.app.order.rabbit;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * * 如果修改时间和售后状态不一致，不进行延时任务
 * @Author zhangrt
 * @Date 2019/9/6 23:00
 **/
@Data
@Accessors(chain = true)
@ToString
public class RabbitAfterBo {

    private Long afterSaleOrderId;


    private AfterSaleStatusEnum afterSaleStatusEnum;
    /**
     * 如果修改时间不一致，不进行延时任务
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime messageCreateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime messageExpireTime;
}
