package com.chauncy.data.vo.manage.store.label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/8/8 11:10
 */
@Data
@ApiModel(value = "SmStoreRelLabelVo", description = "店铺标签信息")
public class SmStoreRelLabelVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺标签id")
    private Long id;

    @ApiModelProperty(value = "店铺标签")
    private String name;


}
