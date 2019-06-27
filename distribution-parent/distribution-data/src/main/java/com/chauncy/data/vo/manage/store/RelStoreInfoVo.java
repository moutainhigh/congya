package com.chauncy.data.vo.manage.store;

import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/24 10:52
 */
@Data
@ApiModel(value = "店铺可关联的店铺列表")
public class RelStoreInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "value")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "公司名称")
    private String companyName;


}
