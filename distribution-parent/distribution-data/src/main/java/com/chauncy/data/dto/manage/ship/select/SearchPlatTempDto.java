package com.chauncy.data.dto.manage.ship.select;

import com.chauncy.common.enums.goods.GoodsShipTemplateEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-06-24 17:35
 *
 * 条件查询平台运费模版字段
 */
@Data
@ApiModel(description = "条件查询平台运费模版字段")
@Accessors
public class SearchPlatTempDto {

    @ApiModelProperty(value = "运费模版类型 1--平台运费模版。2--商家运费模版")
    @NotNull(message = "运费模版类型不能为空")
    @EnumConstraint(target = GoodsShipTemplateEnum.class)
    private Integer type;

    @ApiModelProperty(value = "运费模版ID")
    private Long id;

    @ApiModelProperty(value = "运费模版名称")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "提交时间")
    private LocalDate submitTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核时间")
    private LocalDate verifyTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private  String storeName;

    @ApiModelProperty(value = "审核状态")
    private Integer verifyStatus;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
