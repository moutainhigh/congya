package com.chauncy.data.dto.supplier.store.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yeJH
 * @since 2019/6/23 1:02
 */
@Data
@ApiModel(value = "StoreBusinessLicenseDto对象", description = "店铺经营资质证书")
public class StoreBusinessLicenseDto {

    @ApiModelProperty(value = "value")
    @NotNull(message = "店铺ID不能为空")
    @NeedExistConstraint(tableName = "sm_store")
    private Long id;

    @ApiModelProperty(value = "资质证书图片")
    @NotBlank(message = "图片不能为空")
    private String imgUrl;


}
