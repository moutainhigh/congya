package com.chauncy.data.vo.app.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/9/6 11:58
 */
@Data
@ApiModel(value = "AppStoreBaseInfoVo", description =  "店铺基本信息")
public class AppStoreBaseInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺Id")
    private Long storeId;

    @ApiModelProperty(value = "店铺logo")
    private String logoImage;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "店铺标签")
    private String storeLabels;

    @ApiModelProperty(value = "店铺标签")
    private List<String> storeLabelList;

    @ApiModelProperty(value = "是否关注")
    private Boolean isFocus;

}
