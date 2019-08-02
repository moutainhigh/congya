package com.chauncy.data.vo.app.order.logistics;

import com.chauncy.data.bo.app.logistics.LogisticsDataBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-02 13:38
 *
 * 根据订单号查询物流信息
 */
@Data
@ApiModel(description = "根据订单号查询物流信息")
@Accessors(chain = true)
public class FindLogicDetailVo {

    @ApiModelProperty("收货人手机号")
    private String receiveTel;

    @ApiModelProperty("是否签收 0-否 1-是")
    private String isCheck;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("收货人")
    private String receiveName;

    @ApiModelProperty("快递公司的编码")
    private String expressCompanyCode;

    @ApiModelProperty("快递公司名称")
    private String expressCompanyName;

    @ApiModelProperty("运单号")
    private String logisticsNo;

    @ApiModelProperty("快递单当前状态")
    private String statusName;

    @ApiModelProperty("快递单当前状态码")
    private String statusCode;

    @ApiModelProperty("物流节点信息")
    private List<LogisticsDataBo> logisticsData;
}
