package com.chauncy.data.vo.manage.message.advice.tab.association;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-17 15:27
 *
 * 店铺基本信息Vo,只包含ID和name
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "店铺基本信息Vo,只包含ID和name")
public class StoreVo {

    @ApiModelProperty("店铺ID")
    @JSONField(ordinal = 12)
    private Long storeId;

    @ApiModelProperty("店铺名称")
    @JSONField(ordinal = 13)
    private String storeName;
}
