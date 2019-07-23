package com.chauncy.data.dto.manage.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/23 17:50
 **/

@Data
@ApiModel(description = "搜索订单列表")
@Accessors(chain = true)
public class SearchOrderDto {

    private Long orderId;
    private Long userId;
    private String phone;
    private Long storeId;
    private String storeName;

    private LocalDateTime createTime;


    private Integer orderStatus;
}
