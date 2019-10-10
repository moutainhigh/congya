package com.chauncy.data.dto.app.product;

import com.chauncy.data.dto.base.BasePageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/10/8 20:52
 */
@Data
@ApiModel(value = "SearchSeckillGoodsDto对象", description = "秒杀活动商品列表参数")
public class SearchSeckillGoodsDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "categoryId参数错误，不能为0")
    @ApiModelProperty(value = "商品分类id，选择全部不传参数    \n")
    private Long categoryId;

    @ApiModelProperty(value = "活动开始时间    \n")
    @NotNull(message = "activityStartTime参数不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartTime;

}