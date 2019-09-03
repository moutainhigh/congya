package com.chauncy.data.vo.manage.order.afterSale;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleLogEnum;
import com.chauncy.common.enums.app.order.afterSale.AfterSaleStatusEnum;
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

    @ApiModelProperty(value = "操作 1-待审核 2-处理中 3-售后成功 4-售后失败")
    private AfterSaleLogEnum operate;

    @ApiModelProperty(value = "审核信息")
    private String describe;

    @ApiModelProperty(value = "操作时间 ")
    private LocalDateTime createTime;


}
