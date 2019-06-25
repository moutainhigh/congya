package com.chauncy.data.dto.manage.ship.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-25 21:50
 *
 * 批量修改模版的使用状态
 */
@Data
@ApiModel("批量修改模版的使用状态")
@Accessors(chain = true)
public class EnableTemplateDto {

    @ApiModelProperty("模版ID集合")
    @NeedExistConstraint(tableName = "pm_shipping_template")
    private Long[] ids;

    @ApiModelProperty("启用状态 0-禁用 1-启用")
    private Boolean enable;
}
