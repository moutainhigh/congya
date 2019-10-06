package com.chauncy.data.dto.app.activity;

import com.chauncy.data.dto.base.BasePageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/10/6 17:52
 */
@Data
@ApiModel(value = "SearchSpellGroupInfoDto对象", description = "根据商品查询拼团信息")
public class SearchSpellGroupInfoDto  extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "goodsId参数错误，不能为0")
    @ApiModelProperty(value = "商品id   \n")
    @NotNull(message = "goodsId参数不能为空")
    private Long goodsId;

}

