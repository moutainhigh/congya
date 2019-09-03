package com.chauncy.data.vo.manage.order.report;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/8/11 18:18
 */
@Data
@ApiModel(value = "商品销售报表基本信息")
public class ReportBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报表id")
    private Long id;

    @ApiModelProperty(value = "直属商家")
    private String storeName;

    @ApiModelProperty(value = "分配商家")
    private String branchName;

    @ApiModelProperty(value = "总供货价金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "总商品数量")
    private Integer totalNum;

    @ApiModelProperty(value = "生成时间")
    private LocalDateTime createTime;

    /*@ApiModelProperty(value = "商品信息")
    private PageInfo<ReportRelGoodsTempVo> reportRelGoodsTempVoPageInfo;*/

}
