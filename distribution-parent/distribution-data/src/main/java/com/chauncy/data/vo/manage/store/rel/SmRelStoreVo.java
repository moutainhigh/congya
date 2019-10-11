package com.chauncy.data.vo.manage.store.rel;

import com.chauncy.common.util.serializer.LongJsonDeserializer;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺绑定的其他的铺信息
 * @author yeJH
 * @since 2019/7/8 16:47
 */
@Data
@ApiModel(value = "店铺绑定的其他的铺信息")
public class SmRelStoreVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "关联id")
    private Long id;

    @ApiModelProperty(value = "所属店铺名称")
    private String storeName;

    @ApiModelProperty(value = "所属店铺id")
    private String storeId;

    @ApiModelProperty(value = "业务关系")
    private String typeName;

    @ApiModelProperty(value = "业务关系")
    private String typeId;

}
