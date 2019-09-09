package com.chauncy.data.vo.supplier.good.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/9 18:07
 */
@Data
@ApiModel(description = "商品库存模板信息")
public class GoodsStockTemplateVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模板id")
    private Long id;

    @ApiModelProperty("库存模板名称")
    private String name;

    @ApiModelProperty(value = "商品类型 1->自有商品  2->分配商品")
    private Integer type;

    @ApiModelProperty(value = "商品类型 1->自有商品  2->分配商品")
    private String typeName;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "库存模板对应的商品信息")
    private PageInfo<StockTemplateGoodsInfoVo> stockTemplateGoodsInfoPageInfo;
}
