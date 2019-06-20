package com.chauncy.data.dto.manage.store.select;

import com.chauncy.common.enums.store.StoreTypeEnum;
import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xiaoye
 * @Date: 2019/6/12 10:14
 */
@Data
public class StoreSearchDto extends BaseSearchDto {


    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
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
