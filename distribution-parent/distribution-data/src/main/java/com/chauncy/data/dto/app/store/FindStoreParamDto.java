package com.chauncy.data.dto.app.store;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yeJH
 * @description: 查询店铺的筛选参数
 * @since 2019/10/17 14:28
 */
@Data
@ApiModel(value = "GetStoreParamDto对象", description = "查询店铺的筛选参数")
public class FindStoreParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模糊搜索关键字")
    private String keyword;

    @JsonIgnore
    @JSONField(serialize=false)
    @ApiModelProperty(value = "用户id 获取用户关注的店铺资讯")
    private Long userId;

    @JsonIgnore
    @ApiModelProperty(value = "店铺下商品展示的数量，默认3")
    private Integer goodsNum;

}
