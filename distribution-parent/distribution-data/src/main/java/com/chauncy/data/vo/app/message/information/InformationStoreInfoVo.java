package com.chauncy.data.vo.app.message.information;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/8/22 12:14
 */
@Data
@ApiModel(value = "InformationStoreInfoVo", description =  "资讯列表店铺信息")
public class InformationStoreInfoVo  implements Serializable {

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

    @ApiModelProperty(value = "收藏量/粉丝数")
    private Integer collectionNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likedSum;

    @ApiModelProperty(value = "是否关注")
    private Boolean isFocus;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;


}
