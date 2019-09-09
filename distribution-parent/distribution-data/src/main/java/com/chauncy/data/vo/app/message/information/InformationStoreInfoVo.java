package com.chauncy.data.vo.app.message.information;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.vo.app.store.AppStoreBaseInfoVo;
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
public class InformationStoreInfoVo extends AppStoreBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏量/粉丝数")
    private Integer collectionNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likedSum;

    @ApiModelProperty(value = "店铺描述")
    private String storeDescribe;


}
