package com.chauncy.data.vo.supplier.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询分店信息
 * @author yeJH
 * @since 2019/7/10 20:19
 */
@Data
@ApiModel(value = "BranchInfoVo对象", description = "查询分店信息")
public class BranchInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "店铺名称")
    private String name;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddr;

    @ApiModelProperty(value = "联系电话")
    private String companyMobile;

}
