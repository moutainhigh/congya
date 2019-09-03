package com.chauncy.data.vo.manage.order.afterSale;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleLogEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/9/1 22:54
 **/
@ApiModel(description = "售后进度")
@Data
public class AfterSaleLogVo {

    @ApiModelProperty("操作节点")
    private AfterSaleLogEnum node;

    @ApiModelProperty(value = "审核信息")
    private String describe;

    @ApiModelProperty(value = "操作时间 ")
    private LocalDateTime createTime;


}
