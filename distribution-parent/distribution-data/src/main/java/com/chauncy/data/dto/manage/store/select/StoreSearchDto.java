package com.chauncy.data.dto.manage.store.select;

import com.chauncy.common.enums.store.StoreTypeEnum;
import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xiaoye
 * @Date: 2019/6/12 10:14
 */
@Data
@ApiModel(value = "StoreSearchDto对象", description = "查找平台店铺参数")
public class StoreSearchDto extends BaseSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺id")
    private Long id;

    @ApiModelProperty(value = "店铺主理人手机号")
    private String mobile;


    @ApiModelProperty(value = "店铺类型")
    @EnumConstraint(target = StoreTypeEnum.class)
    private Integer type;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "店铺经营状态")
    private Boolean enabled;


}
